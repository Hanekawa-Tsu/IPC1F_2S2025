import javax.swing.*;
import java.awt.*;

public class Proyecto2 {

    // Matriz para usuarios: [máximo 20 usuarios][usuario, contraseña]
    public static String[][] usuarios = new String[20][2];
    public static int totalUsuarios = 0;

    // Método para registrar un usuario nuevo usando cuadros de diálogo
    public static void registrarUsuario() {
        if (totalUsuarios >= 20) {
            JOptionPane.showMessageDialog(null, "No se pueden registrar más usuarios.");
            return;
        }
        String usuario = JOptionPane.showInputDialog("Ingrese nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese contraseña:");
        usuarios[totalUsuarios][0] = usuario;
        usuarios[totalUsuarios][1] = contrasena;
        totalUsuarios++;
        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
    }
}