package control;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 17/08/2019
 */
public class Simulacao extends Thread {

    private final int veiculosMax;
    private final int intervaloIns;
    private final PanelTableController controller;
    private boolean encerrar;

    public Simulacao(int veiculosMax, int intervaloIns, PanelTableController controller) {
        this.veiculosMax = veiculosMax;
        this.intervaloIns = intervaloIns;
        this.controller = controller;
        this.encerrar = false;
    }

    @Override
    public void run() {
        while (!encerrar) {
            if (controller.getCarrosSize() < veiculosMax) {
                controller.addCarro();
            }
            controller.notificarMudancas();
            try {
                Thread.sleep(intervaloIns);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void encerrar() {
        this.encerrar = true;
    }
}
