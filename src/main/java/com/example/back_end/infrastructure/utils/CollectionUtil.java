package com.example.back_end.infrastructure.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CollectionUtil {

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static <T> Collection partitionBySize(Collection<T> list, int size) {
        AtomicInteger counter = new AtomicInteger(0);
        return ((Map) list.stream().collect(Collectors.groupingBy((it) -> counter.getAndIncrement() / size))).values();
    }

    public static <T> void split(List<T> source, int unitSize, Consumer<List<T>> consumer) {
        int nextStartIndex = 0;
        int sourceSize = source.size();

        do {
            int startIndex = nextStartIndex;
            nextStartIndex = Math.min(startIndex + unitSize, sourceSize);
            List<T> subList = source.subList(startIndex, nextStartIndex);
            if (subList.isEmpty()) {
                return;
            }

            consumer.accept(subList);
        } while (nextStartIndex < sourceSize);

    }

    public static <T> void split(Set<T> source, int unitSize, Consumer<Set<T>> consumer) {
        int nextStartIndex = 0;
        int sourceSize = source.size();
        List<T> sourceL = new ArrayList<>(source);

        do {
            int startIndex = nextStartIndex;
            nextStartIndex = Math.min(startIndex + unitSize, sourceSize);
            Set<T> subList = sourceL.stream().skip(startIndex).limit(unitSize).collect(Collectors.toSet());
            if (subList.isEmpty()) {
                return;
            }

            consumer.accept(subList);
        } while (nextStartIndex < sourceSize);

    }

    public static <T, V> void split(Map<T, V> source, int unitSize, Consumer<Map<T, V>> consumer) {
        int nextStartIndex = 0;
        int sourceSize = source.size();
        List<Map.Entry<T, V>> set = new ArrayList<>(source.entrySet());

        do {
            int startIndex = nextStartIndex;
            nextStartIndex = Math.min(startIndex + unitSize, sourceSize);
            Map<T, V> subMap = set.stream().skip(startIndex).limit(unitSize).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (subMap.isEmpty()) {
                return;
            }

            consumer.accept(subMap);
        } while (nextStartIndex < sourceSize);

    }
}