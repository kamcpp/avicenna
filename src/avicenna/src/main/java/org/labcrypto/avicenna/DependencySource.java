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
