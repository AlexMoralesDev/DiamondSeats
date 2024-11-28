import java.util.Scanner;

public class Operador {
//Variables globales de la clase que estaran cambiando constantemente cada vez que abrimos el programa
static Scanner scanner1 =new Scanner(System.in); 
static String nombreCliente;
static String correoElectronicoCliente;
static int numeroTelefonoCliente;
static String actividadCliente;
static String seccionCliente;
static int cantidadCliente;
    
static Estadio estadio=new Estadio();

//Metodo Main:Corre el Programa de forma indefinida si lo deceamos con el while loop.Contiene todos los metodos esenciales
//de todas las clases.
public static void main(String[]args){

    while(true){
mensajeEntrada();
inputEntraInfoCliente();
Cliente nuevocliente= new Cliente(nombreCliente,correoElectronicoCliente,numeroTelefonoCliente);
    mensajeElige();
    actividadCliente= verificaEntradaString(scanner1).toLowerCase();
switch(actividadCliente){
case "a":
//estadio.disponibilidad() El compañero que hizo Clase Estadio, podria implementar metodo de mostrar la disponibilida de los asientos
break;
case "b":
inputEntraReservacionCliente();
estadio.reserveSeat(nuevocliente,seccionCliente,cantidadCliente);
mensajeElige();
actividadCliente= verificaEntradaString(scanner1).toLowerCase();
;
break;
case "c":
estadio.cancelReservation(nuevocliente);
System.out.println("CANCELACION COMPLETADA");
mensajeElige();
actividadCliente= verificaEntradaString(scanner1).toLowerCase();
break;
case "d":
System.out.println("Apagando Programa");
System.exit(0);
break;
case "e":
continue;

}

}
}
//mensajeEntrada():Mensaje ya predeterminado que se puede observar al principio de programa 
public static void mensajeEntrada(){
System.out.println("BIENVENIDOS AL SISTEMA DE RESERVACIONES DE ASIENTOS"+"\n");

}
//inputEntraInfoCliente():Al principio le pide al user(Operador) que provea información del cliente a travez de la clase Scanner.
//Guardamos los datos que nos provee en variables globales.
public static void inputEntraInfoCliente(){
System.out.println("INFORMACION DE CLIENTE"+"\n");
nombreCliente=verificaEntradaString("Introduzca Nombre: ",scanner1);
System.out.println("Introduzca el Correo Electronico: ");
correoElectronicoCliente=scanner1.nextLine();
numeroTelefonoCliente=verificaEntradaInt("Introduzca Numero de telefono: ", scanner1);
System.out.println("INFORMACION DE CLIENTE COMPLETADA");
}
//inputEntraReservacionCliente(): Cuando entramos a el modo de reservación, le pedimos al user información para crearlareservación del cliente.
//Estructura similar al anterior metodo. Yo pregunto, user responde.
public static void inputEntraReservacionCliente(){
    System.out.println("RESERVACIONES"+"\n");
    System.out.println("3 Opciones:");
    System.out.println("Field Level");
    System.out.println("Main Level");
    System.out.println("Grandstand Level");
    seccionCliente=verificaEntradaString("Escriba Nombre de Seccion Deseada Exactamente Como Aparece en Pantalla", scanner1);
    cantidadCliente=verificaEntradaInt("Introduzca laa cantidad a reservar: ", scanner1);
    System.out.println("INFORMACION DE RESERVA COMPLETADA");
    }

//verificaEntradaString():Metodo que verifica si la entrada es un String. Se comunica con el user para que provea la información necesaria
//en el data type que se espera.
public static String verificaEntradaString(String comunicado, Scanner scanner){
System.out.println(comunicado);
String entrada=scanner.nextLine();  
    while(entrada.isEmpty() || !entrada.matches("[a-zA-Z ]+")){
if(entrada.isEmpty()){
        System.out.println("ERROR:NO DEJE EN BLANCO,ESCRIBA EL DATO QUE SE LE PIDE");
entrada=scanner.nextLine();
    }
else if(!entrada.matches("[a-zA-Z]+"))
    System.out.println("ERROR:NO INTRODUZCAS NUMEROS, SOLO LETRAS");
entrada=scanner.nextLine();
}
return entrada;
}
//verificaEntradaString():Metodo igual al anterior que verifica si la entrada es un String. Se comunica con el user para que provea la información necesaria
//en el data type que se espera. La unica diferencia es que no ponemos un mensaje al principio.
public static String verificaEntradaString(Scanner scanner){
    String entrada=scanner.nextLine();  
        while(entrada.isEmpty() || !entrada.matches("[a-zA-Z ]+")){
    if(entrada.isEmpty()){
            System.out.println("ERROR:NO DEJE EN BLANCO,ESCRIBA EL DATO QUE SE LE PIDE");
    entrada=scanner.nextLine();
        }
    else if(!entrada.matches("[a-zA-Z]+"))
        System.out.println("ERROR:NO INTRODUZCAS NUMEROS, SOLO LETRAS");
    entrada=scanner.nextLine();
    }
    return entrada;
    }
//vrificaEntradaInt(): Metodo que verifica que el user provee un Integer.
public static int verificaEntradaInt(String comunicado, Scanner scanner){
System.out.println(comunicado);
while(!scanner.hasNextInt()){
System.out.println("ERROR:INTRODUZCA SOLO VALORES NUMERICOS");
scanner.next();

}
int entrada=scanner.nextInt();
scanner1.nextLine();
return entrada;
}
//eligeActividad(): Se le provee al usar un manual de opciones y el provee una letra que equivale a la tarea que quiere
//realizar.
public static void eligeActividad(){
mensajeElige();
actividadCliente= verificaEntradaString(scanner1).toLowerCase();
}

//mensajeElige():Metodo que provee un mensaje visual de las actividades que puede hacer el cliente.
public static void mensajeElige(){
System.out.println("ESCOGE LA LETRA DE LA ACTIVIDAD QUE DESEA REALIZAR:  ");
System.out.println("(A) Asientos Disponibles");
System.out.println("(B) Reservacion");
System.out.println("(C) Cancelar Reservaciones");
System.out.println("(D) Cerrar Sistema");
System.out.println("(E) Log off cliente");

}






}

