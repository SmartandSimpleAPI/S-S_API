package fr.dojo59.main;

import fr.dojo59.api.provided.GuiAPI.GuiBuilder;
import fr.dojo59.api.provided.GuiAPI.size.GuiSize;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiTest extends GuiBuilder {
    public GuiTest(String id, GuiSize size, String title) {
        super(size, title, id);
    }

    @Override
    public void initCustomChest() {
    }

    @Override
    public void onInteract(InventoryClickEvent e) {

    }

    @Override
    public void isClickRight(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickLeft(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickMiddle(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickShiftLeft(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickShiftRight(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickNumberKey(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickControlDrop(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickDoubleClick(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickSwapOfHand(ItemStack itemClicked, Player player) {

    }

    @Override
    public void isClickDrop(ItemStack itemClicked, Player player) {

    }
}
