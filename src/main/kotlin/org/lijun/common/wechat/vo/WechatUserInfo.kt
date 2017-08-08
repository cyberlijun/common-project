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

package org.lijun.common.wechat.vo

import java.io.Serializable
import java.math.BigDecimal
import java.util.*


/**
 *
 *
 * @author lijun
 * @constructor
 */
class WechatUserInfo : Serializable {

    /**
     * 微信用户openid
     */
    var openid: String? = null

    /**
     * 昵称
     */
    var nickName: String? = null

    /**
     * 性别
     */
    var gender: String? = null

    /**
     * 用户所在城市
     */
    var city: String? = null

    /**
     * 用户所在国家
     */
    var country: String? = null

    /**
     * 用户所在省份
     */
    var province: String? = null

    /**
     * 用户设备所使用的语言
     */
    var language: String? = null

    /**
     * 用户头像
     */
    var headImgUrl: String? = null

    /**
     * 关注时间
     */
    var subscribeTime: Date? = null

    /**
     * 取消关注时间
     */
    var unsubscribeTime: Date? = null

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    var unionId: String? = null

    /**
     * 公众号运营者对粉丝的备注
     */
    var remark: String? = null

    /**
     * 地理位置纬度
     */
    var latitude: BigDecimal? = null

    /**
     * 地理位置经度
     */
    var longitude: BigDecimal? = null

    /**
     * 地理位置精度
     */
    var precision: BigDecimal? = null

    /**
     * 地图缩放大小
     */
    var scale: Long? = null

    /**
     * 地理位置信息
     */
    var label: String? = null

    /**
     * 最后上传地理位置时间
     */
    var lastModifyLocationDate: Date? = null

    /**
     * 是否关注
     */
    var subscribe: Boolean? = null

    /**
     * 用户所在的分组ID
     */
    var groupId: Long? = null

    /**
     * 用户被打上的标签ID列表
     */
    var tagidList: String? = null

}