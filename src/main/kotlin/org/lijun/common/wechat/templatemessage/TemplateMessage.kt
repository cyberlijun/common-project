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

package org.lijun.common.wechat.templatemessage

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * 微信模板消息封装
 *
 * @author lijun
 * @constructor
 */
class TemplateMessage : Serializable {

    /**
     * 模板消息接收用户
     */
    @JsonProperty("touser")
    var toUser: String? = null

    /**
     * 模板消息ID
     */
    @JsonProperty("template_id")
    var templateId: String? = null

    /**
     * 模板消息详情URL
     */
    var url: String? = null

    /**
     * 模板消息数据
     */
    var data: Map<String, TemplateMessageData> = mapOf()

    /**
     * 添加模板消息数据
     * @param key
     * @param value
     * @return
     */
    fun putData(key: String, value: String): TemplateMessage {
        this.data += (key to TemplateMessageData(value))

        return this
    }

    /**
     * 添加模板消息数据
     * @param key
     * @param value
     * @param color
     * @return
     */
    fun putData(key: String, value: String, color: String): TemplateMessage {
        this.data += (key to TemplateMessageData(value, color))

        return this
    }

}