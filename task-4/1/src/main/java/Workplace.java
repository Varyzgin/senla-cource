package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workplace {
    private final String id;
    public String name;
    public List<TimeInterval> workingIntervals = new ArrayList<>();

    Workplace(String name, List<TimeInterval> workingIntervals) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.workingIntervals = workingIntervals;
    }

    public String getId() {
        return id;
    }
    public void print() {
        System.out.println(name + " работает по графику:");
        for (var interval: workingIntervals) {
            System.out.println("    " + interval.show());
        }
    }
}
