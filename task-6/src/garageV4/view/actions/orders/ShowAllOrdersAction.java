package garageV4.view.actions.orders;

import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

public class ShowAllOrdersAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    @Override
    public void execute() {
        for (var o : garage.showAllOrders()) {
            System.out.println(o);
        }
    }
}
