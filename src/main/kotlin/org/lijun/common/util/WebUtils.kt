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

import com.google.common.net.HttpHeaders
import org.apache.commons.lang3.StringUtils
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Web工具类
 *
 * @author lijun
 */
object WebUtils {

    /**
     * 当使用代理时获取IP地址请求头
     */
    private val proxyIpHeaders: Array<String> = arrayOf(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "X-Real-IP")

    /**
     * 获得真实IP地址。在使用了反向代理时，直接用HttpServletRequest.getRemoteAddr()无法获取客户真实的IP地址
     * @return
     */
    @JvmStatic
    fun getRemoteIp(request: HttpServletRequest): String {
        val unknown: String = "unknown"

        var ip: String? = null

        proxyIpHeaders.forEach {
            if (StringUtils.isBlank(ip) || unknown.equals(ip, true)) {
                ip = request.getHeader(it)
            } else return@forEach
        }

        if (StringUtils.isBlank(ip) || unknown.equals(ip, true)) {
            ip = request.remoteAddr
        } else {
            if (StringUtils.INDEX_NOT_FOUND != ip!!.indexOf(",")) {
                ip = ip?.substring(0, ip!!.indexOf(","))
            }
        }

        return ip!!
    }

    /**
     * 设置让浏览器弹出下载对话框的Header
     * @param filename
     */
    @JvmStatic
    fun setFileDownloadHeader(response: HttpServletResponse, filename: String) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${String(filename.toByteArray(), Charsets.ISO_8859_1)}")
    }

    /**
     * 获得完整项目路径，如http://localhost:9090/xxx/
     * @return
     */
    @JvmStatic
    fun getFullBasePath(): String {
        val basePath: StringBuilder = StringBuilder()

        val request: HttpServletRequest = getRequest()

        val port: Int = request.serverPort

        basePath.append(request.scheme)
                .append("://")
                .append(request.serverName)

        if (80 != port) {
            basePath.append(":").append(port)
        }

        return basePath.append(request.contextPath).toString()
    }

    /**
     * 获得上下文路径
     * @return
     */
    @JvmStatic
    fun getContextPath(): String = getRequest().contextPath

    /**
     * 获得请求参数
     * @param ignoreKeys
     * @return
     */
    @JvmStatic
    fun getRequestParams(vararg ignoreKeys: String?): String {
        val map: Map<String, Array<String>> = getRequest().parameterMap

        return SystemUtils.joinKeyValue(map, "&", *ignoreKeys)
    }

    /**
     * 获得完整请求路径
     * @return
     */
    @JvmStatic
    fun getRequestPath(): String {
        val request: HttpServletRequest = getRequest()

        var path: String = request.requestURL.toString()

        val queryString: String? = request.queryString

        if (StringUtils.isNotBlank(queryString)) {
            path = "$path?$queryString"
        }

        return path
    }

    /**
     * 获得HttpServletRequest对象
     * @return
     */
    @JvmStatic
    fun getRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }

}