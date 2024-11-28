package fr.dojo59.api.provided.Depre_DataBaseAPI.constructeur;

import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.api.provided.Depre_DataBaseAPI.utilsDb.FieldTable;
import fr.dojo59.api.provided.Depre_DataBaseAPI.utilsDb.table.TableIdentifier;
import fr.dojo59.api.provided.Depre_DataBaseAPI.utilsDb.table.TablePlayer;
import fr.dojo59.api.utils.ConvertList;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataBaseBuilder<T extends FieldTable> extends Cache<T> {

    private final MySQLFile sqlFile;
    private final Class<T> table;
    private final String tableName;
    private final String identifierType;
    private final List<List<String>> dataArgs;
    private SomeExpansion<T> someExpansion;

    public DataBaseBuilder(Class<T> table) throws SQLException {
        String tableName;
        if (table == null) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Table cannot be null");
            throw new IllegalArgumentException();
        } else if (!TableIdentifier.class.isAssignableFrom(table) && !TablePlayer.class.isAssignableFrom(table)) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Table not implement of TablesIdentifier or TablesPlayer");
            throw new IllegalArgumentException();
        } else {
            tableName = table.getSimpleName();
        }

        String identifierType = "Identifier";
        if (TablePlayer.class.isAssignableFrom(table)) {
            identifierType = "UUID";
        }

        Field[] fields = table.getDeclaredFields();
        List<CreateDataBaseList> createListObjects = new ArrayList<>();

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldType = field.getType().getSimpleName();

            if (fieldName.equals("uuid") || fieldName.equals("identifier")) {
                continue;
            }

            if (fieldType.equals("String")) {
                if (fieldName.endsWith("$255$")) {
                    createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.STRING));
                } else {
                    String nb = fieldName.substring(fieldName.indexOf("$") + 2, fieldName.lastIndexOf("$"));
                    int nbVarchar = Integer.parseInt(nb);

                    fieldName = fieldName.replaceAll("\\$\\d+\\$", "");

                    TypeVariable.VARCHAR.setValue(fieldName + "_" + tableName, nbVarchar);
                    createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.VARCHAR, fieldName + "_" + tableName));
                }
            } else if (fieldType.equals("int")) {
                createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.INT));
            } else if (fieldType.equals("long")) {
                createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.LONG));
            } else if (fieldType.equals("float")) {
                createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.FLOAT));
            } else if (fieldType.equalsIgnoreCase("double")) {
                createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.DOUBLE));
            } else if (fieldType.equalsIgnoreCase("boolean")) {
                createListObjects.add(new CreateDataBaseList(fieldName, TypeVariable.BOOLEAN));
            } else {
                Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Invalid type during the creation of Table.");
                throw new IllegalArgumentException();
            }
        }

        CreateDataBaseList[] createListsArray = createListObjects.toArray(new CreateDataBaseList[0]);
        List<List<String>> dataArgs = CreateDataBaseList.builderDataList(createListsArray);

        this.sqlFile = new MySQLFile(tableName, tableName);
        this.table = table;
        this.tableName = tableName;
        this.identifierType = identifierType;
        this.dataArgs = dataArgs;

        initializedDataBase();
    }

    protected Connection connection;
    private final boolean[] errorConnectionLogged = new boolean[4];

    private Connection getConnection() throws SQLException {
        if (this.connection == null) {
            String url = sqlFile.getString(".Connection.URL");
            String user = sqlFile.getString(".Connection.USER");
            String password = sqlFile.getString(".Connection.PASSWORD");

            assert url != null;

            logIfEmpty(url.equals("jdbc:mysql://") || url.isEmpty(), 0, "Url can not be empty: ", url);
            logIfEmpty(url.isBlank(), 1, "Url cannot contain a Blank: ", "");
            logIfEmpty(user == null, 2, "User can not be empty: ", user);
            logIfEmpty(password == null, 3, "Password can not be empty: ", password);

            int countError = 0;
            for (boolean errorArgsDriver : errorConnectionLogged) {
                countError++;
                if (errorArgsDriver) {
                    return null;
                } else if (countError == errorConnectionLogged.length) {
                    this.connection = DriverManager.getConnection(url, user, password);
                    logIfEmpty(this.connection == null, 4, "Connection into the Database missing: ", "Invalid MySQLFile");
                }
            }
        }
        return this.connection;
    }

    private void logIfEmpty(boolean condition, int index, String message, String arg) {
        if (condition && !errorConnectionLogged[index]) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Error, " + message + ConsoleColor.YELLOW + arg);
            errorConnectionLogged[index] = true;
        }
    }

    private static final HashMap<Class<?>, Boolean> initialized_DataBase = new HashMap<>();

    public Boolean isInitialized_DataBase() {
        return initialized_DataBase.getOrDefault(this.table, false);
    }

    public MySQLFile getSqlFile() {
        return this.sqlFile;
    }

    public Class<T> getTable() {
        return this.table;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public List<List<String>> getDataArgs() {
        return this.dataArgs;
    }


    private void initializedDataBase() throws SQLException {
        if (isInitialized_DataBase() && this.getConnection() != null) {
            return;
        }
        if (this.getConnection() == null) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "The connection to the DataBase is null");
            throw new IllegalArgumentException();
        } else {
            Statement statement = this.getConnection().createStatement();

            StringBuilder stringBuilder = new StringBuilder();
            int counterFor = this.dataArgs.size();

            for (int i = 0; i < this.dataArgs.size(); i++) {
                List<String> dataList = this.dataArgs.get(i);
                if (dataList != null && dataList.size() >= 2) {
                    String text = dataList.get(0);
                    String clazzStr = dataList.get(1);
                    if (counterFor > 1) {
                        stringBuilder.append(text).append(" ").append(clazzStr).append(", ");
                    } else {
                        stringBuilder.append(text).append(" ").append(clazzStr);
                    }
                    counterFor--;
                }
            }
            String sql = "CREATE TABLE IF NOT EXISTS " + this.tableName + " (" + this.identifierType + " " + TypeVariable.STRING.getVariableType() + " primary key, " + stringBuilder + " )";
            if (this.identifierType.equals("UUID")) {
                sql = "CREATE TABLE IF NOT EXISTS " + this.tableName + " (" + this.identifierType + " " + TypeVariable.UUID.getVariableType() + " primary key, " + stringBuilder + " )";
            }
            try {
                statement.execute(sql);
                statement.close();
            } catch (SQLException e) {
                if (this.getConnection() != null) {
                    try {
                        this.getConnection().rollback();
                    } catch (SQLException rollbackEx) {
                        initialized_DataBase.put(this.table, false);
                        Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "Error during the creation Table Rollback failed: " + rollbackEx.getMessage());
                        Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.YELLOW + "The table " + ConsoleColor.RED + tableName + ConsoleColor.YELLOW + " has not been loaded.");
                    }
                }
            }
            if (this.identifierType.equalsIgnoreCase("UUID")) {
                initialized_DataBase.put(this.table, true);
                Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.LIGHT_PURPLE + "The table " + ConsoleColor.AQUA + tableName + ConsoleColor.LIGHT_PURPLE + " has been loaded.");
            } else {
                if (this.sqlFile.getStringList(".TableName").size() > 1) {
                    Console.println(" ");
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.LIGHT_PURPLE + "Launch of the caching of " + ConsoleColor.AQUA + this.sqlFile.getStringList("." + this.tableName + ".IdentifierList").size() + ConsoleColor.LIGHT_PURPLE + " identifiers in " + ConsoleColor.AQUA + this.tableName);
                } else {
                    Console.println(" ");
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.LIGHT_PURPLE + "Launch of the caching of " + ConsoleColor.AQUA + this.sqlFile.getStringList("." + this.tableName + ".IdentifierList").size() + ConsoleColor.LIGHT_PURPLE + " identifier in" + ConsoleColor.AQUA + this.tableName);
                }

                Caching<T> caching = new Caching<>(this);
                caching.launchCaching_Identifier();
                setLaunchCaching(caching);

                if (caching.isInitialized_LaunchCaching()) {
                    initialized_DataBase.put(this.table, true);
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.LIGHT_PURPLE + "The table " + ConsoleColor.AQUA + tableName + ConsoleColor.LIGHT_PURPLE + " has been loaded.");

                    someExpansion = new SomeExpansion<>(this, this.tableName);
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.LIGHT_PURPLE + "DataBase with PlaceHolderAPI " + ConsoleColor.LIGHT_PURPLE + " has been loaded.");
                } else {
                    initialized_DataBase.put(this.table, false);
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.YELLOW + "The table " + ConsoleColor.RED + tableName + ConsoleColor.YELLOW + " has not been loaded.");
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.YELLOW + "Launch caching has not been loaded.");
                }
            }
        }
    }


    public T findAccount(Object identifier) throws SQLException {
        PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM " + this.tableName + " WHERE " + this.identifierType + "= ?");
        if (identifier instanceof String) {
            statement.setString(1, (String) identifier);
        } else {
            statement.setString(1, identifier.toString());
        }

        ResultSet results = statement.executeQuery();

        if (results.next()) {
            List<String> dataList;
            String text;
            String clazzStr;
            Object valueResult;
            List<Object> constructorResult = new ArrayList<>();
            List<Class<?>> constructorClazz = new ArrayList<>();

            if (identifier instanceof String) {
                constructorClazz.add(String.class);
                constructorResult.add(identifier);
            } else {
                constructorClazz.add(UUID.class);
                constructorResult.add(UUID.fromString(identifier.toString()));
            }

            Class<?> clazz;
            for (int i = 0; i < this.dataArgs.size(); i++) {
                dataList = this.dataArgs.get(i);
                if (dataList != null) {
                    if (dataList.get(0) != null && dataList.get(1) != null) {
                        text = dataList.get(0);
                        clazzStr = dataList.get(1);

                        if (clazzStr.contains("varchar")) {
                            valueResult = results.getString(text);
                            clazz = String.class;
                        } else if (clazzStr.equals("int")) {
                            valueResult = results.getInt(text);
                            clazz = int.class;
                        } else if (clazzStr.equals("long")) {
                            valueResult = results.getLong(text);
                            clazz = long.class;
                        } else if (clazzStr.equals("float")) {
                            valueResult = results.getFloat(text);
                            clazz = float.class;
                        } else if (clazzStr.equals("double")) {
                            valueResult = results.getDouble(text);
                            clazz = double.class;
                        } else if (clazzStr.equals("boolean")) {
                            valueResult = results.getBoolean(text);
                            clazz = boolean.class;
                        } else {
                            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.GOLD + "Error during a function findAccount because the type of class is not listed in TypeVariable.Class");
                            throw new IllegalArgumentException();
                        }

                        constructorResult.add(valueResult);
                        constructorClazz.add(clazz);
                    }
                }
            }

            try {
                Class<?>[] constructorTypeClass = constructorClazz.toArray(new Class<?>[0]);

                Constructor<?> constructor = this.table.getConstructor(constructorTypeClass);
                T account = (T) constructor.newInstance(constructorResult.toArray());

                statement.close();
                return account;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            statement.close();
            return null;
        }
        return null;
    }

    private final HashMap<String, Boolean> hasAccountList = new HashMap<>();

    public void setHasAccountList(String identifier, Boolean value) {
        hasAccountList.put(identifier, value);
    }

    public void removeHasAccountList(String identifier) {
        hasAccountList.remove(identifier);
    }

    public boolean hasAccounts(Object identifier) {
        String identifierStr = (String) identifier;
        if (TablePlayer.class.isAssignableFrom(table)) {
            identifierStr = identifier.toString();
        }

        try {
            Boolean hasAccount = hasAccountList.get(identifierStr);
            if (hasAccount == null) {
                hasAccount = findAccount(identifierStr) != null;
                hasAccountList.put(identifierStr, hasAccount);
            } else if (!hasAccountList.get(identifierStr)) {
                hasAccount = findAccount(identifierStr) != null;
                hasAccountList.put(identifierStr, hasAccount);
            }
            return hasAccount;
        } catch (SQLException e) {
            Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.BLUE + "Error retrieving player data: " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public void registerAccount(T table) throws
            SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getter = table.getClass().getMethod("get" + this.identifierType);
        Object valueIdentifier = getter.invoke(table);

        if (!hasAccounts(valueIdentifier.toString())) {

            StringBuilder stringBuilderText = new StringBuilder();
            StringBuilder stringBuilderInterrogation = new StringBuilder();
            int counterFor = this.dataArgs.size();

            List<String> dataList;
            String text;
            String clazzStr;

            List<String> textList = new ArrayList<>();
            List<String> clazzList = new ArrayList<>();

            for (int i = 0; i < this.dataArgs.size(); i++) {
                counterFor--;
                dataList = this.dataArgs.get(i);
                if (dataList != null) {
                    if (dataList.get(0) != null && dataList.get(1) != null) {
                        text = dataList.get(0);
                        clazzStr = dataList.get(1);
                        if (counterFor == 0) {
                            stringBuilderText.append(text);
                            stringBuilderInterrogation.append("?");
                            textList.add(text);
                            clazzList.add(clazzStr);
                        } else {
                            stringBuilderText.append(text).append(", ");
                            stringBuilderInterrogation.append("?, ");
                            textList.add(text);
                            clazzList.add(clazzStr);
                        }

                    }
                }
            }
            if (textList.size() != clazzList.size()) {
                Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.GOLD + "Error Process during a Compile List");
                throw new IllegalArgumentException();
            }

            PreparedStatement statement = this.getConnection().prepareStatement("INSERT INTO " + this.tableName + " (" + this.identifierType + ", " + stringBuilderText + ") VALUES (?, " + stringBuilderInterrogation + ")");

            statement.setString(1, valueIdentifier.toString());

            for (int i = 0; i < textList.size(); i++) {
                if (clazzList.get(i).contains("varchar")) {
                    statement.setString(i + 2, table.getFieldString(textList.get(i)));
                } else if (clazzList.get(i).equals("int")) {
                    statement.setInt(i + 2, table.getFieldInt(textList.get(i)));
                } else if (clazzList.get(i).equals("long")) {
                    statement.setLong(i + 2, table.getFieldLong(textList.get(i)));
                } else if (clazzList.get(i).equals("float")) {
                    statement.setFloat(i + 2, table.getFieldFloat(textList.get(i)));
                } else if (clazzList.get(i).equals("double")) {
                    statement.setDouble(i + 2, table.getFieldDouble(textList.get(i)));
                } else if (clazzList.get(i).equals("boolean")) {
                    statement.setBoolean(i + 2, table.getFieldBoolean(textList.get(i)));
                } else {
                    Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.GOLD + "Error during a function registerAccount because the type of class is not listed in TypeVariable.Class");
                    throw new IllegalArgumentException();
                }
            }
            statement.executeUpdate();
            statement.close();

            if (this.identifierType.equalsIgnoreCase("identifier")) {
                if (this.sqlFile.getStringList(".TableName") == null || this.sqlFile.getStringList(".TableName").isEmpty() || this.sqlFile.getStringList(".TableName").size() == 1 && !this.sqlFile.getStringList(".TableName").contains(this.tableName)) {
                    this.sqlFile.set(".TableName", this.tableName);
                } else if (!this.sqlFile.getStringList(".TableName").contains(this.tableName)) {
                    List<String> tableNameList = this.sqlFile.getStringList(".TableName");
                    tableNameList.add(this.tableName);
                    this.sqlFile.set(".TableName", tableNameList);
                }
                if (this.sqlFile.getStringList(this.tableName + ".IdentifierList") == null || this.sqlFile.getStringList(this.tableName + ".IdentifierList").isEmpty() || this.sqlFile.getStringList(this.tableName + ".IdentifierList").size() == 1 || !this.sqlFile.getStringList(this.tableName + ".IdentifierList").contains(valueIdentifier.toString())) {
                    this.sqlFile.set(this.tableName + ".IdentifierList", ConvertList.list(valueIdentifier.toString()));
                } else if (!this.sqlFile.getStringList(this.tableName + ".IdentifierList").contains(valueIdentifier.toString())) {
                    this.sqlFile.set(this.tableName + ".IdentifierList", this.sqlFile.getStringList(this.tableName + ".IdentifierList").add(valueIdentifier.toString()));
                }
                setCache(valueIdentifier.toString(), table);
            }

        }
    }

    public void updateAccount(T table) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getter = this.table.getMethod("get" + this.identifierType);
        Object valueIdentifier = getter.invoke(table);

        if (hasAccounts(valueIdentifier.toString())) {
            T existingAccount = findAccount(valueIdentifier);
            if (existingAccount == null) {
                Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.RED + "No account found with identifier: " + valueIdentifier);
                return;
            }

            StringBuilder stringBuilderText = new StringBuilder();
            List<String> textList = new ArrayList<>();
            List<String> clazzList = new ArrayList<>();

            for (int i = 0; i < this.dataArgs.size(); i++) {
                List<String> dataList = this.dataArgs.get(i);
                if (dataList != null && dataList.get(0) != null && dataList.get(1) != null) {
                    String columnName = dataList.get(0);
                    String columnType = dataList.get(1);

                    Object newValue = null;
                    Object oldValue = null;

                    if (columnType.contains("varchar")) {
                        newValue = table.getFieldString(columnName);
                        oldValue = existingAccount.getFieldString(columnName);
                    } else if (columnType.equals("int")) {
                        newValue = table.getFieldInt(columnName);
                        oldValue = existingAccount.getFieldInt(columnName);
                    } else if (columnType.equals("long")) {
                        newValue = table.getFieldLong(columnName);
                        oldValue = existingAccount.getFieldLong(columnName);
                    } else if (columnType.equals("float")) {
                        newValue = table.getFieldFloat(columnName);
                        oldValue = existingAccount.getFieldFloat(columnName);
                    } else if (columnType.equals("double")) {
                        newValue = table.getFieldDouble(columnName);
                        oldValue = existingAccount.getFieldDouble(columnName);
                    } else if (columnType.equals("boolean")) {
                        newValue = table.getFieldBoolean(columnName);
                        oldValue = existingAccount.getFieldBoolean(columnName);
                    }

                    if (newValue != null && !newValue.equals(oldValue)) {
                        stringBuilderText.append(columnName).append(" = ?, ");
                        textList.add(columnName);
                        clazzList.add(columnType);
                    }
                }
            }

            if (textList.isEmpty()) {
                Console.log(SSAPI_Plugin.getINSTANCE().getLogger(), ConsoleColor.YELLOW + "No fields changed for account with identifier: " + valueIdentifier);
                return;
            }

            stringBuilderText.setLength(stringBuilderText.length() - 2);

            PreparedStatement statement = this.getConnection().prepareStatement(
                    "UPDATE " + this.tableName + " SET " + stringBuilderText + " WHERE " + this.identifierType + " = ?");

            for (int i = 0; i < textList.size(); i++) {
                String columnType = clazzList.get(i);
                if (columnType.contains("varchar")) {
                    statement.setString(i + 1, table.getFieldString(textList.get(i)));
                } else if (columnType.equals("int")) {
                    statement.setInt(i + 1, table.getFieldInt(textList.get(i)));
                } else if (columnType.equals("long")) {
                    statement.setLong(i + 1, table.getFieldLong(textList.get(i)));
                } else if (columnType.equals("float")) {
                    statement.setFloat(i + 1, table.getFieldFloat(textList.get(i)));
                } else if (columnType.equals("double")) {
                    statement.setDouble(i + 1, table.getFieldDouble(textList.get(i)));
                } else if (columnType.equals("boolean")) {
                    statement.setBoolean(i + 1, table.getFieldBoolean(textList.get(i)));
                }
            }

            statement.setString(textList.size() + 1, valueIdentifier.toString());

            statement.executeUpdate();
            statement.close();

            if (TableIdentifier.class.isAssignableFrom(this.table)) {
                if (this.sqlFile.getStringList(".TableName") == null || this.sqlFile.getStringList(".TableName").isEmpty() || this.sqlFile.getStringList(".TableName").size() == 1 && !this.sqlFile.getStringList(".TableName").contains(this.tableName)) {
                    this.sqlFile.set(".TableName", this.tableName);
                } else if (!this.sqlFile.getStringList(".TableName").contains(this.tableName)) {
                    List<String> tableNameList = this.sqlFile.getStringList(".TableName");
                    tableNameList.add(this.tableName);
                    this.sqlFile.set(".TableName", tableNameList);
                }
                if (this.sqlFile.getStringList(this.tableName + ".IdentifierList") == null || this.sqlFile.getStringList(this.tableName + ".IdentifierList").isEmpty() || this.sqlFile.getStringList(this.tableName + ".IdentifierList").size() == 1 || !this.sqlFile.getStringList(this.tableName + ".IdentifierList").contains(valueIdentifier.toString())) {
                    this.sqlFile.set(this.tableName + ".IdentifierList", ConvertList.list(valueIdentifier.toString()));
                } else if (!this.sqlFile.getStringList(this.tableName + ".IdentifierList").contains(valueIdentifier.toString())) {
                    this.sqlFile.set(this.tableName + ".IdentifierList", this.sqlFile.getStringList(this.tableName + ".IdentifierList").add(valueIdentifier.toString()));
                }
            }
        }
    }

    public void deleteAccounts(Object identifier) throws SQLException {
        PreparedStatement statement = this.getConnection().prepareStatement("DELETE FROM " + this.tableName + " WHERE " + this.identifierType + " = ?");

        statement.setString(1, (String) identifier);
        hasAccountList.remove((String) identifier);
        sqlFile.set("." + this.tableName + ".IdentifierList", sqlFile.getStringList("." + this.tableName + ".IdentifierList").remove((String) identifier));

        if (TablePlayer.class.isAssignableFrom(table)) {
            statement.setString(1, identifier.toString());
            hasAccountList.remove(identifier.toString());
            sqlFile.set("." + this.tableName + ".IdentifierList", sqlFile.getStringList("." + this.tableName + ".IdentifierList").remove(identifier.toString()));
        }

        statement.executeUpdate();
        statement.close();
    }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        return someExpansion.onRequest(player, params);
    }

    public String onPlaceholderRequest(String identifierType, @NotNull String params) {
        Player fakePlayer = new FakePlayer(identifierType);

        System.out.println(fakePlayer + " --------------------");
        System.out.println(someExpansion.onRequest(fakePlayer, params) + " --------------------");
        return someExpansion.onRequest(fakePlayer, params);
    }
}