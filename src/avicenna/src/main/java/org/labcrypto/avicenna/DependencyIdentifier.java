/**

 ???????????????????????????????????
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class makes an identifier for a given type. We use identifiers to distinguish different
 * types while making maps for injection purposes. This class is mainly used in
 * {@link org.labcrypto.avicenna.DependencyContainer} class.
 *
 * @author Kamran Amini  <kam.cpp@gmail.com>
 * @see org.labcrypto.avicenna.DependencyContainer
 */
class DependencyIdentifier {

    /**
     * TODO
     *
     * @param type
     * @return
     */
    public static DependencyIdentifier getDependencyIdentifierForClass(Type type) {
        List<Type> typeList = new ArrayList<Type>();
        addTypeToList(type, typeList);
        return new DependencyIdentifier(typeList);
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

    public DependencyIdentifier(List<Type> types) {
        identifier = "";
        String delim = "";
        for (Type type : types) {
            String typeString = "" + type;
            typeString = typeString.split(" ")[1];
            identifier += delim + typeString;
            delim = "-";
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
