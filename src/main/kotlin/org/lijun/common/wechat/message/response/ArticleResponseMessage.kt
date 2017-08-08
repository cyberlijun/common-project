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

package org.lijun.common.wechat.message.response

import com.thoughtworks.xstream.annotations.XStreamAlias
import org.lijun.common.wechat.message.vo.Article

/**
 * 微信回复图文消息封装
 *
 * @author lijun
 * @constructor
 */
@XStreamAlias("xml")
class ArticleResponseMessage : ResponseMessage("news") {

    /**
     * 图文消息个数，限制为10条以内
     */
    @XStreamAlias("ArticleCount")
    var articleCount: Int? = null

    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
     */
    @XStreamAlias("Articles")
    var articles: List<Article>? = null
    
}