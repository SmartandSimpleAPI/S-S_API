package fr.dojo59.api.provided.GuiAPI;

import fr.dojo59.api.compile.ConsoleAPI.Console;
import fr.dojo59.api.compile.ConsoleAPI.ConsoleColor;
import fr.dojo59.api.provided.GuiAPI.size.GuiSize;
import fr.dojo59.main.SSAPI_Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class GuiBuilder implements Listener {

    private final String title;
    private final GuiSize size;
    private final String id;
    private Inventory inventory;
    public static final HashMap<String, GuiBuilder> GUIS = new HashMap<>();

    private final List<Integer> cancelledSlots = new ArrayList<>();
    private final List<Integer> cancelledLines = new ArrayList<>();

    public GuiBuilder(GuiSize size, String title, String id) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("The Title of the Gui cannot be empty.");
        }
        if (size == null) {
            throw new IllegalArgumentException("The size of the Gui cannot be null.");
        }
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The id of the Gui cannot be empty.");
        }

        this.title = title;
        this.size = size;
        this.id = id.replace(" ", "_");

        createInventory();

        GUIS.put(this.id, this);
    }

    public abstract void initCustomChest();

    public abstract void onInteract(InventoryClickEvent e);

    public abstract void isClickRight(ItemStack itemClicked, Player player);

    public abstract void isClickLeft(ItemStack itemClicked, Player player);

    public abstract void isClickMiddle(ItemStack itemClicked, Player player);

    public abstract void isClickShiftLeft(ItemStack itemClicked, Player player);

    public abstract void isClickShiftRight(ItemStack itemClicked, Player player);

    public abstract void isClickNumberKey(ItemStack itemClicked, Player player);

    public abstract void isClickControlDrop(ItemStack itemClicked, Player player);

    public abstract void isClickDoubleClick(ItemStack itemClicked, Player player);

    public abstract void isClickSwapOfHand(ItemStack itemClicked, Player player);

    public abstract void isClickDrop(ItemStack itemClicked, Player player);

    private void createInventory() {
        if (size.getInventoryType() != null) {
            inventory = Bukkit.createInventory(null, size.getInventoryType(), title);
        } else {
            inventory = Bukkit.createInventory(null, size.getSize(), title);
        }
        initCustomChest();

        SSAPI_Plugin.getINSTANCE().getServer().getPluginManager().registerEvents(this, SSAPI_Plugin.getINSTANCE());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size.getSize();
    }

    public String getId() {
        return id;
    }

    public void setItem(int line, int slot, ItemStack itemStack) {
        int index = (line - 1) * 9 + (slot - 1);
        if (index < 0) {
            index = index * -1;
        }
        if (index >= 0 && index < inventory.getSize()) {
            inventory.setItem(index, itemStack);
        }
    }

    public void setItem(int slot, ItemStack itemStack) {
        if (slot > 0 && slot <= inventory.getSize()) {
            inventory.setItem(slot - 1, itemStack);
        }
    }

    public void setLine(int line, ItemStack item) {
        int startIndex = (line - 1) * 9;
        if (startIndex >= 0 && startIndex + 9 <= inventory.getSize()) {
            for (int i = startIndex; i < startIndex + 9; i++) {
                inventory.setItem(i, item);
            }
        }
    }

    public void setCancelledItem(int... slots) {
        for (int slot : slots) {
            if (slot > 0 && slot <= inventory.getSize()) {
                cancelledSlots.add(slot - 1);
            }
        }
    }

    public void removeCancelledItem(int... slots) {
        for (int slot : slots) {
            if (slot > 0 && slot <= inventory.getSize()) {
                cancelledSlots.remove(slot - 1);
            }
        }
    }

    public void setCancelledLine(int... lines) {
        for (int line : lines) {
            if (line > 0 && line <= inventory.getSize() / 9) {
                cancelledLines.add(line - 1);
            }
        }
    }

    public void removeCancelledLine(int... lines) {
        for (int line : lines) {
            if (line > 0 && line <= inventory.getSize() / 9) {
                cancelledLines.remove(line - 1);
            }
        }
    }

    public void openGui(Player player) {
        player.openInventory(getInventory());
    }

    public void closeGui(Player player) {
        player.closeInventory();
    }

    public static GuiBuilder editGui(String id) {
        return GUIS.get(id.replace(" ", "_"));
    }


    @EventHandler
    public void interact(InventoryClickEvent e) {
        if (e.getInventory().equals(this.inventory)) {
            if (cancelledSlots.contains(e.getRawSlot()) || cancelledLines.contains(e.getRawSlot() / 9)) {
                e.setCancelled(true);
            }
            Player player = null;
            if (e.getWhoClicked() instanceof Player) {
                player = (Player) e.getWhoClicked();
            }

            if (e.getClick() == SelectClickAction.LEFT.getClickAction()) {
                isClickLeft(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.RIGHT.getClickAction()) {
                isClickRight(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.MIDDLE.getClickAction()) {
                isClickMiddle(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.SHIFT_LEFT.getClickAction()) {
                isClickShiftLeft(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.SHIFT_RIGHT.getClickAction()) {
                isClickShiftRight(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.NUMBER_KEY.getClickAction()) {
                isClickNumberKey(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.CONTROL_DROP.getClickAction()) {
                isClickControlDrop(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.DOUBLE_CLICK.getClickAction()) {
                isClickDoubleClick(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.SWAP_OFFHAND.getClickAction()) {
                isClickSwapOfHand(e.getCurrentItem(), player);
            } else if (e.getClick() == SelectClickAction.DROP.getClickAction()) {
                isClickDrop(e.getCurrentItem(), player);
            }

            onInteract(e);
        }
    }
}
