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

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.lijun.common.support.CustomObjectMapper
import java.io.IOException

/*
 * JSON工具类
 *
 * @author lijun
 */
object JsonUtils {

    /**
     * 将对象转换为JSON字符串
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    @JvmStatic
    @Throws(JsonProcessingException::class)
    fun toJson(o: Any): String = toJson(o, null)

    /**
     * 将对象转换为JSON字符串
     * @param o
     * @param include
     * @return
     * @throws JsonProcessingException
     */
    @JvmStatic
    @Throws(JsonProcessingException::class)
    fun toJson(o: Any, include: JsonInclude.Include?): String = CustomObjectMapper(include).writeValueAsString(o)

    /**
     * 将JSON字符串转换为对象
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @JvmStatic
    @Throws(IOException::class,
            JsonParseException::class,
            JsonMappingException::class)
    fun <T> fromJson(json: String): T = CustomObjectMapper().readValue(json, object : TypeReference<T>() { })

}