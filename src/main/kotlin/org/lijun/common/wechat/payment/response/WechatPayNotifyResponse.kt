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

package org.lijun.common.wechat.payment.response

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * 处理完微信支付异步通知给微信支付服务器响应
 *
 * @author lijun
 * @constructor
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
@XStreamAlias("xml")
class WechatPayNotifyResponse : Serializable {

    /**
     * 返回状态码
     */
    @XmlElement(name = "return_code")
    @XStreamAlias("return_code")
    var returnCode: String? = null

    /**
     * 返回信息
     */
    @XmlElement(name = "return_msg")
    @XStreamAlias("return_msg")
    var returnMsg: String? = null

}