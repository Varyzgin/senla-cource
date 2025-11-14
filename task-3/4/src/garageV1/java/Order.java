package garageV1.java;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private final String id;
    public Status status;
    public String name;
    public Master master;
    public Workplace workplace;
    public LocalDateTime beginTime;
    public LocalDateTime endTime;
    public LocalDateTime creationTime;

    Order(String name, Master master, Workplace workplace, LocalDateTime beginTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.status = Status.OPENED;
        this.master = master;
        this.workplace = workplace;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.creationTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }
}
