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

package org.lijun.common.util

import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.client.ResponseHandler
import org.apache.http.client.protocol.HttpClientContext
import org.lijun.common.support.HttpResponseWrapper

/**
 * HTTP请求工具类，支持http、https协议请求
 *
 * @author lijun
 */
object HttpUtils {

    /**
     * HTTP POST请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> post(url: String, params: Map<String, String>): T = HttpClient().post(url, params)

    /**
     * HTTP POST请求
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> post(url: String, params: Map<String, String> = mapOf(), charset: String?): T {
        return HttpClient().post(url, params, charset)
    }

    /**
     * HTTP POST请求
     * @param url
     * @param entity
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> post(url: String, entity: HttpEntity?, handler: ResponseHandler<T>): T {
        return HttpClient().post(url, entity, handler)
    }

    /**
     * HTTP POST请求
     * @param url
     * @param headers
     * @param entity
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> post(url: String, headers: Collection<Header>?, entity: HttpEntity?, handler: ResponseHandler<T>): T {
        return HttpClient().post(url, headers, entity, handler)
    }

    /**
     * HTTP GET请求
     * @param url
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> get(url: String): T = HttpClient().get(url)

    /**
     * HTTP GET请求
     * @param url
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> get(url: String, handler: ResponseHandler<T>): T = HttpClient().get(url, handler)

    /**
     * HTTP GET请求
     * @param url
     * @param headers
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> get(url: String, headers: Collection<Header>?, handler: ResponseHandler<T>): T {
        return HttpClient().get(url, headers, handler)
    }

    /**
     * HTTP GET请求
     * @param url
     * @param ctx
     * @param handler
     * @return
     */
    @Throws(Exception::class)
    fun <T> get(url: String, ctx: HttpClientContext?, handler: ResponseHandler<T>): T {
        return HttpClient().get(url, ctx, handler)
    }

    /**
     * 向服务器提交JSON数据
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun postJson(url: String, json: String): HttpResponseWrapper<String> = HttpClient().postJson(url, json)

    /**
     * 向服务器提交XML数据
     * @param url
     * @param xml
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun postXml(url: String, xml: String): HttpResponseWrapper<String> = HttpClient().postXml(url, xml)

}