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
 * 常量类
 *
 * @author lijun
 */
object Constants {

    /**
     * 默认字符集
     */
    const val DEFAULT_CHARSET: String = "UTF-8"

    /**
     * JSON ContentType
     */
    const val JSON_CONTENT_TYPE: String = "application/json; charset=UTF-8"

    /**
     * Excel ContentType
     */
    const val EXCEL_CONTENT_TYPE: String = "application/vnd.ms-excel"

    /**
     * Default ContentType
     */
    const val DEFAULT_CONTENT_TYPE: String = "application/octet-stream"

    /**
     * 成功响应码
     */
    const val STATUS_SUCCESS: String = "success"

    /**
     * 错误响应码
     */
    const val STATUS_ERROR: String = "error"

    /**
     * 失败响应码
     */
    const val STATUS_FAIL: String = "fail"

    /**
     * easyui树节点打开状态
     */
    const val TREE_STATE_OPEN: String = "open"

    /**
     * easyui树节点关闭状态
     */
    const val TREE_STATE_CLOSED: String = "closed"

    /**
     * 存放在Session中的用户对象的key
     */
    const val SESSION_USER_KEY: String = "member"

    /**
     * 默认日期格式
     */
    const val DEFAULT_DATE_PATTERN: String = "yyyy-MM-dd"

    /**
     * 默认日期格式含时分秒
     */
    const val DEFAULT_TIMESTAPM_PATTERN: String = "yyyy-MM-dd HH:mm:ss"

    /**
     * 默认用户初始密码
     */
    const val DEFAULT_PASSWORD: String = "111111"

    /**
     * get方法名称前缀
     */
    const val GET_METHOD_PREFIX: String = "get"

    /**
     * XML文件扩展名
     */
    const val FILE_EXTENSION_XML: String = ".xml"

    /**
     * 默认当前页数
     */
    const val DEFAULT_PAGE_NO: Int = 1

    /**
     * 默认每页显示记录数
     */
    const val DEFAULT_PAGE_SIZE: Int = 30

    /**
     * 管理平台根结点图标样式，针对easyui框架设定
     */
    const val ROOT_NODE_ICONCLS: String = "house"

    /**
     * 管理平台根结点ID，针对easyui框架设定
     */
    const val ROOT_NODE_ID: Long = -1L

    /**
     * 树节点attributes的URL对应key，针对easyui框架设定
     */
    const val NODE_ATTRIBUTE_URL: String = "url"

    /**
     * 存入RedirectAttributes中的消息key
     */
    const val FLASH_MESSAGE_KEY: String = "message"

    /**
     * 默认时区
     */
    const val DEFAULT_TIME_ZONE: String = "GMT+8"

}