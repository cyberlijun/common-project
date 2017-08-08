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

package org.lijun.common.wechat.util

import org.lijun.common.wechat.context.AccessTokenContext
import org.lijun.common.wechat.context.WechatSecurityContext
import org.lijun.common.wechat.vo.WechatSecurityInfo
import java.io.UnsupportedEncodingException

/**
 * 微信公众平台接口URL地址
 *
 * @author lijun
 */
object WechatApiUrls {

    /**
     * 获取access_token接口地址
     */
    @JvmStatic
    fun getAccessTokenUrl(): String {
        val ws: WechatSecurityInfo = WechatSecurityContext.get()

        return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${ws.appId}&secret=${ws.appSecret}"
    }

    /**
     * 获取用户基本信息URL
     * @param openid
     * @return
     */
    @JvmStatic
    fun getFetchUserInfoUrl(openid: String): String {
        return "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=$openid&lang=zh_CN"
    }

    /**
     * 获得jsapi_ticket的URL
     * @return
     */
    @JvmStatic
    fun getJsApiTicketUrl(): String {
        return "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=${AccessTokenContext.get().accessToken}&type=jsapi"
    }

    /**
     * 微信网页授权获取code的URL
     * @param redirectUri
     * @param scope
     * @param state
     * @return
     * @throws UnsupportedEncodingException
     */
    @JvmStatic
    @Throws(UnsupportedEncodingException::class)
    fun getOAuthCodeUrl(redirectUri: String, scope: String = "snsapi_base", state: String): String {
        val ws: WechatSecurityInfo = WechatSecurityContext.get()

        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${ws.appId}&redirect_uri=$redirectUri&response_type=code&scope=$scope&state=$state#wechat_redirect"
    }

    /**
     * 通过code换取网页授权access_token的URL
     * @param code
     * @return
     */
    @JvmStatic
    fun getOAuthAccessTokenUrl(code: String): String {
        val ws: WechatSecurityInfo = WechatSecurityContext.get()

        return "https://api.weixin.qq.com/sns/oauth2/access_token?appid=${ws.appId}&secret=${ws.appSecret}&code=$code&grant_type=authorization_code"
    }

    /**
     * 获取临时素材下载URL
     */
    @JvmStatic
    fun getDownloadTemporaryMediaUrl(mediaId: String): String {
        return "https://api.weixin.qq.com/cgi-bin/media/get?access_token=${AccessTokenContext.get().accessToken}&media_id=$mediaId"
    }

    /**
     * 模板消息发送URL
     * @return
     */
    @JvmStatic
    fun getTemplateMsgUrl(): String {
        return "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=${AccessTokenContext.get().accessToken}"
    }

    /**
     * 自定义菜单删除接口URL
     * @return
     */
    @JvmStatic
    fun getMenuDeleteUrl(): String {
        return "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=${AccessTokenContext.get().accessToken}"
    }

    /**
     * 微信自定义菜单创建接口URL
     * @return
     */
    @JvmStatic
    fun getMenuCreateUrl(): String {
        return "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=${AccessTokenContext.get().accessToken}"
    }

}