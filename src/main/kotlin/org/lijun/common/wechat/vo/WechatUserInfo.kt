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

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.lijun.common.deserializer.NicknameDeserializer
import org.lijun.common.deserializer.SubscribeDeserializer
import org.lijun.common.deserializer.SubscribeTimeDeserializer
import org.lijun.common.deserializer.TagidListDeserializer
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 * 微信用户信息封装
 *
 * @author lijun
 * @constructor
 */
class WechatUserInfo : Serializable {

    /**
     * 微信用户openid
     */
    @JsonProperty("openid")
    var openid: String? = null

    /**
     * 昵称
     */
    @JsonProperty("nickname")
    @JsonDeserialize(using = NicknameDeserializer::class)
    var nickName: String? = null

    /**
     * 性别
     */
    @JsonProperty("sex")
    var gender: String? = null

    /**
     * 用户所在城市
     */
    @JsonProperty("city")
    var city: String? = null

    /**
     * 用户所在国家
     */
    @JsonProperty("country")
    var country: String? = null

    /**
     * 用户所在省份
     */
    @JsonProperty("province")
    var province: String? = null

    /**
     * 用户设备所使用的语言
     */
    @JsonProperty("language")
    var language: String? = null

    /**
     * 用户头像
     */
    @JsonProperty("headimgurl")
    var headImgUrl: String? = null

    /**
     * 关注时间
     */
    @JsonProperty("subscribe_time")
    @JsonDeserialize(using = SubscribeTimeDeserializer::class)
    var subscribeTime: Date? = null

    /**
     * 取消关注时间
     */
    @JsonIgnore
    var unsubscribeTime: Date? = null

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    @JsonProperty("unionid")
    var unionId: String? = null

    /**
     * 公众号运营者对粉丝的备注
     */
    @JsonProperty("remark")
    var remark: String? = null

    /**
     * 地理位置纬度
     */
    @JsonIgnore
    var latitude: BigDecimal? = null

    /**
     * 地理位置经度
     */
    @JsonIgnore
    var longitude: BigDecimal? = null

    /**
     * 地理位置精度
     */
    @JsonIgnore
    var precision: BigDecimal? = null

    /**
     * 地图缩放大小
     */
    @JsonIgnore
    var scale: Long? = null

    /**
     * 地理位置信息
     */
    @JsonIgnore
    var label: String? = null

    /**
     * 最后上传地理位置时间
     */
    @JsonIgnore
    var lastModifyLocationDate: Date? = null

    /**
     * 是否关注
     */
    @JsonProperty("subscribe")
    @JsonDeserialize(using = SubscribeDeserializer::class)
    var subscribe: Boolean? = null

    /**
     * 用户所在的分组ID
     */
    @JsonProperty("groupid")
    var groupId: Long? = null

    /**
     * 用户被打上的标签ID列表
     */
    @JsonProperty("tagid_list")
    @JsonDeserialize(using = TagidListDeserializer::class)
    var tagidList: String? = null

}