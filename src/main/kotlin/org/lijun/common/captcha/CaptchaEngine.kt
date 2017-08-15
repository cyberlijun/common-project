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

package org.lijun.common.captcha

import com.octo.captcha.CaptchaException
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator
import com.octo.captcha.component.image.backgroundgenerator.FileReaderRandomBackgroundGenerator
import com.octo.captcha.component.image.color.RandomListColorGenerator
import com.octo.captcha.component.image.fontgenerator.FontGenerator
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster
import com.octo.captcha.component.image.textpaster.TextPaster
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage
import com.octo.captcha.component.image.wordtoimage.WordToImage
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator
import com.octo.captcha.component.word.wordgenerator.WordGenerator
import com.octo.captcha.engine.image.ListImageCaptchaEngine
import com.octo.captcha.image.ImageCaptchaFactory
import com.octo.captcha.image.gimpy.GimpyFactory
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.util.ResourceUtils
import java.awt.Color
import java.awt.Font
import java.io.FileNotFoundException

/**
 * 验证码引擎
 *
 * @author lijun
 * @constructor
 */
open class CaptchaEngine() : ListImageCaptchaEngine() {

    @Throws(FileNotFoundException::class, IllegalArgumentException::class, CaptchaException::class)
    override fun buildInitialFactories() {
        val path: String = ResourceUtils.getFile("classpath:config/captcha").path

        val fontGenerator: FontGenerator = RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, fonts)

        val backgroundGenerator: BackgroundGenerator = FileReaderRandomBackgroundGenerator(IMAGE_WIDTH,
                                                                                           IMAGE_HEIGHT,
                                                                                           path)

        val textPaster: TextPaster = DecoratedRandomTextPaster(MIN_WORD_LENGTH,
                                                               MAX_WORD_LENGTH,
                                                               RandomListColorGenerator(colors),
                                                               null)

        val wordGenerator: WordGenerator = RandomWordGenerator(randomString.toUpperCase())

        val wordToImage: WordToImage = ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster)

        val captchaFactory: ImageCaptchaFactory = GimpyFactory(wordGenerator, wordToImage)

        this.addFactory(captchaFactory)
    }

    companion object {

        /**
         * 验证码图片宽度
         */
        private const val IMAGE_WIDTH: Int = 80

        /**
         * 验证码图片高度
         */
        private const val IMAGE_HEIGHT: Int = 80

        /**
         * 最小字体尺寸
         */
        private const val MIN_FONT_SIZE: Int = 12

        /**
         * 最大字体尺寸
         */
        private const val MAX_FONT_SIZE: Int = 16

        /**
         * 验证码最小字符个数
         */
        private const val MIN_WORD_LENGTH: Int = 4

        /**
         * 验证码最大字符个数
         */
        private const val MAX_WORD_LENGTH: Int = 4

        /**
         * 验证码随机字符串
         */
        private val randomString: String = RandomStringUtils.random(38, 0, 0, true, true, 'o', '0', 'O', 'i', 'I', '1', 'l')

        /**
         * 随机字体
         */
        private val fonts: Array<Font> = arrayOf(
                Font("nyala", Font.BOLD, MAX_FONT_SIZE),
                Font("Arial", Font.BOLD, MAX_FONT_SIZE),
                Font("Bell", Font.BOLD, MAX_FONT_SIZE),
                Font("Bell MT", Font.BOLD, MAX_FONT_SIZE),
                Font("Credit", Font.BOLD, MAX_FONT_SIZE),
                Font("valley", Font.BOLD, MAX_FONT_SIZE),
                Font("Impact", Font.BOLD, MAX_FONT_SIZE)
        )

        /**
         * 随机颜色
         */
        private val colors: Array<Color> = arrayOf(
                Color(255, 255, 255),
                Color(255, 220, 220),
                Color(220, 255, 255),
                Color(220, 220, 255),
                Color(255, 255, 220),
                Color(220, 255, 220)
        )

    }

}