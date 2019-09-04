package control;

import java.util.concurrent.Semaphore;

/**
 *
 * @author 10480989907
 */
public class Contador {

    private long valorSequencial;
    private static Semaphore s = new Semaphore(1);

    public synchronized static Contador getInstance() {
        if (instance == null) {
            instance = new Contador();
        }
        return instance;
    }

    private static Contador instance;

    private Contador() {
        this.valorSequencial = 0;
    }

    public synchronized long next() {
        System.out.println(valorSequencial);
        valorSequencial++;
        return this.valorSequencial;
    }

}
