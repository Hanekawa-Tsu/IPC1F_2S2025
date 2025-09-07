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
                       
                    case 2:
                        Bproducto(scanner); //Bprodcuto = Buscar producto
                    
                    case 3:
                        Eproducto(scanner); // Eproducto = Eliminar producto
                        
                    case 4:
                        Rventa(scanner); // Rventa = Registrar venta
                        
                    case 5:
                        Greporte(scanner); // Greporte = Generar reporte
                        
                    case 6:
                        Vestudiante(scanner); // Vestudiante = Ver datos de estudiante
                        
                    case 7:
                        Bitacora(scanner); // Bitacora
                       
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

        boolean negativo = false;
        do{
            System.out.println("Nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.println("Categoria: ");
            String categoria = scanner.nextLine();

            System.out.println("Precio: ");
            Float precio = scanner.nextFloat(); //Al ingresar decimales usa "," (la coma) el punto puede dar error
        
            System.out.println("Cantidad de Stock");
            int cantidad = scanner.nextInt();

            System.out.println("Codigo de 9 digitos");
            int codigo = scanner.nextInt();

           if(precio < 0){ // si el precio del producto es negativo
                System.out.println("Error el precio del producto es negativo");
                negativo = true; 
           }

           if (cantidad < 0) {
                System.out.println("Error la cantidasd del producto es negativo");
                negativo = true; 
           }

        }
    }

    // Puede cambiar **no tocar**
    public static void Bproducto(Scanner scanner){

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
