package garageV3.view;

public class MenuItem {
    private final String title;
    private final IAction action;
    private final Menu nextMenu;

    MenuItem(String title, IAction action, Menu nextMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    public IAction getAction() {
        return action;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public String getTitle() {
        return title;
    }

    void doAction() {
        if (action != null) {
            action.execute();
        }
    }
}
