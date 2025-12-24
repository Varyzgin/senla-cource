package garageV4.view;

import java.util.List;
import java.util.Stack;

public class Navigator {
    private Menu currentMenu;
    private final Stack<Menu> history = new Stack<>();

    Navigator(Menu menu) {
        currentMenu = menu;
    }

    void printMenu() {
        System.out.println("\n=== " + currentMenu.getName() + " ===");
        List<MenuItem> menuItems = currentMenu.getMenuItems();

        currentMenu.run();

        if (!currentMenu.isRoot()) {
            System.out.println("    0) Назад");
        }
        for (int i = 0; i < menuItems.size(); ++i) {
            System.out.println("    " + (i + 1) + ") " + menuItems.get(i).getTitle());
        }
    }
    void navigate(Integer index) {
        for (var i = 0; i < currentMenu.isNumOfLayersToReturn(); i++) {
            if (!currentMenu.isRoot() && index == 0) {
                currentMenu = history.pop();
                return;
            }
        }
        MenuItem item = currentMenu.getMenuItems().get(index - 1);
        if (item.getNextMenu() != null) {
            history.push(currentMenu);
            currentMenu = item.getNextMenu();
            return;
        }
        if (item.getAction() != null) {
            item.getAction().execute();
        }
    }
}
