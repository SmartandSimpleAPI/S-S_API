package fr.dojo59.api.provided.FilesAPI;

import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import javax.annotation.Nonnull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class FilesLog {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final HashMap<File, Boolean> errorFiles = new HashMap<>();
    private static final HashMap<File, Integer> indexFiles = new HashMap<>();
    private static Integer index = 0;

    @Deprecated
    @SuppressWarnings("all")
    static void configFiles(File file) {
        PluginDescriptionFile description = null;

        assert description != null;
        String pluginName = description.getName().replace("_", " ");
        String author = String.join("-", description.getAuthors());

        int lengthPluginName = pluginName.length() - 4;
        String tiret = "-".repeat(lengthPluginName);

        String nameFile = file.getName();
        String pathFile = file.getAbsolutePath();

        if (file.exists()) {
            System.out.println(" ");
            System.out.println(ConsoleColor.DARK_PURPLE + "[" + pluginName + "] " + ConsoleColor.LIGHT_PURPLE + "File " + getNumberFiles() + ": " + ConsoleColor.AQUA + nameFile + ConsoleColor.LIGHT_PURPLE + " Initialized" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.DARK_PURPLE + "[-+" + tiret + "+-] " + ConsoleColor.LIGHT_PURPLE + "File Path: " + ConsoleColor.AQUA + pathFile + ConsoleColor.RESET);

            errorFiles.put(file, true);
        } else {
            System.out.println(" ");
            if (author.contains("-")) {
                System.out.println(ConsoleColor.DARK_PURPLE + "[" + pluginName + "] " + ConsoleColor.RED + "Erreur produite lors de la creation du fichier " + ConsoleColor.AQUA + nameFile + ConsoleColor.RED + " ,contactez les concepteurs du plugin " + ConsoleColor.LIGHT_PURPLE + author + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.DARK_PURPLE + "[" + pluginName + "] " + ConsoleColor.RED + "Erreur produite lors de la creation du fichier " + ConsoleColor.AQUA + nameFile + ConsoleColor.RED + " ,contactez le concepteurs du plugin " + ConsoleColor.LIGHT_PURPLE + author + ConsoleColor.RESET);
            }
            if (getNumberFiles() <= 9) {
                System.out.println(ConsoleColor.DARK_RED + "[-+" + tiret + "+-] " + ConsoleColor.RED + "ERREUR: " + ConsoleColor.YELLOW + "CHARG-FILE_0" + getNumberFiles() + ConsoleColor.RESET);
            } else {
                System.out.println(ConsoleColor.DARK_RED + "[-+" + tiret + "+-] " + ConsoleColor.RED + "ERREUR: " + ConsoleColor.YELLOW + "CHARG-FILE_" + getNumberFiles() + ConsoleColor.RESET);
            }
            errorFiles.put(file, false);
        }
    }

    static void configFiles(File file, @Nonnull Plugin plugin, String id) {
        PluginDescriptionFile description = plugin.getDescription();

        String pluginName = description.getName().replace("_", " ");
        String author = String.join("-", description.getAuthors());
        Logger logger = plugin.getLogger();
        logFileStatus(logger, pluginName, author, file, id);

    }

    private static void logFileStatus(Logger logger, String pluginName, String author, File file, String id) {
        int lengthPluginName = pluginName.length() - 4;
        String tiret = "-".repeat(lengthPluginName);

        String nameFile = file.getName();
        String pathFile = file.getAbsolutePath();

        index++;

        if (indexFiles.get(file) == null) {
            indexFiles.put(file, index);
        } else {
            index--;
        }

        if (file.exists() && errorFiles.get(file) == null) {
            Console.println(" ");
            Console.log(logger, ConsoleColor.LIGHT_PURPLE +
                    "File " + indexFiles.get(file) + ": " + ConsoleColor.AQUA + nameFile +
                    ConsoleColor.LIGHT_PURPLE + " Initialized");
            Console.log(logger, ConsoleColor.LIGHT_PURPLE +
                    "File Path: " + ConsoleColor.AQUA + pathFile);
            Console.log(logger, ConsoleColor.LIGHT_PURPLE +
                    "File Id: " + ConsoleColor.AQUA + id);
            errorFiles.put(file, true);
        } else if (file.exists() && errorFiles.get(file) != null) {
            Console.println(" ");
            Console.log(logger, ConsoleColor.LIGHT_PURPLE +
                    "File " + indexFiles.get(file) + ": " + ConsoleColor.AQUA + nameFile +
                    ConsoleColor.LIGHT_PURPLE + " Recreated");
            Console.log(logger, ConsoleColor.LIGHT_PURPLE +
                    "File Path: " + ConsoleColor.AQUA + pathFile);
            Console.log(logger, ConsoleColor.LIGHT_PURPLE +
                    "File Id: " + ConsoleColor.AQUA + id);
            errorFiles.put(file, true);
        } else {
            Console.println(" ");
            Console.log(logger, ConsoleColor.RED + "Error occurred during the load of the File :");
            Console.log(logger, ConsoleColor.RED + "Please contact " + (author.contains("-") ? "the developers" : "the developer") + " of the plugin " + ConsoleColor.YELLOW + author);
            Console.log(logger, ConsoleColor.RED + "File Id: " + ConsoleColor.YELLOW + id);
            Console.log(logger, ConsoleColor.RED + "ERROR: " +
                    ConsoleColor.YELLOW + "CHARG-FILE_" + (indexFiles.get(file) <= 9 ? "0" + indexFiles.get(file) : indexFiles.get(file)));
            errorFiles.put(file, false);
        }
    }

    public static int getNumberFiles() {
        return indexFiles.size();
    }

    public static int getIndexFiles(File file) {
        return indexFiles.get(file);
    }

    public static HashMap<File, Boolean> getErrorFiles() {
        return errorFiles;
    }
}
