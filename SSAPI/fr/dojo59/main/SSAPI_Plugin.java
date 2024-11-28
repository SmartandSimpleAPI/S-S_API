package fr.dojo59.main;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.api.compile.StartAPI.StartAPI;
import fr.dojo59.api.compile.StartAPI.StatuteMessageList;
import fr.dojo59.api.provided.DataBaseAPI.constructeur.DataBaseBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SSAPI_Plugin extends JavaPlugin {

    private static SSAPI_Plugin INSTANCE_SSAPI;
    //    private final String apiGitUrl = "https://github.com/SmartandSimpleAPI/S-S_API/blob/main/api_status.json";
    private final List<String> list_API_Compile = new ArrayList<>();
    private final List<String> list_API_Provided = new ArrayList<>();

    @Override
    public void onEnable() {
        INSTANCE_SSAPI = this;
        list_API_Compile.add("ConsoleAPI");
        list_API_Compile.add("StartAPI");
        list_API_Compile.add("ItemAPI");

        list_API_Provided.add("FilesAPI");
        list_API_Provided.add("GuiAPI");
        list_API_Provided.add("DataBaseAPI");
        list_API_Provided.add("ScoreboardAPI");

        consoleMessage();






//DataBaseAPI Start
//        try {
//            TableIdentifierEx test = new TableIdentifierEx("Dojo59", "test", 0, 100, 0);
//
//            DataBaseBuilder<TableIdentifierEx> tableId = new DataBaseBuilder<>(TableIdentifierEx.class);
//
//            tableId.registerAccount(test);
//            test.setIdentifier("Dojo59");
//            test.setFieldDouble("Money1", 2, true);
//
//            System.out.println(tableId.getDoubleCache("Dojo59", "money1"));
//            System.out.println(tableId.getDoubleCache("Dojo59", "money2"));
//            System.out.println(tableId.getStringCache("Dojo59", "str"));
//            System.out.println(tableId.onPlaceholderRequest("Dojo59", "money1"));
//            System.out.println(tableId.onPlaceholderRequest("Dojo59", "money2"));
//            System.out.println(tableId.onPlaceholderRequest("Dojo59", "str"));
//
//        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }


    }

    public static SSAPI_Plugin getINSTANCE() {
        if (INSTANCE_SSAPI == null) {
            throw new IllegalStateException("Erreur: INSTANCE_SSAPI is null");
        }
        return INSTANCE_SSAPI;
    }

    @Override
    public void onDisable() {
        StartAPI.statutPlugin(StatuteMessageList.DISABLE, this);
    }

    public void consoleMessage() {
        StartAPI.statutPlugin(StatuteMessageList.ENABLE, this);

        Console.log(this.getLogger(), ConsoleColor.LIGHT_PURPLE + "Plugin SS_API List: ");

        int nb_API = list_API_Compile.size() + list_API_Provided.size();

        int index_counter = 0;
        for (String api : list_API_Compile) {
            index_counter++;
            if (isDisableAPIStatus(api)) {
                Console.log(this.getLogger(), ConsoleColor.LIGHT_PURPLE + "  " + api + ConsoleColor.GREEN + " Valid " + ConsoleColor.LIGHT_PURPLE + index_counter + "/" + nb_API);
            } else {
                Console.log(this.getLogger(), ConsoleColor.LIGHT_PURPLE + "  " + api + ConsoleColor.RED + " Not Valid " + ConsoleColor.LIGHT_PURPLE + index_counter + "/" + nb_API);
            }
        }

        for (String api : list_API_Provided) {
            index_counter++;
            if (isDisableAPIStatus(api)) {
                Console.log(this.getLogger(), ConsoleColor.LIGHT_PURPLE + "  " + api + ConsoleColor.GREEN + " Valid " + ConsoleColor.LIGHT_PURPLE + index_counter + "/" + nb_API);
            } else {
                Console.log(this.getLogger(), ConsoleColor.LIGHT_PURPLE + "  " + api + ConsoleColor.RED + " Not Valid " + ConsoleColor.LIGHT_PURPLE + index_counter + "/" + nb_API);
            }
        }

        if (isUpdateVersion()) {
            Console.log(getLogger(), " ");
            Console.log(getLogger(), ConsoleColor.YELLOW + "---+----+------------------------+----+---");
            Console.log(getLogger(), ConsoleColor.YELLOW + "   |" + ConsoleColor.LIGHT_GREEN + "         Update available         " + ConsoleColor.YELLOW + "|   ");
            Console.log(getLogger(), ConsoleColor.YELLOW + "---+----+------------------------+----+---");
        }
    }

    private final String apiRestUrl = "https://raw.githubusercontent.com/SmartandSimpleAPI/S-S_API/refs/heads/main/api_status.json";

    private boolean isDisableAPIStatus(String apiName) {
        try {
            URL url = new URL(apiRestUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");

            request.connect();

            if (request.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Console.log(getLogger(), ConsoleColor.RED + "Failed to connect to the API status URL. HTTP response code: " + request.getResponseCode());
                return false;
            }

            JsonObject jsonobject = JsonParser.parseReader(new InputStreamReader(request.getInputStream())).getAsJsonObject();
            JsonObject apiStatuses = jsonobject.getAsJsonObject("S&S_API").getAsJsonObject("Api");

            if (apiStatuses.has(apiName)) {
                return apiStatuses.getAsJsonObject(apiName).get("functional").getAsBoolean();
            } else {
                Console.log(getLogger(), ConsoleColor.RED + "API '" + apiName + "' not found in JSON data.");
            }
        } catch (Exception e) {
            Console.log(getLogger(), ConsoleColor.RED + "Failed to load API status: " + e.getMessage());
        }
        return false;
    }

    private boolean isUpdateVersion() {
        try {
            URL url = new URL(apiRestUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");

            request.connect();

            if (request.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Console.log(getLogger(), ConsoleColor.RED + "Failed to connect to the API status URL. HTTP response code: " + request.getResponseCode());
                return false;
            }

            JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(request.getInputStream())).getAsJsonObject();
            JsonObject apiStatuses = jsonObject.getAsJsonObject("S&S_API");

            return !apiStatuses.get("Version").getAsString().equals(this.getDescription().getVersion());
        } catch (Exception e) {
            Console.log(getLogger(), ConsoleColor.RED + "Failed to load API status: " + e.getMessage());
        }
        return false;
    }

    @EventHandler
    private void onJoinMessageUpdate(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            player.sendMessage("§6§l---+----+------------------------+----+---");
            player.sendMessage("§6§l   |         §a§lUpdate available         §6§l|   ");
            player.sendMessage("§6§l---+----+------------------------+----+---");
        }
    }
}
