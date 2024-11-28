package fr.dojo59.api.provided.Depre_DataBaseAPI.constructeur;

import fr.dojo59.api.provided.FilesAPI.FilesAPI;
import fr.dojo59.api.utils.ConvertList;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.configuration.ConfigurationSection;

class MySQLFile extends FilesAPI {
    public MySQLFile(String pathFile, String id) {
        super(SSAPI_Plugin.getINSTANCE(), "/MySQL/Table_" + pathFile + "/", "Connection.yml", "DataBase", "TableDb_" + id);
    }

    @Override
    public void initBaseConfig(ConfigurationSection configurationSection) {
        configurationSection.set("Connection.URL", "jdbc:mysql://");
        configurationSection.set("Connection.USER", "root");
        configurationSection.set("Connection.PASSWORD", "1234");
        configurationSection.set("TableName", ConvertList.list("Exemple1", "Exemple2"));
        configurationSection.set("Exemple1.IdentifierList", ConvertList.list("Id1", "Id2", "Id3"));
        configurationSection.set("Exemple2.IdentifierList", ConvertList.list("Id1", "Id2", "Id3"));

    }
}
