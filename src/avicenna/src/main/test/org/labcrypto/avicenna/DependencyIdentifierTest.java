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

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

interface DID<A, B> {
}

class DIDTest {

    private DID<Random, Date> field1;
    private DID field2;
    private String field3;

    public DID<String, Double> method1() {
        return null;
    }

    public DID<String, Integer> method2() {
        return null;
    }

    public String method3() {
        return null;
    }

    public DID<Random, Date> method4() {
        return null;
    }
}

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
public class DependencyIdentifierTest {

    @Test
    public void testNonGenericReturnType() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getMethod("method3").getGenericReturnType());
        assertEquals(dependencyIdentifier.toString(), "java.lang.String");
    }

    @Test
    public void testGenericReturnType() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getMethod("method1").getGenericReturnType());
        assertEquals(dependencyIdentifier.toString(),
                "org.labcrypto.avicenna.DID-java.lang.String-java.lang.Double");
    }

    @Test
    public void testNonGenericField() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field3").getGenericType());
        assertEquals(dependencyIdentifier.toString(), "java.lang.String");
    }

    @Test
    public void testNonGenericField2() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field2").getGenericType());
        assertEquals(dependencyIdentifier.toString(), "org.labcrypto.avicenna.DID");
    }

    @Test
    public void testGenericField() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field1").getGenericType());
        assertEquals(dependencyIdentifier.toString(),
                "org.labcrypto.avicenna.DID-java.util.Random-java.util.Date");
    }

    @Test
    public void testEquality() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field1").getGenericType());
        DependencyIdentifier dependencyIdentifier2 = DependencyIdentifier
                .getDependencyIdentifierForClass(getMethod("method4").getGenericReturnType());
        assertEquals(dependencyIdentifier, dependencyIdentifier2);
        assertEquals(dependencyIdentifier2, dependencyIdentifier);
    }

    private Method getMethod(String name) {
        try {
            return DIDTest.class.getMethod(name, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Field getField(String name) {
        try {
            return DIDTest.class.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
