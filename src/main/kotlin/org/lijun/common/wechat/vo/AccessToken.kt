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

package org.lijun.common.wechat.vo

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.time.DateUtils
import java.io.Serializable
import java.util.*

/**
 * 微信access_token封装
 *
 * @author lijun
 * @constructor
 */
class AccessToken : Serializable {

    /**
     * 凭证
     */
    @JsonProperty("access_token")
    var accessToken: String? = null

    /**
     * 凭证有效时间，单位：秒
     */
    @JsonProperty("expires_in")
    var expiresIn: String? = null

    /**
     * 凭证过期时间
     */
    val expireTime: Date = DateUtils.addMinutes(Date(), 100)

    /**
     * 判断凭证是否已过期
     * @return
     */
    fun isExpired(): Boolean {
        return this.expireTime.before(Date())
    }

}