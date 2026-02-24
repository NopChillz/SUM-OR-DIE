package UI;

import log.*;
import javax.swing.*;
import java.awt.*;

public class GameTerminal extends JPanel implements LogListener {

    private JTextArea textArea;

    public GameTerminal(){

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(280,150));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(25,25,25));
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Monospaced",Font.PLAIN,12));

        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.CENTER);

        GameLog.setListener(this);
    }

    @Override
    public void onLogAdded(String msg){
        textArea.append(msg + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}