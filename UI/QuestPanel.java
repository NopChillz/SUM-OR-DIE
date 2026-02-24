package UI;

import java.awt.*;
import javax.swing.*;

public class QuestPanel extends JPanel {

    private JTextArea questArea;

    public QuestPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(80,75,70));

        // =========================
        // TEXT AREA
        // =========================
        questArea = new JTextArea();

        questArea.setEditable(false);
        questArea.setLineWrap(true);
        questArea.setWrapStyleWord(true);

        // ⭐⭐⭐ FIX ภาษาไทย (สำคัญที่สุด)
        questArea.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // ⭐ Unicode rendering
        questArea.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);

        questArea.setForeground(new Color(245,245,245));
        questArea.setBackground(new Color(80,75,70));

        // padding ด้านใน
        questArea.setBorder(
                BorderFactory.createEmptyBorder(8,8,8,8));

        // =========================
        // SCROLL
        // =========================
        JScrollPane scroll = new JScrollPane(questArea);

        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    // =========================
    // ADD QUEST TEXT
    // =========================
    public void addQuest(String text){

        questArea.append(text);
        questArea.append("\n\n");

        // auto scroll ลงล่าง
        questArea.setCaretPosition(
                questArea.getDocument().getLength()
        );
    }

    // =========================
    // CLEAR
    // =========================
    public void clear(){
        questArea.setText("");
    }
}