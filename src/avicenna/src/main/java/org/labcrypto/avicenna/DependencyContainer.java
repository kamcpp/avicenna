package org.labcrypto.avicenna;

import java.util.Hashtable;
import java.util.Map;

public class DependencyContainer {

    private Map<Class, DependencySource> dependencySources;

    public DependencyContainer() {
        dependencySources = new Hashtable<Class, DependencySource>();
    }

    public void add(Class clazz, DependencySource dependencySource) {
        dependencySources.put(clazz, dependencySource);
    }

    public Object get(Class clazz) {
        if (!dependencySources.containsKey(clazz)) {
            throw new AvicennaRuntimeException("There is no registered object for this type " + clazz);
        }
        return dependencySources.get(clazz).getObject();
    }
}
