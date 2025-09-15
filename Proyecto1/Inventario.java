import java.io.FileWriter; //Sirve para crear el archivo .txt
import java.io.IOException; //Sirve para que no haya errores al momento de crear el archivo .txt
import java.io.PrintWriter; //Sirve para escribir el archivo .txt
import java.text.SimpleDateFormat; // Nos ayuda con el formato de fecha y hora
import java.util.Date; // Nos ayuda para obtener la fecha y hora
import java.util.Scanner;

public class Inventario {
    
    //Menu de opciones
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        String estudiante = "Estudiante 9"; //Ejemplo del usuario (Estudiante) 

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
                        Aproducto(scanner, estudiante); //Aproducto = Agregar producto
                        break;
                    case 2:
                        Bproducto(scanner, estudiante); //Bprodcuto = Buscar producto
                        break;
                    case 3:
                        Eproducto(scanner, estudiante); // Eproducto = Eliminar producto
                        break;
                    case 4:
                        Rventa(scanner, estudiante); // Rventa = Registrar venta
                        break;
                    case 5:
                        Greporte(scanner, estudiante); // Greporte = Generar reporte
                        break;
                    case 6:
                        Vestudiante(scanner, estudiante); // Vestudiante = Ver datos de estudiante
                        break;
                    case 7:
                        Bitacora(scanner); // Bitacora
                        break;
                    case 8:
                        System.out.println("Sesion Finalizada"); //Fin
                        break; //Sirve para salir del swirch
                    default:
                        System.out.println("Opcion invalida \n"); // Error
                        RBitacora("Seleccion de menu", "Erronea", estudiante);
                }
            }catch (NumberFormatException e){
                    System.out.println("Error \n");
                    opcion = 0;
                    RBitacora("Entrada menu", "Erronea", estudiante);
            }

        }while (opcion !=8);
        scanner.close();
    }

    //Variables
    public static String[][] invmatriz = new String[50][5]; //[#Espacio de almacenamineto][datos de producto], invmatriz = matriz de inventario
    public static int contarfila = 0; //conteo de la fila
    public static String[][] bitacoraM = new String[100][4]; // Matriz para la bitacora, [100 acciones][4 datos / accion]
    public static int ContarBitacora = 0; // Conteo de registros de la bitacora

    // Agrega producto
    public static void Aproducto(Scanner scanner, String estudiante){

        if (contarfila >= 50) { // Tamaño de las filas de la matriz
            System.out.println("Inventario lleno");
            RBitacora("Agregar producto", "Erronea", estudiante);
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
        RBitacora("Agregar producto", "Correcto", estudiante);
    }    
    
    // Buscar producto
    public static void Bproducto(Scanner scanner, String estudiante){
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
            
            //Obtiene los valores almacenados en la matriz segun la fia 
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
            RBitacora("Buscar producto", "Erronea", estudiante);
        } else{
            RBitacora("Seleccion de menu", "Correcto", estudiante);
        }

    }
    
    // Eliminar producto
    public static void Eproducto(Scanner scanner , String estudiante) {
    
        //Busca el codigo del producto
        System.out.println("--- Eliminar Producto --- \n");
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

        if (confirmacion.equals("si")){
            for (int i = indice; i < contarfila - 1; i++) { // Elimina el producto moviendo los elementos restantes
                    invmatriz[i] = invmatriz[i + 1];
                }
                invmatriz[contarfila - 1] = new String[5]; // Limpiar el último elemento para evitar duplicados
                contarfila--;
                System.out.println("Producto eliminado exitosamente. \n");
                RBitacora("Eliminar producto", "Correcto", estudiante);
            } else{
            System.out.println("Se cancelo la eliminacion \n");
            RBitacora("Eliminar producto", "Erronea", estudiante);
        }
        
        
    }
    
    // Registro de venta
    public static void Rventa(Scanner scanner , String estudiante) {
        System.out.println("--- Registrar Venta ---");
        // Busca el codigo del producto
        System.out.print("Ingrese el codigo del producto a vender: ");
        String Rcodigo = scanner.nextLine();

        int indice = -1;
        for (int i = 0; i < contarfila; i++) {
            if (invmatriz[i][4].equals(Rcodigo)) { //Rcodigo = Registro de codigo
                indice = i;
                break;
            }
        }

        if (indice == -1) {
            System.out.println("Producto no encontrado.");
            RBitacora("Registrar venta", "Erronea", estudiante);
            return;
        }
        System.out.print("Ingrese la cantidad a vender: ");
        int cantidadVendida = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer

        int stockActual = Integer.parseInt(invmatriz[indice][3]);

        if (cantidadVendida > stockActual) {
            System.out.println("No hay suficiente stock para realizar esta venta. Stock disponible: " + stockActual);
            RBitacora("Registrar venta", "Erronea", estudiante);
            return;
        }

        // Resta las unidades vendidas
        int nuevoStock = stockActual - cantidadVendida;
        invmatriz[indice][3] = String.valueOf(nuevoStock);

        // Calcula el total de la venta
        float precioProducto = Float.parseFloat(invmatriz[indice][2]);
        float totalVenta = precioProducto * cantidadVendida;

        // Obtiene la fecha y hora
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sdf.format(new Date());

        // Registra la venta en un archivo de texto
        guardar(Rcodigo, cantidadVendida, fecha, totalVenta);

        System.out.println("Venta registrada exitosamente. \n Nuevo stock para " + invmatriz[indice][0] + ": " + nuevoStock); //Manda mensaje del registro y cantidad disponible del producto
        RBitacora("Registrar venta", "Correcto", estudiante);
    }
    
    // Generar reporte -- No funcional --
    public static void Greporte(Scanner scanner , String estudiante) {
        System.out.println("Generando reporte de ventas... \n"); // Solo mensaje de generacion se creo un metodo aparte para guardar la informacion
        RBitacora("Generar reporte", "Correcto", estudiante);
    }

    //Método para guardar la venta en un archivo de texto
    public static void guardar(String codigo, int cantidad, String fecha, float total) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("ventas.txt", true))) {
            writer.println("Código: " + codigo + ", Cantidad: " + cantidad + ", Fecha y Hora: " + fecha + ", Total: " + total);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de ventas: " + e.getMessage());
         }
    }
    
    // Ver datos de estudiante
    public static void Vestudiante(Scanner scanner, String estudiante) {
        System.out.println("--- Datos del Estudiante ---");
        System.out.println("Nombre: " + estudiante);
        System.out.println("ID del Estudiante: 202308450");
        System.out.println("------------------------------------");
        RBitacora("Ver datos de estudiante", "Correcto", estudiante);
    }
    
    //Bitacora
    public static void Bitacora(Scanner scanner) {
        System.out.println("--- Bitacora de Acciones ---");
        if (ContarBitacora == 0) {
            System.out.println("La bitacora esta vacia \n");
        }

        System.out.println("Fecha y Hora        | Tipo de Accion      | Estado     | Estudiante");
        System.out.println("------------------------------------------------------------------");
        for (int i = 0; i < ContarBitacora; i++) {
            System.out.printf("%-20s| %-20s| %-11s| %s\n", // control de alineado y espaciado del texto
                    bitacoraM[i][0], // % = posicion 
                    bitacoraM[i][1], // - = aliniado a la izquierda
                    bitacoraM[i][2], // # = ancho de caracteres
                    bitacoraM[i][3]);
        }
    }

    //Metodo para la registrar la bitacora 
    public static void RBitacora(String accion, String estado, String estudiante) { //RBitacora = Registrar Bitacora
        if (ContarBitacora >= 100){
            return;
        }

        // Fecha y hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sdf.format(new Date());

        bitacoraM[ContarBitacora][0] = fecha;
        bitacoraM[ContarBitacora][1] = accion;
        bitacoraM[ContarBitacora][2] = estado;
        bitacoraM[ContarBitacora][3] = estudiante;
        
        ContarBitacora++;
    }

}