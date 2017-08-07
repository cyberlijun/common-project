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

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.output.ByteArrayOutputStream
import java.io.IOException

/**
 * 二维码工具类
 *
 * @author lijun
 */
object QrCodeUtils {

    /**
     * 二维码宽度
     */
    private const val DEFAULT_WIDTH: Int = 200

    /**
     * 二维码高度
     */
    private const val DEFAULT_HEIGHT: Int = 200

    /**
     * 二维码图片格式
     */
    private const val DEFAULT_IMAGE_FORMAT: String = "png"

    /**
     * 缓冲区大小
     */
    private const val BUFFER_SIZE: Int = 1024 * 8

    /**
     * 生成二维码
     * @param content 要存入二维码的内容
     * @return 返回base64编码字符串，前台页面可以使用img标签输出
     * @throws WriterException
     * @throws IOException
     */
    @JvmStatic
    @Throws(WriterException::class, IOException::class)
    fun genernate(content: String): String {
        return genernate(content, DEFAULT_WIDTH, DEFAULT_HEIGHT)
    }

    /**
     * 生成二维码
     * @param content 要存入二维码的内容
     * @param width 二维码宽度
     * @param height 二维码高度
     * @return 返回base64编码字符串，前台页面可以使用img标签输出
     * @throws WriterException
     * @throws IOException
     */
    @JvmStatic
    @Throws(WriterException::class, IOException::class)
    fun genernate(content: String, width: Int, height: Int): String {
        val hints: Map<EncodeHintType, Any> = mapOf(EncodeHintType.CHARACTER_SET to Constants.DEFAULT_CHARSET)

        val matrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)

        val out: ByteArrayOutputStream = ByteArrayOutputStream(BUFFER_SIZE)

        MatrixToImageWriter.writeToStream(matrix, DEFAULT_IMAGE_FORMAT, out)

        return Base64.encodeBase64String(out.toByteArray())
    }

}