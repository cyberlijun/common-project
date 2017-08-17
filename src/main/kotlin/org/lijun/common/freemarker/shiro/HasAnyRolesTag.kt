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

package org.lijun.common.freemarker.shiro

import org.apache.commons.lang3.StringUtils
import org.apache.shiro.subject.Subject

/**
 * Displays body content if the current user has any of the roles specified.
 *
 * <p>Equivalent to {@link org.apache.shiro.web.tags.HasAnyRolesTag}</p>
 *
 * @author lijun
 * @constructor
 */
class HasAnyRolesTag : RoleTag() {

    override fun showTagBody(roleName: String?): Boolean {
        var hasAnyRole: Boolean = false

        val subject: Subject? = getSubject()

        if (null != subject) {
            StringUtils.split(roleName, ",").forEach {
                if (subject.hasRole(it.trim())) {
                    hasAnyRole = true

                    return@forEach
                }
            }
        }

        return hasAnyRole
    }

}