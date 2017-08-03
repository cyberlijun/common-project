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

package org.lijun.common.vo

import org.apache.commons.lang3.StringUtils
import java.io.Serializable

/**
 * 前台使用Ajax请求后台时返回的JSON数据
 *
 * @author lijun
 * @property status 响应状态
 * @property message 响应消息
 * @property data 响应数据
 * @constructor
 */
data class JsonResult(var status: String?, var message: String?, var data: Any?) : Serializable {

    constructor(status: String?) : this(status, StringUtils.EMPTY, null)

    constructor(status: String?, message: String?) : this(status, message, null)

    constructor(status: String?, data: Any?) : this(status, StringUtils.EMPTY, data)

}