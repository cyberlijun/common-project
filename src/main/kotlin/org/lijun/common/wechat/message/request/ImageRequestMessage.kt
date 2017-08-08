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

package org.lijun.common.wechat.message.request

import com.thoughtworks.xstream.annotations.XStreamAlias

/**
 * 微信图片消息封装
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
class ImageRequestMessage : RequestMessage() {

    /**
     * 图片链接
     */
    @XStreamAlias("PicUrl")
    var picUrl: String? = null

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XStreamAlias("MediaId")
    var mediaId: String? = null

}