package com.address.helper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ListUtils {
    public interface Filter<T>{
        boolean apply(T item);
    }

    public static <T, K> void filter(Collection<T> src, List<T> dest, Filter<T> filter) {
        for (Iterator<T> iterator = src.iterator(); iterator.hasNext();){
        	T item = iterator.next();
            if(filter.apply(item)){
            	dest.add(item);
            }
        }
    }
}
