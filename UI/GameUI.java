package UI;

import Animation.*;
import Board.*;
import GameATK.*;
import items.Item;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections; // ⭐ เพิ่มบรรทัดนี้
import java.util.Comparator;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import log.GameLog;
import quest.QuestFactory;
import quest.QuestManager;
import quest.QuestRewardSystem;
import status.*;

public class GameUI extends JPanel implements TurnListener {

    // ===== GAME SYSTEM =====
    private Boss currentBoss;
    private Player player;
    private TurnManager turnManager;
    private QuestManager questManager;
    private QuestRewardSystem rewardSystem;
    private JProgressBar bossHpBar;
    private JProgressBar playerHpBar;
    private Board boardModel;
    private JLabel playerLabel;
    private JLabel bossLabel;
    private Battle animator;
    private boolean isAnimating = false;
    private StatusOverlay playerStatusUI;// เพิ่มใหม่
    private StatusOverlay bossStatusUI;// เพิ่มใหม่

    // ===== UI =====
    private JButton[][] gridButtons = new JButton[5][5];
    private JPanel inventoryPanel;
    private JLabel bossIntentLabel; // เพิ่มมาใหม่
    private JLabel selectedSlot = null;
    private items.Item selectedItem = null;
    private ArrayList<Point> selectedPoints = new ArrayList<>();
    private int levelgame;
    private MainFrame mainFrame;
    private GameTerminal terminal;
    private QuestPanel questPanel;
    private JButton confirmBtn;

    // ===== THEME =====
    private final Color BG_DARK = new Color(43, 45, 48);
    private final Color BG_PANEL = new Color(80, 75, 70);
    private final Color BTN_TILE = new Color(220, 215, 205);
    private final Color BTN_SELECTED = new Color(255, 215, 0);
    private final Color BTN_ATTACK = new Color(178, 34, 34);
    private final Color TEXT_COLOR = new Color(245, 245, 245);
    private final Font TITLE_FONT =UI_FONT.deriveFont(Font.BOLD, 18f);
    private final Font TILE_FONT =UI_FONT.deriveFont(Font.BOLD, 28f);
    private final Font ATTACK_FONT =UI_FONT.deriveFont(Font.BOLD, 22f);
    private final Border SLOT_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    private final Border SELECT_BORDER = BorderFactory.createLineBorder(Color.WHITE, 2);

    private static Font UI_FONT;
    static {
        String[] fonts = {"Leelawadee UI","Tahoma","Noto Sans Thai","Arial Unicode MS"};

        for(String f : fonts){
            Font test = new Font(f, Font.PLAIN, 14);
            if(test.canDisplay('ก')){
                UI_FONT = test;
                break;
            }
        }

        if(UI_FONT == null)
            UI_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    }



    public GameUI(MainFrame mainFrame, int levelgame) {

        this.mainFrame = mainFrame;
        this.levelgame = levelgame;

        setLayout(new BorderLayout());
        setBackground(BG_DARK);

        // ===============================
        // ⭐ FIX 1 : CREATE ENTITY SYSTEM
        // ===============================
        player = new Player();

        questManager = new QuestManager();

        // เพิ่ม quest ที่ต้องการ
        questManager.addQuest(QuestFactory.soulHunter());
        questManager.addQuest(QuestFactory.breakerAxe());
        questManager.addQuest(QuestFactory.venomDagger());
        questManager.addQuest(QuestFactory.mirrorShield());
        questManager.addQuest(QuestFactory.titaniumPlate());
        questManager.addQuest(QuestFactory.steadfastHelm());
        questManager.addQuest(QuestFactory.spiritFruit());
        questManager.addQuest(QuestFactory.grandPotion());
        questManager.addQuest(QuestFactory.phoenixOrb());
        questManager.addQuest(QuestFactory.powerElixir());
        questManager.addQuest(QuestFactory.timeSand());
        questManager.addQuest(QuestFactory.warHorn());

        switch (levelgame) {
            case 1 -> currentBoss = new EasyBoss();
            case 2 -> currentBoss = new MediumBoss();
            default -> currentBoss = new HardBoss();
        }

        turnManager = new TurnManager(player, currentBoss);
        player.setTurnManager(turnManager);
        turnManager.setListener(this);
        rewardSystem = new QuestRewardSystem();

        boardModel = new Board();
        boardModel.generateBoard();

        // ==========================================
        // ส่วนซ้าย (Left Panel)
        // ==========================================
        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        leftPanel.setPreferredSize(new Dimension(299, 720));
        leftPanel.setBackground(BG_DARK);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel rulePanel = createStyledPanel("RULE");
        // ===== BASIC RULE =====
        rulePanel.add(createStyledLabel("1. เลือกช่องอย่างน้อย 2 - 3 ช่อง"));
        rulePanel.add(createStyledLabel("   ผลรวมของช่อง = ดาเมจ"));
        rulePanel.add(createStyledLabel("--------------------------------"));

        rulePanel.add(createStyledLabel("2. ดาเมจ 1-5   : Miss 100%"));
        rulePanel.add(createStyledLabel("3. ดาเมจ 6-15  : โจมตีโดน 100%"));
        rulePanel.add(createStyledLabel("4. ดาเมจ 16-25 : Miss 50%"));
        rulePanel.add(createStyledLabel("5. ดาเมจ 26-36 : Miss 50% + ตีตัวเอง 25%"));
        rulePanel.add(createStyledLabel("--------------------------------"));

        // ===== SPECIAL RULE =====
        rulePanel.add(createStyledLabel("6. เลขคู่ 3 ช่อง → เปลี่ยนเป็น Heal"));
        rulePanel.add(createStyledLabel("--------------------------------"));

        // ===== STATUS =====
        rulePanel.add(createStyledLabel("Poison  : ลด HP 0.02% MaxHP"));
        rulePanel.add(createStyledLabel("Regen   : ฟื้น 10% ต่อเทิร์น"));
        rulePanel.add(createStyledLabel("Freeze  : เลือกได้แค่ 2 ช่องและใช้ไอเทมไม่ได้ "));

        questPanel = new QuestPanel();
        questPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(184, 134, 11), 2),
                        "QUEST",
                        0, 0,
                        TITLE_FONT,
                        TEXT_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane ruleScroll = new JScrollPane(rulePanel);
        ruleScroll.setBorder(null);
        ruleScroll.setOpaque(true);
        ruleScroll.setBackground(BG_PANEL);
        ruleScroll.getViewport().setBackground(BG_PANEL);
        ruleScroll.getViewport().setOpaque(true);
        ruleScroll.getVerticalScrollBar().setUnitIncrement(12);

        leftPanel.add(ruleScroll);
        leftPanel.add(questPanel);

        // ==========================================
        // ส่วนกลาง (Center Panel)
        // ==========================================
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_DARK);

        JPanel battlePanel = new JPanel(new BorderLayout());
        battlePanel.setPreferredSize(new Dimension(682, 382));
        battlePanel.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 3));

        Image img = new ImageIcon(getClass().getResource("/Image/Background/BattleScene.png")).getImage();
        Image scaledImg = img.getScaledInstance(682, 382, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(scaledImg));
        bgLabel.setLayout(null);

        Image playerImg = new ImageIcon(getClass().getResource("/Image/Character/Player.png")).getImage();
        Image scaledPlayer = playerImg.getScaledInstance(100, 125, Image.SCALE_SMOOTH);
        playerLabel = new JLabel(new ImageIcon(scaledPlayer));
        playerLabel.setBounds(100, 190, 100, 125);
        bgLabel.add(playerLabel);
        playerStatusUI = new StatusOverlay(player);
        playerStatusUI.setBounds(80, 150, 150, 32);
        bgLabel.add(playerStatusUI);

        playerHpBar = new JProgressBar(0, player.getMaxHp());
        playerHpBar.setValue(player.getCurrentHp());
        playerHpBar.setForeground(new Color(40, 200, 40));
        playerHpBar.setBackground(new Color(43, 45, 48));
        playerHpBar.setBorderPainted(false);
        playerHpBar.setBounds(90, 170, 100, 10);
        bgLabel.add(playerHpBar);

        battlePanel.add(bgLabel, BorderLayout.CENTER);

        BossShow(levelgame, bgLabel, battlePanel);
        animator = new Battle(playerLabel, bossLabel, levelgame);
        updateStatusPositions();// เพิ่มใหม่
        // ⭐ STATUS AUTO REFRESH LISTENER
        player.setStatusListener(() -> {if (playerStatusUI != null)playerStatusUI.refresh();});// เพิ่มใหม่
        currentBoss.setStatusListener(() -> {if (bossStatusUI != null)bossStatusUI.refresh();});// เพิ่มใหม่

        JPanel gridPanel = new JPanel(new GridLayout(5, 5, 4, 4));
        gridPanel.setBackground(BG_DARK);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                JButton btn = new JButton();
                btn.setFont(TILE_FONT);
                btn.setBackground(BTN_TILE);
                btn.setForeground(Color.DARK_GRAY);
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

                final int row = r;
                final int col = c;
                btn.addActionListener(e -> handleTileClick(row, col));

                gridButtons[r][c] = btn;
                gridPanel.add(btn);
            }
        }
        updateGridDisplay();

        centerPanel.add(battlePanel, BorderLayout.NORTH);
        centerPanel.add(gridPanel, BorderLayout.CENTER);

        // ==========================================
        // ส่วนขวา (Right Panel)
        // ==========================================
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(299, 720));
        rightPanel.setBackground(BG_DARK);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- SETTINGS ----------
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        settingPanel.setOpaque(false);

        JButton settingBtn = new JButton("⚙ SETTINGS");
        settingBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        settingBtn.setBackground(new Color(100, 100, 100));
        settingBtn.setForeground(Color.WHITE);
        settingBtn.setFocusPainted(false);
        settingBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingBtn.addActionListener(e -> showSettingsDialog());

        settingPanel.add(settingBtn);

        // ---------- INVENTORY ----------
        inventoryPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inventoryPanel.setBackground(BG_PANEL);
        inventoryPanel.setOpaque(true);
        inventoryPanel.setBorder(
                BorderFactory.createEmptyBorder(12,12,12,12)
        );

        JScrollPane inventoryScroll = new JScrollPane(inventoryPanel);
        inventoryScroll.setPreferredSize(new Dimension(260, 420));

        // ⭐ FIX WHITE BACKGROUND (สำคัญที่สุด)
        inventoryScroll.setOpaque(true);
        inventoryScroll.setBackground(BG_PANEL);
        inventoryScroll.getViewport().setBackground(BG_PANEL);
        inventoryScroll.getViewport().setOpaque(true);

        // ⭐ scroll theme
        inventoryScroll.getVerticalScrollBar().setBackground(BG_PANEL);
        inventoryScroll.getHorizontalScrollBar().setBackground(BG_PANEL);

        inventoryScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(184,134,11),2),
                        "INVENTORY",
                        0,0,
                        TITLE_FONT,
                        TEXT_COLOR),
                BorderFactory.createEmptyBorder(6,6,6,6)));

        inventoryScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        inventoryScroll.getVerticalScrollBar().setUnitIncrement(16);

        // ---------- ATTACK PANEL ----------
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setOpaque(false);

        confirmBtn = new JButton("ATTACK!");
        confirmBtn.setFont(ATTACK_FONT);
        confirmBtn.setBackground(BTN_ATTACK);
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFocusPainted(false);

        confirmBtn.addActionListener(e -> {
            submitSelection();
            try {
                AudioInputStream audioIn = AudioSystem
                        .getAudioInputStream(getClass().getResource("/Sound/ClickStart.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                javax.sound.sampled.FloatControl gainControl = (javax.sound.sampled.FloatControl) clip
                        .getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
                clip.start();
            } catch (Exception ex) {
            }
        });

        confirmPanel.add(confirmBtn, BorderLayout.CENTER);

        // ---------- TERMINAL ----------
        terminal = new GameTerminal();
        terminal.setPreferredSize(new Dimension(280, 150));
        GameLog.add("Game Started");

        // ---------- BOTTOM CONTAINER ----------
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(terminal, BorderLayout.CENTER);
        bottomPanel.add(confirmPanel, BorderLayout.SOUTH);

        // ---------- ASSEMBLE RIGHT PANEL ----------
        rightPanel.add(settingPanel, BorderLayout.NORTH);
        rightPanel.add(inventoryScroll, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        loadQuestDescriptions();
    }

    // ==========================================
    // Helper Methods
    // ==========================================
    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(BG_PANEL);
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(184, 134, 11), 2), title);
        border.setTitleColor(TEXT_COLOR);
        border.setTitleFont(TITLE_FONT);
        panel.setBorder(border);
        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(UI_FONT.deriveFont(14f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // ช่องไอเทม
    private void refreshInventory() {
        selectedItem = null;
        selectedSlot = null;
        inventoryPanel.removeAll();
        for (var item : player.getItemManager().getItems()) {
            Image scaled = item.getIcon().getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaled);
            boolean disabled = false;

            if (item instanceof items.AbstractItem ai && ai.isVisuallyUsed()) {
                Image imgGray = GrayFilter.createDisabledImage(icon.getImage());
                icon = new ImageIcon(imgGray);
            }
            JLabel slot = new JLabel(icon);
            slot.setMinimumSize(new Dimension(110, 110));
            slot.setPreferredSize(new Dimension(110, 110));
            slot.setMaximumSize(new Dimension(110, 110));
            slot.setHorizontalAlignment(JLabel.CENTER);
            slot.setVerticalAlignment(JLabel.CENTER);
            slot.setBorder(SLOT_BORDER);

            if (item instanceof items.PhoenixOrb) {

                slot.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                slot.setToolTipText("Passive Item");

                inventoryPanel.add(slot);
                continue; // ⭐ ข้าม mouse listener ทั้งหมด
            }

            slot.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {

                    // cooldown block
                    if (item instanceof items.AbstractItem ai && ai.isVisuallyUsed()) {
                        Toolkit.getDefaultToolkit().beep();
                        return;
                    }

                    // ⭐ TOGGLE SELECT
                    if(selectedItem == item){

                        // ---- DESELECT ----
                        selectedItem = null;
                        selectedSlot = null;
                        slot.setBorder(SLOT_BORDER);

                        GameLog.add("Item cancelled");
                        return;
                    }

                    // ---- SWITCH SELECT ----
                    if (selectedSlot != null)
                        selectedSlot.setBorder(SLOT_BORDER);

                    selectedSlot = slot;
                    selectedItem = item;
                    slot.setBorder(SELECT_BORDER);

                    System.out.println("Selected: " + item.getName());
                }
            });

            if (item instanceof items.AbstractItem ai && ai.isVisuallyUsed()) {
                slot.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                slot.setToolTipText("Item already used");
            } else {
                slot.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            inventoryPanel.add(slot);
        }
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    // ==========================================
    // Methods ระบบเกม
    // ==========================================
    private void BossShow(int level, JLabel bgLabel, JPanel battlePanel) {

        bossHpBar = new JProgressBar(0, currentBoss.getMaxHp());
        bossHpBar.setValue(currentBoss.getCurrentHp());
        bossHpBar.setForeground(new Color(220, 40, 40));
        bossHpBar.setBackground(new Color(43, 45, 48));
        bossHpBar.setBorderPainted(false);

        // =====================
        // CREATE BOSS SPRITE
        // =====================
        if (level == 1) {

            Image img = new ImageIcon(
                    getClass().getResource("/Image/Character/Boss1.png")).getImage();

            bossLabel = new JLabel(new ImageIcon(
                    img.getScaledInstance(86, 131, Image.SCALE_SMOOTH)));

            bossLabel.setBounds(450, 190, 86, 131);
            bossHpBar.setBounds(440, 170, 100, 10);

        } else if (level == 2) {

            Image img = new ImageIcon(
                    getClass().getResource("/Image/Character/Boss2.png")).getImage();

            bossLabel = new JLabel(new ImageIcon(
                    img.getScaledInstance(86, 165, Image.SCALE_SMOOTH)));

            bossLabel.setBounds(450, 150, 86, 165);
            bossHpBar.setBounds(440, 130, 100, 10);

        } else {

            Image img = new ImageIcon(
                    getClass().getResource("/Image/Character/Boss3.png")).getImage();

            bossLabel = new JLabel(new ImageIcon(
                    img.getScaledInstance(120, 149, Image.SCALE_SMOOTH)));

            bossLabel.setBounds(450, 170, 120, 149);
            bossHpBar.setBounds(460, 150, 100, 10);
        }

        bgLabel.add(bossLabel);

        // =====================
        // ⭐ STATUS UI (สร้างครั้งเดียว)
        // =====================
        bossStatusUI = new StatusOverlay(currentBoss);
        bossStatusUI.setBounds(420, 110, 150, 32);
        bgLabel.add(bossStatusUI);

        // =====================
        // ⭐ INTENT UI
        // =====================
        bossIntentLabel = new JLabel("", SwingConstants.CENTER);
        bossIntentLabel.setForeground(Color.GRAY);
        bossIntentLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        bossIntentLabel.setBounds(410, 100, 200, 25);

        bgLabel.add(bossIntentLabel);

        bgLabel.add(bossHpBar);
        battlePanel.add(bgLabel, BorderLayout.CENTER);
    }

    private void handleTileClick(int r, int c) {

        Point p = new Point(r, c);

        if (selectedPoints.contains(p)) {
            selectedPoints.remove(p);
            gridButtons[r][c].setBackground(BTN_TILE);
            return;
        }

        // ⭐ LIMIT WHEN FROZEN
        int maxSelect = player.getStatusManager().has(FreezeStatus.class) ? 2 : 3;

        if (selectedPoints.size() >= maxSelect)
            return;

        if (isValidMove(p)) {
            selectedPoints.add(p);
            gridButtons[r][c].setBackground(BTN_SELECTED);
        }
    }

    private void updateStatusPositions(){

        // ===== PLAYER =====
        if(playerStatusUI != null && playerLabel != null){

            int x = playerLabel.getX()
                    + playerLabel.getWidth()/2
                    - 80; // center overlay

            int y = playerLabel.getY() - 55; // ⭐ ยกขึ้นอีก

            playerStatusUI.setBounds(x, y, 160, 32);
        }

        // ===== BOSS =====
        if(bossStatusUI != null && bossLabel != null){

            int x = bossLabel.getX()
                    + bossLabel.getWidth()/2
                    - 80;

            int y = bossLabel.getY() - 55; // ⭐ สูงกว่า HP bar

            bossStatusUI.setBounds(x, y, 160, 32);
        }
    }

    private boolean isValidMove(Point p) {
        if (selectedPoints.isEmpty())
            return true;
        Point last = selectedPoints.get(selectedPoints.size() - 1);
        int dr = Math.abs(p.x - last.x);
        int dc = Math.abs(p.y - last.y);
        if (dr > 1 || dc > 1)
            return false;

        if (selectedPoints.size() == 2) {
            Point first = selectedPoints.get(0);
            int v1_x = last.x - first.x;
            int v1_y = last.y - first.y;
            int v2_x = p.x - last.x;
            int v2_y = p.y - last.y;
            return (v1_x == v2_x) && (v1_y == v2_y);
        }
        return true;
    }

    private void submitSelection() {
        if (turnManager.getState() != TurnState.PLAYER_TURN || isAnimating)
            return;

        if (selectedPoints.size() < 2) {
            JOptionPane.showMessageDialog(this, "At least two options must be selected.");
            return;
        }

        Collections.sort(selectedPoints, Comparator
                .comparingInt((Point p) -> p.x)
                .thenComparingInt(p -> p.y));

        Point start = selectedPoints.get(0);
        Point end = selectedPoints.get(1);

        Direction dir;
        if (start.x == end.x)
            dir = Direction.HORIZONTAL;
        else if (start.y == end.y)
            dir = Direction.VERTICAL;
        else if (start.y < end.y)
            dir = Direction.DIAGONAL_RIGHT;
        else
            dir = Direction.DIAGONAL_LEFT;

        List<Integer> numbers = boardModel.replaceNumbers(start.x, start.y, dir, selectedPoints.size());
        ComboResult combo = ComboAnalyzer.analyze(numbers);
        int damage = combo.getDamage();

        List<Item> rewards =
                questManager.checkQuestsAndCollect(numbers, player);
        handleQuestRewards(rewards);

        GameLog.add("Damage = " + damage);

        updateGridDisplay();
        clearSelection();

        setGameControlsEnabled(false);
        isAnimating = true;

        // =========================
        // ⭐ HEAL ONLY TURN
        // =========================
        if (combo.isHealPlayer()) {

            int heal = combo.getDamage();

            player.heal(heal);
            GameLog.add("Heal " + heal);

            // ❗ ไม่ใช้ item
            selectedItem = null;
            selectedSlot = null;

            isAnimating = false;
            setGameControlsEnabled(true);
            // ⭐ จบเทิร์นทันที
            turnManager.playerTurnFinished();

            return;
        }

        // =========================
        // ⭐ USE ITEM (ATTACK ONLY)
        // =========================
        if (selectedItem != null && player.getItemManager().getItems().contains(selectedItem)) {
            GameLog.add("Used item: " + selectedItem.getName());
            player.getItemManager().useItem(selectedItem, currentBoss);

            selectedItem = null;
            selectedSlot = null;
        }
        // ===== PLAY ANIMATION =====
        if (combo.isHitBoss() || combo.isHitSelf()) {
            animator.playerAttack(() -> {

                animator.bossTakeDamage();

                Timer delayTimer = new Timer(300, ev -> {

                    if (combo.isHitSelf()) {
                        player.takeDamage(combo.getDamage());
                        GameLog.add("You hit yourself!");
                    } else if (combo.isHitBoss()) {
                        turnManager.playerAttack(combo.getDamage());
                    } else {
                        GameLog.add("Attack Miss!");
                        turnManager.playerTurnFinished();
                    }

                    bossHpBar.setValue(turnManager.getBoss().getCurrentHp());
                    playerHpBar.setValue(turnManager.getPlayer().getCurrentHp());

                    // ⭐⭐⭐ ADD THIS ⭐⭐⭐
                    refreshInventory(); // ← sync UI หลัง item ถูก remove จริง

                    isAnimating = false;

                    if (turnManager.getState() == TurnState.WIN) {
                        mainFrame.changePage(new WinUi(mainFrame));
                    }
                });

                delayTimer.setRepeats(false);
                delayTimer.start();
            });
        } else {
            GameLog.add("Attack Miss!");
            isAnimating = false;
            turnManager.playerTurnFinished();
        }
    }

    private void refreshHpBars() {
        bossHpBar.setValue(turnManager.getBoss().getCurrentHp());
        playerHpBar.setValue(turnManager.getPlayer().getCurrentHp());
    }

    private void loadQuestDescriptions() {

        questPanel.clear();

        for (var quest : questManager.getQuests()) {
            questPanel.addQuest(quest.getFullText());
        }
    }

    private void updateGridDisplay() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                int val = boardModel.getValue(r, c);
                gridButtons[r][c].setText(String.valueOf(val));
            }
        }
    }

    private void clearSelection() {
        for (Point p : selectedPoints) {
            gridButtons[p.x][p.y].setBackground(BTN_TILE);
        }
        selectedPoints.clear();
    }

    private void handleQuestRewards(List<items.Item> rewards){//เพิ่มใหม่

        if(rewards.isEmpty())
            return;

        // ===== ได้ 1 ชิ้น =====
        if(rewards.size() == 1){
            player.getItemManager().addItem(rewards.get(0));
            refreshInventory();
            return;
        }

        // ===== ได้หลายชิ้น → ให้เลือก =====
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(this),
                "เลือกไอเทม",
                Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(1, rewards.size(),10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        panel.setBackground(new Color(43,45,48));

        for(items.Item item : rewards){

            Image img = item.getIcon().getImage()
                    .getScaledInstance(96,96,Image.SCALE_SMOOTH);

            JButton btn = new JButton(item.getName(), new ImageIcon(img));
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);

            btn.addActionListener(e -> {

                player.getItemManager().addItem(item);

                GameLog.add("You Choose: " + item.getName());

                dialog.dispose();
                refreshInventory();
            });

            panel.add(btn);
        }

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void setGameControlsEnabled(boolean isEnabled) {
        confirmBtn.setEnabled(isEnabled);
        confirmBtn.setBackground(isEnabled ? BTN_ATTACK : Color.GRAY);

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                gridButtons[r][c].setEnabled(isEnabled);
                if (isEnabled) {
                    gridButtons[r][c].setBackground(BTN_TILE);
                    gridButtons[r][c].setForeground(Color.DARK_GRAY);
                } else {
                    gridButtons[r][c].setBackground(new Color(60, 60, 60));
                    gridButtons[r][c].setForeground(Color.GRAY);
                }
            }
        }
    }

    private void showSettingsDialog() {
        JDialog dialog = new JDialog(mainFrame, "PAUSE", true);
        dialog.setSize(300, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(184, 134, 11), 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titleLabel = new JLabel("GAME PAUSED", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(BTN_SELECTED);

        JButton btnContinue = createMenuButton("CONTINUE");
        JButton btnBack = createMenuButton("BACK TO MENU");
        JButton btnExit = createMenuButton("EXIT GAME");

        btnContinue.addActionListener(e -> dialog.dispose());

        btnBack.addActionListener(e -> {
            dialog.dispose();
            mainFrame.changePage(new StartUI(mainFrame));
        });

        btnExit.addActionListener(e -> System.exit(0));

        panel.add(titleLabel);
        panel.add(btnContinue);
        panel.add(btnBack);
        panel.add(btnExit);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void updateBossIntentUI() { // เพิ่มใหม่

        BossIntent intent = turnManager.getBoss().getIntent();

        if (intent == null) {
            bossIntentLabel.setText("");
            return;
        }

        String text = switch (intent.getType()) {

            case ATTACK -> "⚔ Attack " + intent.getValue();
            case FREEZE_STRIKE -> "❄ Freeze " + intent.getValue();
            case GRAVITY_SMASH -> "🌑 Gravity Smash " + intent.getValue();
            case HEAL -> "💚 Heal " + intent.getValue();
            case DEFEND -> "🛡 Defend " + intent.getValue();
        };

        bossIntentLabel.setText(text);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(TITLE_FONT);
        btn.setBackground(BG_PANEL);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 2));
        return btn;
    }

    @Override
    public void onPlayerTurn() {

        System.out.println("PLAYER TURN");

        isAnimating = false;

        // ✅ เปิด control เมื่อ player turn เริ่มจริง
        setGameControlsEnabled(true);

        // ===== UI SYNC =====
        refreshInventory();
        refreshHpBars();
        updateBossIntentUI();

        updateStatusPositions();

        if (playerStatusUI != null)
            playerStatusUI.refresh();

        if (bossStatusUI != null)
            bossStatusUI.refresh();
    }

    @Override
    public void onBossTurn() {

        System.out.println("BOSS TURN START");

        isAnimating = true;

        // ✅ ปิด control ทันที
        setGameControlsEnabled(false);

        updateBossIntentUI();
        updateStatusPositions();

        // ===== PLAY ANIMATION =====
        animator.bossAttack();
        animator.playerTakeDamage();

        Timer bossDelay = new Timer(500, e -> {

            // ===== AFTER BOSS ACTION FINISHED =====
            refreshHpBars();
            refreshInventory();

            if (playerStatusUI != null)
                playerStatusUI.refresh();

            if (bossStatusUI != null)
                bossStatusUI.refresh();

            updateStatusPositions();

            isAnimating = false;

            // ❗ ไม่ต้อง enable control ที่นี่
            // TurnManager จะเรียก onPlayerTurn() เอง
        });

        bossDelay.setRepeats(false);
        bossDelay.start();
    }

    @Override
    public void onBattleWin() {
        mainFrame.changePage(new WinUi(mainFrame));
    }

    @Override
    public void onBattleLose() {
        mainFrame.changePage(new LoseUI(mainFrame));
    }
}