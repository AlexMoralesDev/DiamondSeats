import java.util.*;

public class Estadio {
    private Set<Asiento> availableSeats = new HashSet<>();
    private LinkedList<String> reservationHistory = new LinkedList<>();
    private HashMap<Cliente, List<Asiento>> reservations = new HashMap<>();
    private Stack<String> undoStack = new Stack<>();
    private Map<String, Queue<Cliente>> waitingList = new HashMap<>();
    private HashMap<Cliente, List<Asiento>> waitingSeatNum = new HashMap<>();

    public Estadio() {
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 1; i <= 500; i++) {
            availableSeats.add(new Asiento("Field Level", i / 10, i % 10, 300));
        }
        for (int i = 1; i <= 1000; i++) {
            availableSeats.add(new Asiento("Main Level", i / 10, i % 10, 120));
        }
        for (int i = 1; i <= 2000; i++) {
            availableSeats.add(new Asiento("Grandstand Level", i / 10, i % 10, 45));
        }
    }

    public boolean reserveSeat(Cliente cliente, String section, int quantity) {
        List<Asiento> seatsToReserve = new ArrayList<>();
        for (Asiento asiento : availableSeats) {
            if (asiento.getSection().equals(section) && seatsToReserve.size() < quantity) {
                seatsToReserve.add(asiento);
            }
        }

        if (seatsToReserve.size() < quantity) {
            waitingListProcess(cliente, section, seatsToReserve, "add");
            return false;
        }

        reservations.putIfAbsent(cliente, new ArrayList<>());
        reservations.get(cliente).addAll(seatsToReserve);
        availableSeats.removeAll(seatsToReserve);
        reservationHistory.add("Reserved " + quantity + " seats for " + cliente.getName());
        undoStack.push("Reserve");

        return true;
    }

    public boolean cancelReservation(Cliente cliente) {
        if (cliente == null || !reservations.containsKey(cliente)) {
            return false;
        }

        List<Asiento> reservedSeats = reservations.remove(cliente);
        String availableSection = reservedSeats.get(0).getSection();
        availableSeats.addAll(reservedSeats);
        waitingListProcess(null, availableSection, null, "check");
        reservationHistory.add("Cancelled reservation for " + cliente.getName());
        undoStack.push("Cancel");

        return true;
    }

    private void waitingListProcess(Cliente cliente, String section, List<Asiento> seatsToReserve, String action) {
        switch (action) {
            case "add":
                waitingList.putIfAbsent(section, new LinkedList<>());
                waitingList.get(section).add(cliente);
                waitingSeatNum.putIfAbsent(cliente, new ArrayList<>());
                waitingSeatNum.get(cliente).addAll(seatsToReserve);
                break;

            case "check":
                Queue<Cliente> clients = waitingList.get(section);
                if (clients != null) {
                    while (!clients.isEmpty()) {
                        Cliente nextClient = clients.peek(); 
                        boolean reserved = reserveSeat(nextClient, section, waitingSeatNum.get(nextClient).size());
                        if (reserved) {
                            clients.poll();
                            waitingSeatNum.remove(nextClient);
                        } else {
                            break;
                        }
                    }
                }
                break;
        }
    }
}

