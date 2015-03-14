package org.labcrypto.avicenna;

public class Avicenna {

    public static void addDependencyFactory(Object... dependencyFactories) {
        for (Object dependencyFactory : dependencyFactories) {
            if (!dependencyFactory.getClass().isAnnotationPresent(DependencyFactory.class)) {
                throw new AvicennaRuntimeException(
                        dependencyFactory.getClass() + " should be annotated as a DependencyFactory.");
            }
        }
    }

    public void inject(Object... objects) {
        // TODO
    }
}
