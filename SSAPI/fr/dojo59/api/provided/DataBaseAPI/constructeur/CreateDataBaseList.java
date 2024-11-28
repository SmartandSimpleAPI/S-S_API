package fr.dojo59.api.provided.DataBaseAPI.constructeur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CreateDataBaseList {
    private final List<String> list;

    public CreateDataBaseList(String str, TypeVariable variableType) {
        this.list = convertList(str, variableType.getVariableType());
    }

    public CreateDataBaseList(String str, TypeVariable variableType, String id) {
        this.list = convertList(str, variableType.getVariableType(id));
    }

    public List<String> getList() {
        return list;
    }

    public static List<List<String>> builderDataList(CreateDataBaseList... lists) {
        List<List<String>> result = new ArrayList<>();
        for (CreateDataBaseList convertedList : lists) {
            result.add(convertedList.getList());
        }
        return result;
    }

    private static List<String> convertList(String str, String variableType) {
        return new ArrayList<>(Arrays.asList(str, variableType));
    }
}


