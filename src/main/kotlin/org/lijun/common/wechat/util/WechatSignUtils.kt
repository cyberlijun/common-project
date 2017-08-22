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

package org.lijun.common.wechat.util

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import org.lijun.common.util.SystemUtils
import java.util.*

/**
 * 微信签名工具类
 *
 * @author lijun
 */
object WechatSignUtils {

    /**
     * 接入微信公众平台时，校验微信传递的签名
     * @param token
     * @param timestamp
     * @param nonce
     * @param signature
     */
    @JvmStatic
    fun verifySign(token: String, timestamp: String, nonce: String, signature: String): Boolean {
        val array: Array<String> = arrayOf(token, timestamp, nonce)

        Arrays.sort(array)

        return StringUtils.equals(signature, DigestUtils.sha1Hex(StringUtils.join(array, StringUtils.EMPTY)))
    }

    /**
     * 生成微信JS SDK签名
     * @param nonceStr
     * @param jsApiTicket
     * @param timestamp
     * @param url
     * @return
     */
    @JvmStatic
    fun generateJsApiSignature(nonceStr: String, jsApiTicket: String, timestamp: String, url: String): String {
        val s: String = "jsapi_ticket=$jsApiTicket&noncestr=$nonceStr&timestamp=$timestamp&url=$url"

        return DigestUtils.sha1Hex(s)
    }

    /**
     * 生成微信支付签名
     * @param params
     * @param apiKey
     * @return
     */
    fun generateWechatPaySignature(params: Map<String, String>, apiKey: String): String {
        val s: String = "${SystemUtils.joinKeyValue(params, "&", "sign")}&key=$apiKey"

        return DigestUtils.md5Hex(s).toUpperCase()
    }

}