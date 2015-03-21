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
