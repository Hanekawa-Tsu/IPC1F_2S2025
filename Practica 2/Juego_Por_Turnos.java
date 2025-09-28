import javax.swing.*; // Importar librerías Swing
import java.awt.*; // Importar librerías AWT
import java.awt.event.*; // Importar librerías de eventos

public class Juego_Por_Turnos extends JFrame {
    
// Clase Pokémon
class Pokemon {
    String nombre;
    int hp, atk, def;

    public Pokemon(String nombre, int hp, int atk, int def) {
        this.nombre = nombre;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        }
    }
}