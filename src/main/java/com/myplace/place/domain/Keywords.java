package com.myplace.place.domain;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Keywords {
    private static ConcurrentHashMap<String, AtomicInteger> map =
                    new ConcurrentHashMap<>();

    public int increaseCount(String keyword){
        map.putIfAbsent(keyword, new AtomicInteger());
        return map.computeIfPresent(keyword, (k, v)-> { v.incrementAndGet(); return v; }).intValue();
    }

    public List<Keyword> getTopKeyword(){
        // value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
        List<Map.Entry<String, AtomicInteger>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, AtomicInteger>>() {
            @Override
            public int compare(Map.Entry<String, AtomicInteger> o1, Map.Entry<String, AtomicInteger> o2) {
                int comparision = (o1.getValue().get() - o2.getValue().get()) * -1;
                return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
            }
        });

        List<Keyword> result = list.stream()
                                    .limit(10)
                                    .map( i -> { return new Keyword(i.getKey(), i.getValue().intValue());} )
                                    .collect(Collectors.toList());
        return result;
    }

}
