package garageV2.java;

import java.time.LocalDateTime;

public record TimeInterval(LocalDateTime start, LocalDateTime end) {
    String show() {
        return "c " + start + " до " + end;
    }
}
