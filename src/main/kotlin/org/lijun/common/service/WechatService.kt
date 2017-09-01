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

package org.lijun.common.service

import org.lijun.common.exception.WechatException
import org.lijun.common.wechat.vo.AccessToken
import org.lijun.common.wechat.vo.UserInfoList
import org.lijun.common.wechat.vo.WechatUserInfo

/**
 * Service - WechatService
 *
 * @author lijun
 */
interface WechatService {

    /**
     * 获取access_token
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun getAccessToken(): AccessToken

    /**
     * 刷新access_token
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun refreshAccessToken()

    /**
     * 抓取用户信息
     * @param openid
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun fetchUserInfo(openid: String): WechatUserInfo

    /**
     * 获取jsapi_ticket
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun getJsApiTicket(): String

    /**
     * 刷新jsapi_ticket
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun refreshJsApiTicket()

    /**
     * 通过网页授权获得openid
     * @param code
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun getOAuthOpenId(code: String): String?

    /**
     * 批量抓取用户信息
     * @param openIds
     * @return
     * @throws WechatException
     */
    @Throws(WechatException::class)
    fun batchFetchUserInfo(openIds: List<String>): UserInfoList

}