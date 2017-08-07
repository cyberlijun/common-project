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

import org.apache.commons.collections.MapUtils
import java.util.*

/**
 *
 *
 * @author lijun
 * @constructor
 */
class SizeComparator : Comparator<Hashtable<String, Any>> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     *
     *

     * In the foregoing description, the notation
     * <tt>sgn(</tt>*expression*<tt>)</tt> designates the mathematical
     * *signum* function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * *expression* is negative, zero or positive.
     *
     *

     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)
     *
     *

     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.
     *
     *

     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.
     *
     *

     * It is generally the case, but *not* strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."

     * @param o1 the first object to be compared.
     * *
     * @param o2 the second object to be compared.
     * *
     * @return a negative integer, zero, or a positive integer as the
     * *         first argument is less than, equal to, or greater than the
     * *         second.
     * *
     * @throws NullPointerException if an argument is null and this
     * *         comparator does not permit null arguments
     * *
     * @throws ClassCastException if the arguments' types prevent them from
     * *         being compared by this comparator.
     */
    @Throws(NullPointerException::class, ClassCastException::class)
    override fun compare(o1: Hashtable<String, Any>?, o2: Hashtable<String, Any>?): Int {
        if (MapUtils.getBooleanValue(o1, "is_dir") && !MapUtils.getBooleanValue(o2, "is_dir")) {
            return -1;
        } else if (!MapUtils.getBooleanValue(o1, "is_dir") && MapUtils.getBooleanValue(o2, "is_dir")) {
            return 1;
        } else {
            if (MapUtils.getLongValue(o1, "filesize") > MapUtils.getLongValue(o2, "filesize")) {
                return 1;
            } else if (MapUtils.getLongValue(o1, "filesize") < MapUtils.getLongValue(o2, "filesize")) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}