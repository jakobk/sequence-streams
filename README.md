# Sequence Streams for Java 8

This project provides the class Sequence, which can be used to generate arbitrary integer sequences
for the new Stream API of Java 8. Using this class, a classical for-loop can be performed with the new stream API.

## Examples

Consider the following example:
```java
List<Person> persons = new ArrayList<Person>(100);
for (int i = 0; i < 100; i++) {
    persons.add(new Person(i));
}
// use persons ...
```

This can be done using Sequence in the following way:
```java
List<Person> persons = Sequence.stream(0, 100).mapToObj(Person::new).collect(Collectors.toList());
// use persons ...
```
