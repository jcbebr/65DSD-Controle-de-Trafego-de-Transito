package model;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 17/08/2019
 */
public class ObjetoMalha {

    private int codigo;
    private int codigoX;
    private int codigoY;
    private String imagem;
    private boolean entrada;
    private boolean saida;

    public  ObjetoMalha(int codigo, int codigoX, int codigoY, boolean entrada, boolean saida) {
        this.codigo = codigo;
        this.codigoX = codigoX;
        this.codigoY = codigoY;
        this.entrada = entrada;
        this.saida = saida;
        setImagem();
    }

    public synchronized int getCodigo() {
        return codigo;
    }

    public synchronized int getCodigoX() {
        return codigoX;
    }

    public synchronized int getCodigoY() {
        return codigoY;
    }

    public synchronized String getImagem() {
        return imagem;
    }

    public synchronized boolean isEntrada() {
        return entrada;
    }

    public synchronized boolean isSaida() {
        return saida;
    }

    public boolean isRolamento() {
        if ((codigo == 1) || (codigo == 2) || (codigo == 3) || (codigo == 4)) {
            return true;
        }
        return false;
    }

    public boolean isCruzamento() {
        if ((codigo == 5) || (codigo == 7) || (codigo == 8) || (codigo == 10)) {
            return true;
        }
        return false;
    }

    protected synchronized void setCodigo(int codigo) {
        this.codigo = codigo;
        setImagem();
    }

    private synchronized void setImagem(){
        this.imagem = "images/" + codigo + ".png";
    }
    
    protected synchronized void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    protected synchronized void setSaida(boolean saida) {
        this.saida = saida;
    }

    protected synchronized void setCodigoX(int codigoX) {
        this.codigoX = codigoX;
    }

    protected synchronized void setCodigoY(int codigoY) {
        this.codigoY = codigoY;
    }

}
