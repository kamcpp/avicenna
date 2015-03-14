package org.labcrypto.avicenna.samples.hello_world;

import org.labcrypto.avicenna.Avicenna;

public class Test {
    public static void main(String[] args) {
        Avicenna.addDependencyFactory(new HelloWorldDependencyFactory());
        Sample sample = new Sample();
        sample.sayHelloWorld();
    }
}
