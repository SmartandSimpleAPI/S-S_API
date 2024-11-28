package fr.dojo59.api.provided.DataBaseAPI.constructeur;

import fr.dojo59.api.provided.FilesAPI.FilesAPI;
import fr.dojo59.api.utils.ConvertList;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.configuration.ConfigurationSection;

class MySQLTableFile extends FilesAPI {
    public MySQLTableFile(String pathFile, String id) {
        super(SSAPI_Plugin.getINSTANCE(), "../MySQL/", "Table_" + pathFile + ".yml", "Table", "TableDb_" + id);
    }

    @Override
    public void initBaseConfig(ConfigurationSection configurationSection) {
        configurationSection.set("Connection.IdConnection", "ConnectionX");
        configurationSection.set("IdentifierList", ConvertList.list("Id1", "Id2", "Id3"));
    }
}
