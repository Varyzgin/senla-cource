package garageV3.presenter;

import garageV3.model.TimeInterval;

import java.util.ArrayList;
import java.util.List;

public class MasterService {
    private final List<Master> masters = new ArrayList<>();

    public void add(Master master) {
        masters.add(master);
    }

//    public void remove(Master master) {
//        masters.remove(master);
//    }

    public void addTimeInterval(String id, TimeInterval timeInterval) {
        if (timeInterval.start().isAfter(timeInterval.end())) {
            System.out.println("Не удалось добавить рабочее время так как время начала раньше времени окончания работы");
            return;
        }
        Master master = masters.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (master == null) return;

        boolean intersects = master.getWorkingSchedule().stream().anyMatch(existing ->
                existing.start().isBefore(timeInterval.end()) &&
                        existing.end().isAfter(timeInterval.start())
        );

        if (!intersects) {
            master.addWorkingInterval(timeInterval);
        } else {
            System.out.println("Не удалось добавить рабочее время из-за пересечения с другими временными промежутками");
        }
    }

    public void changeRank(String id, String rank) {
        Master master = masters.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (master == null) return;

        master.addRank(rank);
    }

    public void changePosition(String id, String position) {
        Master master = masters.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (master == null) return;

        master.addPosition(position);
    }

    public List<Master> show() {
        return this.masters;
    }

    public void print() {
        for (var master : masters) {
            System.out.println(master.name + " " +
                    ((master.position != null) ? master.position + " " : "") +
                    ((master.rank != null) ? master.rank : "")
            );
            for (var interval : master.getWorkingSchedule()) {
                System.out.println(interval.show());
            }
        }
    }
}
