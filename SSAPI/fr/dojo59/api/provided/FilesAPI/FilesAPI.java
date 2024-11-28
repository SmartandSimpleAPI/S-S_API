package fr.dojo59.api.provided.FilesAPI;

import fr.dojo59.api.utils.ConvertList;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class FilesAPI {
    private final String nameFile;
    private File file;

    private final String key;
    private final JavaPlugin plugin;
    private final String id;

    /**
     * The function that sets the base value of the plugin
     * when creating it
     *
     * @param yml The configuration section of the variable key set above
     */
    public abstract void initBaseConfig(ConfigurationSection yml);

    /**
     * Constructor for initializing FileAPI
     *
     * @param pathFile The path of the file to be created,
     *                 <p>Format:</p>
     *                 <ul>
     *                     <li>If {@code pathFile} is omitted (Empty value) will set at {@link JavaPlugin#getDataFolder()} of the FilesAPI plugin</li>
     *                     <li>If set it the file will be created at : Server/plugins/pathFile</li>
     *                 </ul>
     * @param nameFile The name of the file that will be created,
     *                 <p>Format:</p>
     *                 <ul>
     *                     <li>If the {@code nameFile} ends with .yml that will be the name</li>
     *                     <li>If the {@code nameFile} doesn't end with .yml it adds it</li>
     *                     <li>If the {@code nameFile} ends with .ym it adds .yml after that</li>
     *                 </ul>
     * @param key      The key where every value will be set .
     * @param id       The id to search for the file locally .
     */
    public FilesAPI(JavaPlugin javaPlugin, String pathFile, String nameFile, String key, String id) {
        this.plugin = javaPlugin;

        if(id == null || id.isEmpty()){
            throw new IllegalArgumentException("The id cannot be Empty.");
        }

        this.id = id.replace(" ", "_");

        if (filesList.containsKey(this.id)) throw new IllegalArgumentException("You cannot create two files with the same ID.");
        if (nameFile.isEmpty()) throw new IllegalArgumentException("The name of the file cannot be empty.");

        String pathFile1 = pathFile;
        if (!pathFile.startsWith("/") || !pathFile.startsWith("../")) {
            pathFile1 = "/" + pathFile1;
        }
        if (!pathFile.endsWith("/")) {
            pathFile1 = pathFile1 + "/";
        }

        this.nameFile = pathFile1 + ((nameFile.endsWith(".yml")) ? nameFile : nameFile + ".yml").replace(" ", "_");

        this.file = new File(plugin.getDataFolder(), this.nameFile);
        this.key = key;

        this.yml = createFile();

        initializationFiles();
    }

    private static boolean initFiles = false;
    private YamlConfiguration yml;
    private static final HashMap<String, FilesAPI> filesList = new HashMap<>();

    /**
     * Creates files when not present or deleted .
     */
    private static void initializationFiles() {
        if (!initFiles) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    filesList.values().forEach(filesAPI -> {
                        if (!filesAPI.fileExists()) {
                            FilesLog.configFiles(filesAPI.getFile(), filesAPI.plugin, filesAPI.getId());

                            filesAPI.initBaseConfig(filesAPI.getYamlSection());
                            filesAPI.save();

                            FilesLog.configFiles(filesAPI.getFile(), filesAPI.plugin, filesAPI.getId());
                        }
                    });
                }
            }.runTaskTimer(SSAPI_Plugin.getINSTANCE(), 1, 20L);

            initFiles = true;
        }
    }

    /**
     * Saves the yml configuration modifications to file ,
     * and loads it to ram .
     */

    private void save() {
        try {
            yml.save(file);
            load();
        } catch (IOException e) {
            throw new RuntimeException("Error saving config file", e);
        }
    }

    /**
     * Loads the yml configuration to ram
     */

    private void load() {
        try {
            if (fileExists()) {
                yml.load(file);
            }
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException("Error loading config file", e);
        }
    }

    /**
     * @return The yml configuration created by the function
     */

    private YamlConfiguration createFile() {
        file = new File(plugin.getDataFolder(), nameFile);

        if (!fileExists()) {
            filesList.put(id, this);
            yml = new YamlConfiguration();
            yml.createSection(getKey());

            initBaseConfig(getYamlSection());
            save();

            FilesLog.configFiles(getFile(), plugin, id);
        } else {
            yml = YamlConfiguration.loadConfiguration(getFile());
            filesList.put(id, this);

            FilesLog.configFiles(getFile(), plugin, id);
        }

        return yml;
    }

    public static FilesAPI getFile(String id) {
        return filesList.get(id);
    }

    private File getFile() {
        return file;
    }

    public String getId() {
        return id;
    }


    private String getKey() {
        return key;
    }

    private boolean fileExists() {
        return getFile().exists();
    }

    private ConfigurationSection getYamlSection() {
        load();
        return yml.getConfigurationSection(getKey());
    }

    /**
     * Getter of an Integer value at {@code getKey() + index}
     */

    public int getInt(@Nonnull String index) {
        return getYamlSection().getInt(index);
    }

    public float getFloat(@Nonnull String index) {
        return (float) getYamlSection().getInt(index);
    }

    public long getLong(@Nonnull String index) {
        return getYamlSection().getLong(index);
    }

    public double getDouble(@Nonnull String index) {
        return getYamlSection().getDouble(index);
    }


    /**
     * Getter of a String at {@code getKey() + index}
     */
    public @Nullable String getString(@Nonnull String index) {
        return getYamlSection().getString(index);
    }

    /**
     * Getter of a List at {@code getKey() + index}
     */
    public List<?> getList(@Nonnull String index) {

        List<?> rawList = getYamlSection().getList(index);
        return (rawList != null) ? convertList(rawList, getType(rawList)) : Collections.emptyList();
    }

    public List<Integer> getIntList(@Nonnull String index) {
        List<Integer> rawList = getYamlSection().getIntegerList(index);
        return rawList;
    }

    public List<Float> getFloatList(@Nonnull String index) {
        List<Float> rawList = getYamlSection().getFloatList(index);
        return rawList;
    }

    public List<Long> getLongList(@Nonnull String index) {
        List<Long> rawList = getYamlSection().getLongList(index);
        return rawList;
    }

    public List<Double> getDoubleList(@Nonnull String index) {
        List<Double> rawList = getYamlSection().getDoubleList(index);
        return rawList;
    }

    public List<String> getStringList(@Nonnull String index) {
        List<String> rawList = getYamlSection().getStringList(index);
        return rawList;
    }

    public List<Boolean> getBooleanList(@Nonnull String index) {
        List<Boolean> rawList = getYamlSection().getBooleanList(index);
        return rawList;
    }

    public List<Byte> getByteList(@Nonnull String index) {
        List<Byte> rawList = getYamlSection().getByteList(index);
        return rawList;
    }

    public List<Character> getCharacterList(@Nonnull String index) {
        List<Character> rawList = getYamlSection().getCharacterList(index);
        return rawList;
    }

    public List<Map<?, ?>> getMapList(@Nonnull String index) {
        List<Map<?, ?>> rawList = getYamlSection().getMapList(index);
        return rawList;
    }

    public List<Short> getShortList(@Nonnull String index) {
        List<Short> rawList = getYamlSection().getShortList(index);
        return rawList;
    }


    /**
     * Getter of all values at an index
     *
     * @param deep Gets all values beyond the index
     */
    public List<String> getSectionKeys(@Nonnull String index, boolean deep) {
        ConfigurationSection sectionList = this.getYamlSection().getConfigurationSection(index);
        return (sectionList != null) ? new ArrayList<>(sectionList.getKeys(deep)) : Collections.emptyList();
    }

    /**
     * Increments an Integer at index by value
     */

    public void incrementInt(@Nonnull String index, int value) {
        int sectionValue = getInt(index);
        sectionValue += value;

        set(index, sectionValue);
        save();
    }

    public void incrementFloat(@Nonnull String index, float value) {
        float sectionValue = getFloat(index);
        sectionValue += value;

        set(index, sectionValue);
        save();
    }

    public void incrementLong(@Nonnull String index, long value) {
        long sectionValue = getLong(index);
        sectionValue += value;

        set(index, sectionValue);
        save();
    }

    /**
     * Increments a Double at index by value
     */
    public void incrementDouble(@Nonnull String index, double value) {
        double sectionValue = getDouble(index);
        sectionValue += value;

        set(index, sectionValue);
        save();
    }


    /**
     * Sets a value {@code Objects} at an index
     *
     * @param index   Index at which it will set the value
     * @param objects The value that will be set ,
     *                <p>Format:</p>
     *                <ul>
     *                    <li>If objects only have 1 value :</li>
     *                    <ul>
     *                        <li>If object is an instance of a List it will set it as so.</li>
     *                        <li>Otherwise , it will set it as the type given.</li>
     *                    </ul>
     *                    <li>If objects have more than 1 value, It converts all those values as a list and sets it as so</li>
     *                </ul>
     */
    public void set(@Nonnull String index, Object... objects) {
        if (objects == null) {
            throw new NullPointerException("Cannot set to null");
        }
        if (objects.length == 1) {
            Object object = objects[0];
            if (object instanceof List<?> list) {
                Class<?> type = getType(list);

                List<?> convertedList = convertList(list, type);
                getYamlSection().set(index, convertedList);
            } else {
                getYamlSection().set(index, object);
            }
        } else {
            List<?> list = ConvertList.list(objects);

            Class<?> type = getType(list);

            List<?> convertedList = convertList(list, type);
            getYamlSection().set(index, convertedList);
        }
        save();
    }

    public void addToList(@Nonnull String index, Object... objects) {
        List<?> valuelist = ConvertList.list(getList(index), objects);

        set(index, valuelist);
    }

    public void removeFromList(@Nonnull String index, Object... objects) {
        List<Object> convertList = ConvertList.list(objects);
        List<?> originalList = getList(index);

        List<?> valuelist = (originalList != null) ? originalList.stream()
                .filter(object -> !convertList.contains(object))
                .toList() : null;

        set(index, valuelist);
    }

    public void remove(@Nullable String index) {
        if (index != null) {
            getYamlSection().set(index, null);
        } else {
            load();
            yml.set(getKey(), null);
        }
        save();
    }

    private static <T> Class<?> getType(List<T> list) {
        if (list.isEmpty()) {
            return Object.class;
        } else {
            Class<?> elementType = null;
            for (T item : list) {
                if (item != null) {
                    Class<?> itemClass = item.getClass();
                    if (elementType == null) {
                        elementType = itemClass;
                    } else if (!elementType.equals(itemClass)) {
                        return Object.class;
                    }
                }
            }
            return elementType != null ? elementType : Object.class;
        }
    }

    private List<?> convertList(List<?> list, Class<?> type) {
        if (type == String.class) {
            return list.stream().map(obj -> (String) obj).toList();
        } else if (type == Double.class) {
            return list.stream().map(obj -> (Double) obj).toList();
        } else if (type == Integer.class) {
            return list.stream().map(obj -> (Integer) obj).toList();
        } else {
            return new ArrayList<>(list);
        }
    }
}