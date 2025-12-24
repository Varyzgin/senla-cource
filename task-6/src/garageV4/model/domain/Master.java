package garageV4.model.domain;

import garageV4.model.status.MasterStatus;

import java.util.UUID;

public class Master {
    private final String id;
    private String name;
    private String position;
    private String rank;
    private MasterStatus status;
    private String workingHours; // например, "9:00-18:00"

    private Master(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.name = builder.name;
        this.position = builder.position;
        this.rank = builder.rank;
        this.status = MasterStatus.ACTIVE;
    }

    public String id() { return id; }
    public String name() { return name; }
    public String position() { return position; }
    public String rank() { return rank; }
    public MasterStatus status() { return status; }

    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setRank(String rank) { this.rank = rank; }
    public void setStatus(MasterStatus status) { this.status = status; }

    public String show() {
        return name + " " + position + " " + rank;
    }

    public String showMore() {
        return status + " " + name + " " + position + " " + rank;
    }

    public String showFull() {
        return id + " " + status + " " + name + " " + position + " " + rank;
    }

    public static class Builder {
        private String name;
        private String position;
        private String rank;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder position(String position) {
            if (!position.isBlank()) this.position = position;
            return this;
        }

        public Builder rank(String rank) {
            if (!rank.isBlank()) this.rank = rank;
            return this;
        }

        public Master build() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name is required");
            }
            return new Master(this);
        }
    }
}