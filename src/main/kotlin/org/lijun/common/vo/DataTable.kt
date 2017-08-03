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

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.StringUtils
import java.io.Serializable

/**
 * 对jquery.dataTables插件进行封装
 *
 * @author lijun
 * @param T DataTable包装的类型
 * @constructor
 */
class DataTable<T> : Serializable {

    /**
     * 绘制计数器
     */
    @JsonProperty
    var draw: Long? = null

    /**
     * 总记录数
     */
    @JsonProperty
    var recordsTotal: Long? = null

    /**
     * 过滤后的记录数
     */
    @JsonProperty
    var recordsFiltered: Long? = null

    /**
     * 表中需要显示的数据
     */
    @JsonProperty
    var data: Collection<T>? = null

    /**
     * 出现错误时的友好提示
     */
    @JsonProperty
    var error: String? = null

    constructor()

    constructor(draw: Long, recordsTotal: Long, data: Collection<T>) : this(draw, recordsTotal, recordsTotal, data)

    constructor(draw: Long, recordsTotal: Long, recordsFiltered: Long, data: Collection<T>) :
            this(draw, recordsTotal, recordsFiltered, data, StringUtils.EMPTY)

    constructor(draw: Long, recordsTotal: Long?, recordsFiltered: Long?, data: Collection<T>?, error: String) {
        this.draw = draw
        this.recordsTotal = recordsTotal
        this.recordsFiltered = recordsFiltered
        this.data = data
        this.error = error
    }

    companion object {

        /**
         * 当发生错误时返回一个错误表格
         * @param draw 绘制计数器
         * @param error 错误消息
         * @return
         */
        fun <T> createErrorTable(draw: Long, error: String): DataTable<out T> {
            return DataTable<T>(draw, null, null, null, error)
        }

    }

}