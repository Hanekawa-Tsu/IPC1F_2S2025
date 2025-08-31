import java.util.Scanner;

public class Inventario {
    
    //Menu de opciones
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do{
            System.out.println("Menu de Gestor de Productos");
            System.out.println("Selecciones una Opcion ingresando el Numero");
            System.out.println("1-Agregar Procducto");
            System.out.println("2-Buscar Producto");
            System.out.println("3-Eliminar Procducto");
            System.out.println("4-Registrar Venta");
            System.out.println("5-Generar Reporte");
            System.out.println("6-Ver Datos de Estudiante");
            System.out.println("7-Bitacora");
            System.out.println("8-Salir");

            try{
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        System.out.println("Sesion Finalizada");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }catch (NumberFormatException e){
                    System.out.println("Error");
                    opcion = 0;
            }

        }while (opcion !=8);
        scanner.close();
    }
}
