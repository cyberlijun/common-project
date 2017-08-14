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
 * JSP tag that renders the tag body only if the current user has executed a <b>successful</b> authentication attempt
 * <em>during their current session</em>.
 *
 * <p>This is more restrictive than the {@link UserTag}, which only
 * ensures the current user is known to the system, either via a current login or from Remember Me services,
 * which only makes the assumption that the current user is who they say they are, and does not guarantee it like
 * this tag does.
 *
 * <p>The logically opposite tag of this one is the {@link NotAuthenticatedTag}
 *
 * <p>Equivalent to {@link org.apache.shiro.web.tags.AuthenticatedTag}</p>
 *
 * @author lijun
 * @constructor
 */
class AuthenticatedTag : SecureTag() {

    override fun render(env: Environment?, params: MutableMap<Any?, Any?>?, body: TemplateDirectiveBody?) {
        if (null != getSubject() && getSubject()!!.isAuthenticated) {
            if (logger.isDebugEnabled) {
                logger.debug("Subject exists and is authenticated. Tag body will be evaluated.")
            }

            renderBody(env, body)
        } else {
            if (logger.isDebugEnabled) {
                logger.debug("Subject does not exist or is not authenticated. Tag body will not be evaluated.")
            }
        }
    }

}