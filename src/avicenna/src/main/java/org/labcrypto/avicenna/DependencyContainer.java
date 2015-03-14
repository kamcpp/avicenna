package org.labcrypto.avicenna;

import java.lang.reflect.Member;
import java.util.Hashtable;
import java.util.Map;

public class DependencyContainer {

    private Map<Class, Member> members;
    private Map<Class, Object> objects;

    public DependencyContainer() {
        members = new Hashtable<Class, Member>();
        objects = new Hashtable<Class, Object>();
    }

    public void add(Class clazz, Object object) {
        objects.put(clazz, object);
    }

    public Object get(Class clazz) {
        if (objects.containsKey(clazz)) {
            throw new AvicennaRuntimeException("There is no registered object for this type " + clazz);
        }
        return objects.get(clazz);
    }
}
