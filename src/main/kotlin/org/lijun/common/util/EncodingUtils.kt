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

/**
 * 字符转换工具类，用于在下载文件时解决中文乱码问题
 *
 * @author lijun
 */
object EncodingUtils {

    /**
     * 转换字符集
     * @param str
     * @return
     */
    @JvmStatic
    fun convert(str: String): String {
        return String(str.toByteArray(), Charsets.ISO_8859_1)
    }

}