package org.labcrypto.avicenna.samples.points;

import org.labcrypto.avicenna.Dependency;
import org.labcrypto.avicenna.DependencyFactory;
import org.labcrypto.avicenna.Singleton;

import java.util.Random;

@DependencyFactory
public class PointsDependencyFactory {

    @Singleton
    @Dependency
    public Point getSingletonPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100),
                random.nextInt(100));
    }

    @Dependency
    public Point getPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100),
                random.nextInt(100));
    }

}
