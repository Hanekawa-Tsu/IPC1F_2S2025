import java.util.Scanner;

public class Inventario {
    
    //Menu de opciones
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do{
            System.out.println("Menu de Gestor de Productos");
            System.out.println("Selecciones una Opcion ingresando el Numero");
            System.out.println("1-Agregar Producto");
            System.out.println("2-Buscar Producto");
            System.out.println("3-Eliminar Producto");
            System.out.println("4-Registrar Venta");
            System.out.println("5-Generar Reporte");
            System.out.println("6-Ver Datos de Estudiante");
            System.out.println("7-Bitacora");
            System.out.println("8-Salir");

            try{
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        Aproducto(scanner); //Aproducto = Agregar producto
                        break;
                    case 2:
                        Bproducto(scanner); //Bprodcuto = Buscar producto
                        break;
                    case 3:
                        Eproducto(scanner); // Eproducto = Eliminar producto
                        break;
                    case 4:
                        Rventa(scanner); // Rventa = Registrar venta
                        break;
                    case 5:
                        Greporte(scanner); // Greporte = Generar reporte
                        break;
                    case 6:
                        Vestudiante(scanner); // Vestudiante = Ver datos de estudiante
                        break;
                    case 7:
                        Bitacora(scanner); // Bitacora
                        break;
                    case 8:
                        System.out.println("Sesion Finalizada"); //Fin
                        break; //Sirve para salir del swirch
                    default:
                        System.out.println("Opcion invalida"); // Error
                        
                }
            }catch (NumberFormatException e){
                    System.out.println("Error");
                    opcion = 0;
            }

        }while (opcion !=8);
        scanner.close();
    }

    // Agregar producto
    public static void Aproducto(Scanner scanner){

        String[][] invmatriz= new String[50][5]; //[#Espacio de almacenamineto][datos de producto]
        int contarfila = 0; //conteo de la fila

        if (contarfila >= 50) { //
            System.out.println("Inventario lleno");
            return;
        } 


        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.print("Precio: ");
        Float precio = scanner.nextFloat(); //Al ingresar decimales usa "," (la coma) el punto puede dar error
        scanner.nextLine(); //limpia el buffer del scanner
        
        System.out.print("Cantidad de Stock: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Codigo de 9 digitos: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if(precio < 0 || cantidad < 0){ // si el precio del producto es negativo
            System.out.println("Error el precio o la cantidad del producto es negativo");
            return;
        }

        invmatriz[contarfila][0]= nombre;
        invmatriz[contarfila][1]= categoria;
        invmatriz[contarfila][2]= String.valueOf(precio); //Convierte el floar a String - "convierte de # reales a texto"
        invmatriz[contarfila][3]= String.valueOf(cantidad); //Convierte el int a String - "convierte de # entero a texto"
        invmatriz[contarfila][4]= String.valueOf(codigo); //Convierte el int a String - "convierte de # entero a texto"
    
        contarfila++;
        System.out.println("Datos guardados \n");
    }    

    
    public static void Bproducto(Scanner scanner){
        String buscar;

        System.out.println("Escriba el nombre del producto");
        buscar =scanner.nextLine(); 

    }

    public static void Eproducto(Scanner scanner) {
        
    }

    public static void Rventa(Scanner scanner) {
        
    }

    public static void Greporte(Scanner scanner) {
        
    }

    public static void Vestudiante(Scanner scanner) {
        
    }

    public static void Bitacora(Scanner scanner) {
        
    }


}
