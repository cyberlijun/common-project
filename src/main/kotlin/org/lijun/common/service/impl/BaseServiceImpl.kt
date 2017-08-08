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

package org.lijun.common.service.impl

import org.apache.ibatis.session.SqlSession
import org.lijun.common.querycondition.DataTableQueryCondition
import org.lijun.common.repository.BaseRepository
import org.lijun.common.service.BaseService
import org.lijun.common.util.SpringDataUtils
import org.lijun.common.vo.DataTable
import org.lijun.common.vo.PageList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

/**
 * Service - BaseServiceImpl
 *
 * @author lijun
 * @constructor
 */
abstract class BaseServiceImpl<T, ID : Serializable>(var repository: BaseRepository<T, ID>) : BaseService<T, ID> {

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    protected lateinit var sqlSession: SqlSession

    /**
     * 新增、修改
     * @param entity 实体类
     */
    @Transactional
    override fun save(entity: T) {
        this.repository.save(entity)
    }

    /**
     * 新增、修改
     * @param entities 实体列表
     */
    @Transactional
    override fun save(entities: Iterable<T>) {
        this.repository.save(entities)
    }

    /**
     * 根据主键查询
     * @param id 主键
     * @return
     */
    @Transactional(readOnly = true)
    override fun findById(id: ID): T {
        return this.repository.findOne(id)
    }

    /**
     * 根据specification查询
     * @param specification
     * @return
     */
    @Transactional(readOnly = true)
    override fun find(specification: Specification<T>): T {
        return this.repository.findOne(specification)
    }

    /**
     * 删除
     * @param ids 主键列表
     */
    @Transactional
    override fun delete(vararg ids: ID) {
        ids.forEach {
            this.repository.delete(it)
        }
    }

    /**
     * 删除
     * @param entities 实体列表
     */
    @Transactional
    override fun delete(entities: Iterable<T>) {
        this.repository.delete(entities)
    }

    /**
     * 刷新到数据库
     */
    @Transactional
    override fun flush() {
        this.repository.flush()
    }

    /**
     * 查询全部
     * @return
     */
    @Transactional(readOnly = true)
    override fun findAll(): List<T> {
        return this.repository.findAll()
    }

    /**
     * 根据主键查询
     * @param ids 主键列表
     * @return
     */
    @Transactional(readOnly = true)
    override fun findAll(ids: Iterable<ID>): List<T> {
        return this.repository.findAll(ids)
    }

    /**
     * 查询全部并排序
     * @param sort 排序
     * @return
     */
    @Transactional(readOnly = true)
    override fun findAll(sort: Sort): List<T> {
        return this.repository.findAll(sort)
    }

    /**
     * 根据specification查询
     * @param specification
     * @return
     */
    @Transactional(readOnly = true)
    override fun findAll(specification: Specification<T>): List<T> {
        return this.findAll(specification, null)
    }

    /**
     * 根据specification和排序查询
     * @param specification
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    override fun findAll(specification: Specification<T>, sort: Sort?): List<T> {
        if (null != sort) {
            return this.repository.findAll(specification, sort)
        }

        return this.repository.findAll(specification)
    }

    /**
     * 下拉刷新查询
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPageList(pageNum: Int, pageSize: Int): PageList<T> {
        return this.findPageList(pageNum, pageSize, null, null)
    }

    /**
     * 下拉刷新查询
     * @param pageNum 当前页数
     * @param pageSize 每页记录数
     * @param sort 排序
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPageList(pageNum: Int, pageSize: Int, sort: Sort): PageList<T> {
        return this.findPageList(pageNum, pageSize, null, sort)
    }

    /**
     * 下拉刷新查询
     * @param pageNum
     * @param pageSize
     * @param specification
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPageList(pageNum: Int, pageSize: Int, specification: Specification<T>): PageList<T> {
        return this.findPageList(pageNum, pageSize, specification, null)
    }

    /**
     * 下拉刷新查询
     * @param pageNum
     * @param pageSize
     * @param specification
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPageList(pageNum: Int, pageSize: Int, specification: Specification<T>?, sort: Sort?): PageList<T> {
        var page: Page<T>

        var pageable: Pageable

        if (null != sort) {
            pageable = PageRequest(pageNum - 1, pageSize, sort)
        } else {
            pageable = PageRequest(pageNum - 1, pageSize)
        }

        if (null != specification) {
            page = this.repository.findAll(specification, pageable)
        } else {
            page = this.repository.findAll(pageable)
        }

        return PageList<T>(page.totalPages.toLong(), page.content)
    }

    /**
     * 分页查询
     * @param condition
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPage(condition: DataTableQueryCondition): DataTable<T> {
        val pageable: Pageable = SpringDataUtils.createPageable(condition)

        val page: Page<T> = this.repository.findAll(pageable)

        return DataTable<T>(condition.draw!!, page.totalElements, page.content)
    }

    /**
     * 分页查询
     * @param condition
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPage(condition: DataTableQueryCondition, sort: Sort): DataTable<T> {
        val pageable: Pageable = SpringDataUtils.createPageable(condition, sort)

        val page: Page<T> = this.repository.findAll(pageable)

        return DataTable<T>(condition.draw!!, page.totalElements, page.content)
    }

    /**
     * 分页查询
     * @param condition
     * @param specification
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPage(condition: DataTableQueryCondition, specification: Specification<T>): DataTable<T> {
        return this.findPage(condition, specification, null)
    }

    /**
     * 分页查询
     * @param condition
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    override fun findPage(condition: DataTableQueryCondition, specification: Specification<T>?, sort: Sort?): DataTable<T> {
        val pageable: Pageable

        if (null != sort) {
            pageable = SpringDataUtils.createPageable(condition, sort)
        } else {
            pageable = SpringDataUtils.createPageable(condition)
        }

        val page: Page<T> = this.repository.findAll(specification, pageable)

        return DataTable<T>(condition.draw!!, page.totalElements, page.content)
    }
}