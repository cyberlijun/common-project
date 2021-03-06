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

package org.lijun.common.support

import org.apache.http.HttpResponse
import org.apache.http.ParseException
import org.apache.http.StatusLine
import org.apache.http.entity.ContentType
import org.apache.http.message.HeaderGroup
import java.io.Serializable
import java.nio.charset.UnsupportedCharsetException
import java.util.*

/**
 * 对HttpClient的请求响应进行封装
 *
 * @author lijun
 * @param T 响应包装的类型
 * @property statusLine 响应状态
 * @property headerGroup 响应头分组
 * @property locale 地区
 * @property contentType 响应类型
 * @property content 响应内容
 * @constructor
 */
class HttpResponseWrapper<T> : Serializable {

    /**
     * 响应状态
     */
    var statusLine: StatusLine? = null

    /**
     * 响应头分组
     */
    var headerGroup: HeaderGroup = HeaderGroup()

    /**
     * 地区
     */
    var locale: Locale? = null

    /**
     * 响应类型
     */
    var contentType: ContentType? = null

    /**
     * 响应内容
     */
    var content: T? = null

    /**
     * @throws ParseException
     * @throws UnsupportedCharsetException
     */
    @Throws(ParseException::class, UnsupportedCharsetException::class)
    constructor(response: HttpResponse) {
        headerGroup.setHeaders(response.allHeaders)

        statusLine = response.statusLine

        locale = response.locale

        contentType = ContentType.get(response.entity)
    }

    /**
     * 判断请求是否成功
     * @return true or false
     */
    fun isSuccess(): Boolean {
        return statusLine!!.statusCode < 300
    }

}