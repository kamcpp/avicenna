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
class SingletonDependencyFactory {

    @Singleton
    @Dependency
    public Point getSingletonPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100), random.nextInt(100));
    }
}

class SingletonFirstTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

class SingletonSecondTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

/**
 * @author Kamran Amini  <kam.cpp@gmail.com>
 */
public class SingletonTest {

    @Test
    public void testNotNull() {
        Avicenna.clear();
        Avicenna.addDependencyFactory(new SingletonDependencyFactory());

        SingletonFirstTarget singletonFirstTarget = new SingletonFirstTarget();
        Avicenna.inject(singletonFirstTarget);
        assertNotNull(singletonFirstTarget.getPoint());
    }

    @Test
    public void testSingleton() {
        Avicenna.clear();
        Avicenna.addDependencyFactory(new SingletonDependencyFactory());

        SingletonFirstTarget singletonFirstTarget = new SingletonFirstTarget();
        SingletonSecondTarget singletonSecondTarget = new SingletonSecondTarget();
        Avicenna.inject(singletonFirstTarget);
        Avicenna.inject(singletonSecondTarget);
        assertTrue(singletonFirstTarget.getPoint() == singletonSecondTarget.getPoint());
    }
}
