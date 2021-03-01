package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public void run(){
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(40, dimension.height/2 - 250, 500, 500);
        this.setTitle("Task_1");

        JPanel panel = new JPanel();
        this.add(panel);
        JLabel rules = new JLabel("<html>Правила ввода регулярного выражения" +
                "<br>В него могут входить следующие символы:" +
                "<br>1. Цифры: 0-9" +
                "<br>2. Буквы: a-z/A-Z" +
                "<br>3. Специальные символы: (,),|,*,&" +
                "<br>" +
                "<br>Также определены следующие операции:" +
                "<br>(w1&w2) - множество W1W2" +
                "<br>(w1|w2) - множество W1 U W2" +
                "<br>(w)* - множество W1*" +
                "<br>" +
                "<br>Примеры:(Выражение и цепочки, которые можно из него построить)" +
                "<br>1) Выражение: ((a&c)|(b)*)" +
                "<br>Цепочки: ac , b , bb , bbb , bbbb ..." +
                "<br>" +
                "<br>2) Выражение: (((((a&b)&c)|d)&(e)*)|((s&g)&a))" +
                "<br>Цепочки: abc , abce , abcee , abceee ... , d , de , dee , deee ... , sga" +
                "<br>" +
                "</html>");


        JLabel info = new JLabel("Введите регулярное выражение:");
        Font labelFont = info.getFont();
        info.setFont(new Font(labelFont.getName(), Font.BOLD, 16));

        JTextField field = new JTextField("(((((a&b)&c)|d)&(e)*)|((s&g)&a))");
        field.setPreferredSize(new Dimension(300, 32));
        field.setFont(new Font(field.getFont().getName(), Font.PLAIN, 16));

        JButton button = new JButton("Построить автомат");
        button.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        button.addActionListener(actionEvent -> {
            String checkStr = field.getText().replaceAll("[a-zA-Z0-9*()|&]", "");
            if(checkStr.length() > 0){
                JLabel error = new JLabel("Обнаружен неверный символ в выражении!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(field.getText().equals("")){
                JLabel error = new JLabel("Введено пустое выражение!");
                error.setFont(new Font(error.getFont().getName(), Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                BuilderFrame builderFrame = new BuilderFrame(field.getText());
                builderFrame.build();
            }
        });

        panel.add(rules);
        panel.add(info);
        panel.add(field);
        panel.add(button);

        this.setVisible(true);
    }
}
