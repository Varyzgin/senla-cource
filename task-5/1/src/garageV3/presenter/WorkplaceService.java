package garageV3.presenter;

import garageV3.model.TimeInterval;

import java.util.ArrayList;
import java.util.List;

public class WorkplaceService {
    private final List<Workplace> workplaces = new ArrayList<>();

    public void add(Workplace workplace) {
        workplaces.add(workplace);
    }

    public void addTimeInterval(String id, TimeInterval timeInterval) {
        if (timeInterval.start().isAfter(timeInterval.end())) {
            System.out.println("Не удалось добавить рабочее время так как время начала раньше времени окончания работы");
            return;
        }
        Workplace wp = workplaces.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (wp == null) return;

        boolean intersects = wp.getWorkingSchedule().stream().anyMatch(existing ->
                existing.start().isBefore(timeInterval.end()) &&
                        existing.end().isAfter(timeInterval.start())
        );

        if (!intersects) {
            wp.addWorkingInterval(timeInterval);
        } else {
            System.out.println("Не удалось добавить рабочее время из-за пересечения с другими временными промежутками");
        }
    }

    public void remove(Workplace workplace) {
        workplaces.remove(workplace);
    }

    public List<Workplace> show() {
        return this.workplaces;
    }

    public void print() {
        for (var place : workplaces) {
            System.out.println(place.name);
            for (var timeInterval : place.getWorkingSchedule()) {
                System.out.println(timeInterval.show());
            }
        }
    }
}
