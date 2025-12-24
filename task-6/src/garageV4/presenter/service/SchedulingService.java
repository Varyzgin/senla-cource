package garageV4.presenter.service;

import garageV4.model.domain.Master;
import garageV4.model.domain.Order;
import garageV4.model.domain.ScheduleEntry;
import garageV4.model.domain.Workplace;
import garageV4.model.repository.MasterRepositoryProvider;
import garageV4.model.repository.OrderRepositoryProvider;
import garageV4.model.repository.ScheduleRepositoryManager;
import garageV4.model.repository.WorkplaceRepositoryProvider;
import garageV4.model.status.MasterStatus;
import garageV4.model.status.OrderStatus;
import garageV4.model.status.WorkplaceStatus;
import garageV4.model.util.DaySlots;
import garageV4.model.util.SlotUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SchedulingService {
    private static final int SLOTS_COUNT = 48;
    private final ScheduleRepositoryManager scheduleRepo;
    private final OrderRepositoryProvider orderRepo;
    private final WorkplaceRepositoryProvider workplaceRepo;
    private final MasterRepositoryProvider masterRepo;

    public SchedulingService(ScheduleRepositoryManager scheduleRepo, OrderRepositoryProvider orderRepo,
                             WorkplaceRepositoryProvider workplaceRepo, MasterRepositoryProvider masterRepo) {
        this.scheduleRepo = scheduleRepo;
        this.orderRepo = orderRepo;
        this.workplaceRepo = workplaceRepo;
        this.masterRepo = masterRepo;
    }

    public Collection<ScheduleEntry> findEntriesInPeriod(LocalDateTime start, LocalDateTime end) {
        return scheduleRepo.getAll().stream()
                .filter(e -> e.start().isAfter(start) && e.end().isBefore(end))
                .toList();
    }

    public int busySlotsCountOfMaster(String masterId, int daysToAnalise) {
        int slots = 0;
        LocalDate day = LocalDate.now();
        for (int i = 0; i < daysToAnalise; i++) {
            slots += buildDaySlotsMaster(masterId, day).busyCount();
            day = day.plusDays(1);
        }
        return slots;
    }

    public Optional<ScheduleEntry> scheduleOrder(LocalDate date, Order order, Workplace place, Master... masters) {
        int slotsNeeded = (int) (order.durationHours() * 60 / SlotUtils.SLOT_MIN);
        if (place.status() == WorkplaceStatus.CLOSED) return Optional.empty();

        Map<String, DaySlots> masterSlots = buildDaySlotsForMasters(date, masters);
        DaySlots placeSlots = buildDaySlotsWorkplace(place.id(), date);

        // ищем первый свободный слот
        for (int start = 0; start < SLOTS_COUNT; start++) { // SLOTS_COUNT слотов по 30 мин
            if (allMastersFree(masterSlots, start, slotsNeeded) &&
                    placeSlots.isFreeRange(start, slotsNeeded)) {

                LocalDateTime startDt = LocalDateTime.of(date, SlotUtils.fromSlot(start));
                LocalDateTime endDt = LocalDateTime.of(date,
                        SlotUtils.fromSlot(start + slotsNeeded));

                ScheduleEntry entry = new ScheduleEntry(
                        UUID.randomUUID().toString(),
                        order.id(),
                        place.id(),
                        Arrays.stream(masters).map(Master::id).toList(),
                        startDt,
                        endDt
                );

                scheduleRepo.add(entry);
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    private DaySlots buildDaySlotsWorkplace(String placeId, LocalDate date) {
        DaySlots ds = new DaySlots(SLOTS_COUNT);
        for (ScheduleEntry e : scheduleRepo.findByWorkplaceAndDate(placeId, date)) {
            int from = SlotUtils.toSlot(e.start().toLocalTime());
            int to = SlotUtils.toSlot(e.end().toLocalTime());
            ds.markBusy(from, to);
        }
        return ds;
    }

    private Map<String, DaySlots> buildDaySlotsForMasters(LocalDate date, Master... masters) {
        Map<String, DaySlots> map = new HashMap<>();
        for (Master m : masters) {
            if (m.status() == MasterStatus.ACTIVE) {
                map.put(m.id(), buildDaySlotsMaster(m.id(), date));
            }
        }
        return map;
    }

    private DaySlots buildDaySlotsMaster(String masterId, LocalDate date) {
        DaySlots ds = new DaySlots(SLOTS_COUNT); // изначально все свободные
        for (ScheduleEntry e : scheduleRepo.findByMasterAndDate(masterId, date)) {
            int from = SlotUtils.toSlot(e.start().toLocalTime());
            int to = SlotUtils.toSlot(e.end().toLocalTime());
            ds.markBusy(from, to); // занятые помечаются
        }
        return ds;
    }

    private boolean allMastersFree(Map<String, DaySlots> slots, int start, int length) {
        for (DaySlots ds : slots.values()) {
            if (!ds.isFreeRange(start, length)) {
                return false;
            }
        }
        return true;
    }

    public boolean masterHasFreeFuture(String masterId) {
        return scheduleRepo.findByMasterAfter(masterId, LocalDateTime.now()).stream()
                .map(e -> orderRepo.get(e.orderId()))
                .noneMatch(o -> o.status() == OrderStatus.OPENED);
    }

    public boolean workplaceHasFreeFuture(String workplaceId) {
        return scheduleRepo.findByWorkplaceAfter(workplaceId, LocalDateTime.now()).stream()
                .map(e -> orderRepo.get(e.orderId()))
                .noneMatch(o -> o.status() == OrderStatus.OPENED);
    }

    public Collection<Workplace> getFreeWorkplacesAtTime(LocalDateTime time) {
        Set<String> busy = scheduleRepo.getAll().stream()
                .filter(e -> e.start().isBefore(time) && e.end().isAfter(time))
                .map(ScheduleEntry::workplaceId)
                .collect(Collectors.toSet());

        return workplaceRepo.getOpened().stream()
                .filter(w -> !busy.contains(w.id()))
                .toList();
    }

    public LocalDateTime plannedStartOfOrder(String orderId) {
        return scheduleRepo.findByOrder(orderId).start();
    }

    public LocalDateTime plannedEndOfOrder(String orderId) {
        return scheduleRepo.findByOrder(orderId).end();
    }

    public Collection<Order> currentOrderOfMaster(String masterId) {
        return scheduleRepo.findByMasterAfter(masterId, LocalDateTime.now()).stream()
                .map(e -> orderRepo.get(e.orderId()))
                .filter(o -> o.status() == OrderStatus.OPENED)
                .toList();
    }

    public Collection<Master> mastersOfOrder(String orderId) {
        Set<String> masterIds = new HashSet<>(scheduleRepo.findByOrder(orderId).masterIds());
        return masterRepo.getActive().stream()
                .filter(m -> masterIds.contains(m.id()))
                .toList();
    }

    public long freeSpacesNumber(LocalDate date, int slotsNeeded, int mastersNeeded) {
        Map<String, DaySlots> masterSlots = buildDaySlotsForMasters(date, masterRepo.getActive().toArray(Master[]::new));
        Map<String, DaySlots> workplaceSlots = new HashMap<>();
        for (var w : workplaceRepo.getOpened()) {
            workplaceSlots.put(w.id(), buildDaySlotsWorkplace(w.id(), date));
        }

        long slotsCount = 0;
        for (int i = 0; i < SLOTS_COUNT - slotsNeeded; i++) {
            int slotIndex = i;

            List<Workplace> freePlaces = workplaceSlots.entrySet().stream()
                    .filter(e -> e.getValue().isFreeRange(slotIndex, slotsNeeded))
                    .map(e -> workplaceRepo.get(e.getKey()))
                    .toList();
            if (freePlaces.isEmpty()) continue;

            List<Master> freeMasters = masterSlots.entrySet().stream()
                    .filter(e -> e.getValue().isFreeRange(slotIndex, slotsNeeded))
                    .map(e -> masterRepo.get(e.getKey()))
                    .toList();
            if (freeMasters.size() < mastersNeeded) continue;

            slotsCount += Math.min(freePlaces.size(), freeMasters.size());
        }
        return slotsCount;
    }

    public long freeSpaceExist(LocalDate date, int slotsNeeded, int mastersNeeded) {
        Map<String, DaySlots> masterSlots = buildDaySlotsForMasters(date, masterRepo.getActive().toArray(Master[]::new));
        Map<String, DaySlots> workplaceSlots = new HashMap<>();
        for (var w : workplaceRepo.getOpened()) {
            workplaceSlots.put(w.id(), buildDaySlotsWorkplace(w.id(), date));
        }

        long slotsCount = 0;
        for (int i = 0; i < SLOTS_COUNT - slotsNeeded; i++) {
            int slotIndex = i;

            List<Workplace> freePlaces = workplaceSlots.entrySet().stream()
                    .filter(e -> e.getValue().isFreeRange(slotIndex, slotsNeeded))
                    .map(e -> workplaceRepo.get(e.getKey()))
                    .toList();
            if (freePlaces.isEmpty()) continue;

            List<Master> freeMasters = masterSlots.entrySet().stream()
                    .filter(e -> e.getValue().isFreeRange(slotIndex, slotsNeeded))
                    .map(e -> masterRepo.get(e.getKey()))
                    .toList();
            if (freeMasters.size() < mastersNeeded) continue;

            slotsCount += Math.min(freePlaces.size(), freeMasters.size());
        }
        return slotsCount;
    }

    public void scheduleOrderInNearestFuture(Order order, int mastersNeeded) {
        int slotsNeeded = (int) (order.durationHours() * 60 / SlotUtils.SLOT_MIN);
        if (slotsNeeded > SLOTS_COUNT) return;
        if (mastersNeeded > masterRepo.getActive().size()) return;

        LocalDate day = LocalDate.now().minusDays(1);
        boolean breaker = false;
        while (!breaker && day.isBefore(LocalDate.now().plusMonths(2))) {
            day = day.plusDays(1);
            Map<String, DaySlots> masterSlots = buildDaySlotsForMasters(day, masterRepo.getActive().toArray(Master[]::new));
            Map<String, DaySlots> workplaceSlots = new HashMap<>();
            for (var w : workplaceRepo.getOpened()) {
                workplaceSlots.put(w.id(), buildDaySlotsWorkplace(w.id(), day));
            }
            for (int i = 0; i < SLOTS_COUNT - slotsNeeded; i++) {
                int slotIndex = i;
                Workplace freePlace = workplaceSlots.entrySet().stream()
                        .filter(e -> e.getValue().isFreeRange(slotIndex, slotsNeeded))
                        .map(e -> workplaceRepo.get(e.getKey()))
                        .findAny()
                        .orElse(null);
                if (freePlace == null) continue;

                List<String> freeMasterIds = masterSlots.keySet().stream()
                        .filter(id -> masterSlots.get(id).isFreeRange(slotIndex, slotsNeeded))
                        .limit(mastersNeeded)
                        .toList();
                if (freeMasterIds.size() < mastersNeeded) continue;

                LocalDateTime startDt = LocalDateTime.of(day, SlotUtils.fromSlot(slotIndex));
                LocalDateTime endDt = LocalDateTime.of(day, SlotUtils.fromSlot(slotIndex + slotsNeeded));

                ScheduleEntry entry = new ScheduleEntry(
                        UUID.randomUUID().toString(),
                        order.id(),
                        freePlace.id(),
                        freeMasterIds,
                        startDt,
                        endDt
                );

                scheduleRepo.add(entry);
                orderRepo.get(order.id()).setStatus(OrderStatus.OPENED);
                System.out.println("Заказ успешно размещен");
                breaker = true;
                break;
            }
        }
    }

    public void scheduleOrderInDay(Order order, LocalDate day, int mastersNeeded) {
        int slotsNeeded = (int) (order.durationHours() * 60 / SlotUtils.SLOT_MIN);

        Map<String, DaySlots> masterSlots = buildDaySlotsForMasters(day, masterRepo.getActive().toArray(Master[]::new));
        Map<String, DaySlots> workplaceSlots = new HashMap<>();
        for (var w : workplaceRepo.getOpened()) {
            workplaceSlots.put(w.id(), buildDaySlotsWorkplace(w.id(), day));
        }
        for (int i = 0; i < SLOTS_COUNT - slotsNeeded; i++) {
            int slotIndex = i;
            Workplace freePlace = workplaceSlots.entrySet().stream()
                    .filter(e -> e.getValue().isFreeRange(slotIndex, slotsNeeded))
                    .map(e -> workplaceRepo.get(e.getKey()))
                    .findAny()
                    .orElse(null);
            if (freePlace == null) continue;

            List<String> freeMasterIds = masterSlots.keySet().stream()
                    .filter(id -> masterSlots.get(id).isFreeRange(slotIndex, slotsNeeded))
                    .limit(mastersNeeded)
                    .toList();
            if (freeMasterIds.size() < mastersNeeded) continue;

            LocalDateTime startDt = LocalDateTime.of(day, SlotUtils.fromSlot(i));
            LocalDateTime endDt = LocalDateTime.of(day,
                    SlotUtils.fromSlot(i + slotsNeeded));

            ScheduleEntry entry = new ScheduleEntry(
                    UUID.randomUUID().toString(),
                    order.id(),
                    freePlace.id(),
                    freeMasterIds,
                    startDt,
                    endDt
            );

            scheduleRepo.add(entry);
            System.out.println("Заказ успешно размещен");
            break;
        }
    }
}
