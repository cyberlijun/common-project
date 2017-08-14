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

package org.lijun.common.freemarker.method

import freemarker.template.SimpleScalar
import freemarker.template.TemplateMethodModelEx
import freemarker.template.TemplateModelException
import org.lijun.common.util.SpringContextHolder
import org.springframework.stereotype.Component

/**
 *
 *
 * @author lijun
 * @constructor
 */
@Component
open class MessageMethod : TemplateMethodModelEx {

    /**
     * Executes the method call.
     *
     * @param arguments a {@link List} of {@link TemplateModel}-s,
     *     containing the arguments passed to the method. If the implementation absolutely wants
     *     to operate on POJOs, it can use the static utility methods in the {@link DeepUnwrap}
     *     class to easily obtain them. However, unwrapping is not always possible (or not perfectly), and isn't always
     *     efficient, so it's recommended to use the original {@link TemplateModel} value as much as possible.
     *
     * @return the return value of the method, or {@code null}. If the returned value
     *     does not implement {@link TemplateModel}, it will be automatically
     *     wrapped using the {@link Environment#getObjectWrapper() environment's
     *     object wrapper}.
     */
    @Throws(TemplateModelException::class)
    override fun exec(arguments: MutableList<Any?>?): Any {
        if (arguments?.isEmpty()!! || arguments.size > 1) {
            throw TemplateModelException("未传递参数或参数个数大于1个")
        }

        return SimpleScalar("${SpringContextHolder.getProperty(arguments[0].toString())}")
    }

}