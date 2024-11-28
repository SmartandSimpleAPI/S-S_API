package fr.dojo59.api.provided.DataBaseAPI.constructeur;

import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.api.provided.DataBaseAPI.utilsDb.FieldTable;
import fr.dojo59.api.provided.DataBaseAPI.utilsDb.table.TableIdentifier;
import fr.dojo59.api.provided.DataBaseAPI.utilsDb.table.TablePlayer;
import fr.dojo59.main.SSAPI_Plugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


class SomeExpansion<T extends FieldTable> extends PlaceholderExpansion {

    private final String tableName;
    private final DataBaseBuilder<T> dataBase;

    public SomeExpansion(DataBaseBuilder<T> dataBase, String tableName) {
        this.dataBase = dataBase;
        this.tableName = tableName;
    }

    private final HashMap<String, String> dataArgsList = new HashMap<>();


    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", SSAPI_Plugin.getINSTANCE().getDescription().getAuthors());
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.tableName;
    }

    @Override
    public @NotNull String getVersion() {
        return SSAPI_Plugin.getINSTANCE().getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    private List<String> identifierRequest = new ArrayList<>();

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        String identifier = "";
        if (TablePlayer.class.isAssignableFrom(this.dataBase.getTable())) {
            if (player == null) {
                return "§r§lError Player";
            } else {
                identifier = player.getUniqueId().toString();
            }
        } else if (TableIdentifier.class.isAssignableFrom(this.dataBase.getTable()) && params.contains("$")) {
            String identifierValue = params.substring(params.indexOf("$") + 1, params.indexOf("$", params.indexOf("$") + 1));
            params = params.replaceFirst("\\$[^$]+\\$", "");

            identifier = identifierValue;

            for (String value : dataBase.getSqlTableFile().getStringList(".IdentifierList")) {
                if (!value.contains("id")) {
                    identifierRequest.add(value);
                }
            }
        } else {
            return "§r§lError Tablme";
        }

        if (!identifierRequest.contains(identifier)) {
            return "§r§lError Identifier";
        }

        for (int i = 0; i < this.dataBase.getDataArgs().size(); i++) {
            List<String> dataList = this.dataBase.getDataArgs().get(i);
            if (dataList != null && dataList.size() >= 2) {
                String text = dataList.get(0);
                String clazzStr = dataList.get(1);

                dataArgsList.put(text, clazzStr);
            }
        }

        if (dataArgsList.get(params).isEmpty() || dataArgsList.get(params) == null) {
            return "§r§lError Args";
        } else {
            try {
                if (dataArgsList.get(params).equals("String")) {
                    return dataBase.getStringCache(identifier, params);
                } else if (dataArgsList.get(params).contains("varchar")) {
                    return dataBase.getStringCache(identifier, params);
                } else if (dataArgsList.get(params).equals("int")) {
                    return String.valueOf(dataBase.getIntCache(identifier, params));
                } else if (dataArgsList.get(params).equals("long")) {
                    return String.valueOf(dataBase.getLongCache(identifier, params));
                } else if (dataArgsList.get(params).equals("float")) {
                    return String.valueOf(dataBase.getFloatCache(identifier, params));
                } else if (dataArgsList.get(params).equalsIgnoreCase("double")) {
                    return String.valueOf(dataBase.getDoubleCache(identifier, params));
                } else if (dataArgsList.get(params).equalsIgnoreCase("boolean")) {
                    return String.valueOf(dataBase.getBooleanCache(identifier, params));
                } else {
                    return "§r§lError Type";
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
