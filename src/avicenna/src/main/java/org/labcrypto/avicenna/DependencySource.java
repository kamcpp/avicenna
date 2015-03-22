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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
class DependencySource {

    public enum DependencySourceType {
        FIELD,
        METHOD
    }

    private boolean singleton;
    private boolean generated;
    private Field field;
    private Method method;
    private Object factoryObject;
    private Object object;
    private DependencySourceType dependencySourceType;

    public DependencySource(DependencySourceType dependencySourceType,
                            Field field,
                            Method method,
                            Object factoryObject,
                            boolean singleton) {
        this.singleton = singleton;
        this.field = field;
        this.method = method;
        this.factoryObject = factoryObject;
        this.dependencySourceType = dependencySourceType;
    }

    public Object getObject() {
        if (singleton && generated) {
            return object;
        }
        try {
            switch (dependencySourceType) {
                case FIELD:
                    field.setAccessible(true);
                    object = field.get(factoryObject);
                    break;
                case METHOD:
                    object = method.invoke(factoryObject, new Object[] {});
                    break;
                default:
                    throw new AvicennaRuntimeException("Dependency source type is not supported.");
            }
        } catch (Exception e) {
            throw new AvicennaRuntimeException(e);
        }
        generated = true;
        return object;
    }
}
