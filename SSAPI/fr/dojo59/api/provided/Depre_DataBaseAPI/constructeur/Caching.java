package fr.dojo59.api.provided.Depre_DataBaseAPI.constructeur;

import fr.dojo59.api.provided.Depre_DataBaseAPI.utilsDb.FieldTable;
import fr.dojo59.api.provided.Depre_DataBaseAPI.utilsDb.table.TablePlayer;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

class Caching<T extends FieldTable> implements Listener {

    private final DataBaseBuilder<T> dataBaseBuilder;

    protected Caching(DataBaseBuilder<T> dataBaseBuilder) {
        this.dataBaseBuilder = dataBaseBuilder;
        SSAPI_Plugin.getINSTANCE().getServer().getPluginManager().registerEvents(this, SSAPI_Plugin.getINSTANCE());
    }

    private final HashMap<Class<?>, Boolean> initialized_LaunchCaching = new HashMap<>();

    protected Boolean isInitialized_LaunchCaching() {
        return initialized_LaunchCaching.getOrDefault(this.dataBaseBuilder.getTable(), false);
    }

    protected DataBaseBuilder<T> getDatabaseAPI() {
        return this.dataBaseBuilder;
    }

    @EventHandler
    private void launchCaching_UUID(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (TablePlayer.class.isAssignableFrom(this.dataBaseBuilder.getTable())) {
            if (dataBaseBuilder.hasAccounts(uuid)) {
                T account = this.dataBaseBuilder.findAccount(uuid);
                dataBaseBuilder.setCache(uuid, account);
            }
        }
    }

    @EventHandler
    private void clearCaching_UUID(PlayerQuitEvent e) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (TablePlayer.class.isAssignableFrom(this.dataBaseBuilder.getTable())) {
            if (dataBaseBuilder.hasAccounts(uuid)) {
                T account = this.dataBaseBuilder.findAccount(uuid);
                if (!Arrays.equals(account.getFieldAll(), this.dataBaseBuilder.getCache(uuid).getFieldAll())) {
                    this.dataBaseBuilder.updateAccount(this.dataBaseBuilder.getCache(uuid));
                }
            }
        }
    }

    protected void launchCaching_Identifier() throws SQLException {
        int identifierListSize = this.dataBaseBuilder.getSqlFile().getStringList("." + this.dataBaseBuilder.getTableName() + ".IdentifierList").size();
        for (String identifier : this.dataBaseBuilder.getSqlFile().getStringList("." + this.dataBaseBuilder.getTableName() + ".IdentifierList")) {
            identifierListSize--;
            if (getDatabaseAPI().getCache(identifier) == null) {
                if (dataBaseBuilder.hasAccounts(identifier)) {
                    T table = this.dataBaseBuilder.findAccount(identifier);
                    dataBaseBuilder.setCache(identifier, table);
                }
            }
        }
        if (identifierListSize == 0) {
            this.initialized_LaunchCaching.put(this.dataBaseBuilder.getTable(), true);
        } else {
            this.initialized_LaunchCaching.put(this.dataBaseBuilder.getTable(), false);
        }
    }

    @EventHandler
    private void clearCaching_Identifier(PluginDisableEvent e) {
        if (e.getPlugin().getName().equals(SSAPI_Plugin.getINSTANCE().getName())) {
            dataBaseBuilder.forcingUpdateIdentifier();
        }
    }
}
