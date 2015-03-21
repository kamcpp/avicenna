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
