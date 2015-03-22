/**

 Avicenna Dependency Injection Framework
 Copyright 2015 Kamran Amini

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

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
