package org.labcrypto.avicenna;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DependencySource {

    public enum DependencySourceType {
        FIELD,
        METHOD
    }

    private boolean singleton;
    private boolean generated;
    private Field field;
    private Method method;
    private Object factoryObject;
    private Object object;
    private DependencySourceType dependencySourceType;

    public DependencySource(DependencySourceType dependencySourceType,
                            Field field,
                            Method method,
                            Object factoryObject,
                            boolean singleton) {
        this.singleton = singleton;
        this.field = field;
        this.method = method;
        this.factoryObject = factoryObject;
        this.dependencySourceType = dependencySourceType;
    }

    public Object getObject() {
        if (singleton && generated) {
            return object;
        }
        try {
            switch (dependencySourceType) {
                case FIELD:
                    field.setAccessible(true);
                    object = field.get(factoryObject);
                    break;
                case METHOD:
                    object = method.invoke(factoryObject, new Object[] {});
                    break;
                default:
                    throw new AvicennaRuntimeException("Dependency source type is not supported.");
            }
        } catch (Exception e) {
            throw new AvicennaRuntimeException(e);
        }
        generated = true;
        return object;
    }
}
