package fr.dojo59.api.provided.DataBaseAPI.constructeur;

import fr.dojo59.api.provided.DataBaseAPI.utilsDb.FieldTable;
import fr.dojo59.api.utils.Tempos;
import fr.dojo59.api.provided.DataBaseAPI.utilsDb.table.TablePlayer;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

class Cache<T extends FieldTable> {

    private Caching<T> launchCaching;
    private DataBaseBuilder<T> databaseAPI;

    protected void setLaunchCaching(Caching<T> launchCaching) {
        this.launchCaching = launchCaching;
        this.databaseAPI = launchCaching.getDatabaseAPI();

        if (this.launchCaching.getDatabaseAPI().getIdentifierType().equals("UUID")) {
            this.autoUpdateUUID();
        } else {
            this.autoUpdateIdentifier();
        }
    }

    private final HashMap<Object, T> cache = new HashMap<>();

    public void setCache(Object identifier, T table) {
        cache.put(identifier, table);
    }

    public T getCache(Object identifier) {
        return cache.get(identifier);
    }

    private void saveCache(T table) throws IllegalAccessException {
        if (TablePlayer.class.isAssignableFrom(databaseAPI.getTable())) {
            setCache(table.getFieldString("uuid"), table);
        } else {
            setCache(table.getFieldString("identifier"), table);
        }
    }

    public void setIntCache(Object identifier, String fieldName, int value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldInt(fieldName, value);
        saveCache(cache);
    }

    public void setIntCache(Object identifier, String fieldName, int value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldInt(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void setLongCache(Object identifier, String fieldName, long value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldLong(fieldName, value);
        saveCache(cache);
    }

    public void setLongCache(Object identifier, String fieldName, long value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldLong(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void setFloatCache(Object identifier, String fieldName, float value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldFloat(fieldName, value);
        saveCache(cache);
    }

    public void setFloatCache(Object identifier, String fieldName, float value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldFloat(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void setDoubleCache(Object identifier, String fieldName, double value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldDouble(fieldName, value);
        saveCache(cache);
    }

    public void setDoubleCache(Object identifier, String fieldName, double value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldDouble(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void setBooleanCache(Object identifier, String fieldName, boolean value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldBoolean(fieldName, value);
        saveCache(cache);
    }

    public void setStringCache(Object identifier, String fieldName, String value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.setFieldString(fieldName, value);
        saveCache(cache);
    }

    public int getIntCache(Object identifier, String fieldName) {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        return cache.getFieldInt(fieldName);
    }

    public long getLongCache(Object identifier, String fieldName) {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        return cache.getFieldLong(fieldName);
    }

    public float getFloatCache(Object identifier, String fieldName) {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        return cache.getFieldFloat(fieldName);
    }

    public double getDoubleCache(Object identifier, String fieldName) {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        return cache.getFieldDouble(fieldName);
    }

    public boolean getBooleanCache(Object identifier, String fieldName) {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        return cache.getFieldBoolean(fieldName);
    }

    public String getStringCache(Object identifier, String fieldName) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        return cache.getFieldString(fieldName);
    }

    public void incrementIntCache(Object identifier, String fieldName, int value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldInt(fieldName, value);
        saveCache(cache);
    }

    public void incrementIntCache(Object identifier, String fieldName, int value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldInt(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void incrementLongCache(Object identifier, String fieldName, long value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldLong(fieldName, value);
        saveCache(cache);
    }

    public void incrementLongCache(Object identifier, String fieldName, long value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldLong(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void incrementFloatCache(Object identifier, String fieldName, float value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldFloat(fieldName, value);
        saveCache(cache);
    }

    public void incrementFloatCache(Object identifier, String fieldName, float value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldFloat(fieldName, value, typeMoney);
        saveCache(cache);
    }

    public void incrementDoubleCache(Object identifier, String fieldName, double value) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldDouble(fieldName, value);
        saveCache(cache);
    }

    public void incrementDoubleCache(Object identifier, String fieldName, double value, boolean typeMoney) throws IllegalAccessException {
        String identifierStr = identifier.toString();
        if (identifier instanceof String) {
            identifierStr = (String) identifier;
        }
        T cache = getCache(identifierStr);
        cache.incrementFieldDouble(fieldName, value, typeMoney);
        saveCache(cache);
    }

    protected void forcingUpdateIdentifier() {
        List<String> identifierList = null;
        if (databaseAPI.getSqlTableFile().getStringList(databaseAPI.getTableName() + ".IdentifierList") != null || !databaseAPI.getSqlTableFile().getStringList(databaseAPI.getTableName() + ".IdentifierList").isEmpty()) {
            identifierList = databaseAPI.getSqlTableFile().getStringList(databaseAPI.getTableName() + ".IdentifierList");
        }
        if (identifierList != null) {
            int counter = 0;
            for (String identifier : identifierList) {
                counter++;

                T profilCache = getCache(identifier);
                T profilFind;
                try {
                    profilFind = databaseAPI.findAccount(identifier);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (!Arrays.equals(profilCache.getFieldAll(), profilFind.getFieldAll())) {
                    try {
                        databaseAPI.updateAccount(cache.get(identifier));
                    } catch (SQLException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (isMultiple(counter)) {
                    try {
                        this.wait(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    protected void forcingUpdateUUID() {
        Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();

        if (!playerList.isEmpty()) {
            int counter = 0;
            for (Player player : playerList) {
                UUID uuid = player.getUniqueId();
                counter++;

                T profilCache = getCache(uuid);
                T profilFind;
                try {
                    profilFind = databaseAPI.findAccount(uuid);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (!Arrays.equals(profilCache.getFieldAll(), profilFind.getFieldAll())) {
                    try {
                        databaseAPI.updateAccount(cache.get(uuid));
                    } catch (SQLException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (isMultiple(counter)) {
                    try {
                        Tempos.waitUpdate(0.125);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void autoUpdateIdentifier() {
        new BukkitRunnable() {
            @Override
            public void run() {
                forcingUpdateIdentifier();
            }
        }.runTaskTimer(SSAPI_Plugin.getINSTANCE(), 20, 20 * 1);
    }

    private void autoUpdateUUID() {
        new BukkitRunnable() {
            @Override
            public void run() {
                forcingUpdateUUID();
            }
        }.runTaskTimer(SSAPI_Plugin.getINSTANCE(), 20, 20 * 1);
    }

    private static boolean isMultiple(int nombre) {
        return nombre % 50 == 0;
    }

}
