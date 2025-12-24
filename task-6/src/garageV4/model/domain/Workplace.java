package garageV4.model.domain;

import garageV4.model.status.WorkplaceStatus;

import java.util.UUID;

public class Workplace {
    private final String id;
    private String name;
    private WorkplaceStatus status;

    public Workplace(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.status = WorkplaceStatus.OPENED;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public WorkplaceStatus status() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(WorkplaceStatus status) {
        this.status = status;
    }

    public String show() {
        return name;
    }

    public String showMore() {
        return status + " " + name;
    }

    public String showFull() {
        return id + " " + status + " " + name;
    }
}
