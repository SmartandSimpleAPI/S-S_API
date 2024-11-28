package fr.dojo59.api.provided.MobAPI;

import fr.dojo59.api.provided.MobAPI.type.Agressif;
import fr.dojo59.api.provided.MobAPI.type.Passif;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Objects;

public abstract class AgressifMobBuilder {

    private String title;
    private String id;
    private Agressif agressifMob;

    public static final HashMap<String, AgressifMobBuilder> MOBS = new HashMap<>();

    public AgressifMobBuilder(Agressif agressifMob, String title, String id) {
        if (agressifMob == null) {
            throw new IllegalArgumentException("The agressifMob of the Gui cannot be empty");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("The Title of the Gui cannot be empty");
        }
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The id of the Gui cannot be empty");
        }

        this.title = title;
        this.agressifMob = agressifMob;
        this.id = id.replace(" ", "_");

        createMob();

        MOBS.put(id.replace(" ", "_").toLowerCase(), this);
    }

    public abstract void initCustomMob();

    private void createMob() {
        initCustomMob();
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public Agressif getAgressifMob() {
        return agressifMob;
    }


    public setHp(int hp) {
        agressifMob.getEntityType().
    }

    public void spawnMonster(Location location) {
        Objects.requireNonNull(location.getWorld()).spawnEntity(location, this.agressifMob.getEntityType());
    }

}
