/*
 * Copyright 2014, Jakob Korherr
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.jakobk.stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void testDemonstration() throws Exception {
        // classic Java
        List<Person> persons = new ArrayList<Person>(100);
        for (int i = 0; i < 100; i++) {
            persons.add(new Person(i));
        }

        // Java 8 + Sequence
        List<Person> persons2 = Sequence.stream(0, 100).mapToObj(Person::new).collect(Collectors.toList());

        // both should produce the same result
        Assert.assertTrue(Arrays.deepEquals(persons.toArray(), persons2.toArray()));
    }

    public static class Person {

        private final int id;

        public Person(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;

            Person person = (Person) o;

            if (id != person.id) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    '}';
        }
    }


}
