import java.util.ArrayList;
import java.util.List;

public class WorkplacesList {
    private List<Workplace> workplaces = new ArrayList<>();

    void addWorkplace(Workplace workplace) {
        workplaces.add(workplace);
    }
    void removeMaster(Workplace workplace) {
        workplaces.remove(workplace);
    }
    List<Workplace> showWorkplaces() {
        return this.workplaces;
    }
}
