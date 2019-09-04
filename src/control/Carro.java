package control;

import model.ObjetoMalha;

public class Carro extends Thread {

    private boolean finalizar;
    private ObjetoMalha ref;

    public Carro(ObjetoMalha ref) {
        this.ref = ref;
    }
    
    @Override
    public void run() {
        while (!finalizar) {
            
            
            
        }
    }

    public void finalizar() {
        this.finalizar = true;
    }

}
