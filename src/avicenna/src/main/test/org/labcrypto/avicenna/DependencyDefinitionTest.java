package org.labcrypto.avicenna;

import org.junit.Assert;
import org.junit.Test;

/**
 * Project: avicenna
 * Created on 6/7/15
 * Created by Kamran Amini <amini@sahab.ir>
 * Reviewed by Mohammad Reza Kargar <kargar@sahab.ir>
 */
public class DependencyDefinitionTest {

    @Test
    public void test1() {
        Avicenna.clear();
        Avicenna.defineDependency(String.class, "ABCDEF");

        class InjectionTarget {
            @InjectHere
            String message;
        }

        InjectionTarget injectionTarget = new InjectionTarget();
        Avicenna.inject(injectionTarget);
        Assert.assertEquals("ABCDEF", injectionTarget.message);
    }
}
