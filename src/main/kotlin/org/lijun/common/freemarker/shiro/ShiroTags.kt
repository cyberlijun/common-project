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

import freemarker.ext.beans.BeansWrapperBuilder
import freemarker.template.Configuration
import freemarker.template.ObjectWrapper
import freemarker.template.SimpleHash

/**
 * Shortcut for injecting the tags into Freemarker
 *
 * <p>Usage: cfg.setSharedVeriable("shiro", new ShiroTags());</p>
 *
 * @author lijun
 * @constructor
 * @property wrapper
 */
class ShiroTags(private val wrapper: ObjectWrapper) : SimpleHash(wrapper) {

    init {
        put("authenticated", AuthenticatedTag())
        put("guest", GuestTag())
        put("hasAnyRoles", HasAnyRolesTag())
        put("hasPermission", HasPermissionTag())
        put("hasRole", HasRoleTag())
        put("lacksPermission", LacksPermissionTag())
        put("lacksRole", LacksRoleTag())
        put("notAuthenticated", NotAuthenticatedTag())
        put("principal", PrincipalTag())
        put("user", UserTag())
    }

}