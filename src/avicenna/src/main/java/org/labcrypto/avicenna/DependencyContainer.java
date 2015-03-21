/**

 Avicenna Dependency Injection Framework
 Copyright (C) 2015  Kamran Amini

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 */

package org.labcrypto.avicenna;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
class DependencyContainer {

    private Map<Class, DependencySource> dependencySources;

    public DependencyContainer() {
        dependencySources = new Hashtable<Class, DependencySource>();
    }

    public void add(Class clazz, DependencySource dependencySource) {
        if (dependencySources.containsKey(clazz)) {
            throw new AvicennaRuntimeException("There exist an entry for type: " + clazz);
        }
        dependencySources.put(clazz, dependencySource);
    }

    public Object get(Class clazz) {
        if (!dependencySources.containsKey(clazz)) {
            throw new AvicennaRuntimeException("There is no registered object for this type " + clazz);
        }
        return dependencySources.get(clazz).getObject();
    }

    public void clear() {
        dependencySources.clear();
    }
}
