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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Main class for loading dependencies and injection purposes. This class
 * should be used statically. It will extract {@link org.labcrypto.avicenna.Dependency}
 * references from {@link org.labcrypto.avicenna.DependencyFactory} objects and inject
 * them into fields annotated by {@link org.labcrypto.avicenna.InjectHere}.
 *
 * @author Kamran Amini  <kam.cpp@gmail.com>
 * @see org.labcrypto.avicenna.Dependency
 * @see org.labcrypto.avicenna.DependencyFactory
 * @see org.labcrypto.avicenna.InjectHere
 */
public class Avicenna {

    /**
     * Dependency container used for keeping all dependency references.
     */
    private static DependencyContainer dependencyContainer;

    static {
        dependencyContainer = new DependencyContainer();
    }

    /**
     * Adds an object annotated by {@link org.labcrypto.avicenna.DependencyFactory}. Added object
     * has some methods or fields which supplied dependencies.
     *
     * @param dependencyFactories An object annotated by {@link org.labcrypto.avicenna.DependencyFactory}.
     */
    public static void addDependencyFactory(Object... dependencyFactories) {
        for (Object dependencyFactory : dependencyFactories) {
            if (!dependencyFactory.getClass().isAnnotationPresent(DependencyFactory.class)) {
                throw new AvicennaRuntimeException(
                        dependencyFactory.getClass() + " should be annotated with DependencyFactory.");
            }
            addDependencyFactoryToContainer(dependencyFactory);
        }
    }

    /**
     * Internal method which iterates over fields and methods to find and store
     * dependency producers.
     */
    private static void addDependencyFactoryToContainer(Object dependencyFactory) {
        try {
            Class clazz = dependencyFactory.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Dependency.class)) {
                    dependencyContainer.add(field.getType(),
                            new DependencySource(DependencySource.DependencySourceType.FIELD,
                                    field,
                                    null,
                                    dependencyFactory,
                                    field.isAnnotationPresent(Singleton.class)));
                }
            }
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Dependency.class)) {
                    dependencyContainer.add(method.getReturnType(),
                            new DependencySource(DependencySource.DependencySourceType.METHOD,
                                    null,
                                    method,
                                    dependencyFactory,
                                    method.isAnnotationPresent(Singleton.class)));
                }
            }
        } catch (Exception e) {
            throw new AvicennaRuntimeException(e);
        }
    }

    /**
     * Whenever this method is called, all objects in input argument are searched for
     * annotated fields. Then, the fields are injected by dependency objects.
     *
     * @param objects List of objects which they should be injected.
     */
    public static void inject(Object... objects) {
        try {
            for (Object object : objects) {
                Class clazz = object.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(InjectHere.class)) {
                        field.setAccessible(true);
                        field.set(object, dependencyContainer.get(field.getType()));
                    }
                }
            }
        } catch (Exception e) {
            throw new AvicennaRuntimeException(e);
        }
    }
}
