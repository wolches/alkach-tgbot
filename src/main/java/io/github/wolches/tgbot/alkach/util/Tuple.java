package io.github.wolches.tgbot.alkach.util;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Value
public class Tuple<A, B> {

    A a;
    B b;

    public static <A, B> Tuple<A, B> create(A a, B b) {
        return new Tuple<>(a, b);
    }

    public static <A, B> List<Tuple<A, B>> fromMap(Map<A, B> map) {
        return map.entrySet().stream()
                .map(entry -> create(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static <A, B> List<Tuple<A, B>> fromArrays(A[] a, B[] b) {
        if (a.length != b.length || a.length == 0) {
            throw Exceptions.IAE("array size", "equal and more than zero", a.length + " and " + b.length);
        }
        List<Tuple<A, B>> list = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            list.add(create(a[i], b[i]));
        }
        return list;
    }

    public static <A, B> Map<A, List<B>> toMap(Tuple<A, B>[] tuples) {
        Map<A, List<B>> map = new HashMap<>();
        Arrays.stream(tuples)
                .collect(Collectors.groupingBy(Tuple::getA))
                .forEach((key, value) -> map.put(key, value.stream().map(Tuple::getB).collect(Collectors.toList())));
        return map;
    }
}
