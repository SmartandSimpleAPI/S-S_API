package fr.dojo59.api.provided.MobAPI;

import fr.dojo59.api.provided.MobAPI.type.Agressif;
import fr.dojo59.api.provided.MobAPI.type.Passif;

import java.util.HashMap;

public abstract class PassifMobBuilder {

    private String title;
    private String id;
    private Passif passifMob;

    public static final HashMap<String, PassifMobBuilder> MOBS = new HashMap<>();

    public PassifMobBuilder(Passif passifMob, String title, String id) {
        if (passifMob == null) {
            throw new IllegalArgumentException("The passifMob of the Gui cannot be empty");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("The Title of the Gui cannot be empty");
        }
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The id of the Gui cannot be empty");
        }

        this.title = title;
        this.passifMob = passifMob;
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

    public Passif getPassifMob() {
        return passifMob;
    }


}
