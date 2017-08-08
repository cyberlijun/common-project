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

import org.apache.commons.lang3.StringUtils
import org.lijun.common.util.SystemUtils
import org.lijun.common.web.controller.BaseController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Interceptor - LoggingInterceptor
 *
 * @author lijun
 * @constructor
 */
open class LoggingInterceptor : HandlerInterceptorAdapter() {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var className: String

    private lateinit var methodName: String

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        if (handler is HandlerMethod && handler.bean is BaseController) {
            className = SystemUtils.extractClassName(handler.bean::class.java)

            methodName = SystemUtils.extractMethodName(handler.method)

            logger.info("{}{}开始执行...", className, methodName)
        }

        return super.preHandle(request, response, handler)
    }

    @Throws(Exception::class)
    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        if (handler is HandlerMethod && handler.bean is BaseController) {
            if (StringUtils.isNotBlank(className) && StringUtils.isNotBlank(methodName)) {
                logger.info("{}{}执行完毕...", className, methodName)
            }
        }
    }

}