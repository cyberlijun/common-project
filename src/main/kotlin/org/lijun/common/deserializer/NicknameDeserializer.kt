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

package org.lijun.common.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.apache.commons.lang3.StringUtils
import org.lijun.common.util.EmojiUtils
import java.io.IOException

/**
 * JsonDeserializer - NicknameDeserializer
 *
 * @author lijun
 * @constructor
 */
class NicknameDeserializer : JsonDeserializer<String>() {

    override fun deserialize(jp: JsonParser?, ctx: DeserializationContext?): String {
        val node: JsonNode? = jp?.codec?.readTree(jp)

        val nickname: String? = node?.asText()

        if (null != nickname) {
            if (StringUtils.isNotBlank(nickname)) {
                return EmojiUtils.parseToHtmlDecimal(nickname)
            }

            return StringUtils.EMPTY
        }

        throw IOException()
    }

}