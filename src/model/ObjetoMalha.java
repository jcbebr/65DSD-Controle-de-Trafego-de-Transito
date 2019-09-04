package model;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 17/08/2019
 */
public class ObjetoMalha {

    private int codigo;
    private int codigoX;
    private int codigoY;
    private boolean entrada;
    private boolean saida;
    private boolean carro;
    private boolean ocupado;
    private Semaphore semaphore;

    public ObjetoMalha(int codigo, int codigoX, int codigoY, boolean entrada, boolean saida) {
        this.codigo = codigo;
        this.codigoX = codigoX;
        this.codigoY = codigoY;
        this.entrada = entrada;
        this.saida = saida;
        this.semaphore = new Semaphore(1);
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigoX() {
        return codigoX;
    }

    public int getCodigoY() {
        return codigoY;
    }

    public String getImagem() {
        if ((carro) && (!ocupado)) {
            return "images/carro0.png";
        }
        if ((carro) && (ocupado)) {
            return "images/carro1.png";
        }
        return "images/" + codigo + ".png";
    }

    public boolean isEntrada() {
        return entrada;
    }

    public boolean isSaida() {
        return saida;
    }

    public void setCodigoX(int codigoX) {
        this.codigoX = codigoX;
    }

    public void setCodigoY(int codigoY) {
        this.codigoY = codigoY;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public void setSaida(boolean saida) {
        this.saida = saida;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    public void setCarro(boolean carro) {
        try {
            semaphore.acquire();
            this.carro = carro;
        } catch (InterruptedException ex) {
            Logger.getLogger(ObjetoMalha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
