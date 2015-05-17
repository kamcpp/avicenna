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

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

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


@interface DIDQualifier1 {
}

@interface DIDQualifier2 {
}

/**
 * @author Kamran Amini  kam.cpp@gmail.com
 */
public class DependencyIdentifierTest {

    @Test
    public void testNonGenericReturnType() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getMethod("method3"), null);
        assertEquals("java.lang.String"
                , dependencyIdentifier.toString());
    }

    @Test
    public void testGenericReturnType() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getMethod("method1"), null);
        assertEquals("org.labcrypto.avicenna.DID-java.lang.String-java.lang.Double"
                , dependencyIdentifier.toString());
    }

    @Test
    public void testNonGenericField() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field3"), null);
        assertEquals("java.lang.String"
                , dependencyIdentifier.toString());
    }

    @Test
    public void testNonGenericField2() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field2"), null);
        assertEquals("org.labcrypto.avicenna.DID"
                , dependencyIdentifier.toString());
    }

    @Test
    public void testGenericField() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field1"), null);
        assertEquals("org.labcrypto.avicenna.DID-java.util.Random-java.util.Date"
                , dependencyIdentifier.toString());
    }

    @Test
    public void testEquality() {
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field1"), null);
        DependencyIdentifier dependencyIdentifier2 = DependencyIdentifier
                .getDependencyIdentifierForClass(getMethod("method4"), null);
        assertEquals(dependencyIdentifier, dependencyIdentifier2);
        assertEquals(dependencyIdentifier2, dependencyIdentifier);
    }

    @Test
    public void testQualifiers() {
        SortedSet<String> qualifiers = new TreeSet<String>();
        qualifiers.add(DIDQualifier2.class.getCanonicalName());
        qualifiers.add(DIDQualifier1.class.getCanonicalName());
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field3"), qualifiers);
        assertEquals("java.lang.String-org.labcrypto.avicenna.DIDQualifier1-org.labcrypto.avicenna.DIDQualifier2"
                , dependencyIdentifier.toString());

    }

    @Test
    public void testQualifiersOutOfOrder() {
        SortedSet<String> qualifiers = new TreeSet<String>();
        qualifiers.add(DIDQualifier1.class.getCanonicalName());
        qualifiers.add(DIDQualifier2.class.getCanonicalName());
        DependencyIdentifier dependencyIdentifier = DependencyIdentifier
                .getDependencyIdentifierForClass(getField("field3"), qualifiers);
        assertEquals("java.lang.String-org.labcrypto.avicenna.DIDQualifier1-org.labcrypto.avicenna.DIDQualifier2"
                , dependencyIdentifier.toString());

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
