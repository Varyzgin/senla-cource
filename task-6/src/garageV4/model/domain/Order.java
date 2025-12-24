package garageV4.model.domain;

import garageV4.model.status.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private final String id;
    private String name;
    private Float price;
    private OrderStatus status;
    private Double durationHours;
    private final LocalDateTime creationTime;

    public Order(String name, Float price, Double durationHours) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.status = OrderStatus.CREATED;
        this.durationHours = durationHours;
        this.creationTime = LocalDateTime.now();
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Float price() {
        return price;
    }

    public OrderStatus status() {
        return status;
    }

    public Double durationHours() {
        return durationHours;
    }

    public LocalDateTime creationTime() {
        return creationTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDurationHours(Double durationHours) {
        this.durationHours = durationHours;
    }

    public String show() {
        return name + " " + price + " " + creationTime;
    }

    public String showMore() {
        return status + " " + name + " " + price + " " + creationTime;
    }

    public String showFull() {
        return id + " " + status + " " + name + " " + price + " " + creationTime;
    }
}
