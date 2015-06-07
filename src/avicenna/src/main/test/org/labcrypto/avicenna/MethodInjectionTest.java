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

import java.util.Random;

import static org.junit.Assert.*;

@DependencyFactory
class MethodInjectionDependencyFactory {

    @Dependency
    public Point getSingletonPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100), random.nextInt(100));
    }
}

class MethodInjectionFirstTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

class MethodInjectionSecondTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

/**
 * @author Kamran Amini  kam.cpp@gmail.com
 */
public class MethodInjectionTest {

    @Test
    public void testNotNull() {
        Avicenna.clear();
        Avicenna.addDependencyFactory(new SingletonDependencyFactory());

        MethodInjectionFirstTarget methodInjectionFirstTarget = new MethodInjectionFirstTarget();
        Avicenna.inject(methodInjectionFirstTarget);
        assertNotNull(methodInjectionFirstTarget.getPoint());
    }

    @Test
    public void testMethodInjection() {
        Avicenna.clear();
        Avicenna.addDependencyFactory(MethodInjectionDependencyFactory.class);

        MethodInjectionFirstTarget methodInjectionFirstTarget = new MethodInjectionFirstTarget();
        MethodInjectionSecondTarget methodInjectionSecondTarget = new MethodInjectionSecondTarget();
        Avicenna.inject(methodInjectionFirstTarget);
        Avicenna.inject(methodInjectionSecondTarget);
        assertTrue(methodInjectionFirstTarget.getPoint() != methodInjectionSecondTarget.getPoint());
    }
}
