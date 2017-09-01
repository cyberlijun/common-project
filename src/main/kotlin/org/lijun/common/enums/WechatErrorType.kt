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

package org.lijun.common.enums

/**
 * 调用微信接口错误类型枚举
 *
 * @author lijun
 */
enum class WechatErrorType {

    /**
     * 获取access_token错误
     */
    GET_ACCESS_TOKEN_ERROR,

    /**
     * 获取用户基本信息错误
     */
    FETCH_USER_INFO_ERROR,

    /**
     * 获取jsapi_ticket错误
     */
    GET_JS_API_TICKET_ERROR,

    /**
     * 网页授权获取code错误
     */
    GET_OAUTH_CODE_ERROR,

    /**
     * 通过code换取网页授权access_token错误
     */
    GET_OAUTH_ACCESS_TOKEN_ERROR,

    /**
     * 获取临时素材错误
     */
    GET_TEMPORARY_MEDIA_ERROR,

    /**
     * 发送模板消息错误
     */
    SEND_TEMPLATE_MESSAGE_ERROR,

    /**
     * 删除自定义菜单错误
     */
    DELETE_MENU_ERROR,

    /**
     * 创建自定义菜单错误
     */
    CREATE_MENU_ERROR,

    /**
     * 批量抓取用户信息错误
     */
    BATCH_FETCH_USER_INFO_ERROR,

    /**
     * 系统异常
     */
    SYSTEM_ERROR

}