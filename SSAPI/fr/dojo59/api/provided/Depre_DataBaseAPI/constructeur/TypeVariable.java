package fr.dojo59.api.provided.Depre_DataBaseAPI.constructeur;

import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.main.SSAPI_Plugin;

import java.util.HashMap;

enum TypeVariable {

    STRING("varchar(255)"),
    UUID("varchar(36)"),
    VARCHAR("varchar"),

    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),

    BOOLEAN("boolean");

    private static final HashMap<String, Integer> varcharMap = new HashMap<>();

    private final String str;

    TypeVariable(String str) {
        this.str = str;
    }

    public void setValue(String id, int varcharLength) {
        if (this == VARCHAR) {
            varcharMap.put(id, varcharLength);
        } else {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.GOLD + "Only VARCHAR can used Function setValue");
        }
    }

    public String getVariableType() {
        return str;
    }

    public String getVariableType(String id) {
        if (this == VARCHAR) {
            if (varcharMap.get(id) != null) {
                return str + "(" + varcharMap.get(id) + ")";
            } else {
                Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.GOLD + "Set a Value to id with a function setValue");
            }
        }
        return str;
    }
}
