package com.jakobk.stream;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SequenceTest {

    @Test
    public void testSequence0To100Step1() throws Exception {
        Assert.assertEquals(100, Sequence.stream(0, 100).count());
        Assert.assertEquals(99, Sequence.stream(0, 100).max().getAsInt());
        Assert.assertEquals(0, Sequence.stream(0, 100).min().getAsInt());
    }

    @Test
    public void testSequence0To100Step2() throws Exception {
        Assert.assertEquals(50, Sequence.stream(0, 100, 2).count());
        Assert.assertEquals(98, Sequence.stream(0, 100, 2).max().getAsInt());
        Assert.assertEquals(0, Sequence.stream(0, 100, 2).min().getAsInt());
    }

}
