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

package org.lijun.common.shiro

import freemarker.core.Environment
import freemarker.template.TemplateDirectiveBody

/**
 * JSP tag that renders the tag body if the current user <em>is not</em> known to the system, either because they
 * haven't logged in yet, or because they have no 'RememberMe' identity.
 *
 * <p>The logically opposite tag of this one is the {@link UserTag}.  Please read that class's JavaDoc as it explains
 * more about the differences between Authenticated/Unauthenticated and User/Guest semantic differences.
 *
 * <p>Equivalent to {@link org.apache.shiro.web.tags.GuestTag}</p>
 *
 * @author lijun
 * @constructor
 */
class GuestTag : SecureTag() {

    override fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?) {
        if (null != getSubject() && null != getSubject()!!.principal) {
            if (logger.isDebugEnabled) {
                logger.debug("Subject does not exist or does not have a known identity (aka 'principal'). Tag body will be evaluated. ")
            }

            renderBody(env, body)
        } else {
            if (logger.isDebugEnabled) {
                logger.debug("Subject exists or has a known identity (aka 'principal'). Tag body will not be evaluated.")
            }
        }
    }

}