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
import org.apache.commons.lang3.Validate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.DisposableBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContextException
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import java.util.*

/**
 * Spring工具类
 *
 * @author lijun
 */
@Component
@Lazy(false)
@Suppress("UNCHECKED_CAST")
open class SpringContextHolder : ApplicationContextAware, DisposableBean {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     *
     * Invoked after population of normal bean properties but before an init callback such
     * as [org.springframework.beans.factory.InitializingBean.afterPropertiesSet]
     * or a custom init-method. Invoked after [ResourceLoaderAware.setResourceLoader],
     * [ApplicationEventPublisherAware.setApplicationEventPublisher] and
     * [MessageSourceAware], if applicable.
     * @param applicationContext the ApplicationContext object to be used by this object
     * *
     * @throws ApplicationContextException in case of context initialization errors
     * *
     * @throws BeansException if thrown by application context methods
     * *
     * @see org.springframework.beans.factory.BeanInitializationException
     */
    @Throws(ApplicationContextException::class,
            BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        if (null != SpringContextHolder.applicationContext) {
            logger.info("SpringContextHolder中的ApplicationContext被覆盖，原有ApplicationContext为：${SpringContextHolder.applicationContext}")
        }

        SpringContextHolder.applicationContext = applicationContext
    }

    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     * @throws Exception in case of shutdown errors.
     * * Exceptions will get logged but not rethrown to allow
     * * other beans to release their resources too.
     */
    override fun destroy() {
        if (logger.isInfoEnabled){
            logger.debug("清除SpringContextHolder中的ApplicationContext：${SpringContextHolder.applicationContext}")
        }

        SpringContextHolder.applicationContext = null
    }

    companion object {

        private var applicationContext: ApplicationContext? = null

        /**
         * 获取指定名称的Bean
         * @param name
         * @return
         * @throws BeansException
         */
        @JvmStatic
        @Throws(BeansException::class)
        fun <T> getBean(name: String): T? {
            Assert.hasText(name, "Bean名称为空！")

            assertContextInjected()

            return SpringContextHolder.applicationContext?.getBean(name) as T?
        }

        /**
         * 根据给定的类型获取Bean
         * @param requiredType
         * @return
         * @throws BeansException
         */
        @JvmStatic
        @Throws(BeansException::class)
        fun <T> getBean(requiredType: Class<T>): T? {
            Assert.notNull(requiredType, "Bean类型不能为空！")

            assertContextInjected()

            return SpringContextHolder.applicationContext?.getBean(requiredType)
        }

        /**
         * 根据Bean名称和类型获得Bean
         * @param name
         * @param requiredType
         * @return
         * @throws BeansException
         */
        @JvmStatic
        @Throws(BeansException::class)
        fun <T> getBean(name: String, requiredType: Class<T>): T? {
            Assert.hasText(name, "Bean名称为空！")

            Assert.notNull(requiredType, "Bean类型不能为空！")

            assertContextInjected()

            return SpringContextHolder.applicationContext?.getBean(name, requiredType)
        }

        /**
         * 获取消息
         * @param code
         * @return
         */
        @JvmStatic
        fun getMessage(code: String): String {
            return getMessage(code, null)
        }

        /**
         * 获取消息
         * @param code
         * @param args
         * @return
         */
        @JvmStatic
        fun getMessage(code: String, vararg args: Any?): String {
            return getMessage(code, StringUtils.EMPTY, Locale.getDefault(), args)
        }

        /**
         * 获取消息
         * @param code
         * @param defaultMessage
         * @param locale
         * @param args
         * @return
         */
        @JvmStatic
        fun getMessage(code: String, defaultMessage: String, locale: Locale, vararg args: Any?): String {
            return SpringContextHolder.applicationContext?.getMessage(code, args, defaultMessage, locale)!!
        }

        /**
         * 获取属性
         * @param key
         * @return
         */
        @JvmStatic
        fun getProperty(key: String): String = getProperty(key, StringUtils.EMPTY)

        /**
         * 获取属性
         * @param key
         * @param defaultValue
         * @return
         */
        @JvmStatic
        fun getProperty(key: String, defaultValue: String): String {
            return SpringContextHolder.applicationContext?.environment?.getProperty(key, defaultValue)!!
        }

        /**
         * 检查applicationContext属性被注入
         */
        @JvmStatic
        private fun assertContextInjected() {
            Validate.validState(null != SpringContextHolder.applicationContext, "applicaitonContext属性未注入,请配置SpringContextHolder Bean")
        }

    }

}