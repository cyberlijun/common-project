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

package org.lijun.common.wechat.message.vo

import java.io.Serializable
import com.thoughtworks.xstream.annotations.XStreamAlias

/**
 * 微信视频消息
 *
 * @author lijun
 * @constructor
 */
class Video : Serializable {

    /**
     * 通过素材管理接口上传多媒体文件，得到的id
     */
    @XStreamAlias("MediaId")
    var mediaId: String? = null

    /**
     * 视频消息的标题
     */
    @XStreamAlias("Title")
    var title: String? = null

    /**
     * 视频消息的描述
     */
    @XStreamAlias("Description")
    var description: String? = null

}