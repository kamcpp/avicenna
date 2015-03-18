package org.labcrypto.avicenna;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Avicenna {

    private static DependencyContainer dependencyContainer;

    static {
        dependencyContainer = new DependencyContainer();
    }

    public static void addDependencyFactory(Object... dependencyFactories) {
        for (Object dependencyFactory : dependencyFactories) {
            if (!dependencyFactory.getClass().isAnnotationPresent(DependencyFactory.class)) {
                throw new AvicennaRuntimeException(
                        dependencyFactory.getClass() + " should be annotated as a DependencyFactory.");
            }
            addDependencyFactoryToContainer(dependencyFactory);
        }
    }

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
