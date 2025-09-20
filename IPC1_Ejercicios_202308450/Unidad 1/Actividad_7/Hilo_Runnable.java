public class Hilo_Runnable {

    // Clase que implementa Runnable
    static class SumadorRunnable implements Runnable {
        @Override
        public void run() {
            int suma = 0;
            for (int i = 1; i <= 10; i++) { // Suma del 1 al 10
                suma += i;
                System.out.println("Sumando: " + i);
                try {
                    Thread.sleep(500); // Espera medio segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Total: " + suma);
        }
    }

    public static void main(String[] args) {
        // Lanza el hilo usando Runnable
        Thread hilo = new Thread(new SumadorRunnable());//Crea el hilo
        hilo.start(); //Inicia el hilo
    }
}
