Students:
Alex Morales Trevisan
Carlos A. Ferrer Hayes
Andrés E. Muñiz Ríos
Estadio:
    Manages the reservation system for stadium seats.
    Variables:
        availableSeats (Set): Stores available seats with unique entries.
        reservationHistory (LinkedList): Keeps a chronological log of reservation actions.
        reservations (HashMap): Maps customers to their reserved seats.
        undoStack (Stack): Tracks actions for undo functionality.
        waitingList (Map): Organizes waiting customers by section.
        waitingSeatNum (HashMap): Links waiting customers to their requested seats.
    Methods:
        reserveSeat(cliente, section, quantity): Reserves seats for a customer if available, or adds them to a waiting list.
        cancelReservation(cliente): Cancels a customer’s reservation and releases seats.
        waitingListProcess(cliente, section, seatsToReserve, action): Manages adding customers to a waiting list or assigning seats from the list.
Cliente:
    Represents a customer in the reservation system.
    Methods:
        getName(): Returns the customer’s name.
        getEmail(): Returns the customer’s email.
        getPhone(): Returns the customer’s phone number.
Asiento:
    Represents a seat in the stadium.
    Methods:
        getSection(): Returns the seat’s section.
        getRow(): Returns the seat’s row number.
        getSeatNumber(): Returns the specific seat number.
        getPrice(): Returns the seat’s price.

Clase Operador:
    Maneja la información que le provee el user para realizar las tareas esenciales del programa.
    
    Metodos:
    Metodo Main:Corre el Programa de forma indefinida si lo deceamos con el while loop.Contiene todos los metodos esenciales                  de todas las clases.-Andres
    
    mensajeEntrada():Mensaje ya predeterminado que se puede observar al principio de programa
    
    inputEntraInfoCliente():Al principio le pide al user(Operador) que provea información del cliente a travez de la clase Scanner.
    Guardamos los datos que nos provee en variables globales.
    
    inputEntraReservacionCliente(): Cuando entramos a el modo de reservación, le pedimos al user información para crearlareservación del      cliente. Estructura similar al anterior metodo. Yo pregunto, user responde.

    verificaEntradaString():Metodo que verifica si la entrada es un String. Se comunica con el user para que provea la información            necesaria en el data type que se espera.

    verificaEntradaString():Metodo igual al anterior que verifica si la entrada es un String. Se comunica con el user para que provea la      información necesaria en el data type que se espera. La unica diferencia es que no ponemos un mensaje al principio.

    verificaEntradaInt(): Metodo que verifica que el user provee un Integer.

    eligeActividad(): Se le provee al usar un manual de opciones y el provee una letra que equivale a la tarea que quiere realizar.

    mensajeElige():Metodo que provee un mensaje visual de las actividades que puede hacer el cliente.
