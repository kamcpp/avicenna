package org.labcrypto.avicenna.samples.hello_world;

import org.labcrypto.avicenna.Avicenna;

public class Sample {
    public static void main(String[] args) {

        Avicenna.addDependencyFactory(new HelloWorldDependencyFactory());
    }
}
