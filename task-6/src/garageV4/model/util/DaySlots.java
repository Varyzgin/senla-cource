package garageV4.model.util;

public class DaySlots {
    private final boolean[] slots; // true = занято

    public DaySlots(int count) {
        this.slots = new boolean[count];
    }

    public void markBusy(int from, int to) {
        for (int i = from; i < to; i++) slots[i] = true;
    }

    public boolean isBusy(int index) {
        return slots[index];
    }

    public int busyCount() {
        int count = 0;
        for (var busy : slots) {
            if (busy) count++;
        }
        return count;
    }

    public boolean isFreeRange(int from, int length) {
        for (int i = from; i < from + length; i++) {
            if (i >= slots.length || slots[i]) return false;
        }
        return true;
    }
}