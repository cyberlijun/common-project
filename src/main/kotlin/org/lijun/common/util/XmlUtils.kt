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

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.core.util.QuickWriter
import com.thoughtworks.xstream.io.HierarchicalStreamDriver
import com.thoughtworks.xstream.io.HierarchicalStreamWriter
import com.thoughtworks.xstream.io.naming.NameCoder
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder
import com.thoughtworks.xstream.io.xml.XppDriver
import org.dom4j.Document
import org.dom4j.DocumentException
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import org.springframework.util.Assert
import java.io.IOException
import java.io.StringWriter
import java.io.Writer

/**
 * XML操作工具类
 *
 * @author lijun
 */
@Suppress("UNCHECKED_CAST")
object XmlUtils {

    /**
     * 将对象转换为XML
     * @param o 任意对象
     * @param writeCDATA 是否写CDATA块儿
     */
    @JvmStatic
    fun toXml(o: Any, writeCDATA: Boolean = false): String {
        Assert.notNull(o, "请传递要转换为XML数据的对象")

        val xstream: XStream = createXStream(writeCDATA)

        xstream.processAnnotations(o.javaClass)

        return xstream.toXML(o)
    }

    /**
     * 将XML转换为对象
     * @param xml
     * @param clazz
     * @return
     */
    @JvmStatic
    fun <T> fromXml(xml: String, clazz: Class<T>): Any {
        Assert.hasText(xml, "XML数据为空")

        val xstream: XStream = createXStream()

        xstream.processAnnotations(clazz)

        return xstream.fromXML(xml)
    }

    /**
     * 将XML转换为对象，支持继承
     * @param xml
     * @param superClass
     * @param subClass
     * @return
     */
    @JvmStatic
    fun <T> fromXml(xml: String, superClass: Class<*>, subClass: Class<*>): Any {
        Assert.hasText(xml, "XML数据为空")

        val xstream: XStream = createXStream()

        xstream.addDefaultImplementation(subClass, superClass)

        xstream.processAnnotations(subClass)

        return xstream.fromXML(xml)
    }

    /**
     * 以优美格式输出XML
     * @param xml
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    @JvmStatic
    @Throws(DocumentException::class, IOException::class)
    fun prettyPrint(xml: String): String {
        Assert.hasText(xml, "XML数据为空")

        var out: XMLWriter? = null

        try {
            val document: Document = DocumentHelper.parseText(xml)

            val format: OutputFormat = OutputFormat.createPrettyPrint()

            format.encoding = Constants.DEFAULT_CHARSET
            format.setIndent(true)
            format.setIndentSize(4)

            val sw: StringWriter = StringWriter()

            out = XMLWriter(sw, format)

            out.write(document)

            return sw.toString()
        } finally {
            out?.flush()
            out?.close()
        }
    }

    /**
     * 获得XML根节点
     * @param xml
     * @return
     * @throws DocumentException
     */
    @JvmStatic
    @Throws(DocumentException::class)
    fun getRootElement(xml: String): Element? = DocumentHelper.parseText(xml).rootElement

    /**
     * 将XML转换为Map
     * @param xml
     * @return
     * @throws DocumentException
     */
    @JvmStatic
    @Throws(DocumentException::class)
    fun xml2Map(xml: String): Map<String, String> {
        Assert.hasText(xml, "XML数据为空")

        var map: Map<String, String> = mapOf()

        val root: Element? = getRootElement(xml)

        if (null != root) {
            val elements: List<Element> = getRootElement(xml)?.elements() as List<Element>

            elements.forEach {
                map += it.name to it.textTrim
            }
        }

        return map
    }

    /**
     * 创建XStream对象
     */
    @JvmStatic
    private fun createXStream(writeCDATA: Boolean = false): XStream {
        val coder: NameCoder = XmlFriendlyNameCoder("-_", "_")

        val driver: HierarchicalStreamDriver = object : XppDriver(coder) {

            override fun createWriter(out: Writer?): HierarchicalStreamWriter {
                return object : PrettyPrintWriter(out, coder) {

                    override fun writeText(writer: QuickWriter?, text: String?) {
                        if (writeCDATA) {
                            writer?.write("<![CDATA[")
                            writer?.write(text)
                            writer?.write("]]>")
                        } else {
                            writer?.write(text)
                        }
                    }

                }
            }

        }

        return XStream(driver)
    }

}