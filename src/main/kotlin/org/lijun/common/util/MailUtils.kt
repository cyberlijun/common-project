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

import org.apache.commons.mail.EmailException
import org.apache.commons.mail.HtmlEmail
import org.lijun.common.vo.EmailAttachment
import java.io.IOException
import java.io.InputStream
import javax.activation.DataSource
import javax.mail.MessagingException
import javax.mail.util.ByteArrayDataSource
import org.lijun.common.util.SpringContextHolder.Companion.getProperty
import java.util.*
import javax.mail.internet.InternetAddress

/**
 * 邮件工具类
 *
 * @author lijun
 */
object MailUtils {

    /**
     * 使用SSL协议默认端口号
     */
    private const val SSL_SMTP_PORT: Int = 465

    /**
     * 发送电子邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws MessagingException
     * @throws EmailException
     */
    @JvmStatic
    @Throws(EmailException::class)
    fun send(to: String, subject: CharSequence, content: CharSequence) {
        doSend(to, subject, content, null)
    }

    /**
     * 发送电子邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param input 附件输入流
     * @param attachmentName 附件名
     * @param attachmentDesc 附件描述
     * @param attachmentType 附件类型，ContentType
     * @throws MessagingException
     * @throws EmailException
     * @throws IOException
     */
    @JvmStatic
    @Throws(EmailException::class)
    fun send(to: String, subject: CharSequence, content: CharSequence, input: InputStream, attachmentName: String, attachmentDesc: String, attachmentType: String = Constants.DEFAULT_CONTENT_TYPE) {
        val dataSource: DataSource = ByteArrayDataSource(input, attachmentType)

        val attachment: EmailAttachment = EmailAttachment(dataSource, attachmentName, attachmentDesc)

        doSend(to, subject, content, attachment)
    }

    /**
     * 发送电子邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param attachment 附件
     * @throws MessagingException
     * @throws EmailException
     */
    @JvmStatic
    @Throws(EmailException::class)
    private fun doSend(to: String, subject: CharSequence, content: CharSequence, attachment: EmailAttachment?) {
        val username: String = getProperty("spring.mail.username")
        val password: String = getProperty("spring.mail.password")
        val host: String = getProperty("spring.mail.host")
        val from: String = getProperty("spring.mail.from")

        val port: Int = getProperty("spring.mail.port").toInt()

        if (SSL_SMTP_PORT == port) {
            System.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            System.setProperty("mail.smtp.socketFactory.fallback", "false")
            System.setProperty("mail.smtp.ssl.enable", "true")
        }

        System.setProperty("mail.mime.splitlongparameters", "false")

        val email: HtmlEmail = HtmlEmail()

        email.hostName = host
        email.setSmtpPort(port)
        email.setAuthentication(username, password)
        email.setFrom(from)
        email.setTo(listOf(InternetAddress(to)))
        email.subject = subject.toString()
        email.setCharset(Constants.DEFAULT_CHARSET)
        email.setDebug(true)
        email.sentDate = Date()
        email.setHtmlMsg(content.toString())

        if (null != attachment?.dataSource) {
            email.attach(attachment.dataSource, attachment.name, attachment.description)
        }

        email.send()
    }

}