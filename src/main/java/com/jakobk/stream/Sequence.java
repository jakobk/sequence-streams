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

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Implementation of {@link Spliterator.OfInt}, which generates a (lazy) sequence of integers from start to end
 * with a specific step size.
 *
 * Using this class, a classical for-loop can be performed with the new stream API of Java 8.
 * Consider the following example:
 * <pre>{@code
 *   List<Person> persons = new ArrayList<Person>(100);
 *   for (int i = 0; i < 100; i++) {
 *       persons.add(new Person(i));
 *   }
 *   // use persons ...
 * }</pre>
 *
 * This can be done using {@link Sequence} in the following way:
 * <pre>{@code
 *   List<Person> persons = Sequence.stream(0, 100).mapToObj(Person::new).collect(Collectors.toList());
 *   // use persons ...
 * }</pre>
 *
 *
 * @author Jakob Korherr
 */
public class Sequence implements Spliterator.OfInt {

    public static IntStream stream(int start, int end) {
        return StreamSupport.intStream(new Sequence(start, end), false);
    }

    public static IntStream stream(int start, int end, int step) {
        return StreamSupport.intStream(new Sequence(start, end, step), false);
    }

    public static IntStream parallelStream(int start, int end) {
        return StreamSupport.intStream(new Sequence(start, end), true);
    }

    public static IntStream parallelStream(int start, int end, int step) {
        return StreamSupport.intStream(new Sequence(start, end, step), true);
    }

    private int start;
    private final int end;
    private final int step;

    public Sequence(int start, int end) {
        this(start, end, 1);
    }

    public Sequence(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (start < end) {
            action.accept(start);
            start += step;
            return true;
        }
        return false;
    }

    @Override
    public OfInt trySplit() {
        if (start >= end) {
            return null; // no split possible
        }
        Sequence prefix = new Sequence(start, start + (((end - start) / step / 2) * step) - 1, step);
        this.start = prefix.end + 1;
        return prefix;
    }

    @Override
    public long estimateSize() {
        return (end - start) / step;
    }

    @Override
    public int characteristics() {
        return ORDERED | DISTINCT | SORTED | IMMUTABLE | NONNULL | SIZED;
    }

    @Override
    public Comparator<? super Integer> getComparator() {
        return Integer::compare;
    }
}
