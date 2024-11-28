package fr.dojo59.api.provided.MobAPI.type;

import org.bukkit.entity.EntityType;

public enum Agressif {


    SKELETON(EntityType.SKELETON),
    ZOMBIE(EntityType.ZOMBIE);

    private final EntityType entityType;

    Agressif(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }
}
