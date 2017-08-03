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

package org.lijun.common.util

import com.vdurmont.emoji.EmojiParser

/*
 * emoji表情工具类
 *
 * @author lijun
 */
object EmojiUtils {

    /**
     * 过滤emoji表情字符
     * @param str 含有emoji表情字符的字符串
     * @return 过滤后的字符串
     */
    @JvmStatic
    fun filterEmoji(str: String): String {
        return EmojiParser.removeAllEmojis(str)
    }

    /**
     * 将给定字符串中的emoji表情字符串转换为html格式
     * @param str 含有emoji表情字符的字符串
     * @return 转换后的字符串
     */
    @JvmStatic
    fun parseToHtmlDecimal(str: String): String {
        return EmojiParser.parseToHtmlDecimal(str)
    }

}