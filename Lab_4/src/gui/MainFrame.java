package gui;

import parser.ParserArguments;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private void setBoldFont(JComponent component){
        component.setFont(new Font(component.getFont().getName(), Font.BOLD, 16));
    }

    private void setPlainFont(JComponent component){
        component.setFont(new Font(component.getFont().getName(), Font.PLAIN, 16));
    }

    private boolean checkEmptyTextFields(JTextField n, JTextField sigma, JTextField p, JTextField s){
        return n.getText().isEmpty() || sigma.getText().isEmpty() || p.getText().isEmpty() || s.getText().isEmpty();
    }

    public void run(){
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 500, dimension.height/2 - 300, 800, 600);
        this.setTitle("Task_4");

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);
        this.add(panel);

        JLabel info = new JLabel("<html> Свободно контекстная грамматика имеет вид:" +
                "<br> G = (N, Σ, P, S), где" +
                "<br> N - алфавит нетерминальных символов, который может состоять из любых символов кроме {', \", -, >}" +
                "<br> Σ - алфавит терминальных символов, который может состоять из любых символов кроме N ∪ {', \", -, >}" +
                "<br> P - комнечное множество правил. Каждое правило имеет вид A->a, где A∈N,  a∈(N∪Σ)*" +
                "<br> S - выделенный символ из N, считаемый конечным" +
                "<br> e - <b>полагается пустым символом, его можно использовать только в правилах!</b></html>");
        info.setFont(new Font(info.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, panel);
        panel.add(info);

        JLabel configInto = new JLabel("<html> Введите параметры КСГ. Символы и правила разделяются запятой <b>без пробелов и других разделителей!</b></html>");
        configInto.setFont(new Font(configInto.getFont().getName(), Font.PLAIN, 14));
        layout.putConstraint(SpringLayout.NORTH, configInto, 20, SpringLayout.SOUTH, info);
        layout.putConstraint(SpringLayout.WEST, configInto, 20, SpringLayout.WEST, panel);
        panel.add(configInto);

        JLabel nConfig = new JLabel("N = ");
        nConfig.setFont(new Font(nConfig.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, nConfig, 40, SpringLayout.SOUTH, configInto);
        layout.putConstraint(SpringLayout.WEST, nConfig, 20, SpringLayout.WEST, panel);
        panel.add(nConfig);

        JTextField nValue = new JTextField(15);
        nValue.setFont(new Font(nValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, nValue, 40, SpringLayout.SOUTH, configInto);
        layout.putConstraint(SpringLayout.WEST, nValue, 5, SpringLayout.EAST, nConfig);
        panel.add(nValue);

        JLabel sigmaConfig = new JLabel("Σ = ");
        sigmaConfig.setFont(new Font(sigmaConfig.getFont().getName(), Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, sigmaConfig, 20, SpringLayout.SOUTH, nValue);
        layout.putConstraint(SpringLayout.WEST, sigmaConfig, 20, SpringLayout.WEST, panel);
        panel.add(sigmaConfig);

        JTextField sigmaValue = new JTextField(15);
        sigmaValue.setFont(new Font(sigmaValue.getFont().getName(), Font.PLAIN, 16));
        layout.putConstraint(SpringLayout.NORTH, sigmaValue, 20, SpringLayout.SOUTH, nValue);
        layout.putConstraint(SpringLayout.WEST, sigmaValue, 7, SpringLayout.EAST, sigmaConfig);
        panel.add(sigmaValue);

        JLabel pConfig = new JLabel("P = ");
        setBoldFont(pConfig);
        layout.putConstraint(SpringLayout.WEST, pConfig, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, pConfig, 25, SpringLayout.SOUTH, sigmaConfig);
        panel.add(pConfig);

        JTextField pValue = new JTextField(30);
        setPlainFont(pValue);
        layout.putConstraint(SpringLayout.NORTH, pValue, 20, SpringLayout.SOUTH, sigmaValue);
        layout.putConstraint(SpringLayout.WEST, pValue, 5, SpringLayout.EAST, pConfig);
        panel.add(pValue);

        JLabel sConfig = new JLabel("S = ");
        setBoldFont(sConfig);
        layout.putConstraint(SpringLayout.WEST, sConfig, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sConfig, 25, SpringLayout.SOUTH, pConfig);
        panel.add(sConfig);

        JTextField sValue = new JTextField(15);
        setPlainFont(sValue);
        layout.putConstraint(SpringLayout.WEST, sValue, 5, SpringLayout.EAST, sConfig);
        layout.putConstraint(SpringLayout.NORTH, sValue, 20, SpringLayout.SOUTH, pValue);
        panel.add(sValue);

        JButton leftSideAutomat = new JButton("Построить левосторонний автомат");
        setBoldFont(leftSideAutomat);
        layout.putConstraint(SpringLayout.WEST, leftSideAutomat, this.getWidth()/2 - 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, leftSideAutomat, 30, SpringLayout.SOUTH, sValue);
        panel.add(leftSideAutomat);

        JButton rightSideAutomat = new JButton("Построить правосторонний автомат");
        setBoldFont(rightSideAutomat);
        layout.putConstraint(SpringLayout.WEST, rightSideAutomat, this.getWidth()/2 - 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rightSideAutomat, 10, SpringLayout.SOUTH, leftSideAutomat);
        panel.add(rightSideAutomat);

        JButton firstTest = new JButton("<html>Пример для левостороннего автомата:<br>N={S}<br>Σ={0,1}<br>P={S->0S1,S->1S0,S->S01,S->e}<br>S={S}</html>");
        layout.putConstraint(SpringLayout.NORTH, firstTest, 20, SpringLayout.SOUTH, configInto);
        layout.putConstraint(SpringLayout.WEST, firstTest, 10, SpringLayout.EAST, pValue);
        firstTest.addActionListener(e -> {
            nValue.setText("S");
            sigmaValue.setText("0,1");
            pValue.setText("S->0S1,S->1S0,S->S01,S->e");
            sValue.setText("S");
        });
        panel.add(firstTest);

        JButton secondTest = new JButton("<html>Пример для правостороннего автомата:<br>N={S,A}<br>Σ={a,b}<br>P={S->aAaaa,S->bAba,A->b}<br>S={S}</html>");
        layout.putConstraint(SpringLayout.NORTH, secondTest, 10, SpringLayout.SOUTH, firstTest);
        layout.putConstraint(SpringLayout.WEST, secondTest, 10, SpringLayout.EAST, pValue);
        secondTest.addActionListener(e -> {
            nValue.setText("S,A");
            sigmaValue.setText("a,b");
            pValue.setText("S->aAaaa,S->bAba,A->b");
            sValue.setText("S");
        });
        panel.add(secondTest);


        leftSideAutomat.addActionListener(e -> {
            if(checkEmptyTextFields(nValue, sigmaValue, pValue, sValue)){
                JLabel error = new JLabel("Не полностью заполнены значения полей!");
                setBoldFont(error);
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                try {
                    ParserArguments parserArguments = new ParserArguments(nValue.getText(), sigmaValue.getText(), pValue.getText(), sValue.getText(), true);
                    AutomatFrame automatFrame = new AutomatFrame(true, parserArguments);
                    automatFrame.run();

                } catch (Exception exception) {
                    JLabel error = new JLabel(exception.getMessage());
                    setBoldFont(error);
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rightSideAutomat.addActionListener(e -> {
            if(checkEmptyTextFields(nValue, sigmaValue, pValue, sValue)){
                JLabel error = new JLabel("Не полностью заполнены значения полей!");
                setBoldFont(error);
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                try {
                    ParserArguments parserArguments = new ParserArguments(nValue.getText(), sigmaValue.getText(), pValue.getText(), sValue.getText(), false);
                    AutomatFrame automatFrame = new AutomatFrame(false, parserArguments);
                    automatFrame.run();

                } catch (Exception exception) {
                    JLabel error = new JLabel(exception.getMessage());
                    setBoldFont(error);
                    JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.setVisible(true);
    }
}
