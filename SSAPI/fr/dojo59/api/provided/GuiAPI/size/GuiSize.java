package fr.dojo59.api.provided.GuiAPI.size;

import org.bukkit.event.inventory.InventoryType;

public enum GuiSize {

    Chest_SMALL(9),

    CHEST_MEDIUM(18),

    CHEST_LARGE(27),

    CHEST_HUGE(36),

    CHEST_GIGANTIC(45),

    CHEST_ENORMOUS(54),

    ANVIL(InventoryType.ANVIL),

    BEACON(InventoryType.BEACON),

    BREWING(InventoryType.BREWING),

    CARTOGRAPHY(InventoryType.CARTOGRAPHY),

    CHEST(InventoryType.CHEST),

    CRAFTING(InventoryType.CRAFTING),

    CRAFTER(InventoryType.CRAFTER),

    DISPENSER(InventoryType.DISPENSER),

    DROPPER(InventoryType.DROPPER),

    ENCHANTING(InventoryType.ENCHANTING),

    ENDER_CHEST(InventoryType.ENDER_CHEST),

    FURNACE(InventoryType.FURNACE),

    GRINDSTONE(InventoryType.GRINDSTONE),

    HOPPER(InventoryType.HOPPER),

    JUKEBOX(InventoryType.JUKEBOX),

    LOOM(InventoryType.LOOM),

    MERCHANT(InventoryType.MERCHANT),

    SMITHING(InventoryType.SMITHING),

    STONECUTTER(InventoryType.STONECUTTER),

    WORKBENCH(InventoryType.WORKBENCH);

    private final int size;
    private final InventoryType inventoryType;

    GuiSize(int numberSlot) {
        this.size = numberSlot;
        this.inventoryType = null;
    }

    GuiSize(InventoryType inventoryType) {
        this.size = inventoryType.getDefaultSize();
        this.inventoryType = inventoryType;

    }

    public int getSize() {
        return size;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }
}
