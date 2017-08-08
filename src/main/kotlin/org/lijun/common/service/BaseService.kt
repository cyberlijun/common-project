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

package org.lijun.common.service

import org.lijun.common.querycondition.DataTableQueryCondition
import org.lijun.common.vo.DataTable
import org.lijun.common.vo.PageList
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import java.io.Serializable

/**
 * Service - BaseService
 *
 * @author lijun
 * @param T 包装的实体类
 * @param ID 主键
 */
interface BaseService<T, in ID : Serializable> {

    /**
     * 新增、修改
     * @param entity 实体类
     */
    fun save(entity: T)

    /**
     * 新增、修改
     * @param entities 实体列表
     */
    fun save(entities: Iterable<T>)

    /**
     * 根据主键查询
     * @param id 主键
     * @return
     */
    fun findById(id: ID): T

    /**
     * 根据specification查询
     * @param specification
     * @return
     */
    fun find(specification: Specification<T>): T

    /**
     * 删除
     * @param ids 主键列表
     */
    fun delete(vararg ids: ID)

    /**
     * 删除
     * @param entities 实体列表
     */
    fun delete(entities: Iterable<T>)

    /**
     * 刷新到数据库
     */
    fun flush()

    /**
     * 查询全部
     * @return
     */
    fun findAll(): List<T>

    /**
     * 根据主键查询
     * @param ids 主键列表
     * @return
     */
    fun findAll(ids: Iterable<ID>): List<T>

    /**
     * 查询全部并排序
     * @param sort 排序
     * @return
     */
    fun findAll(sort: Sort): List<T>

    /**
     * 根据specification查询
     * @param specification
     * @return
     */
    fun findAll(specification: Specification<T>): List<T>

    /**
     * 根据specification和排序查询
     * @param specification
     * @param sort
     * @return
     */
    fun findAll(specification: Specification<T>, sort: Sort?): List<T>

    /**
     * 下拉刷新查询
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    fun findPageList(pageNum: Int, pageSize: Int): PageList<T>

    /**
     * 下拉刷新查询
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @param sort 排序
     * @return
     */
    fun findPageList(pageNum: Int, pageSize: Int, sort: Sort): PageList<T>

    /**
     * 下拉刷新查询
     * @param pageNum
     * @param pageSize
     * @param specification
     * @return
     */
    fun findPageList(pageNum: Int, pageSize: Int, specification: Specification<T>): PageList<T>

    /**
     * 下拉刷新查询
     * @param pageNum
     * @param pageSize
     * @param specification
     * @param sort
     * @return
     */
    fun findPageList(pageNum: Int, pageSize: Int, specification: Specification<T>?, sort: Sort?): PageList<T>

    /**
     * 分页查询
     * @param condition
     * @return
     */
    fun findPage(condition: DataTableQueryCondition): DataTable<T>

    /**
     * 分页查询
     * @param condition
     * @param sort
     * @return
     */
    fun findPage(condition: DataTableQueryCondition, sort: Sort): DataTable<T>

    /**
     * 分页查询
     * @param condition
     * @param specification
     * @return
     */
    fun findPage(condition: DataTableQueryCondition, specification: Specification<T>): DataTable<T>

    /**
     * 分页查询
     * @param condition
     * @param sort
     * @return
     */
    fun findPage(condition: DataTableQueryCondition, specification: Specification<T>?, sort: Sort?): DataTable<T>

}