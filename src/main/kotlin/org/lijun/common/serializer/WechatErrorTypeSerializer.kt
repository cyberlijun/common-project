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

package org.lijun.common.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.lijun.common.enums.WechatErrorType

/**
 * JsonSerializer - WechatErrorTypeSerializer
 *
 * @author lijun
 * @constructor
 */
class WechatErrorTypeSerializer : JsonSerializer<WechatErrorType>() {

    override fun serialize(value: WechatErrorType?, jgen: JsonGenerator?, provider: SerializerProvider?) {
        var text: String? = null

        when (value) {
            WechatErrorType.FETCH_USER_INFO_ERROR -> text = "获取用户基本信息错误"
            WechatErrorType.GET_ACCESS_TOKEN_ERROR -> text = "获取access_token错误"
            WechatErrorType.GET_JS_API_TICKET_ERROR -> text = "获取jsapi_ticket错误"
            WechatErrorType.GET_OAUTH_ACCESS_TOKEN_ERROR -> text = "通过code换取网页授权access_token错误"
            WechatErrorType.GET_OAUTH_CODE_ERROR -> text = "网页授权获取code错误"
            WechatErrorType.GET_TEMPORARY_MEDIA_ERROR -> text = "获取临时素材错误"
            WechatErrorType.SEND_TEMPLATE_MESSAGE_ERROR -> text = "发送模板消息错误"
            WechatErrorType.DELETE_MENU_ERROR -> text = "删除自定义菜单错误"
            WechatErrorType.CREATE_MENU_ERROR -> text = "创建自定义菜单错误"
            WechatErrorType.SYSTEM_ERROR -> text = "系统异常"
        }

        jgen?.writeString(text)
    }

}