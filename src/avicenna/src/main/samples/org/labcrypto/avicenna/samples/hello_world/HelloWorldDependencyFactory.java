package org.labcrypto.avicenna.samples.hello_world;

import org.labcrypto.avicenna.Dependency;
import org.labcrypto.avicenna.DependencyFactory;
import org.labcrypto.avicenna.samples.points.Point;

import java.util.Random;

@DependencyFactory
public class HelloWorldDependencyFactory {

    @Dependency
    public Point getSingletonPoint() {
        Random random = new Random();
        return new Point(random.nextInt(100),
                random.nextInt(100));
    }
}
