Students:
Alex Morales Trevisan
Carlos A. Ferrer Hayes
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