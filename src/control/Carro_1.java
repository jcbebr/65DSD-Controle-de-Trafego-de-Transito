//package model;
//
//import control.PanelTableController;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author José Carlos Bernardes Brümmer
// * @date 17/08/2019
// */
//public class Carro extends ObjetoMalha implements Runnable {
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
////                Thread.sleep(500 + new Random().nextInt(1000));
//                Thread.sleep(1500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            if (allDone) {
//                controller.notificarMudancas();
//                return;
//            } else {
//                moverCarro();
//                controller.notificarMudancas();
////                System.out.println(controller.getCarrosSize() + " " + super.getCodigoX() + " " + super.getCodigoY());
//            }
//        }
//    }
//
//    private long codigoOcupado;
//
//    private synchronized void moverCarro() {
//        int xAtual = super.getCodigoX();
//        int yAtual = super.getCodigoY();
//
//        boolean podemover = true;
//        if (rumo.isEmpty()) {
//            getRumo(xAtual, yAtual);
//
//        }
//
//        for (int[] r : rumo) {
//            System.out.println("x: " + r[0] + " y: " + r[1]);
//            if (!acessoNegado(r[0], r[1])) {
//                if (controller.getDeck()[r[0]][r[1]].getCarro() != -1 && controller.getDeck()[r[0]][r[1]].getCarro() != this.codigoOcupado) {
//                    podemover = false;
//                }
//            }
//        }
//
//        System.out.println("--------------");
//        if (podemover) {
//            for (int[] r : rumo) {
//                if (!acessoNegado(r[0], r[1])) {
//                    controller.getDeck()[r[0]][r[1]].setCarro(this.codigoOcupado);
//                }
//            }
//        }
//
//        if (podemover) {
//            this.imagemCarro = "images/carro0.png";
//            int[] aux = rumo.remove(0);
//
//            int xNovo = aux[0];
//            int yNovo = aux[1];
//            for (int i = 0; i < controller.getDeck().length; i++) {
//                for (int j = 0; j < controller.getDeck()[0].length; j++) {
//                    System.out.print(controller.getDeck()[i][j].getCarro() + "\t");
//                }
//                System.out.println();
//            }
//            System.out.println();
//
//            if ((xNovo < 0) || (xNovo >= controller.getRowCount())
//                    || (yNovo < 0) || (yNovo >= controller.getColumnCount())) {
//                controller.getDeck()[xAtual][yAtual] = new ObjetoMalha(super.getCodigo(), super.getCodigoX(), super.getCodigoY(), super.isEntrada(), super.isSaida());
//                controller.removerCarro(this);
//                allDone = true;
//                return;
//            }
//
//            if (!controller.getDeck()[xNovo][yNovo].getClass().equals(Carro.class)) {
////            if (controller.getDeck()[xNovo][yNovo].getCarro() == this.codigoOcupado) {
//                controller.getDeck()[xAtual][yAtual] = new ObjetoMalha(super.getCodigo(), super.getCodigoX(), super.getCodigoY(), super.isEntrada(), super.isSaida());
//                this.setCodigo(controller.getDeck()[xNovo][yNovo].getCodigo());
//                this.setCodigoX(controller.getDeck()[xNovo][yNovo].getCodigoX());
//                this.setCodigoY(controller.getDeck()[xNovo][yNovo].getCodigoY());
//                this.setEntrada(controller.getDeck()[xNovo][yNovo].isEntrada());
//                this.setSaida(controller.getDeck()[xNovo][yNovo].isSaida());
//                controller.getDeck()[xNovo][yNovo] = this;
//            }
//        } else {
//            this.imagemCarro = "images/carro1.png";
//
//        }
//
//    }
//
//    private List<int[]> rumo = new ArrayList<>();
//
//    private synchronized boolean acessoNegado(int x, int y) {
//        return ((x < 0) || (x >= controller.getRowCount())
//                || (y < 0) || (y >= controller.getColumnCount()));
//    }
//
//    private int lastselected = 0;
//
//    private synchronized void getRumo(int x, int y) {
////        if (!rumo.isEmpty()) {
////            int nx = rumo.get(rumo.size() - 1)[0];
////            int ny = rumo.get(rumo.size() - 1)[1];
////            if (!acessoNegado(nx, ny)) {
////                if (controller.getDeck()[nx][ny].getCodigo() == lastselected && controller.getDeck()[nx][ny].isCruzamento()) {
////                    rumo.remove(rumo.size() - 1);
////                }
////            }
////        }
//        int[] i;
//        switch (controller.getDeck()[x][y].getCodigo()) {
//            case 1: //cima
//                i = new int[]{x - 1, y};
//                if (rumo.size() < 2) {
//                    rumo.add(i);
//                }
//                if ((!acessoNegado(i[0], i[1])) && (controller.getDeck()[i[0]][i[1]].isCruzamento())) {
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 2: //direita
//                i = new int[]{x, y + 1};
//                if (rumo.size() < 2) {
//                    rumo.add(i);
//                }
//                if ((!acessoNegado(i[0], i[1])) && (controller.getDeck()[i[0]][i[1]].isCruzamento())) {
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 3: //baixo
//                i = new int[]{x + 1, y};
//                if (rumo.size() < 2) {
//                    rumo.add(i);
//                }
//                if ((!acessoNegado(i[0], i[1])) && (controller.getDeck()[i[0]][i[1]].isCruzamento())) {
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 4: //esquerda
//                i = new int[]{x, y - 1};
//                if (rumo.size() < 2) {
//                    rumo.add(i);
//                }
//                if ((!acessoNegado(i[0], i[1])) && (controller.getDeck()[i[0]][i[1]].isCruzamento())) {
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 5: //cruzamento cima
//                i = new int[]{x - 1, y};
//                rumo.add(i);
//                getRumo(i[0], i[1]);
//                break;
//            case 6: //cruzamento direita
//                i = new int[]{x, y + 1};
//                rumo.add(i);
//                getRumo(i[0], i[1]);
//                break;
//            case 7: //cruzamento baixo
//                i = new int[]{x + 1, y};
//                rumo.add(i);
//                getRumo(i[0], i[1]);
//                break;
//            case 8: //cruzamento esquerda
//                i = new int[]{x, y - 1};
//                rumo.add(i);
//                getRumo(i[0], i[1]);
//                break;
//            case 9: //cima e direita
//                if (new Random().nextBoolean()) {
//                    i = new int[]{x - 1, y};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                } else {
//                    i = new int[]{x, y + 1};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 10: //cima e esquerda
//                if (new Random().nextBoolean()) {
//                    i = new int[]{x - 1, y};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                } else {
//                    i = new int[]{x, y - 1};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 11: //baixo e direita
//                if (new Random().nextBoolean()) {
//                    i = new int[]{x + 1, y};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                } else {
//                    i = new int[]{x, y + 1};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            case 12: //baixo e esquerda
//                if (new Random().nextBoolean()) {
//                    i = new int[]{x + 1, y};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                } else {
//                    i = new int[]{x, y - 1};
//                    rumo.add(i);
//                    getRumo(i[0], i[1]);
//                }
//                break;
//            default:
////                System.out.println("Erro 01 - Código: " + super.getCodigo());
//                return;
//        }
//    }
//
//    private PanelTableController controller;
//    private boolean allDone;
//
//    public Carro(ObjetoMalha o, PanelTableController controller, long codigoOcupado) {
//        super(o.getCodigo(), o.getCodigoX(), o.getCodigoY(), o.isEntrada(), o.isSaida());
//        this.controller = controller;
//        this.allDone = false;
//        this.codigoOcupado = codigoOcupado;
//        this.setCarro(codigoOcupado);
//    }
//
//    public Carro(int codigo, int codigoX, int codigoY, boolean entrada, boolean saida, PanelTableController controller, long codigoOcupado) {
//        super(codigo, codigoX, codigoY, entrada, saida);
//        this.controller = controller;
//        this.allDone = false;
//        this.codigoOcupado = codigoOcupado;
//        this.setCarro(codigoOcupado);
//    }
//
//    private String imagemCarro = "images/carro0.png";
//
//    @Override
//    public synchronized String getImagem() {
//        return imagemCarro;
//    }
//
//    public synchronized void finalizar() {
//        controller.getDeck()[super.getCodigoX()][super.getCodigoY()] = new ObjetoMalha(super.getCodigo(), super.getCodigoX(), super.getCodigoY(), super.isEntrada(), super.isSaida());
//        this.allDone = true;
//    }
//
//}
