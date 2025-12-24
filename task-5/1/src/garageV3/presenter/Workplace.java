package garageV3.presenter;

import garageV3.model.TimeInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workplace {
    private final String id;
//    private List<TimeInterval> freeIntervals;
    private List<TimeInterval> workingSchedule = new ArrayList<>();
    public String name;

    public Workplace(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Workplace(String name, List<TimeInterval> workingSchedule) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
//        this.freeIntervals = workingSchedule;
        if (workingSchedule != null) {
            this.workingSchedule = workingSchedule;
//            this.freeIntervals = workingSchedule;
        }
    }

    public void addWorkingInterval(TimeInterval timeInterval) {
        workingSchedule.add(timeInterval);
//        freeIntervals.add(timeInterval);
    }

    public void removeWorkingInterval(TimeInterval timeInterval) {
        if (workingSchedule.contains(timeInterval)) {
            workingSchedule.removeIf(interval -> interval.equals(timeInterval));
        } else {
            System.out.println("Удаляемый элемент отсутствует в массиве, либо массив пуст");
        }
    }

//    public void addFreeIntervals(TimeInterval[] timeIntervals) {
//        freeIntervals.addAll(asList(timeIntervals));
//    }
//
//    public void removeFreeInterval(TimeInterval timeInterval) {
//        if (freeIntervals.contains(timeInterval)) {
//            freeIntervals.removeIf(interval -> interval.equals(timeInterval));
//        } else {
//            System.out.println("Удаляемый элемент отсутствует в массиве, либо массив пуст");
//        }
//    }

    public String getId() {
        return id;
    }

    public List<TimeInterval> getWorkingSchedule() {
        return workingSchedule;
    }

//    public List<TimeInterval> getFreeIntervals() {
//        return freeIntervals;
//    }

    public void print() {
        System.out.println(name + " работает по графику:");
        for (var interval : workingSchedule) {
            System.out.println("    " + interval.show());
        }
    }
}
