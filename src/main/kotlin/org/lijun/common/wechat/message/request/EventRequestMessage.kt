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

package org.lijun.common.wechat.message.request

import com.thoughtworks.xstream.annotations.XStreamAlias

/**
 * 微信事件请求消息基类
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
abstract class EventRequestMessage : RequestMessage() {

    /**
     * 事件类型
     */
    @XStreamAlias("Event")
    var event: String? = null

    /**
	 * 1、点击菜单拉取消息：事件KEY值，与自定义菜单接口中KEY值对应
	 * 2、点击菜单跳转链接：事件KEY值，设置的跳转URL 
	 */
    @XStreamAlias("EventKey")
    var eventKey: String? = null
    
}