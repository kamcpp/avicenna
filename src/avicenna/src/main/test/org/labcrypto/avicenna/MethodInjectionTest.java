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

public class MethodInjectionTest {

    @Test
    public void testNotNull() {
        Avicenna.addDependencyFactory(new SingletonDependencyFactory());

        MethodInjectionFirstTarget methodInjectionFirstTarget = new MethodInjectionFirstTarget();

        Avicenna.inject(methodInjectionFirstTarget);

        assertNotNull(methodInjectionFirstTarget.getPoint());
    }

    @Test
    public void testMethodInjection() {
        Avicenna.addDependencyFactory(new MethodInjectionDependencyFactory());

        MethodInjectionFirstTarget methodInjectionFirstTarget = new MethodInjectionFirstTarget();
        MethodInjectionSecondTarget methodInjectionSecondTarget = new MethodInjectionSecondTarget();

        Avicenna.inject(methodInjectionFirstTarget);
        Avicenna.inject(methodInjectionSecondTarget);

        assertTrue(methodInjectionFirstTarget.getPoint() != methodInjectionSecondTarget.getPoint());
    }
}
