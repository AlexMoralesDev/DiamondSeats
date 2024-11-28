import java.util.*;
/**
 * Clase `Estadio` maneja la reservas de asientos en un estadio de pelota.
 * Estructura:
 * - Hash set usado para availableSeats usado por su eficiencia en operaciones 
 *   como agregar y buscar elementos únicos.
 * - LinkedList usado para reservationHistory porque es eficiente para operaciones de inserción y recorrido.
 * - HashMap usado para reservaciones ya que asocia clientes con los asientos que han reservado, lo que permite 
 *   búsquedas rápidas basadas en los clientes.
 * - Stack utilizado para implementar una funcionalidad de deshacer operaciones. Un stack es ideal 
 *   porque sigue la estructura LIFO.
 * - HashMap usado para waitingList para poder almacenar listas de espera por sección, usando colas (Queue) para gestionar 
 *   el orden de llegada de los clientes.
 * - HashMap usado para waitingSeatNum para asociar clientes con los asientos que están esperando, permitiendo 
 *   búsquedas rápidas.
 */
public class Estadio {
    
    private Set<Asiento> availableSeats = new HashSet<>();
    private LinkedList<String> reservationHistory = new LinkedList<>();
    private HashMap<Cliente, List<Asiento>> reservations = new HashMap<>();
    private Stack<String> undoStack = new Stack<>();
    private Map<String, Queue<Cliente>> waitingList = new HashMap<>();
    private HashMap<Cliente, List<Asiento>> waitingSeatNum = new HashMap<>();
    public Map<String,Integer>seccionvscosto=Map.of("Grandstand Level",45,"Main Field",120,"Field Level",300);
    
    public Estadio() {
        initializeSeats();
    }
    // Inicializa los asientos del estadio
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
    // Reserva asientos tomando el cliente, seccion y cantidad de boletos a comprar
    public boolean reserveSeat(Cliente cliente, String section, int quantity) {
        List<Asiento> seatsToReserve = new ArrayList<>();
        // Busca asientos disponibles en la sección
        for (Asiento asiento : availableSeats) {
            if (asiento.getSection().equals(section) && seatsToReserve.size() < quantity) {
                seatsToReserve.add(asiento);
            }
        }
        // Si no hay suficientes asientos, añade al cliente a la lista de espera.
        if (seatsToReserve.size() < quantity) {
            waitingListProcess(cliente, section, seatsToReserve, "add");
            return false;
        }
         // Actualiza las reservas y los asientos disponibles.
        reservations.putIfAbsent(cliente, new ArrayList<>());
        reservations.get(cliente).addAll(seatsToReserve);
        availableSeats.removeAll(seatsToReserve);
        reservationHistory.add("Reserved " + quantity + " seats for " + cliente.getName());
        undoStack.push("Reserve");

        return true;
    }
    // Elimina la reservacion hecha por un cliente. Al porder eliminar el cliente, verifica la lista de espara para clientes elegibles
    // para los asientos quitados de la reservacion.
    public boolean cancelReservation(Cliente cliente) {
        // Verifica si el cliente existe/esta en la lista de las reservaciones hechas
        if (cliente == null || !reservations.containsKey(cliente)) {
            return false;
        }

        List<Asiento> reservedSeats = reservations.remove(cliente); //consigue los asientos de la reservacion cancelada
        String availableSection = reservedSeats.get(0).getSection(); //coge la seccion del cliente que elimino su reservacion para el proceso de la lista de espera
        availableSeats.addAll(reservedSeats); //regresa los asientos de la reservacion cancelada a los asientos disponibles
        waitingListProcess(null, availableSection, null, "check"); //verifica la lista de espera
        reservationHistory.add("Cancelled reservation for " + cliente.getName()); //anade un log al historia de las reservaciones
        undoStack.push("Cancel");

        return true;
    }

    //Proceso completo del proceso de la lista de eserpa. Tiene dos casos: uno para el proceso de hacer una reservacion y el otro verifica la lista de espera
    // luego de cancelar una reservacion.
    private void waitingListProcess(Cliente cliente, String section, List<Asiento> seatsToReserve, String action) {
        switch (action) {
            // Anade cliente a lista de espera luego de tratar de hacer una reservacion
            case "add":
                waitingList.putIfAbsent(section, new LinkedList<>()); // verifica si la seccion que quiere el cliente ya esta en la lista de espera, sino la anade
                waitingList.get(section).add(cliente); //anade al cliente a la lista de espera por seccion
                waitingSeatNum.putIfAbsent(cliente, new ArrayList<>()); // verifica si el cliente ya tiene la cantidad de sillas que quiere guardas, sino registra el cliente para poder guardar sus sillas
                waitingSeatNum.get(cliente).addAll(seatsToReserve); //guarda la cantidad de asientos que queria el cliente recien anadido a la lista de espera
                break;

            // verifica lista de espera para clientes de la seccion que acaba de recibir asientos de la reservacion cancelada
            case "check":
                Queue<Cliente> clients = waitingList.get(section); //Recibe un queue de los clientes de la seccion disponible para mantener el orden de la lista de espera
                if (clients != null) { // verifica que el cliente existe
                    while (!clients.isEmpty()) { //verifica que el queue no este vacio
                        Cliente nextClient = clients.peek(); // guarda el cliente primero/proximo en fila para verificar si es elegible para las silla ahora disponibles
                        boolean reserved = reserveSeat(nextClient, section, waitingSeatNum.get(nextClient).size()); //trata crear una reservacion para el cliente en lista de espera
                        if (reserved) { // si pudo hacerle una reservacion, elimina el cliente de la lista de espera
                            clients.poll();
                            waitingSeatNum.remove(nextClient); // elimina las sillas que el cliente queria ya que pudo hacer su reservacion
                        } else {
                            // si no pudo hacer reservacion, se queda en la lista
                            break;
                        }
                    }
                }
                break;
        }
    }


}

