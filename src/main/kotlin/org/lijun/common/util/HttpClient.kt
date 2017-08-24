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

import org.apache.http.*
import org.apache.http.client.ResponseHandler
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.DefaultSchemePortResolver
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicNameValuePair
import org.lijun.common.support.NoopX509TrustManager
import org.lijun.common.support.StringResponseHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.net.ssl.SSLContext
import org.apache.http.entity.StringEntity
import org.lijun.common.support.HttpResponseWrapper

/**
 * HTTP请求工具类，支持HTTPS
 *
 * @author lijun
 * @property logger
 * @property client
 * @property requestConfig
 * @constructor
 */
@Suppress("UNCHECKED_CAST")
internal class HttpClient {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * CloseableHttpClient instance
     */
    private lateinit var client: CloseableHttpClient

    /**
     * RequestConfig instance
     */
    private var requestConfig: RequestConfig

    /**
     * 请求头
     */
    private var headers: Collection<Header> = arrayListOf()

    /**
     * 判断CloseableHttpClient是否被初始化
     */
    private var initialize: Boolean = false

    /**
     * 初始化
     */
    init {
        headers += BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")
        headers += BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString())

        requestConfig = RequestConfig.custom()
                                     .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                                     .setConnectionRequestTimeout(DEFAULT_CONN_POOL_TIMEOUT)
                                     .setSocketTimeout(DEFAULT_READ_TIMEOUT)
                                     .setCircularRedirectsAllowed(true)
                                     .build()
    }

    /**
     * HTTP POST请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> post(url: String, params: Map<String, String>): T {
        return this.post(url, params, Constants.DEFAULT_CHARSET)
    }

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
        val handler: StringResponseHandler = StringResponseHandler(charset!!)

        var nvps: List<NameValuePair> = listOf()

        params.forEach { k, v ->
            nvps += BasicNameValuePair(k, v)
        }

        val entity: HttpEntity = UrlEncodedFormEntity(nvps, charset)

        return this.doPost(url, null, requestConfig, headers, entity, handler) as T
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
        return this.doPost(url, null, requestConfig, headers, entity, handler)
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
        return this.doPost(url, null, requestConfig, headers, entity, handler)
    }

    /**
     * HTTP GET请求
     * @param url
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> get(url: String): T {
        return this.doGet(url, null, requestConfig, headers, StringResponseHandler()) as T
    }

    /**
     * HTTP GET请求
     * @param url
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> get(url: String, handler: ResponseHandler<T>): T {
        return this.doGet(url, null, requestConfig, headers, handler)
    }

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
        return this.doGet(url, null, requestConfig, headers, handler)
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
        return this.doGet(url, ctx, requestConfig, headers, handler)
    }

    /**
     * HTTP POST请求
     * @param url
     * @param ctx
     * @param entity
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun <T> post(url: String, ctx: HttpClientContext?, entity: HttpEntity?, handler: ResponseHandler<T>): T {
        return doPost(url, ctx, requestConfig, headers, entity, handler)
    }

    /**
     * 向服务器提交JSON数据
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun postJson(url: String, json: String): HttpResponseWrapper<String> {
        return postStringData(url, json, ContentType.APPLICATION_JSON)
    }

    /**
     * 向服务器提交XML数据
     * @param url
     * @param xml
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun postXml(url: String, xml: String): HttpResponseWrapper<String> {
        return postStringData(url, xml, ContentType.create("application/xml", Consts.UTF_8))
    }

    companion object {

        /**
         * 最大连接数
         */
        private const val DEFAULT_MAX_CONN: Int = 200

        /**
         * 单路由最大连接数
         */
        private const val DEFAULT_MAX_CONN_PER_ROUTE: Int = 200

        /**
         * 连接超时时间
         */
        private const val DEFAULT_CONNECT_TIMEOUT: Int = 15000

        /**
         * 读取超时时间
         */
        private const val DEFAULT_READ_TIMEOUT: Int = 30000

        /**
         * 连接池超时时间
         */
        private const val DEFAULT_CONN_POOL_TIMEOUT: Int = 10000

    }

    /**
     * 初始化CloseableHttpClient
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun initHttpClient() {
        if (!initialize) {
            logger.info("CloseableHttpClient未被初始化，即将初始化...")
            logger.info("开始创建CloseableHttpClient实例...")

            val sslContext: SSLContext = SSLContext.getInstance("TLS")

            sslContext.init(null, arrayOf(NoopX509TrustManager()), null)

            val sf: SSLConnectionSocketFactory = SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier())

            this.client = HttpClientBuilder.create()
                                           .setSchemePortResolver(DefaultSchemePortResolver.INSTANCE)
                                           .setSSLSocketFactory(sf)
                                           .setMaxConnTotal(DEFAULT_MAX_CONN)
                                           .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE)
                                           .setDefaultRequestConfig(this.requestConfig)
                                           .setDefaultHeaders(this.headers)
                                           .build()

            initialize = true

            logger.info("CloseableHttpClient已创建...")
        }
    }

    /**
     * 执行post请求
     * @param url
     * @param ctx
     * @param requestConfig
     * @param headers
     * @param entity
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun <T> doPost(url: String,
                       ctx: HttpClientContext?,
                       requestConfig: RequestConfig?,
                       headers: Collection<Header>?,
                       entity: HttpEntity?,
                       handler: ResponseHandler<T>): T {
        try {
            initHttpClient()

            val httpPost: HttpPost = HttpPost(url)

            httpPost.setHeaders(headers?.toTypedArray())
            httpPost.config = requestConfig
            httpPost.entity = entity

            return client.execute(httpPost, handler, ctx)
        } finally {
            release()
        }
    }

    /**
     * 执行post请求
     * @param url
     * @param ctx
     * @param requestConfig
     * @param headers
     * @param handler
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun <T> doGet(url: String,
                           ctx: HttpClientContext?,
                           requestConfig: RequestConfig?,
                           headers: Collection<Header>?,
                           handler: ResponseHandler<T>): T {
        try {
            initHttpClient()

            val httpGet: HttpGet = HttpGet(url)

            httpGet.setHeaders(headers?.toTypedArray())
            httpGet.config = requestConfig

            return client.execute(httpGet, handler, ctx)
        } finally {
            release()
        }
    }

    /**
     * 提交文本数据，如XML或JSON数据
     * @param content
     * @param contentType
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun postStringData(url: String, content: String, contentType: ContentType): HttpResponseWrapper<String> {
        val headers: Collection<Header> = listOf(BasicHeader(HttpHeaders.CONTENT_TYPE, contentType.toString()))

        val entity: HttpEntity = StringEntity(content, contentType)

        return this.post(url, headers, entity, StringResponseHandler())
    }

    /**
     * 释放HttpClient资源
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun release() {
        client.close()

        initialize = false

        logger.info("CloseableHttpClient资源已释放...")
    }

}