package garageV4.view;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String name;
    private final List<MenuItem> menuItems;
    private Runnable someFunc = () -> {
    };
    private boolean isRoot = false;
    private int numOfLayersToReturn = 1;

    Menu(String name, Runnable someFunc, int numOfLayersToReturn, MenuItem... menuItems) {
        this.name = name;
        this.someFunc = someFunc;
        this.numOfLayersToReturn = numOfLayersToReturn;
        this.menuItems = new ArrayList<>(List.of(menuItems));
    }

    Menu(String name, Runnable someFunc, MenuItem... menuItems) {
        this.name = name;
        this.someFunc = someFunc;
        this.menuItems = new ArrayList<>(List.of(menuItems));
    }

    Menu(String name, Runnable someFunc, boolean isRoot, MenuItem... menuItems) {
        this.name = name;
        this.someFunc = someFunc;
        this.isRoot = isRoot;
        this.menuItems = new ArrayList<>(List.of(menuItems));
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void run() {
        someFunc.run();
    }

    String getName() {
        return name;
    }

    List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public int isNumOfLayersToReturn() {
        return numOfLayersToReturn;
    }

    public void setNumOfLayersToReturn(int numOfLayersToReturn) {
        this.numOfLayersToReturn = numOfLayersToReturn;
    }
}
