package garageV2.java;

import java.util.ArrayList;
import java.util.List;

public class WorkplaceService {
    private final List<Workplace> workplaces = new ArrayList<>();

    void add(Workplace workplace) {
        workplaces.add(workplace);
    }
    void remove(Workplace workplace) {
        workplaces.remove(workplace);
    }
    List<Workplace> show() {
        return this.workplaces;
    }
}
