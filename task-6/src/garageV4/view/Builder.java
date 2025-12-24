package garageV4.view;

import garageV4.model.param.OrdersSortParam;
import garageV4.presenter.GarageManager;
import garageV4.view.actions.masters.AddMasterAction;
import garageV4.view.actions.masters.RemoveMasterAction;
import garageV4.view.actions.masters.ShowAllMastersAction;
import garageV4.view.actions.orders.AddOrderNearestAction;
import garageV4.view.actions.orders.EditOrderAction;
import garageV4.view.actions.orders.ShowAllOrdersAction;
import garageV4.view.actions.workplaces.AddWorkplaceAction;
import garageV4.view.actions.workplaces.RemoveWorkplaceAction;
import garageV4.view.actions.workplaces.ShowAllWorkplacesAction;

public class Builder {
    private Menu rootMenu;

    private static Menu ordersMenu() {
        Menu addOrder = new Menu("Добавить заказ", () -> {
            for (var o : GarageManager.Singleton()
                    .showOpenedOrdersSortedAs(OrdersSortParam.CREATION_TIME, false)) {
                System.out.println(o.show());
            }
        }, // тут надо бы вывести графическое изображение всех занятых слотов для мастеров и мест
                2,
                new MenuItem("Автоматически (ближайшее/на дату)", new AddOrderNearestAction(), null),
                new MenuItem("Разместить вручную", null, null)
        );

        return new Menu("Заказы", () -> {
            for (var o : GarageManager.Singleton()
                    .showOpenedOrdersSortedAs(OrdersSortParam.CREATION_TIME, false)) {
                System.out.println(o.show());
            }
        },
                new MenuItem("Добавить", null, addOrder),
                new MenuItem("Выбрать для редактирования", new EditOrderAction(), null),
                new MenuItem("Показать все", new ShowAllOrdersAction(), null)
        );
    }

    void buildMenu() {
        Menu workplaces = new Menu("Рабочие места", () -> {
            for (var w : GarageManager.Singleton().showOpenedWorkplaces()) {
                System.out.println(w.show());
            }
        },
                new MenuItem("Добавить", new AddWorkplaceAction(), null),
                new MenuItem("Удалить", new RemoveWorkplaceAction(), null),
                new MenuItem("Просмотреть всех", new ShowAllWorkplacesAction(), null)
        );

        Menu masters = new Menu("Мастера", () -> {
            for (var m : GarageManager.Singleton().showActiveMasters()) {
                System.out.println(m.show());
            }
        },
                new MenuItem("Добавить", new AddMasterAction(), null),
                new MenuItem("Удалить", new RemoveMasterAction(), null),
                new MenuItem("Просмотреть всех", new ShowAllMastersAction(), null)
        );

        Menu orders = ordersMenu();

        rootMenu = new Menu("Главная страница", () -> {
        }, true,
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
