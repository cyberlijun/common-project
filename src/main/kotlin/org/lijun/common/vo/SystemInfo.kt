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

package org.lijun.common.vo

import java.io.Serializable

/**
 * 系统信息，用于在管理后台首页显示系统基本信息
 *
 * @author lijun
 * @constructor
 */
class SystemInfo : Serializable {

    /**
     * 系统名称
     */
    var sytemName: String? = null

    /**
     * 系统版本
     */
    var version: String? = null

    /**
     * JAVA版本
     */
    val javaVersion: String = System.getProperty("java.version")

    /**
     * JAVA_HOME路径
     */
    val javaHome: String = System.getProperty("java.home")

    /**
     * 操作系统名称
     */
    val osName: String = System.getProperty("os.name")

    /**
     * 操作系统架构
     */
    val osArch: String = System.getProperty("os.arch")

    /**
     * Servlet容器
     */
    var servletContainer: String? = null

    /**
     * Servlet版本
     */
    var servletVersion: String? = null

}