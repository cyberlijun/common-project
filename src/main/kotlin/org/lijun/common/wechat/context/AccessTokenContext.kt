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

package org.lijun.common.wechat.context

import org.lijun.common.domain.AccessToken

/**
 * AccessToken上下文
 *
 * @author lijun
 * @constructor
 */
object AccessTokenContext {

    /**
     * AccessToken实例
     */
    @JvmStatic
    private var accessToken: AccessToken? = null

    /**
     * 设置AccessToken
     * @param accessToken
     */
    @JvmStatic
    fun set(accessToken: AccessToken) {
        this.accessToken = accessToken
    }

    /**
     * 获取AccessToken
     */
    @JvmStatic
    fun get(): AccessToken {
        return this.accessToken!!
    }

}