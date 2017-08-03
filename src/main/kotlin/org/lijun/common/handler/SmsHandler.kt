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

package org.lijun.common.handler

/**
 * 短信发送处理接口
 *
 * @author lijun
 */
interface SmsHandler {

    /**
     * 发送短信
     * @param mobile 手机号
     * @param content 短信内容
     * @return 短信接口返回的消息ID，短信接口无法回消息ID返回null
     */
    fun send(mobile: CharSequence, content: CharSequence): String?;

    /**
     * 获取短信消息签名，如【阿里巴巴】
     * @return 短信消息签名
     */
    fun getSignature();

}