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

package org.lijun.common.querycondition

import org.apache.commons.lang3.StringUtils
import java.beans.BeanInfo
import java.beans.IntrospectionException
import java.beans.Introspector
import java.beans.PropertyDescriptor
import java.io.Serializable
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*

/**
 * 查询条件基类
 *
 * @author lijun
 * @constructor
 */
abstract class BaseQueryCondition : Serializable {

    /**
     * 起始日期
     */
    var startDate: Date? = null

    /**
     * 结束日期
     */
    var endDate: Date? = null

    /**
     * 获得查询参数
     * @param o 任意对象
     * @return 查询参数映射
     */
    @Throws(IntrospectionException::class,
            IllegalAccessException::class,
            IllegalArgumentException::class,
            InvocationTargetException::class)
    fun getQueryParameters(o: Any): Map<String, Any?> {
        var map: Map<String, Any?> = mapOf()

        val beanInfo: BeanInfo = Introspector.getBeanInfo(o.javaClass)

        val propertyDescriptors: Array<PropertyDescriptor> = beanInfo.propertyDescriptors

        propertyDescriptors.forEach {
            if (StringUtils.equalsIgnoreCase("class", it.name)) {
                return@forEach
            }

            val method: Method = it.readMethod

            map += it.name to method.invoke(o)
        }

        return map
    }

}