package fr.dojo59.api.provided.DataBaseAPI.constructeur;

import fr.dojo59.api.provided.FilesAPI.FilesAPI;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.configuration.ConfigurationSection;

class MySQLConnectionFile extends FilesAPI {
    public MySQLConnectionFile() {
        super(SSAPI_Plugin.getINSTANCE(), "../MySQL/", "Connection.yml", "DataBase", "Connection");
    }

    @Override
    public void initBaseConfig(ConfigurationSection configurationSection) {
        configurationSection.set("Connection1.URL", "jdbc:mysql://");
        configurationSection.set("Connection1.USER", "root");
        configurationSection.set("Connection1.PASSWORD", "1234");

    }
}
