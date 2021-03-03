package gui;

import parser.ParserArguments;

import javax.swing.*;
import java.awt.*;

public class AutomatFrame extends JFrame {
    private boolean isLeftSide;
    private ParserArguments parserArguments;

    public AutomatFrame(boolean isLeftSide, ParserArguments parserArguments){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(600, dimension.height/2 - 250, 500, 200);
        if(isLeftSide){
            this.setTitle("Левосторонний автомат");
        }
        else {
            this.setTitle("Правосторонний автомат");
        }
        this.isLeftSide = isLeftSide;
        this.parserArguments = parserArguments;

        this.setResizable(false);
    }

    public void run(){
        JPanel jPanel = new JPanel();
        this.add(jPanel);

        StringBuilder alphabet = new StringBuilder();
        for(Character c : parserArguments.getSIGMA()){
            alphabet.append(" ").append(c);
        }

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
                JLabel error = new JLabel("Ошибка в записи цепочки! Введен символ, не содержащийся в множестве ∑, попробуйте еще раз");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                boolean result;
                if(isLeftSide){
                    left.StoreMemoryMachine leftAutomat = new left.StoreMemoryMachine(parserArguments.getN(),
                            parserArguments.getSIGMA(), parserArguments.getLP(), parserArguments.getS());
                    result = leftAutomat.syntaxAnalyzer(checkField.getText());
                }
                else {
                    right.StoreMemoryMachine rightAutomat = new right.StoreMemoryMachine(parserArguments.getN(),
                            parserArguments.getSIGMA(), parserArguments.getRP(), parserArguments.getS());
                    result = rightAutomat.syntaxAnalyzer(checkField.getText());
                }

                if(!result){
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

        jPanel.add(check);
        jPanel.add(checkField);
        jPanel.add(checkButton);
        this.setVisible(true);
    }
}
