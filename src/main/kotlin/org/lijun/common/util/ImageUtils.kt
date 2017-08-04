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

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

/**
 * 图片处理工具类
 *
 * @author lijun
 */
object ImageUtils {

    /**
     * 将图片转换为Base64编码
     * @param input
     * @return
     * @throws IOException
     */
    @JvmStatic
    @Throws(IOException::class)
    fun toBase64(input: InputStream): String {
        try {
            val binaryData: ByteArray = IOUtils.toByteArray(input)

            return Base64.encodeBase64String(binaryData)
        } finally {
            IOUtils.closeQuietly(input)
        }
    }

}