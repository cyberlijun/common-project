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

package org.lijun.common.wechat.menu

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.collect.Lists

/**
 * 组合按钮，用于自定义菜单子菜单
 *
 * @author lijun
 * @constructor
 */
class CompositeButton : Button {

    constructor()

    constructor(name: String) {
        this.name = name
    }

    /**
     * 子菜单
     */
    @JsonProperty("sub_button")
    var subButtons: List<Button> = Lists.newLinkedList()

}