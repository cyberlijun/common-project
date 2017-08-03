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

package org.lijun.common.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.lijun.common.util.Constants
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

/**
 * 受管理的实体模型，用于自动填入创建时间和最后修改时间
 *
 * @author lijun
 * @constructor
 */
@MappedSuperclass
abstract class ManageableEntity : BaseEntity() {

    /**
     * 创建时间
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAPM_PATTERN)
    private var createDate: Date? = null

    /**
     * 最后修改时间
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty
    @JsonFormat(pattern = Constants.DEFAULT_TIMESTAPM_PATTERN)
    private var updateDate: Date? = null

}