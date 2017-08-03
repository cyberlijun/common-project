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