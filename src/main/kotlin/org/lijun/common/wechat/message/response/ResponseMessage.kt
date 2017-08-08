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

package org.lijun.common.wechat.message.response

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.io.Serializable

/**
 * 微信响应消息基类
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
abstract class ResponseMessage : Serializable {

    /**
     * 开发者微信号
     */
    @XStreamAlias("ToUserName")
    var toUserName: String? = null

    /**
     * 发送方帐号（一个OpenID）
     */
    @XStreamAlias("FromUserName")
    var fromUserName: String? = null

    /**
     * 消息创建时间
     */
    @XStreamAlias("CreateTime")
    var createTime: Long? = null

    /**
     * 消息类型
     * 可选值：text、image、voice、video、music、news
     */
    @XStreamAlias("MsgType")
    var msgType: String? = null

    constructor()

    constructor(msgType: String) {
        this.msgType = msgType
    }

}