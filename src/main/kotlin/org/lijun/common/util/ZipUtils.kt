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

import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Project
import org.apache.tools.ant.taskdefs.Zip
import org.apache.tools.ant.types.FileSet
import org.apache.tools.ant.types.ZipFileSet
import org.springframework.util.Assert
import java.io.File

/**
 * ZIP工具类
 *
 * @author lijun
 */
object ZipUtils {

    /**
     * 压缩为zip
     * @param dir
     * @param filename
     * @throws BuildException
     */
    @JvmStatic
    @Throws(BuildException::class)
    fun compress(dir: String, filename: String) = compress(dir, File(filename), null)

    /**
     * 压缩为zip
     * @param dir
     * @param includeFiles
     * @param destFile
     * @throws BuildException
     */
    @JvmStatic
    @Throws(BuildException::class)
    fun compress(dir: String, destFile: File, vararg includeFiles: File?) {
        Assert.hasText(dir, "要压缩的目录不能为空")
        Assert.notNull(destFile, "生成的zip文件不能为空")

        val f: File = File(dir)

        if (!f.isDirectory) throw IllegalArgumentException("$dir is not a directory.")

        val project: Project = Project()

        val zip: Zip = Zip()

        zip.project = project
        zip.destFile = destFile

        val fileSet: FileSet = ZipFileSet()

        fileSet.project = project
        fileSet.dir = f

        includeFiles.forEach {
            if (it?.exists()!! && it.isFile) {
                fileSet.setFile(it)
            }
        }

        zip.addFileset(fileSet)

        zip.execute()
    }

}