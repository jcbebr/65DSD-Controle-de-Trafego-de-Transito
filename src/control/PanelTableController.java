package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Carro;
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

    public synchronized ObjetoMalha getValueAt(int row, int column, boolean selected) {
//        if (deck[row][column].isEntrada() && deck[row][column].isRolamento()) {
//            return deck[row][column].getCarro();
//        }
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

    public synchronized int getCarrosSize() {
        return carros.size();
    }

    public synchronized ObjetoMalha[][] getDeck() {
        return deck;
    }

    public synchronized void removerCarro(Carro carro) {
        carros.remove(carro);
    }

    public synchronized void encerrarTodos() {
        for (Carro carro : carros) {
            carro.finalizar();
        }
        carros.removeAll(carros);
    }

    public synchronized void addCarro(int row, int column) {
        Carro carro = new Carro(this.deck[row][column], this);
        this.deck[row][column] = carro;
        this.carros.add(carro);
        new Thread(carro).start();
        notificarMudancas();
    }

    public synchronized void addCarro() {
        for (int[] entrada : entradas) {
            if ((deck[entrada[0]][entrada[1]].getClass().equals(ObjetoMalha.class))) {
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
            scanner = new Scanner(new File("malha.txt"));
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

    public synchronized void notificarMudancas() {
        for (PanelTableObserver obs : obss) {
            obs.notifyChangedCards();
        }
    }

}
