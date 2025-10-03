import javax.swing.*;
import java.awt.*;

public class Juego_Por_Turnos extends JFrame {
    private static final int MAX_POKEMON = 20;
    private String[][] pokematriz = new String[MAX_POKEMON][4];
    private int pokeCount = 0;
    private String[][] bitacora = new String[100][2];
    private int bitacoraCount = 0;

    private JTextArea battleLog; // Área de texto para el log de batalla
    private JButton atacarBtn, turnoBtn, agregarBtn, iniciarBtn, buscarBtn, eliminarBtn, bitacoraBtn; // Botones
    private JComboBox<String> jugadorBox, rivalBox; // ComboBoxes para selección de Pokémon

    public Juego_Por_Turnos() {
        setTitle("Juego por Turnos Pokémon"); // Titulo de la ventana
        setSize(700, 500); // Tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana
        setLocationRelativeTo(null); // Centrar la ventana

        JPanel panel = new JPanel(new BorderLayout()); // Panel principal

        JPanel agregarPanel = new JPanel(); // Panel para agregar Pokémon
        JTextField nombreField = new JTextField(8); // Campo para el nombre
        JTextField hpField = new JTextField(4); // Campo para HP
        JTextField atkField = new JTextField(4); // Campo para ATK
        JTextField defField = new JTextField(4); // Campo para DEF
        agregarBtn = new JButton("Agregar"); // Botón para agregar
        buscarBtn = new JButton("Buscar"); // Botón para buscar
        eliminarBtn = new JButton("Eliminar"); // Botón para eliminar
        bitacoraBtn = new JButton("Bitácora");// Botón para ver bitácora
        agregarPanel.add(new JLabel("Nombre:")); // Etiqueta para nombre
        agregarPanel.add(nombreField);// Campo para nombre
        agregarPanel.add(new JLabel("HP:"));
        agregarPanel.add(hpField);
        agregarPanel.add(new JLabel("ATK:"));
        agregarPanel.add(atkField);
        agregarPanel.add(new JLabel("DEF:"));
        agregarPanel.add(defField);
        agregarPanel.add(agregarBtn); // Botón para agregar
        agregarPanel.add(buscarBtn); // Botón para buscar
        agregarPanel.add(eliminarBtn); // Botón para eliminar
        agregarPanel.add(bitacoraBtn); // Botón para ver bitácora

        JPanel seleccionPanel = new JPanel(); // Panel para selección de Pokémon
        jugadorBox = new JComboBox<>(); // ComboBox para el jugador
        rivalBox = new JComboBox<>(); // ComboBox para el rival
        iniciarBtn = new JButton("Iniciar Batalla"); // Botón para iniciar batalla
        seleccionPanel.add(new JLabel("Tu Pokémon:")); // Etiqueta para el jugador
        seleccionPanel.add(jugadorBox); // ComboBox para el jugador
        seleccionPanel.add(new JLabel("Rival:")); // Etiqueta para el rival
        seleccionPanel.add(rivalBox); // ComboBox para el rival
        seleccionPanel.add(iniciarBtn); // Botón para iniciar batalla

        battleLog = new JTextArea(10, 40); // Área de texto para el log de batalla
        battleLog.setEditable(false); // No editable
        JScrollPane scroll = new JScrollPane(battleLog); // Scroll para el área de texto

        JPanel batallaPanel = new JPanel(); // Panel para botones de batalla 
        atacarBtn = new JButton("Atacar"); // Botón para atacar
        turnoBtn = new JButton("Turno Rival"); // Botón para turno del rival
        atacarBtn.setEnabled(false);  // Inicialmente deshabilitado
        turnoBtn.setEnabled(false); // Inicialmente deshabilitado
        batallaPanel.add(atacarBtn); // Botón para atacar
        batallaPanel.add(turnoBtn); // Botón para turno del rival

        panel.add(agregarPanel, BorderLayout.NORTH); // Agregar panel en la parte superior
        panel.add(seleccionPanel, BorderLayout.CENTER); // Agregar panel en el centro
        panel.add(batallaPanel, BorderLayout.EAST); // Agregar panel a la derecha
        panel.add(scroll, BorderLayout.SOUTH); // Agregar área de texto en la parte inferior

        add(panel); // Agregar panel principal a la ventana

        // La "e" es para que se resica informacion del evento (click del boton)
        agregarBtn.addActionListener(e -> { // Acción para agregar Pokémon
            try {
                String nombre = nombreField.getText().trim(); // Obtener nombre
                int hp = Integer.parseInt(hpField.getText()); // Convertir HP a entero
                int atk = Integer.parseInt(atkField.getText()); // Convertir ATK a entero
                int def = Integer.parseInt(defField.getText()); // Convertir DEF a entero
                if (nombre.isEmpty() || hp <= 0 || atk < 0 || def < 0 || pokeCount >= MAX_POKEMON) throw new Exception();
                pokematriz[pokeCount][0] = nombre; // Guardar nombre
                pokematriz[pokeCount][1] = String.valueOf(hp); // Guardar HP como cadena
                pokematriz[pokeCount][2] = String.valueOf(atk); // Guardar ATK como cadena
                pokematriz[pokeCount][3] = String.valueOf(def); // Guardar DEF como cadena
                jugadorBox.addItem(nombre); // Agregar al ComboBox del jugador
                rivalBox.addItem(nombre); // Agregar al ComboBox del rival
                pokeCount++;
                battleLog.append("Pokémon agregado: " + nombre + "\n");
                nombreField.setText(""); hpField.setText(""); atkField.setText(""); defField.setText("");
                registrarBitacora("Agregar Pokémon", "Correcto");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos o límite alcanzado.");
                registrarBitacora("Agregar Pokémon", "Error");
            }
        });

        buscarBtn.addActionListener(e -> { // Acción para buscar Pokémon
            String nombre = JOptionPane.showInputDialog(this, "Nombre a buscar:");
            boolean encontrado = false; // Bandera para encontrar
            for (int i = 0; i < pokeCount; i++) { // Recorrer matriz
                if (pokematriz[i][0].equalsIgnoreCase(nombre)) { // Comparar nombres
                    battleLog.append("Encontrado: " + pokematriz[i][0] +
                        " HP:" + pokematriz[i][1] +
                        " ATK:" + pokematriz[i][2] +
                        " DEF:" + pokematriz[i][3] + "\n");
                    encontrado = true;
                }
            }
            if (!encontrado) battleLog.append("No se encontró el Pokémon.\n");
            registrarBitacora("Buscar Pokémon", encontrado ? "Correcto" : "Error");
        });

        eliminarBtn.addActionListener(e -> { // Acción para eliminar Pokémon
            String nombre = JOptionPane.showInputDialog(this, "Nombre a eliminar:");
            boolean eliminado = false; // Bandera para eliminar
            for (int i = 0; i < pokeCount; i++) { // Recorrer matriz
                if (pokematriz[i][0].equalsIgnoreCase(nombre)) { // Comparar nombres
                    for (int j = i; j < pokeCount - 1; j++) pokematriz[j] = pokematriz[j + 1]; // Desplazar filas
                    pokematriz[pokeCount - 1] = new String[4]; // Limpiar última fila
                    jugadorBox.removeItemAt(i); // Eliminar del ComboBox del jugador
                    rivalBox.removeItemAt(i); // Eliminar del ComboBox del rival
                    pokeCount--;
                    battleLog.append("Eliminado: " + nombre + "\n");
                    eliminado = true;
                    break;
                }
            }
            if (!eliminado) battleLog.append("No se encontró el Pokémon para eliminar.\n");
            registrarBitacora("Eliminar Pokémon", eliminado ? "Correcto" : "Error");
        });

        bitacoraBtn.addActionListener(e -> { // Acción para ver bitácora
            StringBuilder sb = new StringBuilder("--- Bitácora ---\n");
            for (int i = 0; i < bitacoraCount; i++) // Recorrer bitácora
                sb.append(bitacora[i][0]).append(" - ").append(bitacora[i][1]).append("\n"); // Agregar a StringBuilder
            JOptionPane.showMessageDialog(this, sb.toString()); // Mostrar bitácora
        });

    }

    private void registrarBitacora(String accion, String estado) { // Registrar en bitácora
        if (bitacoraCount < 100) { // Limitar a 100 entradas
            bitacora[bitacoraCount][0] = accion; // Guardar acción
            bitacora[bitacoraCount][1] = estado; // Guardar estado
            bitacoraCount++;
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Juego_Por_Turnos().setVisible(true)); // Ejecutar en el hilo de eventos
    }
}

class Pokemon {
    String nombre;
    int hp, atk, def;
    public Pokemon(String nombre, int hp, int atk, int def) { // Constructor
        this.nombre = nombre; // Nombre
        this.hp = hp; // HP
        this.atk = atk; // ATK
        this.def = def; // DEF
    }
}
