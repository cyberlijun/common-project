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

import org.apache.commons.io.IOUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.apache.http.util.EntityUtils
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

/**
 * ResponseHandler - StreamResponseHandler
 * 处理返回二进制数据，如调用微信获取临时素材和永久素材接口
 *
 * @author lijun
 * @constructor
 */
class StreamResponseHandler : ResponseHandler<HttpResponseWrapper<InputStream>> {

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
    override fun handleResponse(response: HttpResponse?): HttpResponseWrapper<InputStream> {
        var input: InputStream? = null

        try {
            val wrapper: HttpResponseWrapper<InputStream> = HttpResponseWrapper<InputStream>(response!!)

            val entity: HttpEntity? = response.entity

            if (null != entity) {
                input = entity.content

                wrapper.content = ByteArrayInputStream(IOUtils.toByteArray(input))
            }

            EntityUtils.consumeQuietly(entity)

            return wrapper
        } finally {
            IOUtils.closeQuietly(input)
        }
    }


}