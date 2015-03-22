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

    private Map<DependencyIdentifier, DependencySource> dependencySources;

    public DependencyContainer() {
        dependencySources = new Hashtable<DependencyIdentifier, DependencySource>();
    }

    public void add(DependencyIdentifier dependencyIdentifier, DependencySource dependencySource) {
        if (dependencySources.containsKey(dependencyIdentifier)) {
            throw new AvicennaRuntimeException("There exist an entry for dependency identifier: " + dependencyIdentifier);
        }
        dependencySources.put(dependencyIdentifier, dependencySource);
    }

    public Object get(DependencyIdentifier dependencyIdentifier) {
        if (!dependencySources.containsKey(dependencyIdentifier)) {
            throw new AvicennaRuntimeException("There is no registered object for this dependency identifier: " + dependencyIdentifier);
        }
        return dependencySources.get(dependencyIdentifier).getObject();
    }

    public void clear() {
        dependencySources.clear();
    }
}
