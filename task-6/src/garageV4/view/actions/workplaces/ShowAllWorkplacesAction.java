package garageV4.view.actions.workplaces;

import garageV4.presenter.GarageManager;
import garageV4.view.IAction;

public class ShowAllWorkplacesAction implements IAction {
    GarageManager garage = GarageManager.Singleton();
    @Override
    public void execute() {
        for (var w : garage.showAllWorkplaces()) {
            System.out.println(w.showFull());
        }
    }
}
