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

import org.lijun.common.vo.EmailAttachment
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.activation.DataSource
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage
import javax.mail.util.ByteArrayDataSource

/**
 * 邮件工具类
 *
 * @author lijun
 */
object MailUtils {

    private val mailSender: JavaMailSender? = SpringContextHolder.getBean(JavaMailSender::class.java)

    /**
     * 发送电子邮件
     * @param to 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws MessagingException
     * @throws MailException
     */
    @JvmStatic
    @Throws(MessagingException::class,
            MailException::class)
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
     * @throws MessagingException
     * @throws MailException
     * @throws IOException
     */
    @JvmStatic
    @Throws(MessagingException::class,
            MailException::class,
            IOException::class)
    fun send(to: String, subject: CharSequence, content: CharSequence, input: InputStream, attachmentName: String, attachmentDesc: String) {
        val dataSource: DataSource = ByteArrayDataSource(input, Constants.DEFAULT_CONTENT_TYPE)

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
     * @throws MailException
     */
    @JvmStatic
    @Throws(MessagingException::class,
            MailException::class)
    private fun doSend(to: String, subject: CharSequence, content: CharSequence, attachment: EmailAttachment?) {
        val mimeMessage: MimeMessage? = this.mailSender?.createMimeMessage()

        val helper: MimeMessageHelper = MimeMessageHelper(mimeMessage, true)

        helper.setFrom(SpringContextHolder.getProperty("spring.mail.from"))
        helper.setTo(to)
        helper.setSubject(subject.toString())
        helper.setText(content.toString(), true)
        helper.setSentDate(Date())

        if (null != attachment?.dataSource) {
            helper.addAttachment(attachment.name, attachment.dataSource)
        }

        this.mailSender?.send(mimeMessage)
    }

}