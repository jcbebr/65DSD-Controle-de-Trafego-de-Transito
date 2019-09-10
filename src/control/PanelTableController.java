package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ObjetoMalha;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 01/05/2019
 */
public class PanelTableController {

    private final List<PanelTableObserver> obss;
    private final List<int[]> entradas;
    private final List<Carro> carros;
    private ObjetoMalha[][] deck;

    public PanelTableController() {
        this.obss = new ArrayList<>();
        this.entradas = new ArrayList<>();
        this.carros = new ArrayList<>();
        initMalha();
    }

    public ObjetoMalha getValueAt(int row, int column, boolean selected) {
        return deck[row][column];
    }

    public int getRowCount() {
        return deck.length;
    }

    public int getColumnCount() {
        return deck[0].length;
    }

    public void clickCell(int row, int column) {
        addCarro(row, column);
    }

    public int getCarrosSize() {
        return carros.size();
    }

    public ObjetoMalha[][] getDeck() {
        return deck;
    }

    public void removerCarro(Carro carro) {
        carro.finalizar();
        carros.remove(carro);
    }

    public void encerrarTodos() {
        for (Carro carro : carros) {
            carro.finalizar();
        }
        carros.removeAll(carros);
        notificarMudancas();
    }
    
    public void addCarro(int row, int column) {
        this.deck[row][column].inserirCarro(null);
        Carro carro = new Carro(this, this.deck[row][column]);
        this.carros.add(carro);
        new Thread(carro).start();
    }

    public void addCarro() {
        for (int[] entrada : entradas) {
            if (!(deck[entrada[0]][entrada[1]].getClass().equals(Carro.class))) {
                addCarro(entrada[0], entrada[1]);
                entradas.remove(entrada);
                entradas.add(entrada);
                return;
            }
        }
    }

    public void addObservador(PanelTableObserver obs) {
        this.obss.add(obs);
    }

    public void removerObservador(PanelTableObserver obs) {
        this.obss.remove(obs);
    }

    private void initMalha() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("malha-exemplo-3.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        deck = new ObjetoMalha[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int codigo = scanner.nextInt();
                if ((i == 0) || (j == 0)) {
                    if ((codigo == 1) || (codigo == 4)) {
                        deck[i][j] = new ObjetoMalha(codigo, i, j, false, true);
                    } else if ((codigo == 3) || (codigo == 2)) {
                        deck[i][j] = new ObjetoMalha(codigo, i, j, true, false);
                        entradas.add(new int[]{i, j});
                    } else {
                        deck[i][j] = new ObjetoMalha(codigo, i, j, false, false);
                    }
                } else if ((i == x - 1) || (j == y - 1)) {
                    if ((codigo == 1) || (codigo == 4)) {
                        deck[i][j] = new ObjetoMalha(codigo, i, j, true, false);
                        entradas.add(new int[]{i, j});
                    } else if ((codigo == 3) || (codigo == 2)) {
                        deck[i][j] = new ObjetoMalha(codigo, i, j, false, true);
                    } else {
                        deck[i][j] = new ObjetoMalha(codigo, i, j, false, false);
                    }
                } else {
                    deck[i][j] = new ObjetoMalha(codigo, i, j, false, false);
                }
            }
        }
    }

    public void notificarMudancas() {
        for (PanelTableObserver obs : obss) {
            obs.notifyChangedCards();
        }
    }

}
