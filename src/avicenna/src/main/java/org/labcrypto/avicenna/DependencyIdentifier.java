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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

/**
 * This class makes an identifier for a given type. We use identifiers to distinguish different
 * types while making maps for injection purposes. This class is mainly used in
 * {@link org.labcrypto.avicenna.DependencyContainer} class.
 *
 * @author Kamran Amini  kam.cpp@gmail.com
 * @see org.labcrypto.avicenna.DependencyContainer
 */
class DependencyIdentifier {

    /**
     * TODO
     *
     * @param method
     * @param qualifiers
     * @return
     */
    public static DependencyIdentifier getDependencyIdentifierForClass(Method method, SortedSet<String> qualifiers) {
        List<Type> typeList = new ArrayList<Type>();
        addTypeToList(method.getGenericReturnType(), typeList);
        return new DependencyIdentifier(typeList, qualifiers);
    }

    /**
     * TODO
     *
     * @param field
     * @param qualifiers
     * @return
     */
    public static DependencyIdentifier getDependencyIdentifierForClass(Field field, SortedSet<String> qualifiers) {
        List<Type> typeList = new ArrayList<Type>();
        addTypeToList(field.getGenericType(), typeList);
        return new DependencyIdentifier(typeList, qualifiers);
    }

    /**
     * TODO
     *
     * @param type
     * @param typeList
     */
    private static void addTypeToList(Type type, List<Type> typeList) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            addTypeToList(parameterizedType.getRawType(), typeList);
            for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                addTypeToList(actualTypeArgument, typeList);
            }
        } else {
            typeList.add(type);
        }
    }

    /**
     * TODO
     */
    public String identifier;

    public DependencyIdentifier(List<Type> types, SortedSet<String> qualifiers) {
        identifier = "";
        String delim = "";
        for (Type type : types) {
            String typeString = "" + type;
            typeString = typeString.split(" ")[1];
            identifier += delim + typeString;
            delim = "-";
        }
        if (qualifiers != null) {
            for (String qualifier : qualifiers) {
                identifier += delim + qualifier;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DependencyIdentifier that = (DependencyIdentifier) o;
        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return identifier;
    }
}
