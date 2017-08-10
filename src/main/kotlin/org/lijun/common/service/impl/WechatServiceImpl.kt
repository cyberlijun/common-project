/*
 * Copyright 2006-2017 the original author or authors.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lijun.common.service.impl

import org.apache.commons.collections.MapUtils
import org.apache.commons.lang3.StringUtils
import org.lijun.common.service.WechatService
import org.lijun.common.support.HttpResponseWrapper
import org.lijun.common.util.HttpUtils
import org.lijun.common.wechat.util.WechatApiUrls
import org.lijun.common.wechat.vo.WechatUserInfo
import org.lijun.common.util.JsonUtils
import org.lijun.common.exception.WechatException
import org.lijun.common.enums.WechatErrorType
import org.lijun.common.exception.WechatErrorDetail
import org.lijun.common.util.EmojiUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.lijun.common.wechat.context.AccessTokenContext
import org.lijun.common.wechat.vo.AccessToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import org.lijun.common.wechat.context.JsApiTicketContext
import org.springframework.scheduling.annotation.Scheduled

/**
 * Service - WechatServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("wechatService")
open class WechatServiceImpl : WechatService {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * 获取access_token
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    override fun getAccessToken(): AccessToken {
        val response: HttpResponseWrapper<String> = HttpUtils.get(WechatApiUrls.getAccessTokenUrl())

        if (response.isSuccess() && StringUtils.isNotBlank(response.content)) {
            if (StringUtils.contains(response.content, "errcode")) {
                val wechatError: WechatErrorDetail = JsonUtils.fromJson(response.content!!)

                if (!StringUtils.equals("0", wechatError.errorCode)) {
                    wechatError.errorType = WechatErrorType.GET_ACCESS_TOKEN_ERROR

                    throw WechatException(wechatError)
                }
            } else {
                return JsonUtils.fromJson(response.content!!)
            }
        }

        throw WechatException(WechatErrorDetail.createSystemError("获取access_token时发生错误！"))
    }

    /**
     * 刷新access_token
     * @throws WechatException
     */
    @Throws(WechatException::class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Scheduled(cron = "0 0 1/1 * * ? *")
    override fun refreshAccessToken() {
        logger.info("开始刷新access_token...")

        val accessToken: AccessToken = this.getAccessToken()

        logger.info("成功获取到新的access_token：{}", accessToken.accessToken)

        AccessTokenContext.set(accessToken)

        logger.info("结束刷新access_token...")
    }

    /**
     * 抓取用户信息
     * @param openid
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    override fun fetchUserInfo(openid: String): WechatUserInfo {
        val response: HttpResponseWrapper<String> = HttpUtils.get(WechatApiUrls.getFetchUserInfoUrl(openid))

        if (response.isSuccess() && StringUtils.isNotBlank(response.content)) {
            if (StringUtils.contains(response.content, "errcode")) {
                val wechatError: WechatErrorDetail = JsonUtils.fromJson(response.content!!)

                wechatError.errorType = WechatErrorType.FETCH_USER_INFO_ERROR

                throw WechatException(wechatError)
            } else {
                val map: Map<String, Any> = JsonUtils.fromJson(response.content!!)

                val user: WechatUserInfo = WechatUserInfo()

                user.subscribe = 0 != MapUtils.getIntValue(map, "subscribe")
                user.openid = openid
                user.nickName = EmojiUtils.parseToHtmlDecimal(MapUtils.getString(map, "nickname", StringUtils.EMPTY))
                user.gender = MapUtils.getString(map, "sex")
                user.city = MapUtils.getString(map, "city")
                user.country = MapUtils.getString(map, "country")
                user.province = MapUtils.getString(map, "province")
                user.language = MapUtils.getString(map, "language")
                user.headImgUrl = MapUtils.getString(map, "headimgurl")

                when {
                    StringUtils.isNotBlank(MapUtils.getString(map, "subscribe_time")) -> {
                        val ts: Long = MapUtils.getLong(map, "subscribe_time")

                        user.subscribeTime = Date(ts * 1000)
                    }
                    else -> user.subscribeTime = Date()
                }

                user.unionId = MapUtils.getString(map, "unionid")
                user.remark = MapUtils.getString(map, "remark")
                user.groupId = MapUtils.getLong(map, "groupid")
                user.tagidList = MapUtils.getString(map, "tagid_list")

                return user
            }
        }

        throw WechatException(WechatErrorDetail.createSystemError("获取用户基本信息时发生错误！"))
    }

    /**
     * 获取jsapi_ticket
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    override fun getJsApiTicket(): String {
        val response: HttpResponseWrapper<String> = HttpUtils.get(WechatApiUrls.getJsApiTicketUrl())

        if (response.isSuccess() && StringUtils.isNotBlank(response.content)) {
            val map: Map<String, Any> = JsonUtils.fromJson(response.content!!)

            val errcode: String = MapUtils.getString(map, "errcode")

            if (!StringUtils.equals("0", errcode)) {
                val errmsg: String = MapUtils.getString(map, "errmsg")

                val wechatError = WechatErrorDetail(WechatErrorType.GET_JS_API_TICKET_ERROR, errcode, errmsg)

                throw WechatException(wechatError)
            } else {
                val ticket: String = MapUtils.getString(map, "ticket")

                return ticket
            }
        }

        throw WechatException(WechatErrorDetail.createSystemError("获取jsapi_ticket时发生错误！"))
    }

    /**
     * 刷新jsapi_ticket
     * @throws WechatException
     */
    @Throws(WechatException::class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Scheduled(cron = "0 30 1/1 * * ? ")
    override fun refreshJsApiTicket() {
        logger.info("开始刷新jsapi_ticket...")

        val ticket: String = this.getJsApiTicket()

        logger.info("成功获取到新的jsapi_ticket：{}", ticket)

        JsApiTicketContext.set(ticket)

        logger.info("结束刷新jsapi_ticket...")
    }

    /**
     * 通过网页授权获得openid
     * @param code
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    override fun getOAuthOpenId(code: String): String {
        val response: HttpResponseWrapper<String> = HttpUtils.get(WechatApiUrls.getOAuthAccessTokenUrl(code))

        if (response.isSuccess() && StringUtils.isNotBlank(response.content)) {
            if (StringUtils.contains(response.content, "errcode")) {
                val wechatError: WechatErrorDetail = JsonUtils.fromJson(response.content!!)

                wechatError.errorType = WechatErrorType.GET_OAUTH_ACCESS_TOKEN_ERROR

                throw WechatException(wechatError)
            } else {
                val map: Map<String, Any> = JsonUtils.fromJson(response.content!!)

                return MapUtils.getString(map, "openid")
            }
        }

        throw WechatException(WechatErrorDetail.createSystemError("通过code换取网页授权access_token时发生错误！"))
    }
}