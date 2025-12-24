package garageV3.view;

public class Builder {
    private Menu rootMenu;

    void buildMenu() {


        Menu workplaces = new Menu("Рабочие места",
                new MenuItem("Добавить", Actions::addWorkplaceDialog, null)
//                ,
//                new MenuItem("Удалить", null, workplaceRemove),
//                new MenuItem("Изменить", null, workplaceEdit)
        );

        Menu masters = new Menu("Мастера",
                new MenuItem("Добавить", Actions::addMasterDialog, null)
        );
        Menu orders = new Menu("Заказы",
                new MenuItem("Добавить", Actions::addOrderDialog, null)
        );



        rootMenu = new Menu("Главная страница", true,
                new MenuItem("Рабочие места", null, workplaces),
                new MenuItem("Мастера", null, masters),
                new MenuItem("Заказы", null, orders),
                new MenuItem("Выход", () -> System.exit(0), null)
        );
    }
    Menu getRootMenu() {
        return rootMenu;
    }
}
