package control;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ObjetoMalha;

public class Carro extends Thread {

    private boolean finalizar;
    private PanelTableController ref;
    private ObjetoMalha obj;
    private int velocidade;
    private List<int[]> rumo;

    public Carro(PanelTableController ref, ObjetoMalha obj) {
        this.ref = ref;
        this.obj = obj;
        this.velocidade = 300 + new Random().nextInt(600);
        this.rumo = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!finalizar) {
            try {
                this.sleep(velocidade);
            } catch (InterruptedException ex) {
                Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!finalizar) {
                if (rumo.isEmpty()) {
                    //pega a proxima posicao
                    rumo.add(proximaPosicao(obj.getCodigoX(), obj.getCodigoY()));

                    if (!((rumo.get(rumo.size() - 1)[0] < 0) || (rumo.get(rumo.size() - 1)[0] >= ref.getRowCount())
                            || (rumo.get(rumo.size() - 1)[1] < 0) || (rumo.get(rumo.size() - 1)[1] >= ref.getColumnCount()))) {
                        //enquanto não estiver fora dos limites e o ultimo adicionado for um cruzamento ele pega a proxima posicao
                        boolean p = true;
                        while (ref.getDeck()[rumo.get(rumo.size() - 1)[0]][rumo.get(rumo.size() - 1)[1]].isCruzamento() && p) {
                            int[] proximaPosicao = proximaPosicao(rumo.get(rumo.size() - 1)[0], rumo.get(rumo.size() - 1)[1]);
                            if (!(((proximaPosicao[0] < 0) || (proximaPosicao[0] >= ref.getRowCount())
                                    || (proximaPosicao[1] < 0) || (proximaPosicao[1] >= ref.getColumnCount())))) {
                                rumo.add(proximaPosicao);
                            } else {
                                p = false;
                            }
                        }
                    } else {
                        finalizar();
                        ref.removerCarro(this);
                        break;
                    }
                }

                boolean n = true;
                for (int[] r : rumo) {
                    if (n) {
                        n = ref.getDeck()[r[0]][r[1]].inserirRumo();
                    }
                }

                if (n) {
                    int[] rumoN = rumo.remove(0);

                    if ((rumoN[0] < 0) || (rumoN[0] >= ref.getRowCount())
                            || (rumoN[1] < 0) || (rumoN[1] >= ref.getColumnCount())) {
                        finalizar();
                        ref.removerCarro(this);
                    } else {
                        ref.getDeck()[rumoN[0]][rumoN[1]].inserirCarro(obj);
                        obj = ref.getDeck()[rumoN[0]][rumoN[1]];
                    }
                } else {
                    for (int[] r : rumo) {
                        ref.getDeck()[r[0]][r[1]].retiraRumo();
                    }
                }
            }
            ref.notificarMudancas();
        }
    }

    private int[] proximaPosicao(int x, int y) {
        int[] i = new int[2];
        switch (ref.getDeck()[x][y].getCodigo()) {
            case 1: //cima
                i = new int[]{x - 1, y};
                break;
            case 2: //direita
                i = new int[]{x, y + 1};
                break;
            case 3: //baixo
                i = new int[]{x + 1, y};
                break;
            case 4: //esquerda
                i = new int[]{x, y - 1};
                break;
            case 5: //cruzamento cima
                i = new int[]{x - 1, y};
                break;
            case 6: //cruzamento direita
                i = new int[]{x, y + 1};
                break;
            case 7: //cruzamento baixo
                i = new int[]{x + 1, y};
                break;
            case 8: //cruzamento esquerda
                i = new int[]{x, y - 1};
                break;
            case 9: //cima e direita
                if (new Random().nextBoolean()) {
                    i = new int[]{x - 1, y};
                } else {
                    i = new int[]{x, y + 1};
                }
                break;
            case 10: //cima e esquerda
                if (new Random().nextBoolean()) {
                    i = new int[]{x - 1, y};
                } else {
                    i = new int[]{x, y - 1};
                }
                break;
            case 11: //baixo e direita
                if (new Random().nextBoolean()) {
                    i = new int[]{x + 1, y};
                } else {
                    i = new int[]{x, y + 1};
                }
                break;
            case 12: //baixo e esquerda
                if (new Random().nextBoolean()) {
                    i = new int[]{x + 1, y};
                } else {
                    i = new int[]{x, y - 1};
                }
                break;
            default:
                break;
        }
        return i;
    }

    public void finalizar() {
        this.obj.retirarCarro();
        this.obj.retiraRumo();
        this.finalizar = true;
    }

}
