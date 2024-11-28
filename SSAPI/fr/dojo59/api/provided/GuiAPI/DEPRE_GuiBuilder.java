//package fr.dojo59.api.GuiAPI;
//
////import fr.dojo59.guiBuilder.menuNav.AssetMenu;
////import fr.dojo59.guiBuilder.menuNav.NavSelector;
//
//import fr.dojo59.api.GuiAPI.size.GuiSize;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.HashMap;
//
//public abstract class GuiBuilder {
//
//    public abstract void initCustomChest();
//
//    public GuiBuilder(String id, GuiSize size, String title) {
//        if (title.isEmpty()) throw new IllegalArgumentException("The Title of the Gui cannot be empty");
//
//        this.title = title;
//        this.size = size;
//
//        createInventory();
//
//        GUIS.put(id.replace(" ", "_").toLowerCase(), this);
//
//        initCustomChest();
//    }
//
//    private final String title;
//    private final GuiSize size;
//    public static final HashMap<String, GuiBuilder> GUIS = new HashMap<>();
//
//    private Inventory inventory;
//    private Inventory inventoryBack;
//    private Inventory inventoryNextPage;
//    private Inventory inventoryPreviousPage;
//
//    private void createInventory() {
//        if (size.getInventoryType() != null) {
//            inventory = Bukkit.createInventory(null, size.getInventoryType(), title);
//        } else {
//            inventory = Bukkit.createInventory(null, size.getSize(), title);
//        }
//    }
//
//    public Inventory getInventory() {
//        return inventory;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public int getSize() {
//        return size.getSize();
//    }
//
//    public void setItem(int line, int slot, ItemStack itemStack) {
//        if (slot > 0 && slot <= 9 && line <= 6) {
//            int numCase = 0;
//            if (line == 1) {
//                numCase = slot - 1;
//            } else if (line >= 2) {
//                numCase = (slot - 1) + (9 * (line - 1));
//            }
//            inventory.setItem(numCase, itemStack);
//        }
//    }
//
//    public void setItem(int slot, ItemStack itemStack) {
//        int numCase = slot - 1;
//        inventory.setItem(numCase, itemStack);
//    }
//
//    public void setLine(int line, ItemStack item) {
//        int requiredSize = line * 9;
//        if (inventory.getSize() % 9 == 0 && inventory.getSize() >= requiredSize) {
//            int startIndex = (line - 1) * 9;
//            for (int i = startIndex; i < startIndex + 9; i++) {
//                inventory.setItem(i, item);
//            }
//        }
//    }
//
//    public void openGui(Player player) {
//        player.openInventory(inventory);
//    }
//
//    public void closeGui(Player player) {
//        player.closeInventory();
//    }
//
//    public GuiBuilder editeGui(String id) {
//        return GUIS.get(id.replace(" ", "_").toLowerCase());
//    }
//
////    public void setMenuNav(String lang, NavSelector... style) {
////        Map<GuiSize, GuiSize> sizeMapping = Map.of(
////                GuiSize.Chest_SMALL, GuiSize.CHEST_MEDIUM,
////                GuiSize.CHEST_MEDIUM, GuiSize.CHEST_LARGE,
////                GuiSize.CHEST_LARGE, GuiSize.CHEST_HUGE,
////                GuiSize.CHEST_HUGE, GuiSize.CHEST_GIGANTIC,
////                GuiSize.CHEST_GIGANTIC, GuiSize.CHEST_ENORMOUS
////        );
////
////        size = sizeMapping.getOrDefault(size, size);
////
////        ItemStack[] contents = inventory.getContents();
////        inventory = Bukkit.createInventory(null, size.getSize(), title);
////        inventory.setContents(contents);
////
////        Map<NavSelector, Integer> navPositions = new EnumMap<>(NavSelector.class);
////        navPositions.put(NavSelector.CLOSE, size.getSize() - 4);
////        navPositions.put(NavSelector.BACK, size.getSize() - 5);
////        navPositions.put(NavSelector.NEXT_PAGE, size.getSize());
////        navPositions.put(NavSelector.PREVIOUS_PAGE, size.getSize() - 8);
////
////        setLine(size.getSize() / 9, AssetMenu.FONT);
////
////        for (NavSelector nav : style) {
////            setItem(navPositions.get(nav), getMenuItem(lang, nav.name()));
////        }
////    }
////
////    public void setBACK(GuiBuilder backInventory) {
////        inventoryBack = backInventory.getInventory();
////    }
////
////    public Inventory getInventoryBack() {
////        return inventoryBack;
////    }
////
////    public void setNEXT_PAGE(GuiBuilder nextPageInventory) {
////        new BukkitRunnable() {
////            @Override
////            public void run() {
////                inventoryNextPage = nextPageInventory.getInventory();
////            }
////        }.runTaskLater(Main.getINSTANCE(), 20*5);
////
////    }
////
////    public Inventory getInventoryNextPage() {
////        return inventoryNextPage;
////    }
////
////    public void setPREVIOUS_PAGE(GuiBuilder previousPageInventory) {
////        inventoryPreviousPage = previousPageInventory.getInventory();
////    }
////
////    public Inventory getInventoryPreviousPage() {
////        return inventoryPreviousPage;
////    }
////
////    private ItemStack getMenuItem(String lang, String item) {
////        AssetMenu menu;
////        switch (lang.toUpperCase()) {
////            case "FR" -> menu = AssetMenu.FR;
////            case "DE" -> menu = AssetMenu.DE;
////            case "ES" -> menu = AssetMenu.ES;
////            case "IT" -> menu = AssetMenu.IT;
////            case "EN" -> menu = AssetMenu.EN;
////            default -> throw new IllegalArgumentException("Unknown language: " + lang);
////        }
////
////        return switch (item) {
////            case "CLOSE" -> menu.CLOSE();
////            case "BACK" -> menu.BACK();
////            case "NEXT_PAGE" -> menu.NEXT_PAGE();
////            case "PREVIOUS_PAGE" -> menu.PREVIOUS_PAGE();
////            default -> throw new IllegalArgumentException("Unknown menu item: " + item);
////        };
////    }
//}
