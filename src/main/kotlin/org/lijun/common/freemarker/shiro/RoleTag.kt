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
import java.io.IOException

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.RoleTag}</p>
 *
 * @author lijun
 * @constructor
 */
abstract class RoleTag : SecureTag() {

    @Throws(TemplateException::class, IOException::class)
    override fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?) {
        val show: Boolean = showTagBody(getName(params))

        if (show) {
            renderBody(env, body)
        }
    }

    abstract fun showTagBody(roleName: String?): Boolean

    private fun getName(params: MutableMap<Any?, Any?>?): String? = getParam(params, "name")

}