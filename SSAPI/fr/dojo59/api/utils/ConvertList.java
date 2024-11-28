package fr.dojo59.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertList {
    public static List<Object> list(Object... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }
}
