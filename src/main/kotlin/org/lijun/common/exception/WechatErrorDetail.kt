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

package org.lijun.common.exception

import com.fasterxml.jackson.annotation.JsonProperty
import org.lijun.common.enums.WechatErrorType
import org.lijun.common.util.Constants
import java.io.Serializable

/**
 * 微信异常错误详情
 *
 * @author lijun
 * @property errorType 错误类型
 * @property errorCode 错误代码
 * @property errorMsg 错误消息
 * @constructor
 */
class WechatErrorDetail : Serializable {

    /**
     * 错误类型
     */
    var errorType: WechatErrorType? = null

    /**
     * 错误代码
     */
    @JsonProperty("errcode")
    var errorCode: String? = null

    /**
     * 错误消息
     */
    @JsonProperty("errmsg")
    var errorMsg: String? = null

    constructor()

    constructor(errorType: WechatErrorType, errorCode: String, errorMsg: String) {
        this.errorType = errorType
        this.errorCode = errorCode
        this.errorMsg = errorMsg
    }

    companion object {

        /**
         * 创建系统错误
         * @param errorMsg 错误消息
         * @return
         */
        fun createSystemError(errorMsg: String): WechatErrorDetail {
            return WechatErrorDetail(WechatErrorType.SYSTEM_ERROR, Constants.STATUS_ERROR, errorMsg);
        }

    }

}