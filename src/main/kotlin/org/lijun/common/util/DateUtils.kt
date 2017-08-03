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

import org.apache.commons.lang3.time.DateFormatUtils
import java.text.ParseException
import org.apache.commons.lang3.time.DateUtils as CommonsDateUtils
import java.util.*

/**
 * 日期工具类
 *
 * @author lijun
 */
object DateUtils {

    /**
     * 获取开始日期，供数据库查询使用
     * @param d 日期
     * @return 返回时分秒格式的日期，如1990-01-01 00:00:00
     * @throws ParseException
     */
    @Throws(ParseException::class)
    @JvmStatic
    fun getStartDate(d: Date): Date {
        val str: String = "${DateFormatUtils.format(d, Constants.DEFAULT_DATE_PATTERN)} 00:00:00";

        return CommonsDateUtils.parseDate(str, Constants.DEFAULT_TIMESTAPM_PATTERN)
    }

    /**
     * 获取结束日期，供数据库查询使用
     * @param d 日期
     * @return 返回时分秒格式的日期，如1990-01-01 23:59:59
     * @throws ParseException
     */
    @Throws(ParseException::class)
    @JvmStatic
    fun getEndDate(d: Date): Date {
        val str: String = "${DateFormatUtils.format(d, Constants.DEFAULT_DATE_PATTERN)} 23:59:59";

        return CommonsDateUtils.parseDate(str, Constants.DEFAULT_TIMESTAPM_PATTERN)
    }

}