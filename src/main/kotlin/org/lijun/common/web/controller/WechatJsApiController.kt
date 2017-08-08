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

package org.lijun.common.web.controller

import org.lijun.common.util.SystemUtils
import org.lijun.common.util.WebUtils
import org.lijun.common.wechat.context.JsApiTicketContext
import org.lijun.common.wechat.context.WechatSecurityContext
import org.lijun.common.wechat.util.WechatSignUtils
import org.lijun.common.wechat.vo.WechatSecurityInfo
import org.springframework.ui.Model

/**
 * Controller - WechatJsApiController
 *
 * @author lijun
 * @constructor
 */
abstract class WechatJsApiController : BaseController() {

    /**
     * 初始化微信JS API
     * @param model
     */
    protected fun initWechatJsApi(model: Model) {
        val jsApiTicket = JsApiTicketContext.get()
        val timestamp: String = (System.currentTimeMillis() / 1000).toString()
        val nonceStr: String = SystemUtils.uuid()
        val url: String = WebUtils.getRequestPath()

        val signature: String = WechatSignUtils.generateJsApiSignature(nonceStr, jsApiTicket, timestamp, url)

        val ws: WechatSecurityInfo = WechatSecurityContext.get()

        model.addAttribute("appId", ws.appId)
        model.addAttribute("timestamp", timestamp)
        model.addAttribute("nonceStr", nonceStr)
        model.addAttribute("signature", signature)
    }

}