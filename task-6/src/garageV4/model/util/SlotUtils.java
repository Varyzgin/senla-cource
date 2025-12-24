package garageV4.model.util;

import java.time.LocalTime;

public class SlotUtils {
    public static final int SLOT_MIN = 30;

    public static int toSlot(LocalTime t) {
        return (t.getHour() * 60 + t.getMinute()) / SLOT_MIN;
    }

    public static LocalTime fromSlot(int slot) {
        int totalMinutes = slot * SLOT_MIN;
        return LocalTime.of(totalMinutes / 60, totalMinutes % 60);
    }
}