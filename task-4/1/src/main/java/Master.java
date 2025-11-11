package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Master {
    private final String id;
    public String name;
    public String position;
    public String rank;
    public List<TimeInterval> timeIntervals = new ArrayList<>();

    Master(String name, String position, String rank) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.position = position;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }
    public void addWorkTime(TimeInterval timeInterval) {
        this.timeIntervals.add(timeInterval);
    }
    public void print() {
        System.out.println(name + " работает по графику:");
        for (var interval: timeIntervals) {
            System.out.println("    " + interval.show());
        }
    }
}
