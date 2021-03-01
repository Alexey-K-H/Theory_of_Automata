package gui;

import builder.LexAnalyzer;
import builder.NFABuilder;
import builder.Path;
import builder.SyntaxAnalyzer;
import expression.Word;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BuilderFrame extends JFrame {
    private final String regVal;

    public BuilderFrame(String regVal){
        this.regVal = regVal;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(600, dimension.height/2 - 250, 500, 500);
        this.setTitle("Builder");
        this.setResizable(false);
    }

    public void build(){
        JPanel jPanel = new JPanel();
        this.add(jPanel);

        StringBuilder errMsg = new StringBuilder();

        List<Word> lw = LexAnalyzer.parse(regVal, errMsg);

        if(lw == null){
            JLabel error = new JLabel(errMsg.toString());
            error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else {
            NFABuilder nfaBuilder = new NFABuilder();
            nfaBuilder.makeNFA(lw.get(0));

            StringBuilder alphabet = new StringBuilder();
            for(Character c:nfaBuilder.getAlphabet()){
                alphabet.append(" ").append(c);
            }

            JLabel tableTitle = new JLabel("<html> Таблица переходов конечного автомата <br> Примечание: ε - обозначает пустое слово </html>");
            tableTitle.setFont(new Font(tableTitle.getFont().getName(), Font.BOLD, 16));

            Object[] columnsHeader = new String[] {"Текущее состояние", "Символ", "Следующее состояние"};
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(columnsHeader);

            for(Path p:nfaBuilder.getNfa()){
                char c = (p.getW() == '\0')?'ε':p.getW();
                tableModel.addRow(new Object[]{p.getFrom(), c, p.getTo()});
            }
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            JLabel check = new JLabel("<html>Введите строку для распознавания из символов <br> {" + alphabet + "}</html>");
            check.setFont(new Font(check.getFont().getName(), Font.BOLD, 16));

            JTextField checkField = new JTextField();
            checkField.setPreferredSize(new Dimension(400, 32));
            checkField.setFont(new Font(checkField.getFont().getName(), Font.PLAIN, 16));

            JButton checkButton = new JButton("Прочитать цепочку");
            checkButton.setFont(new Font(check.getName(), Font.BOLD, 16));

            checkButton.addActionListener(actionEvent -> {
                String newStr = checkField.getText().replaceAll("["+alphabet+"]", "");
                if(0 < newStr.length()){
                    JLabel error = new JLabel("Ошибка в записи цепочки!");
                    error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    boolean finish = SyntaxAnalyzer.analyze(nfaBuilder, checkField.getText());
                    if(!finish){
                        JLabel error = new JLabel("Цепочка не была распознана автоматом!");
                        error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JLabel success = new JLabel("Процесс распознования автоматом завершен успешно!");
                        success.setFont(new Font(success.getFont().getName(), Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, success, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            jPanel.add(tableTitle);
            scrollPane.setPreferredSize(new Dimension(this.getWidth() - 30, this.getHeight()/2));
            jPanel.add(scrollPane);

            jPanel.add(check);
            jPanel.add(checkField);
            jPanel.add(checkButton);
            this.setVisible(true);
        }
    }
}
