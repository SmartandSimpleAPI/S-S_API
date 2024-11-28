package fr.dojo59.api.provided.GuiAPI;

import org.bukkit.event.inventory.ClickType;

enum SelectClickAction {
    LEFT(ClickType.LEFT),
    RIGHT(ClickType.RIGHT),
    MIDDLE(ClickType.MIDDLE),
    SHIFT_LEFT(ClickType.SHIFT_LEFT),
    SHIFT_RIGHT(ClickType.SHIFT_RIGHT),
    NUMBER_KEY(ClickType.NUMBER_KEY),
    CONTROL_DROP(ClickType.CONTROL_DROP),
    DOUBLE_CLICK(ClickType.DOUBLE_CLICK),
    SWAP_OFFHAND(ClickType.SWAP_OFFHAND),
    DROP(ClickType.DROP);

    private final ClickType action;

    SelectClickAction(ClickType action) {
        this.action = action;
    }

    public ClickType getClickAction() {
        return action;
    }

    public boolean isSame(ClickType clickedType) {
        return this.getClickAction().equals(clickedType);
    }

}
