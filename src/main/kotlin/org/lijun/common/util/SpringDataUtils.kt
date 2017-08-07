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

import org.lijun.common.querycondition.DataTableQueryCondition
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

/**
 * SpringData工具类
 *
 * @author lijun
 */
object SpringDataUtils {

    /**
     * 根据查询条件创建Pageable对象
     * @param condition
     * @return
     */
    @JvmStatic
    fun createPageable(condition: DataTableQueryCondition) = createPageable(condition, null)

    /**
     * 根据查询条件和排序对象创建Pageable对象
     * @param condition
     * @param sort
     * @return
     */
    @JvmStatic
    fun createPageable(condition: DataTableQueryCondition, sort: Sort?): Pageable {
        return createPageable(condition.start.toInt(), condition.length.toInt(), sort)
    }

    /**
     * 根据当前页数及每页显示记录数创建Pageable对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    @JvmStatic
    fun createPageable(pageNo: Int, pageSize: Int): Pageable = createPageable(pageNo, pageSize, null)

    /**
     * 根据当前页数、每页显示记录数、排序对象创建Pageable对象
     * @param pageNo
     * @param pageSize
     * @param sort
     * @return
     */
    @JvmStatic
    fun createPageable(pageNo: Int, pageSize: Int, sort: Sort?): Pageable = PageRequest(pageNo / pageSize, pageSize, sort)

}