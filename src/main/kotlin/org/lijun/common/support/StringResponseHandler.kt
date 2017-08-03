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

import org.apache.commons.lang3.StringUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.apache.http.util.EntityUtils
import org.lijun.common.util.Constants
import java.io.IOException

/**
 * ResponseHandler - StringResponseHandler
 * 处理返回字符数据
 *
 * @author lijun
 * @constructor
 */
class StringResponseHandler(val charset: String) : ResponseHandler<HttpResponseWrapper<String>> {

    constructor() : this(Constants.DEFAULT_CHARSET)

    /**
     * Processes an [HttpResponse] and returns some value
     * corresponding to that response.

     * @param response The response to process
     * *
     * @return A value determined by the response
     * *
     * *
     * @throws ClientProtocolException in case of an http protocol error
     * *
     * @throws IOException in case of a problem or the connection was aborted
     */
    @Throws(ClientProtocolException::class, IOException::class)
    override fun handleResponse(response: HttpResponse?): HttpResponseWrapper<String> {
        val wrapper: HttpResponseWrapper<String> = HttpResponseWrapper<String>(response!!)

        val entity: HttpEntity? = response.entity

        if (null != entity) {
            wrapper.content = EntityUtils.toString(entity, this.charset)
        } else {
            wrapper.content = StringUtils.EMPTY
        }

        EntityUtils.consumeQuietly(entity)

        return wrapper
    }

}