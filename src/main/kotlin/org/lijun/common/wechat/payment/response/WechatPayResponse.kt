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

/**
 * 微信统一下单响应
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
class WechatPayResponse : Serializable {

    /**
     * 返回状态码
     */
    @XStreamAlias("return_code")
    var returnCode: String? = null

    /**
     * 返回信息
     */
    @XStreamAlias("return_msg")
    var returnMsg: String? = null

    /**
     * 公众账号ID
     */
    @XStreamAlias("appid")
    var appId: String? = null

    /**
     * 商户号
     */
    @XStreamAlias("mch_id")
    var mchId: String? = null

    /**
     * 设备号
     */
    @XStreamAlias("device_info")
    var deviceInfo: String? = null

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    var nonceStr: String? = null

    /**
     * 签名
     */
    @XStreamAlias("sign")
    var sign: String? = null

    /**
     * 业务结果
     */
    @XStreamAlias("result_code")
    var resultCode: String? = null

    /**
     * 错误代码
     */
    @XStreamAlias("err_code")
    var errCode: String? = null

    /**
     * 错误代码描述
     */
    @XStreamAlias("err_code_des")
    var errCodeDes: String? = null

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    var tradeType: String? = null

    /**
     * 预支付交易会话标识
     */
    @XStreamAlias("prepay_id")
    var prepayId: String? = null

    /**
     * 二维码链接
     */
    @XStreamAlias("code_url")
    var codeUrl: String? = null
    
}