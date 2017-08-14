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

package org.lijun.common.shiro

import freemarker.core.Environment
import freemarker.template.*
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.SecureTag}</p>
 *
 * @author lijun
 * @constructor
 */
abstract class SecureTag : TemplateDirectiveModel {

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(TemplateException::class, IOException::class)
    override fun execute(env: Environment?, params: MutableMap<Any?, Any?>?, loopVars: Array<out TemplateModel>?, body: TemplateDirectiveBody?) {
        verifyParameters(params)

        render(env, params, body)
    }

    @Throws(TemplateException::class, IOException::class)
    abstract fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?)

    protected fun getParam(params: MutableMap<Any?, Any?>?, name: String): String? {
        val value: Any = params?.get(name)!!

        if (value is SimpleScalar) {
            return value.asString
        }

        return null
    }

    @Throws(TemplateModelException::class)
    protected open fun verifyParameters(params: MutableMap<Any?, Any?>?) {

    }

    protected fun getSubject(): Subject? = SecurityUtils.getSubject()

    @Throws(TemplateException::class, IOException::class)
    protected fun renderBody(env: Environment?, body: TemplateDirectiveBody?) = body?.render(env!!.out)

}