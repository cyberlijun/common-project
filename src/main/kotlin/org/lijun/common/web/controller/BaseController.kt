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

package org.lijun.common.web.controller

import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.lijun.common.util.Constants
import org.lijun.common.util.WebUtils
import org.lijun.common.vo.JsonResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.context.ServletContextAware
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.beans.PropertyEditor
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletResponse

/**
 * Controller - BaseController
 *
 * @author lijun
 * @constructor
 */
abstract class BaseController : ServletContextAware {

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    protected lateinit var ctx: ServletContext

    override fun setServletContext(servletContext: ServletContext?) {
        this.ctx = servletContext!!
    }

    /**
     * 初始化
     * @param binder
     */
    @InitBinder
    open fun init(binder: WebDataBinder) {
        val format: DateFormat = SimpleDateFormat(Constants.DEFAULT_DATE_PATTERN)

        val propertyEditor: PropertyEditor = CustomDateEditor(format, true)

        binder.registerCustomEditor(Date::class.java, propertyEditor)
        binder.registerCustomEditor(String::class.java, StringTrimmerEditor(true))
    }

    /**
     * 添加Flash消息
     * @param message
     * @param redirectAttributes
     */
    protected open fun addFlashMessage(message: String, redirectAttributes: RedirectAttributes) {
        addFlashMessage(Constants.FLASH_MESSAGE_KEY, message, redirectAttributes)
    }

    /**
     * 添加Flash消息
     * @param attributeName
     * @param attributeValue
     * @param redirectAttributes
     */
    protected open fun addFlashMessage(attributeName: String, attributeValue: String, redirectAttributes: RedirectAttributes) {
        redirectAttributes.addFlashAttribute(attributeName, attributeValue)
    }

    /**
     * 添加Flash消息
     * @param message
     * @param model
     */
    protected open fun addFlashMessage(message: String, model: Model) {
        addFlashMessage(Constants.FLASH_MESSAGE_KEY, message, model)
    }

    /**
     * 添加Flash消息
     * @param attributeName
     * @param message
     * @param model
     */
    protected open fun addFlashMessage(attributeName: String, message: String, model: Model) {
        model.addAttribute(attributeName, message)
    }

    /**
     * 获得上传文件路径
     * @param dir 目录
     * @return 返回/upload/dir/当前日期格式的目录
     */
    protected open fun getUploadDir(dir: String): String {
        return "/upload/$dir/${DateFormatUtils.format(Date(), "yyyyMMdd")}"
    }

    /**
     * 文件上传
     * @param dir 上传目录
     * @param randomFileName 随机文件名
     * @param file 文件
     * @throws IOException
     */
    @Throws(IOException::class)
    protected open fun fileUpload(dir: String, randomFileName: String, file: MultipartFile) {
        var input: InputStream? = null

        var out: OutputStream? = null

        try {
            val f: File = File(this.ctx.getRealPath(getUploadDir(dir)))

            if (!f.exists()) {
                f.mkdirs()
            }

            val path: String = "${this.ctx.getRealPath(getUploadDir(dir))}${File.separator}$randomFileName.${FilenameUtils.getExtension(file.originalFilename).toLowerCase()}"

            input = file.inputStream

            out = BufferedOutputStream(FileOutputStream(path))

            IOUtils.copy(input, out)
        } finally {
            IOUtils.closeQuietly(out)
            IOUtils.closeQuietly(input)
        }
    }

    /**
     * 向客户端输出文本内容
     * @param text 文本内容
     * @param response
     * @throws IOException
     */
    @Throws(IOException::class)
    protected open fun writePlain(text: String, response: HttpServletResponse) = write("text/plain; charset=UTF-8", text, response)

    /**
     * 向客户端输出文本内容
     * @param json JSON数据内容
     * @param response
     * @throws IOException
     */
    @Throws(IOException::class)
    protected open fun writeJson(json: String, response: HttpServletResponse) = write("application/json; charset=UTF-8", json, response)

    /**
     * 向客户端输出文本内容
     * @param xml XML数据内容
     * @param response
     * @throws IOException
     */
    @Throws(IOException::class)
    protected open fun writeXml(xml: String, response: HttpServletResponse) = write("application/xml; charset=UTF-8", xml, response)

    /**
     * 导出文件
     * @param contentType 响应类型
     * @param filename 文件名
     * @param input
     * @param response
     * @throws
     */
    protected open fun export(contentType: String = Constants.EXCEL_CONTENT_TYPE, filename: String, input: InputStream, response: HttpServletResponse) {
        var out: OutputStream? = null

        try {
            response.contentType = contentType

            WebUtils.setFileDownloadHeader(response, filename)

            out = response.outputStream

            IOUtils.copy(input, out)
        } finally {
            IOUtils.closeQuietly(out)
        }
    }

    /**
     * 操作成功
     * @return
     */
    protected open fun success(): JsonResult = success(Constants.STATUS_SUCCESS)

    /**
	 * 操作成功
	 * @param data
	 * @return
	 */
	protected open fun success(data: Any?) = createJsonResult(Constants.STATUS_SUCCESS, data = data)

    /**
     * 操作失败
     * @param message
     * @return
     */
    protected open fun error(message: String) = error(message, null)

    /**
     * 操作失败
     * @param message
     * @param data
     * @return
     */
    protected open fun error(message: String, data: Any?) = createJsonResult(Constants.STATUS_ERROR, message, data = data)

    /**
     * 向客户端输出内容
     * @param contentType 响应类型
     * @param content 响应内容
     * @param response
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun write(contentType: String, content: String, response: HttpServletResponse) {
        response.contentType = contentType
        response.characterEncoding = Constants.DEFAULT_CHARSET

        var out: PrintWriter? = null

        try {
            out = response.writer

            out.println(content)

            out.flush()
        } finally {
            IOUtils.closeQuietly(out)
        }
    }

    /**
     * 创建JsonResult对象
     * @param status
     * @param message
     * @param data
     * @return
     */
    private fun createJsonResult(status: String, message: String = StringUtils.EMPTY, data: Any?): JsonResult {
        return JsonResult(status, message, data)
    }

}