import java.util.UUID;

public class Workplace {
    private final String id;
    public String name;

    Workplace(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
