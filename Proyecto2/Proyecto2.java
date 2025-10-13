import javax.swing.*;
import java.awt.*;

public class Proyecto2 {

    // Matriz para usuarios: [máximo 20 usuarios][usuario, contraseña]
    public static String[][] usuarios = new String[20][2];
    public static int totalUsuarios = 0;

    // Método para registrar un usuario nuevo
    public static void registrarUsuario() {
        if (totalUsuarios >= 20) { // Límite de usuarios alcanzado
            JOptionPane.showMessageDialog(null, "No se pueden registrar más usuarios.");
            return;
        }
        String usuario = JOptionPane.showInputDialog("Ingrese nombre de usuario:"); // Solicitar nombre de usuario
        String contrasena = JOptionPane.showInputDialog("Ingrese contraseña:"); // Solicitar contraseña
        usuarios[totalUsuarios][0] = usuario;
        usuarios[totalUsuarios][1] = contrasena;
        totalUsuarios++;
        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente."); // Confirmación
    }
     // Método para autenticar usuario
    public static boolean autenticar() { // Solicitar usuario y contraseña
        String usuario = JOptionPane.showInputDialog("Usuario:");
        String contrasena = JOptionPane.showInputDialog("Contraseña:");
        for (int i = 0; i < totalUsuarios; i++) { // Verificar credenciales
            if (usuarios[i][0].equals(usuario) && usuarios[i][1].equals(contrasena)) { // datos correctos
                JOptionPane.showMessageDialog(null, "¡Bienvenido, " + usuario + "!"); // Mensaje de bienvenida
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos."); //  datos incorrectos
        return false;
    }

    public static void main(String[] args) {
        // Registrar un usuario de ejemplo
        Proyecto2.registrarUsuario();
        // Intentar login
        if (Proyecto2.autenticar()) {
            // Acceso permitido, aquí va el menú principal o ventana principal
            JOptionPane.showMessageDialog(null, "Acceso concedido al sistema.");
        } else {
            JOptionPane.showMessageDialog(null, "Acceso denegado.");
        }
    }
}