package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private void setBoldFont(JComponent component){
        component.setFont(new Font(component.getFont().getName(), Font.BOLD, 16));
    }

    private void setPlainFont(JComponent component){
        component.setFont(new Font(component.getFont().getName(), Font.PLAIN, 16));
    }

    private boolean checkCorrectTextFields(JTextField n, JTextField sigma, JTextField p, JTextField s){
        if(n.getText().isEmpty() || sigma.getText().isEmpty() || p.getText().isEmpty() || s.getText().isEmpty()){
            return false;
        }
        else {
            System.out.println("Everything is correct");
            return true;
        }
    }

    public void run(){
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 400, dimension.height/2 - 300, 800, 600);
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
                "<br> e - полагается пустым символом, его можно использовать только в правилах!</html>");
        info.setFont(new Font(info.getFont().getName(), Font.BOLD, 14));
        layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, info, 20, SpringLayout.NORTH, panel);
        panel.add(info);

        JLabel configInto = new JLabel("<html> Введите параметры КСГ:</html>");
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

        JButton emptyCheck = new JButton("Проверка пустоты языка");
        setBoldFont(emptyCheck);
        layout.putConstraint(SpringLayout.WEST, emptyCheck, this.getWidth()/2 - 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, emptyCheck, 30, SpringLayout.SOUTH, sValue);
        panel.add(emptyCheck);

        JButton removeChainRules = new JButton("Убрать цепные правила");
        setBoldFont(removeChainRules);
        layout.putConstraint(SpringLayout.WEST, removeChainRules, this.getWidth()/2 - 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, removeChainRules, 10, SpringLayout.SOUTH, emptyCheck);
        panel.add(removeChainRules);

        JButton toChomsky = new JButton("Преобразовать в номальную форму Хомского");
        setBoldFont(toChomsky);
        layout.putConstraint(SpringLayout.WEST, toChomsky, this.getWidth()/2 - 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, toChomsky, 10, SpringLayout.SOUTH, removeChainRules);
        panel.add(toChomsky);

        emptyCheck.addActionListener(e -> {
            if(!checkCorrectTextFields(nValue, sigmaValue, pValue, sValue)){
                JLabel error = new JLabel("Неверно заданы параметры грамматики!");
                setBoldFont(error);
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeChainRules.addActionListener(e -> {
            if(!checkCorrectTextFields(nValue, sigmaValue, pValue, sValue)){
                JLabel error = new JLabel("Неверно заданы параметры грамматики!");
                setBoldFont(error);
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        toChomsky.addActionListener(e -> {
            if(!checkCorrectTextFields(nValue, sigmaValue, pValue, sValue)){
                JLabel error = new JLabel("Неверно заданы параметры грамматики!");
                setBoldFont(error);
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.setVisible(true);
    }
}
