package com.mycompany.jtablesmarcaveis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.table.DefaultTableCellRenderer;

public class ExemploJTableComCheckBoxTamanhoAjustado extends JFrame {
    private DefaultTableModel model;
    private List<Integer> checkedRows = new ArrayList<>();
    private JLabel statusLabel;

    public ExemploJTableComCheckBoxTamanhoAjustado() {
        setTitle("Exemplo de JTable com JCheckBox (Tamanho Ajustado)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);

        // Cria um modelo de tabela
        model = new DefaultTableModel();
        model.addColumn("Selecionado");
        model.addColumn("Coluna 1");
        model.addColumn("Coluna 2");
        model.addColumn("Coluna 3");

        // Preenche a tabela com dados aleatórios e JCheckBox
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Object[] row = {
                false, // Inicialmente, todos os checkboxes estão desmarcados
                random.nextInt(100),
                random.nextDouble(),
                "Texto " + i
            };
            model.addRow(row);
        }

        // Cria a tabela com o modelo de dados
        JTable table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class; // Usamos Boolean em vez de JCheckBox
                }
                return super.getColumnClass(column);
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 0) {
                    return new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                                                       boolean isSelected, boolean hasFocus, int row, int column) {
                            JCheckBox checkBox = new JCheckBox();
                            checkBox.setSelected((Boolean) value); // Define o estado do checkbox com base no valor Boolean
                            checkBox.setHorizontalAlignment(JCheckBox.CENTER);
                            checkBox.setPreferredSize(new Dimension(36, 36)); // Ajusta o tamanho do checkbox
                            return checkBox;
                        }
                    };
                }
                return super.getCellRenderer(row, column);
            }
        };

        // Define a altura das linhas para dobrar o tamanho atual
        table.setRowHeight(table.getRowHeight() * 2);

        // Adiciona um ActionListener para os checkboxes
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        // Adiciona a tabela a um JScrollPane para rolagem
        JScrollPane scrollPane = new JScrollPane(table);

        // Cria um rótulo (JLabel) para exibir o status
        statusLabel = new JLabel("Linhas selecionadas: ");

        // Cria um botão "Imprimir" e adiciona um ActionListener
        JButton imprimirButton = new JButton("Imprimir");
        imprimirButton.addActionListener((ActionEvent e) -> {
            checkedRows.clear(); // Limpa a lista antes de adicionar os índices das linhas selecionadas
            for (int i = 0; i < table.getRowCount(); i++) {
                Boolean checked = (Boolean) table.getValueAt(i, 0);
                if (checked) {
                    checkedRows.add(i);
                }
            }
            // Atualiza o rótulo com o status
            statusLabel.setText("Linhas selecionadas: " + checkedRows);
        });

        // Adiciona o JScrollPane, o rótulo e o botão ao JFrame
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(statusLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(imprimirButton, BorderLayout.SOUTH);
        getContentPane().add(panel);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExemploJTableComCheckBoxTamanhoAjustado exemplo = new ExemploJTableComCheckBoxTamanhoAjustado();
            exemplo.setVisible(true);
        });
    }
}
