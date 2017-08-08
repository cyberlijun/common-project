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

package org.lijun.common.wechat.payment.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.io.Serializable

/**
 * 微信支付统一下单请求对象
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
class WechatPayRequest : Serializable {

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
     * 签名类型
     */
    @XStreamAlias("sign_type")
    val signType = DEFAULT_SIGN_TYPE

    /**
     * 商品描述
     */
    @XStreamAlias("body")
    var body: String? = null

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    var outTradeNo: String? = null

    /**
     * 总金额
     */
    @XStreamAlias("total_fee")
    var totalFee: String? = null

    /**
     * 终端IP
     */
    @XStreamAlias("spbill_create_ip")
    var spbillCreateIp: String? = null

    /**
     * 通知地址
     */
    @XStreamAlias("notify_url")
    var notifyUrl: String? = null

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    var tradeType = DEFAULT_TRADE_TYPE

    /**
     * 用户标识
     */
    @XStreamAlias("openid")
    var openid: String? = null

    companion object {

        /**
         * 签名算法
         */
        private const val DEFAULT_SIGN_TYPE: String = "MD5"

        /**
         * 交易类型
         */
        private const val DEFAULT_TRADE_TYPE: String = "JSAPI"

    }

}