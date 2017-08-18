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

import jxl.Workbook
import jxl.write.Label
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException
import org.apache.commons.beanutils.PropertyUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.commons.lang3.ClassUtils
import org.apache.commons.lang3.StringUtils
import org.lijun.common.annotation.ExcelResource
import org.lijun.common.vo.ExcelHead
import java.io.ByteArrayInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * Excel工具类
 *
 * @author lijun
 */

object ExcelUtils {

    /**
     * 将数据导出到Excel
     * @param data 要导出的数据
     * @param sheetName sheet名称
     * @param writeNoHead 是否写编号列
     * @return
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun <T> exportData2Excel(data: List<T>, sheetName: String, writeNoHead: Boolean? = false): InputStream {
        val out: ByteArrayOutputStream = ByteArrayOutputStream(1024 * 8)

        val wb: WritableWorkbook = Workbook.createWorkbook(out)

        val sheet: WritableSheet = wb.createSheet(sheetName, 0)

        val clazz: Class<*> = ClassUtils.toClass(data[0])[0]

        var heads: List<ExcelHead> = getHeads(clazz)

        if (writeNoHead!!) {
            heads += ExcelHead("序号", 0, StringUtils.EMPTY)
        }

        Collections.sort(heads)

        writeHead(sheet, heads)

        writeData(sheet, data, heads)

        wb.write()

        wb.close()

        return ByteArrayInputStream(out.toByteArray())
    }

    /**
     * 获得Excel标题
     * @param clazz 要操作的类
     * @return
     * @throws SecurityException
     * @throws NullPointerException
     */
    @JvmStatic
    @Throws(SecurityException::class, NullPointerException::class)
    private fun getHeads(clazz: Class<*>): List<ExcelHead> {
        var heads: List<ExcelHead> = listOf()

        val fields: Array<Field> = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(ExcelResource::class.java)) {
                val resource: ExcelResource = it.getAnnotation(ExcelResource::class.java)

                heads += ExcelHead(resource.title, resource.order, resource.property)
            }
        }

        return heads
    }

    /**
     * 写Excel标题行
     * @param sheet
     * @param heads
     * @throws WriteException
     * @throws RowsExceededException
     */
    @JvmStatic
    @Throws(WriteException::class, RowsExceededException::class)
    private fun writeHead(sheet: WritableSheet, heads: List<ExcelHead>) {
        heads.withIndex().forEach { (colIndex, head) -> sheet.addCell(Label(colIndex, 0, head.title)) }
    }

    /**
     * 写Excel数据
     * @param sheet
     * @param data
     * @param heads
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws WriteException
     * @throws RowsExceededException
     */
    @JvmStatic
    @Throws(IllegalAccessException::class,
            InvocationTargetException::class,
            NoSuchMethodException::class,
            WriteException::class,
            RowsExceededException::class)
    private fun <T> writeData(sheet: WritableSheet, data: List<T>, heads: List<ExcelHead>) {
        data.forEachIndexed { i, t ->
            heads.forEachIndexed { j, head ->
                var value: Any? = null

                if (StringUtils.isNotBlank(head.property)) {
                    if (StringUtils.INDEX_NOT_FOUND != head.property?.indexOf(".")) {
                        value = PropertyUtils.getNestedProperty(t, head.property)
                    } else {
                        value = PropertyUtils.getProperty(t, head.property)
                    }
                }

                sheet.addCell(Label(j, i + 1, value?.toString() ?: StringUtils.EMPTY))
            }
        }
    }

}