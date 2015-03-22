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
class DependencyFactory1 {

    @Dependency
    private String message = "Hello ...";
}

@DependencyFactory
class DependencyFactory2 {

    @Dependency
    @Singleton
    public String getMessage() {
        return "Bye world ...";
    }
}

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
public class DuplicateDependecnySourcesExceptionTest {

    @Test
    public void testDuplicateDependencySources() {
        try {
            Avicenna.clear();
            Avicenna.addDependencyFactory(new DependencyFactory1());
            Avicenna.addDependencyFactory(new DependencyFactory2());
            assertTrue("Duplicate dependency sources must not be allowed.", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
