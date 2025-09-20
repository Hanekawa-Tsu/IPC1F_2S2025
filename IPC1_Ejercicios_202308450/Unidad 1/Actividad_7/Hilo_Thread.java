public class Hilo_Thread {

    // Clase interna que extiende Thread
    static class MensajeThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) { // Repite 3 veces
                System.out.println("Este es un mensaje desde un hilo");
                try {
                    Thread.sleep(2000); // Espera 2 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Inicia el hilo
        MensajeThread hilo = new MensajeThread(); //Crea el hilo
        hilo.start();
    }
}