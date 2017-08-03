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