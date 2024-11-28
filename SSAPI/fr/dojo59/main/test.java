package fr.dojo59.main;

import fr.dojo59.api.provided.MobAPI.PassifMobBuilder;
import fr.dojo59.api.provided.MobAPI.type.Passif;

public class test extends PassifMobBuilder {

    public test(Passif passifMob, String title, String id) {
        super(passifMob, title, id);
    }

    @Override
    public void initCustomMob() {

    }
}
