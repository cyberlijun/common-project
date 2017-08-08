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
 * 微信异步通知本机服务器请求对象
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
class WechatPayNotifyRequest : Serializable {

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
     * 签名类型
     */
    @XStreamAlias("sign_type")
    var signType: String? = null

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
     * 用户标识
     */
    @XStreamAlias("openid")
    var openid: String? = null

    /**
     * 是否关注公众账号
     */
    @XStreamAlias("is_subscribe")
    var isSubscribe: String? = null

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    var tradeType: String? = null

    /**
     * 付款银行
     */
    @XStreamAlias("bank_type")
    var bankType: String? = null

    /**
     * 订单金额
     */
    @XStreamAlias("total_fee")
    var totalFee: String? = null

    /**
     * 应结订单金额
     */
    @XStreamAlias("settlement_total_fee")
    var settlementTotalFee: String? = null

    /**
     * 货币种类
     */
    @XStreamAlias("fee_type")
    var feeType: String? = null

    /**
     * 现金支付金额
     */
    @XStreamAlias("cash_fee")
    var cashFee: String? = null

    /**
     * 现金支付货币类型
     */
    @XStreamAlias("cash_fee_type")
    var cashFeeType: String? = null

    /**
     * 代金券金额
     */
    @XStreamAlias("coupon_fee")
    var couponFee: String? = null

    /**
     * 代金券使用数量
     */
    @XStreamAlias("coupon_count")
    var couponCount: String? = null

    /**
     * 微信支付订单号
     */
    @XStreamAlias("transaction_id")
    var transactionId: String? = null

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    var outTradeNo: String? = null

    /**
     * 商家数据包
     */
    @XStreamAlias("attach")
    var attach: String? = null

    /**
     * 支付完成时间
     */
    @XStreamAlias("time_end")
    var timeEnd: String? = null

}