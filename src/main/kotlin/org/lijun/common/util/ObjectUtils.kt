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

import org.apache.commons.lang3.ClassUtils
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * 对象操作工具类
 *
 * @author lijun
 */
object ObjectUtils {

    /**
     * 拷贝对象属性
     * @param destnation 目标对象
     * @param origional 原始对象
     * @param ignoreProperties 忽略的属性
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @JvmStatic
    @Throws(SecurityException::class,
            IllegalArgumentException::class,
            IllegalAccessException::class)
    fun copyProperties(destnation: Any, origional: Any, vararg ignoreProperties: String? = arrayOf()) {
        var destnationFields: Array<Field> = addSuperClassFields(destnation.javaClass::class.java, destnation.javaClass.declaredFields)
        var origionalFields: Array<Field> = addSuperClassFields(origional.javaClass, origional.javaClass.declaredFields)

        AccessibleObject.setAccessible(destnationFields, true)
        AccessibleObject.setAccessible(origionalFields, true)

        origionalFields.forEach { origField ->
            val name: String = origField.name
            val returnType: String = origField.type.name

            destnationFields.forEach innerLoop@ { destField ->
                if (Modifier.isFinal(origField.modifiers) || Modifier.isFinal(destField.modifiers)) {
                    return@innerLoop
                }

                if (name == destField.name && returnType == destField.type.name && ignoreProperties.contains(destField.name).not()) {
                    val value: Any? = origField.get(origional)

                    destField.set(destnation, value)
                }
            }
        }
    }

    /**
     * 添加父类属性
     * @param clazz
     * @param fields
     */
    @JvmStatic
    private fun addSuperClassFields(clazz: Class<*>, fields: Array<Field>): Array<Field> {
        var returnFields: Array<Field> = fields

        val superClasses: List<Class<*>> = ClassUtils.getAllSuperclasses(clazz)

        superClasses.forEach {
            returnFields += it.declaredFields
        }

        return returnFields
    }

}