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

public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        HelloWorldTarget helloWorldTarget = new HelloWorldTarget();
        Avicenna.addDependencyFactory(new HelloWorldDependencyFactory());
        Avicenna.inject(helloWorldTarget);
        assertEquals("Hello World ...", helloWorldTarget.getMessage());
    }
}
