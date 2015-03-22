package ca.vijaysharma.resume.utils;

import java.util.ArrayList;
import java.util.Collections;

public class Lists {
    public static <E> ArrayList<E> newArrayList(E... elements) {
        if (elements == null || elements.length == 0)
            return new ArrayList<>();

        int capacity = elements.length;
        ArrayList<E> list = new ArrayList<>(capacity);
        Collections.addAll(list, elements);

        return list;
    }
}
