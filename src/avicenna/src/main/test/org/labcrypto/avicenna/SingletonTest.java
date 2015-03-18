package org.labcrypto.avicenna;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

@DependencyFactory
class SingletonDependenctFactory {

    @Singleton
    @Dependency
    public Point getSingletonPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100), random.nextInt(100));
    }
}

class SingletonFirstTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

class SingletonSecondTarget {

    @InjectHere
    private Point point;

    public Point getPoint() {
        return point;
    }
}

public class SingletonTest {

    @Test
    public void testNotNull() {
        Avicenna.addDependencyFactory(new SingletonDependenctFactory());

        SingletonFirstTarget singletonFirstTarget = new SingletonFirstTarget();

        Avicenna.inject(singletonFirstTarget);

        assertNotNull(singletonFirstTarget.getPoint());
    }

    @Test
    public void testSingleton() {
        Avicenna.addDependencyFactory(new SingletonDependenctFactory());

        SingletonFirstTarget singletonFirstTarget = new SingletonFirstTarget();
        SingletonSecondTarget singletonSecondTarget = new SingletonSecondTarget();

        Avicenna.inject(singletonFirstTarget);
        Avicenna.inject(singletonSecondTarget);

        assertTrue(singletonFirstTarget.getPoint() == singletonSecondTarget.getPoint());
    }
}
