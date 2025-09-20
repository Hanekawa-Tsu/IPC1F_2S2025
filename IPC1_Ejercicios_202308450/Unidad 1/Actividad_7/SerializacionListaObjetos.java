import java.io.*;

// Clase Libro serializable
class Libro implements Serializable {
    private String titulo;
    private String autor;
    private int año;

    public Libro(String titulo, String autor, int año) { //Constructor
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
    }

    public String getTitulo() { //Getters
        return titulo;
    }
    public String getAutor() { //Getters
        return autor;
    }
    public int getAño() { //Getters
        return año;
    }
}

public class SerializacionListaObjetos {
    public static void main(String[] args) {
        System.out.println("Prueba de serializacion de Libro");
        Libro libro = new Libro("ECUACIONES DIFERENCIALES", "Dennis G. Zill", 2019);

        // Serializar el objeto libro
        try {
            FileOutputStream archivo = new FileOutputStream("libro.ser"); //Crear el archivo
            ObjectOutputStream salida = new ObjectOutputStream(archivo); //Crear el flujo de salida
            salida.writeObject(libro); //Escribir el objeto en el archivo
            salida.close();
            System.out.println("Libro serializado correctamente");
        } catch (IOException e) {
            System.out.println("El error es: " + e.toString()); //Mostrar el error
        }

        // Deserializar el objeto libro
        try {
            FileInputStream archivoD = new FileInputStream("libro.ser"); //Leer el archivo
            ObjectInputStream entrada = new ObjectInputStream(archivoD); //Crear el flujo de entrada
            Libro libroRecuperado = (Libro) entrada.readObject(); //Leer el objeto del archivo
            entrada.close();
            System.out.println("Titulo: " + libroRecuperado.getTitulo());
            System.out.println("Autor: " + libroRecuperado.getAutor());
            System.out.println("Año: " + libroRecuperado.getAño());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}