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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.beans.BeanInfo
import java.beans.IntrospectionException
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

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(TemplateException::class, IOException::class)
    override fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?) {
        var result: String? = null

        val subject: Subject? = getSubject()

        if (null != subject) {
            var principal: Any?

            if (StringUtils.isBlank(getType(params))) {
                principal = subject.principal
            } else {
                principal = getPrincipalFromClassName(params)
            }

            if (null != principal) {
                val property: String? = getProperty(params)

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

    private fun getType(params: MutableMap<Any?, Any?>?): String? = getParam(params, "type")

    private fun getProperty(params: MutableMap<Any?, Any?>?): String? = getParam(params, "property")

    @Throws(ClassNotFoundException::class)
    private fun getPrincipalFromClassName(params: MutableMap<Any?, Any?>?): Any? {
        val type: String? = getType(params)

        val subject: Subject? = getSubject()

        if (StringUtils.isNotBlank(type) && null != subject) {
            val cls: Class<*> = Class.forName(type)

            return subject.principals.oneByType(cls)
        }

        return null
    }

    @Throws(TemplateModelException::class, IntrospectionException::class)
    private fun getPrincipalProperty(principal: Any?, property: String?): String? {
        if (null != principal) {
            val beanInfo: BeanInfo = Introspector.getBeanInfo(principal::class.java)

            beanInfo.propertyDescriptors.forEach {
                if (StringUtils.equals(it.name, property)) {
                    val value: Any? = it.readMethod.invoke(principal, null)

                    return value?.toString()
                }
            }
        }

        throw TemplateModelException()
    }

}