package ca.vijaysharma.resume.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Sets {
    public static <T> Set<T> newHashSet(T...elements) {
        if (elements == null || elements.length == 0)
            return Collections.<T>emptySet();

        HashSet set = new HashSet(elements.length);
        for (T element : elements) set.add(element);

        return set;
    }
}
