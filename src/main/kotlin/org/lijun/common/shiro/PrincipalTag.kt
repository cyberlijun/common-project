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
import freemarker.template.TemplateDirectiveBody
import freemarker.template.TemplateModelException
import org.apache.commons.lang3.StringUtils
import java.beans.BeanInfo
import java.beans.Introspector
import java.io.IOException

/**
 * <p>Tag used to print out the String value of a user's default principal,
 * or a specific principal as specified by the tag's attributes.</p>
 *
 * <p> If no attributes are specified, the tag prints out the <tt>toString()</tt>
 * value of the user's default principal.  If the <tt>type</tt> attribute
 * is specified, the tag looks for a principal with the given type.  If the
 * <tt>property</tt> attribute is specified, the tag prints the string value of
 * the specified property of the principal.  If no principal is found or the user
 * is not authenticated, the tag displays nothing unless a <tt>defaultValue</tt>
 * is specified.</p>
 *
 * <p>Equivalent to {@link org.apache.shiro.web.tags.PrincipalTag}</p>
 *
 * @author lijun
 * @constructor
 */

class PrincipalTag : SecureTag() {

    override fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?) {
        var result: String? = null

        if (null != getSubject()) {
            var principal: Any?

            if (StringUtils.isBlank(getType(params))) {
                principal = getSubject()?.principal
            } else {
                principal = getPrincipalFromClassName(params)
            }

            if (null != principal) {
                val property: String = getProperty(params)

                if (StringUtils.isBlank(property)) {
                    result = principal.toString()
                } else {
                    result = getPrincipalProperty(principal, property)
                }
            }
        }

        if (StringUtils.isNotBlank(result)) {
            env?.out?.write(result)
        }
    }

    @Throws(IOException::class, TemplateModelException::class)
    private fun getPrincipalFromClassName(params: MutableMap<Any?, Any?>?): Any? {
        val type: String = getType(params)

        return getSubject()?.principals?.oneByType(Class.forName(type))
    }

    @Throws(TemplateModelException::class)
    private fun getPrincipalProperty(principal: Any, property: String): String {
        var result: String = StringUtils.EMPTY

        val beanInfo: BeanInfo = Introspector.getBeanInfo(principal.javaClass)

        beanInfo.propertyDescriptors.forEach {
            if (StringUtils.equals(it.name, property)) {
                result = it.readMethod.invoke(principal).toString()

                return@forEach
            }
        }

        return result
    }

    private fun getType(params: MutableMap<Any?, Any?>?): String = getParam(params, "type")!!

    private fun getProperty(params: MutableMap<Any?, Any?>?): String = getParam(params, "property")!!

}