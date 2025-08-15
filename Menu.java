import java.util.Scanner;

import Guerrero.Mago.Asesino.Arquero;

public class Menu{
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        int Opcion;

        System.out.println("Juego por turnos");
        System.out.println("Seleccione una Opcion ingresando el numero");
        System.out.println("1-Jugar");
        System.out.println("2-Salir");

        Opcion=scanner.nextInt();
        scanner.nextLine(); //Limpia el buffer
        
        switch (Opcion) {
            case 1:
                jugar(scanner);
                break;
            case 2:
                System.out.println("Se cerro el juego");
                break;
                default:
                System.out.println("Opcion invalidad");
        }while (Opcion !=2);
        
        scanner.close();
    }

    // Logica principal del juego
    public static void jugar(Scanner scanner){
        personaje clase = elegirpersonaje(scanner);
        personaje rival = elegirpersonaje(scanner);
        
        System.out.println("Comienza la pelea");
        System.out.println("Tu personaje es: " + clase.getclase());
        System.out.println("Tu enemigo es: " rival.getclase());
        
        
        while (clase.vivo() && rival.vivo()) {
            turno(clase, rival);
            if (!rival.vivo()){
                break;
            }
            turno(rival,clase);
        }
    
        System.out.println("Fin de la partida");
        System.out.println((clase.vivo() ? clase.getclase() : enemigo.getclase()));
    }

    //Interaccion entre usuario para seleccionar personaje
    private static personaje elegirpersonaje(String clase, Scanner scanner){
            while (true) {
                try{
                    System.out.println("\nSeleccion a su personaje");
                    System.out.println("1-Guerrero");
                    System.out.println("2-Mago");
                    System.out.println("3-Asesisno");
                    System.out.println("4-Arquero");
                    System.out.println("Opcion: ");
                    personaje jugador = elegirpersonaje(clase, scanner);

                    int Opcion2 = Integer.parseInt(scanner.nextLine());

                    switch (Opcion2) {
                        case 1: 
                            return new Guerrero("Guerrero");
                        
                        case 2: 
                            return new Mago("Mago");
                        
                        case 3: 
                            return new Asesino("Asesino");
                        
                        case 4: 
                            return new Arquero("Arquero");
                        
                        default:
                            throw new IllegalArgumentException("Opcion invalida")
                    }
                } catch (NumberFormatException e){
                    System.out.println("Error: Ingrese una opcion valida");
                } catch (IllegalArgumentException e){
                    System.out.println("Error:" + e.getMessage());
                }
            }
    }

        private static void turno(personaje atacante, personaje denfensor){
            System.out.println("\nTurno de" + atacante.getclase() + "!");
            atacante.atacar(denfensor);
            if (denfensor.getdot() > 0) {
                denfensor.aplicardor();
            }
        }

}


abstract class personaje {
    protected String clase;
    protected int hp; //vida
    protected int atk; //Ataque
    protected int def; //defensa
    protected int dot; //Daño con el tiempo (Veneno o Sangrado) 
        
    public personaje(String clase, int hp, int atk, int def, int dot){
        this.clase = clase;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.dot = dot;
    }

    public abstract void atacar(rival);

    // Calculo de daño recibido
    public void dano(int dano) {
        int danoreal = Math.max(dano, def, 0);
        hp -= danoreal;
        System.out.println(clase + "recibio"+ danoreal + "daño");   
    }
        
    // Ciclo de turnos hasta que la vida sea 0
    public boolean vivo(){
        return hp > 0;
    }
        
    //texto
    public String getclase(){
        return clase;
    }


}

//Clases de Personajes y estadisticas principales
class Guerrero extends personaje {
    public Guerrero(String clase){
        super(clase, 1200, 30, 100, 0);
    }

    @Override
    public void atacar(rival){
        System.out.println(clase + "ataca");
        rival.recibirdano(atk);
    }
}

class Mago extends personaje {
    public Mago(String clase){
        super(clase, 800, 10, 50, hp*0.2);
    }

    @Override
    public void atacar(rival){
        System.out.println(clase + "ataca");
        rival.recibirdano(atk+dot);
    }
}

class Asesino extends personaje {
    public Asesino(String clase){
        super(clase, 1000, 25, 80, hp*0.2);
    }

    @Override
    public void atacar(rival){
        System.out.println(clase + "ataca");
        rival.recibirdano(atk+dot);
    }
}

class Arquero extends personaje {
    public Arquero(String clase){
        super(clase, 800, 20, 75, hp*0.2);
    }

    @Override
    public void atacar(rival){
        System.out.println(clase + "ataca");
        rival.recibirdano(atk+dot);
    }
}

