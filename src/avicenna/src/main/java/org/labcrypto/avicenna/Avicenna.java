/**
 * Avicenna Dependency Injection Framework
 * Copyright 2015 Kamran Amini
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.labcrypto.avicenna;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Main class for loading dependencies and injection purposes. This class
 * should be used statically. It will extract {@link org.labcrypto.avicenna.Dependency}
 * references from {@link org.labcrypto.avicenna.DependencyFactory} objects and inject
 * them into fields annotated by {@link org.labcrypto.avicenna.InjectHere}.
 *
 * @author Kamran Amini  kam.cpp@gmail.com
 * @see org.labcrypto.avicenna.Dependency
 * @see org.labcrypto.avicenna.DependencyFactory
 * @see org.labcrypto.avicenna.InjectHere
 */
public class Avicenna {

    /**
     * Dependency container used for keeping all dependency references.
     */
    private static DependencyContainer dependencyContainer;
    private static SortedSet<String> qualifiers;
    private static Set<Class> dependencyFactories;

    static {
        dependencyContainer = new DependencyContainer();
        qualifiers = new TreeSet<String>();
        dependencyFactories = new HashSet<Class>();
    }

    /**
     * Adds classes annotated by {@link org.labcrypto.avicenna.DependencyFactory}. Added class
     * has some methods or fields which supplied dependencies.
     *
     * @param dependencyFactoryClasses An object annotated by {@link org.labcrypto.avicenna.DependencyFactory}.
     */
    public static void addDependencyFactory(Class... dependencyFactoryClasses) {
        for (Class dependencyFactoryClass : dependencyFactoryClasses) {
            if (!dependencyFactoryClass.isAnnotationPresent(DependencyFactory.class)) {
                throw new AvicennaRuntimeException(
                        dependencyFactoryClass + " should be annotated with DependencyFactory.");
            }
            if (Avicenna.dependencyFactories.contains(dependencyFactoryClass)) {
                throw new AvicennaRuntimeException(
                        dependencyFactoryClass + " has already been added to container.");
            }
            try {
                addDependencyFactoryToContainer(dependencyFactoryClass.newInstance());
                Avicenna.dependencyFactories.add(dependencyFactoryClass);
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Adds objects annotated by {@link org.labcrypto.avicenna.DependencyFactory}. Added object
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
            if (Avicenna.dependencyFactories.contains(dependencyFactory.getClass())) {
                throw new AvicennaRuntimeException(
                        dependencyFactory.getClass() + " has already been added to container.");
            }
            addDependencyFactoryToContainer(dependencyFactory);
            Avicenna.dependencyFactories.add(dependencyFactory.getClass());
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
                for (Field field : ReflectionHelper.getFields(clazz)) {
                    if (field.isAnnotationPresent(InjectHere.class)) {
                        qualifiers.clear();
                        for (Annotation annotation : field.getAnnotations()) {
                            if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                                qualifiers.add(annotation.annotationType().getCanonicalName());
                            }
                        }
                        field.setAccessible(true);
                        field.set(object, dependencyContainer.get(DependencyIdentifier
                                .getDependencyIdentifierForClass(field, qualifiers)));
                    }
                }
            }
        } catch (Exception e) {
            throw new AvicennaRuntimeException(e);
        }
    }

    /**
     * Clears the container.
     */
    public static void clear() {
        dependencyContainer.clear();
        qualifiers.clear();
        dependencyFactories.clear();
    }

    /**
     * Adds a direct mapping between a type and an object.
     *
     * @param clazz      Class type which should be injected.
     * @param dependency Dependency reference which should be copied to injection targets.
     */
    public static <T> void defineDependency(Class<T> clazz, T dependency) {
        defineDependency(clazz, null, dependency);
    }

    /**
     * Adds a direct mapping between a type and an object.
     *
     * @param clazz      Class type which should be injected.
     * @param qualifiers Qualifiers to distinguish between similar types.
     * @param dependency Dependency reference which should be copied to injection targets.
     */
    public static <T> void defineDependency(Class<T> clazz, Collection<String> qualifiers, T dependency) {
        SortedSet<String> qs = new TreeSet<String>();
        if (qualifiers != null) {
            qs.addAll(qualifiers);
        }
        dependencyContainer.add(DependencyIdentifier
                        .getDependencyIdentifierForClass(clazz, qs),
                new DependencySource(clazz, dependency));
    }

    /**
     * Retrieves a stored dependnecy using class object and qualifiers.
     *
     * @param clazz Class type which its related dependency reference should be retrieved.
     */
    public static <T> T get(Class<T> clazz) {
        return get(clazz, null);
    }

    /**
     * Retrieves a stored dependnecy using class object and qualifiers.
     *
     * @param clazz      Class type which its related dependency reference should be retrieved.
     * @param qualifiers List of qualifiers to distinguish between similar types.
     */
    public static <T> T get(Class<T> clazz, Collection<String> qualifiers) {
        SortedSet<String> qs = new TreeSet<String>();
        if (qualifiers != null) {
            qs.addAll(qualifiers);
        }
        return dependencyContainer.get(DependencyIdentifier.getDependencyIdentifierForClass(clazz, qs));
    }

    /**
     * Internal method which iterates over fields and methods to find and store
     * dependency producers.
     */
    private static void addDependencyFactoryToContainer(Object dependencyFactory) {
        try {
            Class clazz = dependencyFactory.getClass();
            for (Field field : ReflectionHelper.getFields(clazz)) {
                if (field.isAnnotationPresent(Dependency.class)) {
                    qualifiers.clear();
                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                            qualifiers.add(annotation.annotationType().getCanonicalName());
                        }
                    }
                    dependencyContainer.add(DependencyIdentifier
                                    .getDependencyIdentifierForClass(field, qualifiers),
                            new DependencySource(DependencySource.DependencySourceType.FIELD,
                                    field,
                                    null,
                                    dependencyFactory,
                                    field.isAnnotationPresent(Singleton.class)));
                }
            }
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Dependency.class)) {
                    qualifiers.clear();
                    for (Annotation annotation : method.getAnnotations()) {
                        if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                            qualifiers.add(annotation.annotationType().getCanonicalName());
                        }
                    }
                    dependencyContainer.add(DependencyIdentifier
                                    .getDependencyIdentifierForClass(method, qualifiers),
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
}
