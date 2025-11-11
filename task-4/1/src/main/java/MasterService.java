package main.java;

import java.util.ArrayList;
import java.util.List;

public class MasterService {
    private final List<Master> masters = new ArrayList<>();

    void add(Master master) {
        masters.add(master);
    }
    void remove(Master master) {
        masters.remove(master);
    }
    List<Master> show() {
        return this.masters;
    }
}
