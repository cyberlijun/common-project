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

package org.lijun.common.support

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import org.apache.commons.lang3.StringUtils

/**
 * 自定义ObjectMapper
 *
 * @author lijun
 * @constructor
 */
class CustomObjectMapper : ObjectMapper {

    constructor() : this(null)

    constructor(include: JsonInclude.Include?) {
        if (null != include) {
            setSerializationInclusion(include)
        }

        this.serializerProvider.setNullValueSerializer(object : JsonSerializer<Any>() {

            override fun serialize(value: Any?, jgen: JsonGenerator?, provider: SerializerProvider?) {
                jgen?.writeString(StringUtils.EMPTY)
            }

        })

        configure(SerializationFeature.INDENT_OUTPUT, true)
        configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true)
    }

}