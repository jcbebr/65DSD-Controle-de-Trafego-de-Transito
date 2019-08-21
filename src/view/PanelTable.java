package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import control.PanelTableController;
import control.PanelTableObserver;

/**
 *
 * @author José Carlos Bernardes Brümmer
 * @date 21/04/2019
 */
public class PanelTable extends JPanel implements PanelTableObserver {

    protected final JTable table;
    protected int cellSize = 25;
    private PanelTableController controller;
    private Boolean selected = false;

    private class PanelTableRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setIcon((ImageIcon) value);
            setSize(cellSize, cellSize);
            return this;
        }
    }

    /**
     *
     * @author José Carlos Bernardes Brümmer
     * @date 20/04/2019
     */
    private class PanelTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return controller.getRowCount();
        }

        @Override
        public int getColumnCount() {
            return controller.getColumnCount();
        }

        @Override
        public ImageIcon getValueAt(int row, int column) {
            return new ImageIcon(new ImageIcon(controller.getValueAt(row, column, selected).getImagem()).getImage().getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        }
    }

    private class PanelTableMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            controller.clickCell(table.rowAtPoint(e.getPoint()), table.columnAtPoint(e.getPoint()));
            table.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            selected = true;
            table.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            selected = false;
            table.repaint();
        }
    }

    public PanelTable(PanelTableController controller, int x, int y) {
        this.controller = controller;
        this.controller.addObservador(this);

        table = new JTable(new PanelTableModel());

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setWidth(cellSize);
            table.getColumnModel().getColumn(i).setMinWidth(cellSize);
            table.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }
        table.setRowHeight(cellSize);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(-1, 0));
        table.setDefaultRenderer(Object.class, new PanelTableRenderer());
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.setSize(cellSize * table.getColumnCount(), cellSize * table.getRowCount());
        table.addMouseListener(new PanelTableMouseListener());

        super.add(table);
        super.setLocation(x, y);
        super.setSize(cellSize * table.getColumnCount(), cellSize * table.getRowCount());
        super.setOpaque(false);
    }

    public PanelTableController getController() {
        return controller;
    }

    @Override
    public synchronized void notifyChangedCards() {
        table.repaint();
    }

}
