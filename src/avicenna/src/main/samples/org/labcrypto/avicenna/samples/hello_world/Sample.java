package org.labcrypto.avicenna.samples.hello_world;

import org.labcrypto.avicenna.Avicenna;
import org.labcrypto.avicenna.InjectHere;

public class Sample {

    @InjectHere
    private String message;

    public Sample() {
        Avicenna.inject(this);
    }

    public void sayHelloWorld() {
        System.out.println(message);
    }
}
