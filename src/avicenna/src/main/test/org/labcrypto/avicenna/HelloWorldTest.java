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

import static org.junit.Assert.*;

@DependencyFactory
class HelloWorldDependencyFactory {

    @Dependency
    private String helloWorldMessage = "Hello World ...";
}

class HelloWorldTarget {

    @InjectHere
    private String message;

    public String getMessage() {
        return message;
    }
}

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        Avicenna.clear();
        Avicenna.addDependencyFactory(new HelloWorldDependencyFactory());

        HelloWorldTarget helloWorldTarget = new HelloWorldTarget();
        Avicenna.inject(helloWorldTarget);
        assertEquals("Hello World ...", helloWorldTarget.getMessage());
    }
}
