package org.labcrypto.avicenna;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

@DependencyFactory
class MethodInjectionDependencyFactory {

    @Dependency
    public Point getSingletonPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100), random.nextInt(100));
    }
}

class MethodInjectionFirstTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

class MethodInjectionSecondTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

public class MethodInjectionTest {

    @Test
    public void testNotNull() {
        Avicenna.addDependencyFactory(new SingletonDependenctFactory());

        MethodInjectionFirstTarget methodInjectionFirstTarget = new MethodInjectionFirstTarget();

        Avicenna.inject(methodInjectionFirstTarget);

        assertNotNull(methodInjectionFirstTarget.getPoint());
    }

    @Test
    public void testMethodInjection() {
        Avicenna.addDependencyFactory(new MethodInjectionDependencyFactory());

        MethodInjectionFirstTarget methodInjectionFirstTarget = new MethodInjectionFirstTarget();
        MethodInjectionSecondTarget methodInjectionSecondTarget = new MethodInjectionSecondTarget();

        Avicenna.inject(methodInjectionFirstTarget);
        Avicenna.inject(methodInjectionSecondTarget);

        assertTrue(methodInjectionFirstTarget.getPoint() != methodInjectionSecondTarget.getPoint());
    }
}
