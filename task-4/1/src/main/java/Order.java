package main.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    public Status status;
    public String name;
    public List<String> masterIds = new ArrayList<>();
    public String workplaceId;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public LocalDateTime creationTime;
    public Float price;

    Order(String name, Float price, List<String> masterIds, String workplaceId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.status = Status.OPENED;
        this.masterIds = masterIds;
        this.workplaceId = workplaceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.creationTime = LocalDateTime.now();
        this.price = price;
    }

    public String getId() { return id; }
//    public String getWorkplaceId() { return workplace.getId(); }

    public LocalDateTime getStartTime() {
        return startTime;
    }

}
