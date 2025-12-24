package garageV3.view;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String name;
    private final List<MenuItem> menuItems;
    private boolean isRoot = false;

    Menu(String name, MenuItem... menuItems) {
        this.name = name;
        this.menuItems = new ArrayList<>(List.of(menuItems));
    }

    Menu(String name, boolean isRoot, MenuItem... menuItems) {
         this.name = name;
         this.isRoot = isRoot;
         this.menuItems = new ArrayList<>(List.of(menuItems));
    }

    public boolean isRoot() {
        return isRoot;
    }

    String getName() {
        return name;
    }

    List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
