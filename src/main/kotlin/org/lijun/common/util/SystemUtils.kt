package org.lijun.common.util

import org.apache.commons.lang3.ClassUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateFormatUtils
import java.lang.reflect.Method
import java.util.*
import java.util.UUID

/**
 * 系统工具类
 *
 * @author lijun
 */
object SystemUtils {

    /**
     * 抽取类名
     * @param cls
     * @return
     */
    @JvmStatic
    fun extractClassName(cls: Class<*>): String {
        return "类(${ClassUtils.getPackageName(cls)}.${ClassUtils.getSimpleName(cls)})"
    }

    /**
     * 抽取方法名
     * @param method
     * @return
     */
    @JvmStatic
    fun extractMethodName(method: Method): String {
        return "方法(${method.name})"
    }

    /**
     * 获得当前日期时间戳
     * @return
     */
    @JvmStatic
    fun getCurrentTimestamp(): String {
        return DateFormatUtils.format(Date(), "yyyyMMddHHmmssSSS")
    }

    /**
     * 生成UUID
     * @return
     */
    @JvmStatic
    fun uuid(): String {
        return uuid(true)
    }

    /**
     * 生成UUID
     * @param removeSeparator
     * @return
     */
    @JvmStatic
    fun uuid(removeSeparator: Boolean): String {
        val uuid: UUID = UUID.randomUUID()

        if (removeSeparator) {
            return uuid.toString().replace("-", StringUtils.EMPTY)
        }

        return uuid.toString()
    }

}