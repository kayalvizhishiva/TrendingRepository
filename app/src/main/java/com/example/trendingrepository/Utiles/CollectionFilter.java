package com.example.trendingrepository.Utiles;

import java.util.ArrayList;
import java.util.Collection;

// Filter Two collection

public class CollectionFilter {

    public interface Predicate<T> { boolean apply(T type); }

    public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element: col) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
