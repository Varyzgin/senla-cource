package main.java;

import java.util.ArrayList;
import java.util.List;

public class MastersList {
    private List<Master> masters = new ArrayList<>();

    void addMaster(Master master) {
        masters.add(master);
    }
    void removeMaster(Master master) {
        masters.remove(master);
    }
    List<Master> showMasters() {
        return this.masters;
    }
}
