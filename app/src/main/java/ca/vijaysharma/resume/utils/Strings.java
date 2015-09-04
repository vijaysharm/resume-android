package ca.vijaysharma.resume.utils;

import java.util.List;

public class Strings {
    public static String join(List<String> items) {
        StringBuilder out =new StringBuilder();
        for (int index = 0, length = items.size(); index < length; index++) {
            out.append(items.get(index));
            if (index != (length - 1))
                out.append(", ");
        }

        return out.toString();
    }
}
