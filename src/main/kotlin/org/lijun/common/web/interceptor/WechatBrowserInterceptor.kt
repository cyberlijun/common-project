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

package org.lijun.common.web.interceptor

import com.google.common.net.HttpHeaders
import org.apache.commons.lang3.StringUtils
import org.lijun.common.util.WebUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Interceptor - WechatBrowserInterceptor
 *
 * @author lijun
 * @param failRedirectUrl 当不是使用微信内置浏览器访问时跳转的URL
 * @constructor
 */
open class WechatBrowserInterceptor(var failRedirectUrl: String) : HandlerInterceptorAdapter() {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        val agent: String? = request?.getHeader(HttpHeaders.USER_AGENT.toLowerCase())

        if (StringUtils.isNotBlank(agent)) {
            logger.info("WechatBrowserInterceptor获取的User-Agent为：$agent")
        }

        if (StringUtils.isNotBlank(agent) && StringUtils.INDEX_NOT_FOUND != StringUtils.indexOf(agent?.toLowerCase(), "micromessenger")) {
            return true
        }

        logger.info("传递的重定向URL参数为：$failRedirectUrl")

        val url: String = "${WebUtils.getContextPath()}/$failRedirectUrl"

        logger.info("失败跳转的URL为：$url")

        response?.sendRedirect(url)

        return false
    }

}