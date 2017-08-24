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
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.NumberToTextConverter
import java.text.DateFormat

/**
 * POI工具类
 *
 * @author lijun
 */
object POIUtils {

    /**
     * 获取单元格值
     * @param cell
     * @return
     */
    @JvmStatic
    fun getCellValue(cell: Cell): String {
        return when (cell.cellType) {
            Cell.CELL_TYPE_BLANK -> StringUtils.EMPTY
            Cell.CELL_TYPE_BOOLEAN -> cell.booleanCellValue.toString()
            Cell.CELL_TYPE_ERROR -> Constants.STATUS_ERROR
            Cell.CELL_TYPE_FORMULA -> getFormulaValue(cell)
            Cell.CELL_TYPE_NUMERIC -> getNumericValue(cell)
            Cell.CELL_TYPE_STRING -> cell.richStringCellValue.toString()
            else -> ""
        }
    }

    /**
     * 获取公式单元格值
     * @param cell
     * @return
     */
    @JvmStatic
    private fun getFormulaValue(cell: Cell): String {
        val wb: Workbook = cell.sheet.workbook

        val creationHelper: CreationHelper = wb.creationHelper

        val formulaEvaluator: FormulaEvaluator = creationHelper.createFormulaEvaluator()

        return getCellValue(formulaEvaluator.evaluateInCell(cell))
    }

    /**
     * 获取数值单元格值
     * @param cell
     * @return
     */
    @JvmStatic
    private fun getNumericValue(cell: Cell): String {
        if (DateUtil.isCellDateFormatted(cell)) {
            return DateFormat.getInstance().format(cell.dateCellValue)
        }

        return NumberToTextConverter.toText(cell.numericCellValue)
    }

}