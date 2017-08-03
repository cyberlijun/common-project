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

import java.io.Serializable
import java.util.*

/**
 * 对jquery easyui的tree组件封装
 *
 * @author lijun
 * @property id 树节点id
 * @property text 树节点标题
 * @property iconCls 节点css样式
 * @property state 节点状态
 * @property attributes 树节点属性
 * @property children 子节点
 * @constructor
 */
class Tree : Serializable {

    /**
     * 树节点id
     */
    var id: Long? = null

    /**
     * 树节点标题
     */
    var text: String? = null

    /**
     * 节点css样式
     */
    var iconCls: String? = null

    /**
     * 节点状态
     */
    var state: String? = null

    /**
     * 树节点属性
     */
    var attributes: Map<String, Any> = hashMapOf()

    /**
     * 子节点
     */
    var children: List<Tree> = LinkedList()

}