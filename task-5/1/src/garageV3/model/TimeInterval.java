package garageV3.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TimeInterval(LocalDateTime start, LocalDateTime end) {
    public String show() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy h:mm");
        String startFormatted = String.format("%2s", start.format(formatter).substring(0, 2)) +
                start.format(formatter).substring(2);
        String endFormatted = String.format("%2s", end.format(formatter).substring(0, 2)) +
                end.format(formatter).substring(2);
        return String.format("c %s до %s", startFormatted, endFormatted);
    }
}
