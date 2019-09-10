package model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
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
    private boolean espera;
    private Semaphore posicaoAtual;
    private Semaphore rumo;

    public ObjetoMalha(int codigo, int codigoX, int codigoY, boolean entrada, boolean saida) {
        this.codigo = codigo;
        this.codigoX = codigoX;
        this.codigoY = codigoY;
        this.entrada = entrada;
        this.saida = saida;
        this.posicaoAtual = new Semaphore(1);
        this.rumo = new Semaphore(1);
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
        if ((carro) && (espera)) {
            return "images/carro0.png";
        }
        if ((carro) && (!espera)) {
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

    public boolean isCruzamento() {
        return codigo != 1 && codigo != 2 && codigo != 3 && codigo != 4;
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

    public boolean inserirRumo() {
        try {
            this.espera = rumo.tryAcquire(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ObjetoMalha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.espera;
    }

    public void inserirCarro(ObjetoMalha objAnterior) {
        try {
            posicaoAtual.acquire();
            this.carro = true;
            if (objAnterior != null) {
                objAnterior.retirarCarro();
//                objAnterior.retiraRumo();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ObjetoMalha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void retirarCarro() {
        posicaoAtual.release();
        this.carro = false;
    }

    public void retiraRumo() {
        this.rumo.release();
        this.espera = false;
    }
}
