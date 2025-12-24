package garageV3.presenter;

import garageV3.model.Status;
import garageV3.model.TimeInterval;

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
    public TimeInterval timeInterval;
    public LocalDateTime creationTime;
    public Float price;

    public Order(String name, Float price, List<String> masterIds, String workplaceId, TimeInterval timeInterval) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.status = Status.OPENED;
        this.masterIds = masterIds;
        this.workplaceId = workplaceId;
        this.timeInterval = timeInterval;
        this.creationTime = LocalDateTime.now();
        this.price = price;
    }

    public String getId() {
        return id;
    }
//    public String getWorkplaceId() { return workplace.getId(); }

    public LocalDateTime getStartTime() {
        return timeInterval.start();
    }

}
