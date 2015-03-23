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


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assert.*;

import org.junit.Test;

@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface QualifierTestFirst {
}

@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface QualifierTestSecond {
}

@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface QualifierTestThird {
}

@DependencyFactory
class QualifierTestDependencyFactory {

    @Dependency
    @QualifierTestFirst
    public String byeMessage = "Bye World.";

    @Dependency
    @QualifierTestSecond
    public String getHelloMessage() {
        return "Hello World.";
    }

    @Dependency
    @QualifierTestSecond
    @QualifierTestThird
    public String getNothingMessage() {
        return "Nothing.";
    }

}

class QualifierTestTarget {

    @InjectHere
    @QualifierTestSecond
    private String helloMessage;

    @InjectHere
    @QualifierTestFirst
    private String byeMessage;

    @InjectHere
    @QualifierTestThird
    @QualifierTestSecond
    private String nothingMessage;

    public String getHelloMessage() {
        return helloMessage;
    }

    public String getByeMessage() {
        return byeMessage;
    }

    public String getNothingMessage() {
        return nothingMessage;
    }
}

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
public class QualifiersTest {

    @Test
    public void testQualifiers() {
        Avicenna.clear();
        Avicenna.addDependencyFactory(new QualifierTestDependencyFactory());

        QualifierTestTarget qualifierTestTarget = new QualifierTestTarget();
        Avicenna.inject(qualifierTestTarget);

        assertEquals("Hello World.", qualifierTestTarget.getHelloMessage());
        assertEquals("Bye World.", qualifierTestTarget.getByeMessage());
        assertEquals("Nothing.", qualifierTestTarget.getNothingMessage());
    }
}
