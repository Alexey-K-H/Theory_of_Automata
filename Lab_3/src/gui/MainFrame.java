package gui;

import parser.ParserArguments;
import storeMemoryAutomat.StoreMemoryMachine;

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
        this.setBounds(dimension.width/2 - 500, dimension.height/2 - 300, 1000, 600);
        this.setTitle("Task_2");

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

        JButton emptyCheck = new JButton("<html>Проверка LL<sub>1</sub></html>");
        setBoldFont(emptyCheck);
        layout.putConstraint(SpringLayout.WEST, emptyCheck, this.getWidth()/2 - 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, emptyCheck, 50, SpringLayout.SOUTH, sValue);
        panel.add(emptyCheck);

        JButton firstTest = new JButton("<html>Пример LL<sub>1</sub> грамматики:<br>N={S,B}<br>Σ={0,1,2}<br>P={S->B2,B->0,B->1}<br>S={S}</html>");
        layout.putConstraint(SpringLayout.NORTH, firstTest, 20, SpringLayout.SOUTH, configInto);
        layout.putConstraint(SpringLayout.WEST, firstTest, 10, SpringLayout.EAST, pValue);
        firstTest.addActionListener(e -> {
            nValue.setText("S,B");
            sigmaValue.setText("0,1,2");
            pValue.setText("S->B2,B->0,B->1");
            sValue.setText("S");
        });
        panel.add(firstTest);

        JButton secondTest = new JButton("<html>Пример не LL<sub>1</sub> грамматики:<br>N={S,B,A}<br>Σ={a,b}<br>P={S->A|B,A->aA|a,B->bB|b}<br>S={S}</html>");
        layout.putConstraint(SpringLayout.NORTH, secondTest, 20, SpringLayout.SOUTH, configInto);
        layout.putConstraint(SpringLayout.WEST, secondTest, 10, SpringLayout.EAST, firstTest);
        secondTest.addActionListener(e -> {
            nValue.setText("S,B,A");
            sigmaValue.setText("a,b");
            pValue.setText("S->A|B,A->aA|a,B->bB|b");
            sValue.setText("S");
        });
        panel.add(secondTest);

        JButton thirdTest = new JButton("<html>Пример LL<sub>1</sub> грамматики:<br>N={S,B,A}<br>Σ={a,b,c}<br>P={S->aAaB|bAbB,A->S|cb,B->cB|a}<br>S={S}</html>");
        layout.putConstraint(SpringLayout.NORTH, thirdTest, 20, SpringLayout.SOUTH, firstTest);
        layout.putConstraint(SpringLayout.WEST, thirdTest, 10, SpringLayout.EAST, pValue);
        thirdTest.addActionListener(e -> {
            nValue.setText("S,B,A");
            sigmaValue.setText("a,b,c");
            pValue.setText("S->aAaB|bAbB,A->S|cb,B->cB|a");
            sValue.setText("S");
        });
        panel.add(thirdTest);

        emptyCheck.addActionListener(e -> {
            if(checkEmptyTextFields(nValue, sigmaValue, pValue, sValue)){
                JLabel error = new JLabel("Не полностью заполнены значения полей!");
                setBoldFont(error);
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
                try {
                    ParserArguments parserArguments = new ParserArguments(nValue.getText(), sigmaValue.getText(), pValue.getText(), sValue.getText());
                    StoreMemoryMachine storeMemoryMachine = new StoreMemoryMachine(parserArguments.getN(),
                            parserArguments.getSIGMA(),
                            parserArguments.getP(),
                            parserArguments.getS());

                    JLabel checkLL1 = new JLabel();
                    if(storeMemoryMachine.checkLLk()){
                        checkLL1.setText("<html>Это грамматика: LL<sub>1</sub></html>");
                    }
                    else {
                        checkLL1.setText("<html>Это не грамматика: LL<sub>1</sub></html>");
                    }
                    setBoldFont(checkLL1);
                    JOptionPane.showMessageDialog(null, checkLL1, "Проверка LL1", JOptionPane.INFORMATION_MESSAGE);
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
