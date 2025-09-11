import java.util.Scanner;

public class Inventario {
    
    //Menu de opciones
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do{
            System.out.println("--------- Menu de Gestor de Productos --------- \n");
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
                        System.out.println("Opcion invalida \n"); // Error
                        
                }
            }catch (NumberFormatException e){
                    System.out.println("Error \n");
                    opcion = 0;
            }

        }while (opcion !=8);
        scanner.close();
    }

    public static String[][] invmatriz = new String[50][5]; //[#Espacio de almacenamineto][datos de producto], invmatriz = matriz de inventario
    public static int contarfila = 0; //conteo de la fila
    // Agregar producto
    public static void Aproducto(Scanner scanner){

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

        invmatriz[contarfila][0]= nombre;
        invmatriz[contarfila][1]= categoria;
        invmatriz[contarfila][2]= String.valueOf(precio); //Convierte el floar a String - "convierte de # reales a texto"
        invmatriz[contarfila][3]= String.valueOf(cantidad); //Convierte el int a String - "convierte de # entero a texto"
        invmatriz[contarfila][4]= String.valueOf(codigo); //Convierte el int a String - "convierte de # entero a texto"

        contarfila++;
        System.out.println("Datos guardados \n");
    }    

    public static void Bproducto(Scanner scanner){
        System.out.println("--- Busqueda de Producto ---");
        System.out.println("Buscar por: ");
        System.out.println("1. Nombre");
        System.out.println("2. Categoria");
        System.out.println("3. Codigo");
        
        String tipobuscar = scanner.nextLine();
        
        System.out.print("Ingrese el valor a buscar: ");
        String buscar = scanner.nextLine().toLowerCase(); // Convertir a minusculas para una busqueda mejor

        int productosEncontrados = 0;

        for (int i = 0; i < contarfila; i++) {
            String nombre = invmatriz[i][0].toLowerCase();
            String categoria = invmatriz[i][1].toLowerCase();
            String codigo = invmatriz[i][4];

            boolean encontrado = false;
            
            switch (tipobuscar) {
                case "1": // Busca por nombre
                    if (nombre.contains(buscar)) { // 
                        encontrado = true;
                    }
                    break;
                case "2": // Busca por categoría
                    if (categoria.contains(buscar)) {
                        encontrado = true;
                    }
                    break;
                case "3": // Busca por codigo
                    if (codigo.equals(buscar)) {
                        encontrado = true;
                    }
                    break;
                default:
                    System.out.println("Opcion de busqueda invalida. \n");
                    return;
            }

            if (encontrado) {
                System.out.println("------------------------------------");
                System.out.println("Producto Encontrado:");
                System.out.println("Nombre: " + invmatriz[i][0]);
                System.out.println("Categoria: " + invmatriz[i][1]);
                System.out.println("Precio: " + invmatriz[i][2]);
                System.out.println("Stock: " + invmatriz[i][3]);
                System.out.println("Codigo: " + invmatriz[i][4]);
                System.out.println("------------------------------------");
                productosEncontrados++;
            }
        }

        if (productosEncontrados == 0) {
            System.out.println("No se encontro ningun producto con ese criterio. \n");
        }

    }

    public static void Eproducto(Scanner scanner) {
    
        
        System.out.println("--- Eliminar Producto ---");
        System.out.print("Ingrese el codigo del producto a eliminar: ");
        String eliminar = scanner.nextLine();

        int indice = -1;
        for (int i = 0; i < contarfila; i++) {
            if (invmatriz[i][4].equals(eliminar)) { // invmatriz[i][4] contiene el código del producto
                indice = i;
                break;
            }
        }
        System.out.print("¿Esta seguro de que desea eliminar este producto? (si/no): ");
        String confirmacion = scanner.nextLine().toLowerCase();

        if (confirmacion.equals("si"));{
            for (int i = indice; i < contarfila - 1; i++) { // Elimina el producto moviendo los elementos restantes
                    invmatriz[i] = invmatriz[i + 1];
                }
                invmatriz[contarfila - 1] = new String[5]; // Limpiar el último elemento para evitar duplicados
                contarfila--;
                System.out.println("Producto eliminado exitosamente. \n");
        } else{
            System.out.println("Se cancelo la eliminacion");
        }
        
        
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
