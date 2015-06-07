package org.labcrypto.avicenna;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: avicenna
 * Created on 6/7/15
 * Created by Kamran Amini <amini@sahab.ir>
 * Reviewed by Mohammad Reza Kargar <kargar@sahab.ir>
 */
public class DependencyDefinitionTest {

    @Test
    public void testSimpleInjection() {
        Avicenna.clear();
        Avicenna.defineDependency(String.class, "ABC");

        class InjectionTarget {
            @InjectHere
            String message;
        }

        InjectionTarget injectionTarget = new InjectionTarget();
        Avicenna.inject(injectionTarget);
        Assert.assertEquals("ABC", injectionTarget.message);
    }

    @Test
    public void testExceptionOnDuplication() {
        Avicenna.clear();
        Avicenna.defineDependency(String.class, "ABC");
        try {
            Avicenna.defineDependency(String.class, "XYZ");
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGet() {
        Avicenna.clear();
        Avicenna.defineDependency(Integer.class, 24);
        Assert.assertEquals(24, Avicenna.get(Integer.class));
    }

    @Test
    public void testUsingQualifiers() {
        Avicenna.clear();
        Avicenna.defineDependency(String.class, "ABC");
        List<String> qs = new ArrayList<String>();
        qs.add("X");
        Avicenna.defineDependency(String.class, qs, "DEF");

        Assert.assertEquals("ABC", Avicenna.get(String.class));
        Assert.assertEquals("DEF", Avicenna.get(String.class, qs));
    }
}
