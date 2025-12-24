package garageV3.presenter;

import garageV3.model.TimeInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Master {
    private final String id;
//    private final List<TimeInterval> freeIntervals = new ArrayList<>();
    private final List<TimeInterval> workingSchedule = new ArrayList<>();
    public String name;
    public String position;
    public String rank;

    public Master(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Master(String name, String position, String rank) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.position = position;
        this.rank = rank;
    }

    void addWorkingInterval(TimeInterval timeInterval) {
        workingSchedule.add(timeInterval);
//        freeIntervals.addAll(Arrays.asList(timeIntervals));
    }
    void addRank(String rank) {
        this.rank = rank;
    }

    void addPosition(String position) {
        this.position = position;
    }

    void removeWorkingInterval(TimeInterval timeInterval) {
        if (workingSchedule.contains(timeInterval)) {
            workingSchedule.removeIf(interval -> interval.equals(timeInterval));
        } else {
            System.out.println("Удаляемый элемент отсутствует в массиве, либо массив пуст");
        }
    }

//    void addFreeIntervals(TimeInterval[] timeIntervals) {
//        freeIntervals.addAll(Arrays.asList(timeIntervals));
//    }

//    void removeFreeInterval(TimeInterval timeInterval) {
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

    public void addWorkTime(TimeInterval timeInterval) {
        this.workingSchedule.add(timeInterval);
    }

    public void print() {
        System.out.println(name + " работает по графику:");
        for (var interval : workingSchedule) {
            System.out.println("    " + interval.show());
        }
    }
}
