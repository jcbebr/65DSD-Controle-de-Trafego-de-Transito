package view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import control.PanelTableController;
import control.Simulacao;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 21/04/2019
 */
public class MainScreen extends JFrame {

    private JPanel contentPane;
    private PanelTable panelTable;
    private JTextField tfLinha01;
    private JTextField tfLinha02;

    public MainScreen() throws HeadlessException {
        super("55PPR - Haru Ichiban");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        super.setVisible(true);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
    }

    private void initComponents() {
        contentPane = new JPanel(new BorderLayout());
        getContentPane().add(contentPane);

        PanelTableController c = new PanelTableController();

        JPanel filtros = new JPanel(new BorderLayout());

        JPanel linha01 = new JPanel(new FlowLayout());
        JLabel lbLinha01 = new JLabel("Quantidade máxima de veículos (unidade):");
        tfLinha01 = new JTextField("");
        tfLinha01.setPreferredSize(new Dimension(100, 20));
        linha01.add(lbLinha01);
        linha01.add(tfLinha01);
        filtros.add(linha01, BorderLayout.NORTH);

        JPanel linha02 = new JPanel(new FlowLayout());
        JLabel lbLinha02 = new JLabel("Intervalo de inserção de veículos (ms):");
        tfLinha02 = new JTextField("");
        tfLinha02.setPreferredSize(new Dimension(100, 20));
        linha02.add(lbLinha02);
        linha02.add(tfLinha02);
        filtros.add(linha02, BorderLayout.CENTER);

        JPanel linha03 = new JPanel(new FlowLayout());
        JButton btIniciar = new JButton("Iniciar");
        btIniciar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                iniciarSimulacao();
            }
        });
        JButton btEncerrarIm = new JButton("Encerrar Imediatamente");
        btEncerrarIm.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (s != null) {
                    s.encerrar();
                    c.encerrarTodos();
                }
            }
        });
        JButton btEncerrarAg = new JButton("Encerrar e Aguardar");
        btEncerrarAg.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (s != null) {
                    s.encerrar();
                }
            }
        });
        linha03.add(btIniciar);
        linha03.add(btEncerrarIm);
        linha03.add(btEncerrarAg);
        filtros.add(linha03, BorderLayout.SOUTH);

        contentPane.add(filtros, BorderLayout.NORTH);
        panelTable = new PanelTable(c, 0, 0);
        contentPane.add(panelTable, BorderLayout.SOUTH);

        pack();
    }

    public void iniciarSimulacao() {
        try {
            String veiculosMax = tfLinha01.getText();
            String intervaloIns = tfLinha02.getText();
            if (intervaloIns.equals("")) {
                intervaloIns = "500";
            }
            s = new Simulacao(Integer.parseInt(veiculosMax),
                    Integer.parseInt(intervaloIns), panelTable.getController());
            s.start();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Parâmetros inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Simulacao s;
}
