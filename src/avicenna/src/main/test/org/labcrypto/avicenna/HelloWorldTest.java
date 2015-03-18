package org.labcrypto.avicenna;

import org.junit.Test;

import static org.junit.Assert.*;

@DependencyFactory
class HelloWorldDependencyFactory {

    @Dependency
    private String helloWorldMessage = "Hello World ...";
}

class HelloWorldTarget {

    @InjectHere
    private String message;

    public String getMessage() {
        return message;
    }
}

public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        HelloWorldTarget helloWorldTarget = new HelloWorldTarget();
        Avicenna.addDependencyFactory(new HelloWorldDependencyFactory());
        Avicenna.inject(helloWorldTarget);
        assertEquals("Hello World ...", helloWorldTarget.getMessage());
    }
}
