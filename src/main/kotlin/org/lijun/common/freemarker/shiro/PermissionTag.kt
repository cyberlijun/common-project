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

package org.lijun.common.freemarker.shiro

import freemarker.core.Environment
import freemarker.template.TemplateDirectiveBody
import freemarker.template.TemplateException
import freemarker.template.TemplateModelException
import org.apache.commons.lang3.StringUtils
import org.apache.shiro.subject.Subject

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.PermissionTag}</p>
 *
 * @author lijun
 * @constructor
 */
abstract class PermissionTag : SecureTag() {

    @Throws(TemplateException::class)
    override fun verifyParameters(params: MutableMap<Any?, Any?>?) {
        val permission: String? = getName(params)

        if (StringUtils.isBlank(permission)) {
            throw TemplateModelException("The 'name' tag attribute must be set.")
        }
    }

    override fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?) {
        val permission: String? = getName(params)

        val show: Boolean = showTagBody(permission!!)

        if (show) {
            renderBody(env, body)
        }
    }

    abstract fun showTagBody(permission: String): Boolean

    protected fun isPermitted(permission: String): Boolean {
        val subject: Subject? = getSubject()

        if (null != subject) {
            return subject.isPermitted(permission)
        }

        return false
    }

    private fun getName(params: MutableMap<Any?, Any?>?): String? = getParam(params, "name")

}