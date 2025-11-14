package garageV1.java;

import java.util.UUID;

public class Master {
    private final String id;
    public String name;
    public String position;
    public String rank;

    Master(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
