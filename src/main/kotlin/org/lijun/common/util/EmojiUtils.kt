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