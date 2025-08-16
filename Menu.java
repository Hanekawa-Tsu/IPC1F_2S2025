import java.util.Scanner;
import java.util.Random;

public class Menu {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        int Opcion;

        do{
            System.out.println("Juego por turnos");
            System.out.println("Seleccione una Opcion ingresando el numero");
            System.out.println("1-Jugar");
            System.out.println("2-Salir");

            try {
                Opcion= Integer.parseInt(scanner.nextLine());
                        
            
                switch (Opcion) {
                    case 1:
                        jugar(scanner);
                        break;
                    case 2:
                        System.out.println("Se cerro el juego");
                        break;
                    default:
                        System.out.println("Opcion invalidad");
                }

                }catch (NumberFormatException e){
                    System.out.println("Error");
                    Opcion = 0;

                } 
            }while (Opcion !=2);  
            scanner.close();
        }

    // Logica principal del juego
    public static void jugar(Scanner scanner){
        System.out.println("\n---Seleccion de tu personaje---");
        personaje jugador = elegirpersonaje(scanner);

        System.out.println("\n El rival se esta preparando...");
        personaje rival = elegirpersonaje(scanner);
        
        System.out.println("\n Comienza la pelea!");
        System.out.println("Tu personaje es: " + jugador.getclase());
        System.out.println("Tu enemigo es: " + rival.getclase());
        
    // clase =  jugador, se modifico porque empezo a hacer confuso para identificar al jugador de la clase
    // de personaje seleccionado.

        while (jugador.vivo() && rival.vivo()) {
            turno(jugador, rival);
            // El bucle comienza y finaliza hasta que el jugador derroto al rival
            if (!rival.vivo()){
                break;
            }
            turno(rival,jugador);
        }
    
        System.out.println("\n Fin de la partida");
        if (jugador.vivo()) {
            System.out.println("Has ganado :) " + jugador.getclase() );
        } else {
            System.out.println("Has sido Derrotado :/ " + rival.getclase());
        }
    }

    //Interaccion entre usuario para seleccionar personaje
    private static personaje elegirpersonaje(Scanner scanner){
            while (true) {
                try{
                    System.out.println("\nSeleccion a su personaje");
                    System.out.println("1-Guerrero");
                    System.out.println("2-Mago");
                    System.out.println("3-Asesisno");
                    System.out.println("4-Arquero");
                    System.out.println("Opcion: ");
                    
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
                            throw new IllegalArgumentException("Opcion invalida");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Error: Ingrese una opcion valida");
                }
            }
        }    

    // Seleccion aleatoria del rival
    private static personaje rivalaleatorio(){
        Random random = new Random();
        int Opcionrival = random.nextInt(4) + 1; //Genera un numero entre 1 a 4 que son los personajes disponibles

        switch (Opcionrival) {
            case 1:
                return new Guerrero("Guerrero");
            case 2:
                return new Mago("Mago");
            case 3:
                return new Asesino("Asesino");
            case 4:
                return new Arquero("Arquero");
            default: //condicion que nunca se cumplira
                return null;
        }
    }

        // Como funciona los turnos
        private static void turno(personaje atacante, personaje denfensor){
            System.out.println("\nTurno de " + atacante.getclase() + "!");
            atacante.atacar(denfensor);
            if (denfensor.getdot() > 0) {
                denfensor.aplicardot();
            }
            System.out.println("Vida de " + atacante.getclase() + ":" + atacante.gethp());
            System.out.println("Vida de "+ denfensor.getclase()+ "!" + denfensor.gethp());
        }

}

// Clase para personajes y estadisticas
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

    public abstract void atacar(personaje rival);
    // Calculo de daño recibido
    public void recibirdano(int dano) {
        int danoreal = Math.max(0, dano - def);
        hp -= danoreal;
        System.out.println(clase + " recibio "+ danoreal + " daño ");
        System.out.println(" Vida restante " + this.clase + ":" + this.hp);   
    }
    
    //Daño con el tiempo
    public void aplicardot(){
        if(this.dot > 0){
            this.hp -= this.dot;
            System.out.println(this.clase + " recibio " + this.dot + " de daño ");
        }
    }
    

    // Ciclo de turnos hasta que la vida sea 0
    public boolean vivo(){
        return hp > 0;
    }
        
    //texto
    public String getclase(){
        return clase;
    }

    public int getdot(){
        return dot;
    }

    public int gethp(){
        return hp;
    }
}

//Clases de Personajes y estadisticas principales
class Guerrero extends personaje {
    public Guerrero(String clase){
        super(clase, 1200, 90, 100, 0);
    }

    @Override
    public void atacar(personaje rival){
        System.out.println(clase + " ataca ");
        rival.recibirdano(atk);
    }
}

class Mago extends personaje {
    public Mago(String clase){
        super(clase, 800, 15, 50, 60);
    }

    @Override
    public void atacar(personaje rival){
        System.out.println(clase + " lanza hechizo ");
        rival.recibirdano(atk);
        rival.dot = this.dot; //Daño con el tiempo (Dot)
    }
}

class Asesino extends personaje {
    public Asesino(String clase){
        super(clase, 1000, 55, 80, 25);
    }

    @Override
    public void atacar(personaje rival){
        System.out.println(clase + " ataca con cuchillas ");
        rival.recibirdano(atk);
        rival.dot = this.dot;
    }
}

class Arquero extends personaje {
    public Arquero(String clase){
        super(clase, 800, 20, 75, 50);
    }

    @Override
    public void atacar(personaje rival){
        System.out.println(clase + " dispara una flecha ");
        rival.recibirdano(atk);
        rival.dot = this.dot;
    }
}
