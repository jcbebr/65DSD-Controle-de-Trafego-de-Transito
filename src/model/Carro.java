package model;

import control.PanelTableController;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 17/08/2019
 */
public class Carro extends ObjetoMalha implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (allDone) {
                controller.notificarMudancas();
                return;
            } else {
                moverCarro();
                controller.notificarMudancas();
                System.out.println(controller.getCarrosSize() + " " + super.getCodigoX() + " " + super.getCodigoY());
            }
        }
    }

    private synchronized void moverCarro() {
        int xAtual = super.getCodigoX();
        int yAtual = super.getCodigoY();

        if (rumo.isEmpty()) {
            getRumo(xAtual, yAtual);
            for (int[] r : rumo) {
                if (acessoNegado(r[0], r[1])) {
                } else {
                    if (controller.getDeck()[r[0]][r[1]].getClass().equals(Carro.class)) {
                        this.imagemCarro = "images/carro1.png";
                        return;
                    }
                }
            }
        }

        this.imagemCarro = "images/carro0.png";
        int[] aux = rumo.remove(0);

        int xNovo = aux[0];
        int yNovo = aux[1];

        if ((xNovo < 0) || (xNovo >= controller.getRowCount())
                || (yNovo < 0) || (yNovo >= controller.getColumnCount())) {
            controller.getDeck()[xAtual][yAtual] = new ObjetoMalha(super.getCodigo(), super.getCodigoX(), super.getCodigoY(), super.isEntrada(), super.isSaida());
            controller.removerCarro(this);
            allDone = true;
            return;
        }

        if (!controller.getDeck()[xNovo][yNovo].getClass().equals(Carro.class)) {
            controller.getDeck()[xAtual][yAtual] = new ObjetoMalha(super.getCodigo(), super.getCodigoX(), super.getCodigoY(), super.isEntrada(), super.isSaida());
            this.setCodigo(controller.getDeck()[xNovo][yNovo].getCodigo());
            this.setCodigoX(controller.getDeck()[xNovo][yNovo].getCodigoX());
            this.setCodigoY(controller.getDeck()[xNovo][yNovo].getCodigoY());
            this.setEntrada(controller.getDeck()[xNovo][yNovo].isEntrada());
            this.setSaida(controller.getDeck()[xNovo][yNovo].isSaida());
            controller.getDeck()[xNovo][yNovo] = this;
        }
    }

    private List<int[]> rumo = new ArrayList<>();

    private synchronized boolean acessoNegado(int x, int y) {
        return ((x < 0) || (x >= controller.getRowCount())
                || (y < 0) || (y >= controller.getColumnCount()));
    }

    private synchronized void getRumo(int x, int y) {
        int[] i;
        switch (controller.getDeck()[x][y].getCodigo()) {
            case 1: //cima
                i = new int[]{x - 1, y};
                rumo.add(i);
                if (!acessoNegado(i[0], i[1])) {
                    if (controller.getDeck()[i[0]][i[1]].isCruzamento()) {
                        getRumo(i[0], i[1]);
                    }
                }
                break;
            case 2: //direita
                i = new int[]{x, y + 1};
                rumo.add(i);
                if (!acessoNegado(i[0], i[1])) {
                    if (controller.getDeck()[i[0]][i[1]].isCruzamento()) {
                        getRumo(i[0], i[1]);
                    }
                }
                break;
            case 3: //baixo
                i = new int[]{x + 1, y};
                rumo.add(i);
                if (!acessoNegado(i[0], i[1])) {
                    if (controller.getDeck()[i[0]][i[1]].isCruzamento()) {
                        getRumo(i[0], i[1]);
                    }
                }
                break;
            case 4: //esquerda
                i = new int[]{x, y - 1};
                rumo.add(i);
                if (!acessoNegado(i[0], i[1])) {
                    if (controller.getDeck()[i[0]][i[1]].isCruzamento()) {
                        getRumo(i[0], i[1]);
                    }
                }
                break;
            case 5: //cima e direita
                if (new Random().nextInt(2) == 1) {
                    rumo.add(new int[]{x - 1, y});
                    getRumo(x - 1, y);
                } else {
                    rumo.add(new int[]{x, y + 1});
                    getRumo(x, y + 1);
                }
                break;
            case 7: //cima e esquerda
                if (new Random().nextInt(2) == 1) {
                    rumo.add(new int[]{x - 1, y});
                    getRumo(x - 1, y);
                } else {
                    rumo.add(new int[]{x, y - 1});
                    getRumo(x, y - 1);
                }
                break;
            case 8: //baixo e direita
                if (new Random().nextInt(2) == 1) {
                    rumo.add(new int[]{x + 1, y});
                    getRumo(x + 1, y);
                } else {
                    rumo.add(new int[]{x, y + 1});
                    getRumo(x, y + 1);
                }
                break;
            case 10: //baixo e esquerda
                if (new Random().nextInt(2) == 1) {
                    rumo.add(new int[]{x + 1, y});
                    getRumo(x + 1, y);
                } else {
                    rumo.add(new int[]{x, y - 1});
                    getRumo(x, y - 1);
                }
                break;
            default:
                System.out.println("Erro 01 - Código: " + super.getCodigo());
                return;
        }
    }

    private PanelTableController controller;
    private boolean allDone;

    public Carro(ObjetoMalha o, PanelTableController controller) {
        super(o.getCodigo(), o.getCodigoX(), o.getCodigoY(), o.isEntrada(), o.isSaida());
        this.controller = controller;
        this.allDone = false;
    }

    public Carro(int codigo, int codigoX, int codigoY, boolean entrada, boolean saida, PanelTableController controller) {
        super(codigo, codigoX, codigoY, entrada, saida);
        this.controller = controller;
        this.allDone = false;
    }
    
    private String imagemCarro = "images/carro0.png";

    @Override
    public synchronized String getImagem() {
        return imagemCarro;
    }

    public synchronized void finalizar() {
        controller.getDeck()[super.getCodigoX()][super.getCodigoY()] = new ObjetoMalha(super.getCodigo(), super.getCodigoX(), super.getCodigoY(), super.isEntrada(), super.isSaida());
        this.allDone = true;
    }

}
