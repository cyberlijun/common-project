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

import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.apache.commons.lang3.time.DateUtils
import java.text.ParseException
import java.util.*
import java.util.Calendar

/**
 * 身份证工具类
 *
 * @author lijun
 */
object IdentityUtils {

    /**
     * 中国公民身份证号码最小长度
     */
    private const val CHINA_ID_MIN_LENGTH: Int = 15

    /**
     * 中国公民身份证号码最大长度
     */
    private const val CHINA_ID_MAX_LENGTH: Int = 18

    /**
     * 每位加权因子
     */
    private val powers: IntArray = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

    /**
     * 最低年限
     */
    private const val MIN_YEAR: Int = 1930

    /**
     * 省市映射
     */
    private val cityCodes: Map<String, String> = mapOf(
        "11" to "北京",
        "12" to "天津",
        "13" to "河北",
        "14" to "山西",
        "15" to "内蒙古",
        "21" to "辽宁",
        "22" to "吉林",
        "23" to "黑龙江",
        "31" to "上海",
        "32" to "江苏",
        "33" to "浙江",
        "34" to "安徽",
        "35" to "福建",
        "36" to "江西",
        "37" to "山东",
        "41" to "河南",
        "42" to "湖北",
        "43" to "湖南",
        "44" to "广东",
        "45" to "广西",
        "46" to "海南",
        "50" to "重庆",
        "51" to "四川",
        "52" to "贵州",
        "53" to "云南",
        "54" to "西藏",
        "61" to "陕西",
        "62" to "甘肃",
        "63" to "青海",
        "64" to "宁夏",
        "65" to "新疆",
        "71" to "台湾",
        "81" to "香港",
        "82" to "澳门",
        "91" to "国外"
    )

    /**
     * 校验身份证
     * @param identity
     * @return
     */
    @JvmStatic
    fun validate(identity: String): Boolean {
        if (validate18(identity)) return true

        if (validate15(identity)) return true

        return false
    }

    /**
     * 根据身份证号获取出生日期
     * @param identity
     * @return
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class, ParseException::class)
    fun getBirth(identity: String): Date {
        var idCard: String = prepare(identity)

        return DateUtils.parseDateStrictly(idCard.substring(6, 14), "yyyyMMdd")
    }

    /**
     * 根据身份证号获取年龄
     * @param identity
     * @return
     * @throws IllegalArgumentException
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun getAge(identity: String): Int {
        var idCard: String = prepare(identity)

        val year: String = idCard.substring(6, 10)

        val cal: Calendar = Calendar.getInstance()

        return cal.get(Calendar.YEAR) - year.toInt()
    }

    /**
     * 校验18位身份证
     * @param identity
     * @return
     * @throws IllegalArgumentException
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    private fun validate18(identity: String): Boolean {
        if (identity.length == CHINA_ID_MAX_LENGTH) {
            if (!NumberUtils.isDigits(identity.substring(0, 17))) throw IllegalArgumentException("身份账号格式错误")

            val nums: IntArray = convertCharArray2oIntArray(identity.substring(0, 17).toCharArray())

            val power: Int = getPower(nums)

            val checkCode: String = getCheckCode(power)

            return StringUtils.equalsIgnoreCase(checkCode, identity.substring(17))
        }

        throw IllegalArgumentException("身份证号长度无效")
    }

    /**
     * 校验15位身份证
     * @param identity
     * @return
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    @JvmStatic
    @Throws(ParseException::class,
            IllegalArgumentException::class)
    private fun validate15(identity: String): Boolean {
        if (identity.length == CHINA_ID_MIN_LENGTH) {
            if (!NumberUtils.isDigits(identity)) throw IllegalArgumentException("身份账号格式错误")

            if (!cityCodes.containsKey(identity.substring(0, 2))) {
                return false
            }

            val d: Date = DateUtils.parseDateStrictly(identity.substring(6, 12).substring(0, 2), "yy")

            val cal: Calendar = Calendar.getInstance()

            cal.time = d

            return validateDate(cal.get(Calendar.YEAR),
                                Integer.valueOf(identity.substring(6, 12).substring(2, 4)),
                                Integer.valueOf(identity.substring(6, 12).substring(4, 6)))
        }

        throw IllegalArgumentException("身份证号长度无效")
    }

    /**
     * 将15位身份证号转换为18位
     * @param identity
     * @return
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class,
            ParseException::class)
    private fun convert15To18(identity: String): String {
        if (identity.length != CHINA_ID_MIN_LENGTH) throw IllegalArgumentException("身份证号长度无效")

        if (!NumberUtils.isDigits(identity)) throw IllegalArgumentException("身份账号格式错误")

        val d: Date = DateUtils.parseDateStrictly(identity.substring(6, 12), "yyMMdd")

        val cal: Calendar = Calendar.getInstance()

        cal.time = d

        val identity18: String = "${identity.substring(0, 6)}${cal.get(Calendar.YEAR).toString()}${identity.substring(8)}"

        val chars: CharArray = identity18.toCharArray()

        val nums: IntArray = convertCharArray2oIntArray(chars)

        val power: Int = getPower(nums)

        val checkCode: String = getCheckCode(power)

        return "$identity18$checkCode"
    }

    /**
     * 将字符数组转换为整型数组
     * @param chars
     * @return
     * @throws NumberFormatException
     */
    @JvmStatic
    @Throws(NumberFormatException::class)
    private fun convertCharArray2oIntArray(chars: CharArray): IntArray {
        var nums: IntArray = intArrayOf()

        chars.forEach {
            nums += Integer.parseInt(it.toString())
        }

        return nums
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     * @param nums
     * @return
     */
    @JvmStatic
    private fun getPower(nums: IntArray): Int {
        var sum: Int = 0

        if (powers.size == nums.size) {
            nums.withIndex().forEach { (i, n) ->
                powers.withIndex().forEach { (j, p) ->
                    if (i == j) {
                        sum += n * p
                    }
                }
            }
        }

        return sum
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     * @param power
     * @return
     * @throws IllegalArgumentException
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    private fun getCheckCode(power: Int): String {
        when (power % 11) {
            10 -> return "2"
            9 -> return "3"
            8 -> return "4"
            7 -> return "5"
            6 -> return "6"
            5 -> return "7"
            4 -> return "8"
            3 -> return "9"
            2 -> return "x"
            1 -> return "0"
            0 -> return "1"
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * 校验日期
     * @param iYear
     * @param iMonth
     * @param iDate
     * @return
     */
    @JvmStatic
    private fun validateDate(iYear: Int, iMonth: Int, iDate: Int): Boolean {
        val cal = Calendar.getInstance()

        val year = cal.get(Calendar.YEAR)

        val datePerMonth: Int

        if (iYear < MIN_YEAR || iYear >= year) {
            return false
        }

        if (iMonth < 1 || iMonth > 12) {
            return false
        }

        when (iMonth) {
            4, 6, 9, 11 -> datePerMonth = 30
            2 -> {
                val dm: Boolean = (iYear % 4 == 0 && iYear % 100 != 0 || iYear % 400 == 0) && iYear > MIN_YEAR && iYear < year

                datePerMonth = if (dm) 29 else 28
            }
            else -> datePerMonth = 31
        }

        return iDate in 1..datePerMonth
    }

    /**
     * 预处理
     * @param identity
     * @return
     * @throws IllegalArgumentException
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    private fun prepare(identity: String): String {
        var idCard: String = identity

        if (idCard.length < CHINA_ID_MIN_LENGTH || idCard.length > CHINA_ID_MAX_LENGTH) {
            throw IllegalArgumentException("身份证号长度无效")
        }

        if (!validate(idCard)) throw IllegalArgumentException("无效身份证号")

        if (idCard.length == CHINA_ID_MIN_LENGTH) {
            idCard = convert15To18(idCard)
        }

        return idCard
    }

}