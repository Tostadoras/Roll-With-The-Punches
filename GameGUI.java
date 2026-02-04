import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.sound.sampled.*;
import java.util.Random;

public class GameGUI extends JFrame {

    // --- GUI STRUCTURE ---
    private JPanel mainContainer;
    private CardLayout cardLayout;
    
    // --- SELECTION SCREEN COMPONENTS ---
    private JLabel selectionInstruction;
    private JPanel characterGrid;

    // --- BATTLE SCREEN COMPONENTS ---
    private JTextArea battleLog;
    private JLabel p1StatsLabel, p2StatsLabel; 
    private JLabel p1HPLabel, p2HPLabel;
    private JLabel p1UltLabel, p2UltLabel; // Shows [X] [X] [ ]
    private JLabel p1StatusLabel, p2StatusLabel; // --- This shows the Status Effects ---
    private JProgressBar p1HealthBar, p2HealthBar;
    private JLabel p1SpriteLabel, p2SpriteLabel; 
    
    // Buttons
    private JButton btnRoll;   // Move 1
    private JButton btnAttack; // Move 2
    private JButton btnUlt;    // Move 3
    private JLabel turnIndicator;

    // --- GAME LOGIC ---
    private static PCharacter p1;
    private static PCharacter p2;
    private String p1NameRaw, p2NameRaw; 
    private PCharacter currentPlayer;
    private PCharacter currentOpponent;

    // Ult Charges (0 to 3)
    private static int p1UltCharge = 0;
    private static int p2UltCharge = 0;
    
    // --- Audio Clip for Music ---
    private Clip musicClip;

    // List of available characters
    private final String[] CHAR_ROSTER = {
        "Big Mom", "Ezreal", "Joe", "Junkrat", 
        "Luffy", "Pyke", "Steve", "Rammus", "Veigar", 
        "Volibear", "Yami", "Nasus", "Akali",
        "Smolder", "Aang", "Master Yi", "Zed"
    };

    public GameGUI() {
        setTitle("Java Fight Arena");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(900, 750); 
        setResizable(true); 
        
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        add(mainContainer);

        initSelectionScreen();
        initBattleScreen();

        cardLayout.show(mainContainer, "SELECTION");
        
        redirectSystemOut();
        setVisible(true);
    }

    private void playRandomMusic() {
        try {
            // Stop any existing music
            if (musicClip != null && musicClip.isRunning()) {
                musicClip.stop();
            }

            File musicDir = new File("Music");

            // Safety check
            if (!musicDir.exists() || !musicDir.isDirectory()) {
                System.out.println("No 'music' folder found. Please create one and add .wav files.");
                return;
            }

            // Get all .wav files
            File[] files = musicDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

            if (files != null && files.length > 0) {
                // Pick random file
                Random rand = new Random();
                File selectedSong = files[rand.nextInt(files.length)];
                
                System.out.println("Playing music: " + selectedSong.getName());
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(selectedSong);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioInput);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
                musicClip.start();
            } else {
                System.out.println("No .wav files found in the 'music' folder.");
            }

        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    // =================================================================
    // HELPER: DARKEN COLOR FOR CLICK EFFECT
    // =================================================================
    private Color darkenColor(Color color) {
        int r = Math.max(0, (int)(color.getRed() * 0.7));
        int g = Math.max(0, (int)(color.getGreen() * 0.7));
        int b = Math.max(0, (int)(color.getBlue() * 0.7));
        return new Color(r, g, b);
    }

    // =================================================================
    // SCREEN 1: CHARACTER SELECTION
    // =================================================================
    private void initSelectionScreen() {
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBackground(new Color(30, 30, 30));
        selectionPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        selectionInstruction = new JLabel("Player 1: Select Your Character", SwingConstants.CENTER);
        selectionInstruction.setFont(new Font("Arial", Font.BOLD, 28));
        selectionInstruction.setForeground(Color.CYAN);
        selectionPanel.add(selectionInstruction, BorderLayout.NORTH);

        // Updated grid to fit the RANDOM button
        characterGrid = new JPanel(new GridLayout(4, 5, 10, 10)); // Changed rows to 4
        characterGrid.setBackground(new Color(30, 30, 30));

        for (String charName : CHAR_ROSTER) {
            JButton charBtn = createCharacterButton(charName);
            characterGrid.add(charBtn);
        }
        
        // --- RANDOM BUTTON ---
        JButton randomBtn = new JButton("Random");
        randomBtn.setFont(new Font("Arial", Font.BOLD, 16));
        randomBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        randomBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        // 1. STYLE FIXES: Remove whitespace and match background
        Color normalColor = new Color(60, 60, 60);
        Color pressedColor = darkenColor(normalColor);

        randomBtn.setBackground(normalColor);
        randomBtn.setForeground(Color.WHITE);
        randomBtn.setFocusPainted(false);
        randomBtn.setOpaque(true);
        randomBtn.setBorderPainted(false);
        // 2. IMAGE LOADING
        try {
            File imgFile = new File("random.jpg"); 
            if (imgFile.exists()) {
                Image img = ImageIO.read(imgFile);
                Image resized = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                randomBtn.setIcon(new ImageIcon(resized));
            } else {
                // Fallback if image isn't found
                randomBtn.setText("RANDOM");
                randomBtn.setForeground(Color.WHITE);
                randomBtn.setFont(new Font("Arial", Font.BOLD, 16));
            }
        } catch (IOException e) {}

        randomBtn.getModel().addChangeListener(e -> {
            randomBtn.setBackground(randomBtn.getModel().isPressed() ? pressedColor : normalColor);
        });
        // 3. ACTION LISTENER
        randomBtn.addActionListener(e -> {
            Random rand = new Random();
            String randomChar = CHAR_ROSTER[rand.nextInt(CHAR_ROSTER.length)];
            handleCharacterSelection(randomChar);
        });
        
        characterGrid.add(randomBtn);
        // --------------------------
        selectionPanel.add(characterGrid, BorderLayout.CENTER);
        mainContainer.add(selectionPanel, "SELECTION");
    }

    private JButton createCharacterButton(String name) {
        JButton btn = new JButton(name);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        
        Color normalColor = new Color(60, 60, 60);
        Color pressedColor = darkenColor(normalColor);
        
        btn.setBackground(normalColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        
        btn.getModel().addChangeListener(e -> {
            btn.setBackground(btn.getModel().isPressed() ? pressedColor : normalColor);
        });

        ImageIcon icon = loadCharacterImage(name, 100); 
        if (icon != null) {
            btn.setIcon(icon);
        } else {
            Color missingColor = new Color(100, 100, 100);
            btn.setBackground(missingColor); 
            btn.setText("<html><center>[Image Not Found]<br>" + name + "</center></html>");
             btn.getModel().addChangeListener(e -> {
                 btn.setBackground(btn.getModel().isPressed() ? darkenColor(missingColor) : missingColor);
            });
        }

        btn.addActionListener(e -> handleCharacterSelection(name));
        return btn;
    }

    private ImageIcon loadCharacterImage(String name, int size) {
        String[] extensions = {".png", ".jpg", ".jpeg"};
        // Basically I should have all the file extensions I want to use covered
        for (String ext : extensions) {
            try {
                // Tries absolute path first (as requested), falls back if needed
                File imgFile = new File("FILEPATHUNTILCHARACTERNAME" + name + ext); 
                if (!imgFile.exists()) {
                     // Fallback to local project folder if absolute path fails
                     imgFile = new File("images/" + name + ext);
                }
                
                if (imgFile.exists()) {
                    Image img = ImageIO.read(imgFile);
                    Image resized = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                    return new ImageIcon(resized);
                }
            } catch (IOException e) {}
        }
        return null;
    }

    private void handleCharacterSelection(String name) {
        if (p1 == null) {
            p1 = getCharacterInstance(name);
            p1.setName("Player 1 (" + name + ")");
            p1NameRaw = name; 
            
            selectionInstruction.setText("Player 2: Select Your Character");
            selectionInstruction.setForeground(Color.ORANGE);
            System.out.println("Player 1 selected: " + name);
        } else if (p2 == null) {
            p2 = getCharacterInstance(name);
            p2.setName("Player 2 (" + name + ")");
            p2NameRaw = name; 
            
            System.out.println("Player 2 selected: " + name);
            startGame();
        }
    }

    private PCharacter getCharacterInstance(String name) {
        switch (name) {
            case "Big Mom": return new BigMom();
            case "Ezreal": return new Ezreal();
            case "Joe": return new Joe();
            case "Junkrat": return new Junkrat();
            case "Luffy": return new Luffy();
            case "Pyke": return new Pyke();
            case "Rammus": return new Rammus();
            case "Veigar": return new Veigar();
            case "Volibear": return new Volibear();
            case "Yami": return new Yami();
            case "Nasus": return new Nasus();
            case "Akali": return new Akali();
            case "Smolder": return new Smolder();
            case "Master Yi": return new MasterYi();
            case "Zed": return new Zed();
            case "Steve": return new Steve();
            case "Aang": return new Aang();
            default: return new PCharacter();
        }
    }
    // =================================================================
    // SCREEN 2: BATTLE ARENA
    // =================================================================
    private void initBattleScreen() {
        JPanel battlePanel = new JPanel(new BorderLayout());
        battlePanel.setBackground(new Color(20, 20, 20));
        
        // ------------------------------------------------
        // 1. TOP PANEL (STATS)
        // ------------------------------------------------
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        topPanel.setBackground(new Color(40, 40, 40));

        // P1 Stats
        JPanel p1Wrapper = createStatWrapper();
        p1StatsLabel = new JLabel("Waiting...");
        p1StatsLabel.setForeground(Color.CYAN);
        p1StatsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        p1HPLabel = new JLabel("0 / 0");
        p1HPLabel.setForeground(Color.WHITE);
        p1HPLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        p1UltLabel = new JLabel("☐ ☐ ☐");
        p1UltLabel.setForeground(Color.LIGHT_GRAY);
        p1UltLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        
        // --- Status Label ---
        p1StatusLabel = new JLabel("");
        p1StatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // --- UPDATED GRID LAYOUT TO 4 ROWS ---
        JPanel p1Info = new JPanel(new GridLayout(4, 1)); 
        p1Info.setOpaque(false);
        p1Info.add(p1StatsLabel); 
        p1Info.add(p1HPLabel);
        p1Info.add(p1UltLabel);
        p1Info.add(p1StatusLabel); // Add status line
        p1Wrapper.add(p1Info, BorderLayout.NORTH);
        
        p1HealthBar = new JProgressBar(0, 100);
        p1HealthBar.setStringPainted(false);
        p1HealthBar.setForeground(Color.GREEN);
        p1HealthBar.setBackground(Color.DARK_GRAY);
        p1Wrapper.add(p1HealthBar, BorderLayout.CENTER);

        // P2 Stats
        JPanel p2Wrapper = createStatWrapper();
        p2StatsLabel = new JLabel("Waiting...");
        p2StatsLabel.setForeground(Color.RED);
        p2StatsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        p2HPLabel = new JLabel("0 / 0");
        p2HPLabel.setForeground(Color.WHITE);
        p2HPLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        p2UltLabel = new JLabel("☐ ☐ ☐");
        p2UltLabel.setForeground(Color.LIGHT_GRAY);
        p2UltLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        
        // --- Status Label ---
        p2StatusLabel = new JLabel("");
        p2StatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // --- UPDATED GRID LAYOUT TO 4 ROWS ---
        JPanel p2Info = new JPanel(new GridLayout(4, 1)); 
        p2Info.setOpaque(false);
        p2Info.add(p2StatsLabel); 
        p2Info.add(p2HPLabel);
        p2Info.add(p2UltLabel); 
        p2Info.add(p2StatusLabel); // Add status line
        p2Wrapper.add(p2Info, BorderLayout.NORTH);

        p2HealthBar = new JProgressBar(0, 100);
        p2HealthBar.setStringPainted(false);
        p2HealthBar.setForeground(Color.GREEN);
        p2HealthBar.setBackground(Color.DARK_GRAY);
        p2Wrapper.add(p2HealthBar, BorderLayout.CENTER);

        topPanel.add(p1Wrapper);
        topPanel.add(p2Wrapper);
        battlePanel.add(topPanel, BorderLayout.NORTH);

        // ------------------------------------------------
        // 2. CENTER PANEL (ARENA / SPRITES)
        // ------------------------------------------------
        JPanel arenaPanel = new JPanel(new GridLayout(1, 2));
        arenaPanel.setBackground(new Color(50, 50, 50)); 
        arenaPanel.setBorder(new LineBorder(Color.BLACK, 2));

        p1SpriteLabel = new JLabel("P1", SwingConstants.CENTER);
        p2SpriteLabel = new JLabel("P2", SwingConstants.CENTER);

        arenaPanel.add(p1SpriteLabel);
        arenaPanel.add(p2SpriteLabel);

        battlePanel.add(arenaPanel, BorderLayout.CENTER);

        // ------------------------------------------------
        // 3. BOTTOM CONTAINER (LOG + BUTTONS)
        // ------------------------------------------------
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(new Color(30, 30, 30));
        bottomContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        turnIndicator = new JLabel("Preparing Battle...");
        turnIndicator.setFont(new Font("Arial", Font.BOLD, 20));
        turnIndicator.setForeground(Color.YELLOW);
        turnIndicator.setHorizontalAlignment(SwingConstants.CENTER);
        turnIndicator.setBorder(new EmptyBorder(0,0,10,0));
        bottomContainer.add(turnIndicator, BorderLayout.NORTH);

        battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        battleLog.setBackground(new Color(20, 20, 20));
        battleLog.setForeground(Color.WHITE);
        battleLog.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(battleLog);
        scrollPane.setBorder(new LineBorder(Color.GRAY, 1));
        // Adjustable width so resizing works better
        scrollPane.setPreferredSize(new Dimension(700, 120)); 
        bottomContainer.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        btnRoll = createActionButton("Roll Special (1)", new Color(100, 149, 237));
        btnAttack = createActionButton("Basic Attack (2)", new Color(255, 69, 0));
        btnUlt = createActionButton("ULTIMATE (3)", new Color(138, 43, 226));
        btnUlt.setEnabled(false);

        btnRoll.addActionListener(e -> performTurn(1));
        btnAttack.addActionListener(e -> performTurn(2));
        btnUlt.addActionListener(e -> performTurn(3));

        buttonPanel.add(btnRoll);
        buttonPanel.add(btnAttack);
        buttonPanel.add(btnUlt);
        bottomContainer.add(buttonPanel, BorderLayout.SOUTH);

        battlePanel.add(bottomContainer, BorderLayout.SOUTH);

        mainContainer.add(battlePanel, "BATTLE");
    }

    private JPanel createStatWrapper() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        return p;
    }

    private JButton createActionButton(String text, Color normalColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(normalColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        Color pressedColor = darkenColor(normalColor);
        btn.getModel().addChangeListener(e -> {
            if (btn.isEnabled()) {
                btn.setBackground(btn.getModel().isPressed() ? pressedColor : normalColor);
            } else {
                btn.setBackground(Color.GRAY);
            }
        });
        
        btn.setPreferredSize(new Dimension(180, 50));
        return btn;
    }

    // =================================================================
    // GAME START & LOOP LOGIC
    // =================================================================
    
    private void startGame() {
        p1.setOpponent(p2);
        p2.setOpponent(p1);

        currentPlayer = p1;
        currentOpponent = p2;

        p1HealthBar.setMaximum((int)p1.getMaxHealth());
        p1HealthBar.setValue((int)p1.getHealth());
        p2HealthBar.setMaximum((int)p2.getMaxHealth());
        p2HealthBar.setValue((int)p2.getHealth());

        // LOAD SPRITES
        ImageIcon p1Icon = loadCharacterImage(p1NameRaw, 250);
        if (p1Icon != null) p1SpriteLabel.setIcon(p1Icon);
        else p1SpriteLabel.setText("<html><h1>" + p1NameRaw + "</h1></html>");

        ImageIcon p2Icon = loadCharacterImage(p2NameRaw, 250);
        if (p2Icon != null) p2SpriteLabel.setIcon(p2Icon);
        else p2SpriteLabel.setText("<html><h1>" + p2NameRaw + "</h1></html>");
        
        if (p1Icon != null) p1SpriteLabel.setText("");
        if (p2Icon != null) p2SpriteLabel.setText("");

        cardLayout.show(mainContainer, "BATTLE");
        updateUI();

        // --- START MUSIC ---
        playRandomMusic();

        System.out.println("Battle Start! " + p1.getCharacterName() + " vs " + p2.getCharacterName());
    }

    // --- REVISED PERFORM TURN METHOD ---
    private void performTurn(int choice) {
        // Disable buttons immediately
        setControlsEnabled(false);

        // Run Logic in Background Thread
        new Thread(() -> {
            try {
                if (p1 == null || p2 == null || p1.isDead() || p2.isDead()) {
                     SwingUtilities.invokeLater(() -> setControlsEnabled(true));
                     return;
                }

                // 1. SNAPSHOT CURRENT SPEED/SLOW
                // (Assuming PCharacter has getSpeed/getSlow methods)
                int storedSpeed = currentPlayer.getSpeed();
                int storedSlow = currentPlayer.getSlow();

                // 2. RESET EXTRA (Clears Block/Dodge, and also incorrectly clears speed/slow)
                currentPlayer.resetExtra();
                
                // 3. RESTORE SPEED/SLOW
                // Put the values back so they affect the roll or persist
                currentPlayer.setSpeed(storedSpeed);
                currentPlayer.setSlow(storedSlow);

                System.out.println("\n--- " + currentPlayer.getName() + "'s Turn ---");
                boolean turnEnded = false;

                if (currentPlayer.checkCC()) {
                    System.out.println(currentPlayer.getName() + " is stunned!");
                    currentPlayer.cleanseCC();
                    turnEnded = true;
                } else if (currentPlayer.isChanneling()) {
                    currentPlayer.endChannel(); 
                    System.out.println("Channeling finished!");
                    currentPlayer.moveSeven(currentOpponent);
                    // Also reset speed here if channeling consumed the turn
                    currentPlayer.setSpeed(0);
                    currentPlayer.setSlow(0);
                    turnEnded = true;
                }

                if (!turnEnded) {
                    if (choice == 1) {
                        // ROLL SPECIAL
                        // Current speed/slow affects this roll
                        currentPlayer.rollForMove(currentOpponent);
                        
                        // 4. CONSUME SPEED/SLOW
                        // Since we rolled, the modifiers are used up. Reset to 0.
                        currentPlayer.setSpeed(0);
                        currentPlayer.setSlow(0);
                        
                    } else if (choice == 2) {
                        // BASIC ATTACK
                        currentPlayer.autoAttack(currentOpponent);
                        // Do NOT consume speed/slow here.
                        // If this move adds speed (like Jax Q), it keeps it for next turn.
                        // If you had speed and chose to attack, you keep the speed for next turn.
                        
                    } else if (choice == 3) {
                        // ULTIMATE
                        System.out.println(currentPlayer.getName() + " UNLEASHES ULTIMATE!");
                        
                        if (currentPlayer == p1){
                            p1UltCharge = 0;
                            p1.moveSix(p2);
                        }
                        else{
                            p2UltCharge = 0;
                            p2.moveSix(p1);
                        }
                        // Do NOT consume speed/slow here.
                    }
                    currentPlayer.turnIncrease();
                }

                // Update UI on Main Thread
                SwingUtilities.invokeLater(() -> {
                    if (currentOpponent.getHealth() <= 0) {
                        currentOpponent.setDead(true);
                        updateUI();
                        // --- Pass the Character Object to gameOver ---
                        gameOver(currentPlayer);
                    } else {
                        switchTurn();
                        setControlsEnabled(true);
                    }
                });

            } catch (Exception e) {
                System.err.println("Logic Error: " + e.getMessage());
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> setControlsEnabled(true));
            }
        }).start();
    }
    
    public static void addUltChargeTo(PCharacter p) {
        if (p == p1) {
            if (p1UltCharge < 3) {
                p1UltCharge++;
                System.out.println(p1.getName() + " gained an Ult Counter! (" + p1UltCharge + "/3)");
            }
        } else {
            if (p2UltCharge < 3) {
                p2UltCharge++;
                System.out.println(p2.getName() + " gained an Ult Counter! (" + p2UltCharge + "/3)");
            }
        }
    }

    private void switchTurn() {
        if (currentPlayer == p1) {
            currentPlayer = p2;
            currentOpponent = p1;
        } else {
            currentPlayer = p1;
            currentOpponent = p2;
        }
        updateUI();
    }

    private void updateUI() {
        p1HealthBar.setValue((int)p1.getHealth());
        p2HealthBar.setValue((int)p2.getHealth());
        
        p1HPLabel.setText("Health: " + (int)p1.getHealth() + " / " + (int)p1.getMaxHealth());
        p2HPLabel.setText("Health: " + (int)p2.getHealth() + " / " + (int)p2.getMaxHealth());
        
        // --- UPDATED: Display Shield Amount ---
        String p1ShieldTxt = (p1.getShield() > 0) ? " | Shield: " + (int)p1.getShield() : "";
        p1StatsLabel.setText(p1.getName() + " | Stacks: " + (int)p1.getStack() + p1ShieldTxt);

        String p2ShieldTxt = (p2.getShield() > 0) ? " | Shield: " + (int)p2.getShield() : "";
        p2StatsLabel.setText(p2.getName() + " | Stacks: " + (int)p2.getStack() + p2ShieldTxt);
        // --------------------------------------

        // --- UPDATE STATUS VISUALS ---
        updateStatusVisuals(p1StatusLabel, p1);
        updateStatusVisuals(p2StatusLabel, p2);

        updateUltVisuals(p1UltLabel, p1UltCharge);
        updateUltVisuals(p2UltLabel, p2UltCharge);

        turnIndicator.setText("Turn: " + currentPlayer.getName());
        
        if (currentPlayer == p1) {
            p1StatsLabel.setForeground(Color.YELLOW);
            p2StatsLabel.setForeground(Color.GRAY);
            btnUlt.setEnabled(p1UltCharge == 3);
        } else {
            p1StatsLabel.setForeground(Color.GRAY);
            p2StatsLabel.setForeground(Color.YELLOW);
            btnUlt.setEnabled(p2UltCharge == 3);
        }
        
        if (!btnUlt.isEnabled()) btnUlt.setBackground(Color.GRAY);
        else btnUlt.setBackground(new Color(138, 43, 226));
    }
    
    // =================================================================
    // HELPER: UPDATE STATUS LABEL
    // =================================================================
    private void updateStatusVisuals(JLabel label, PCharacter p) {
        if (p == null) return;
        
        // 1. Get Slow/Speed amounts safely (even if PCharacter doesn't have methods yet)
        int slowAmt = p.getSlow();
        int speedAmt = p.getSpeed();

        if (p.checkCC()) { 
            label.setText("⚡ STUNNED");
            label.setForeground(Color.ORANGE);
        } 
        else if (p.isChanneling()) {
            label.setText("✨ CHANNELING");
            label.setForeground(Color.MAGENTA);
        }
        else if (p.getDodge()) {
             label.setText("💨 DODGING");
             label.setForeground(Color.CYAN);
        }
        else if (p.getBlock()) { // Reverted to use standard method
             label.setText("🛡️ BLOCKING");
             label.setForeground(new Color(100, 149, 237)); // Cornflower Blue
        }
        
        // --- STATUS VISUALS ---
        else if (slowAmt > 0) {
             label.setText("🐢 SLOWED (-" + slowAmt + ")");
             label.setForeground(new Color(70, 130, 180)); // Steel Blue
        }
        else if (speedAmt > 0) {
             label.setText("⚡ HASTED (+" + speedAmt + ")");
             label.setForeground(Color.GREEN);
        }
        else {
            label.setText(""); // Clear text if no status
        }
    }
    
    
    private void updateUltVisuals(JLabel label, int charge) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 3; i++) {
            if (i < charge) sb.append("☒ "); 
            else sb.append("☐ ");            
        }
        
        if (charge == 3) {
            label.setText(sb.toString() + " ULTIMATE READY!");
            label.setForeground(Color.RED);
        } else {
            label.setText(sb.toString());
            label.setForeground(Color.LIGHT_GRAY);
        }
    }

    // ---  Return to Selection Screen after Game Over ---
    private void gameOver(PCharacter winner) {
        turnIndicator.setText("GAME OVER! " + winner.getName() + " WINS!");
        btnRoll.setEnabled(false);
        btnAttack.setEnabled(false);
        btnUlt.setEnabled(false);
        
        // Stop the music when game is over
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }

        // Determine which raw image name to load
        String winnerImageName = (winner == p1) ? p1NameRaw : p2NameRaw;
        
        // Load the image
        ImageIcon winnerIcon = loadCharacterImage(winnerImageName, 150);
        
        // Show Message Dialog WITH Image
        JOptionPane.showMessageDialog(this, 
            winner.getName() + " Wins!", 
            "Victory!", 
            JOptionPane.INFORMATION_MESSAGE, 
            winnerIcon);
            
        // --- NEW: Reset and Return to Selection ---
        resetGame();
        cardLayout.show(mainContainer, "SELECTION");
    }
    
    // Helper to clear state for new game
    private void resetGame() {
        p1 = null;
        p2 = null;
        p1NameRaw = null;
        p2NameRaw = null;
        currentPlayer = null;
        currentOpponent = null;
        p1UltCharge = 0;
        p2UltCharge = 0;

        battleLog.setText("");
        p1HealthBar.setValue(0);
        p2HealthBar.setValue(0);
        p1SpriteLabel.setIcon(null);
        p2SpriteLabel.setIcon(null);
        p1StatsLabel.setText("Waiting...");
        p2StatsLabel.setText("Waiting...");
        p1StatusLabel.setText("");
        p2StatusLabel.setText("");
        p1UltLabel.setText("☐ ☐ ☐");
        p2UltLabel.setText("☐ ☐ ☐");
        
        btnRoll.setEnabled(true);
        btnAttack.setEnabled(true);
        btnUlt.setEnabled(false);
        
        selectionInstruction.setText("Player 1: Select Your Character");
        selectionInstruction.setForeground(Color.CYAN);
    }
    
    private void setControlsEnabled(boolean enabled) {
        btnRoll.setEnabled(enabled);
        btnAttack.setEnabled(enabled);
        if (enabled) updateUI();
        else btnUlt.setEnabled(false);
    }

    // --- SYSTEM.OUT REDIRECTION ---
    private void redirectSystemOut() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateTextPane(String.valueOf((char) b));
            }
            @Override
            public void write(byte[] b, int off, int len) {
                updateTextPane(new String(b, off, len));
            }
            @Override
            public void write(byte[] b) {
                write(b, 0, b.length);
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    private void updateTextPane(final String text) {
        SwingUtilities.invokeLater(() -> {
            if (battleLog != null) {
                battleLog.append(text);
                battleLog.setCaretPosition(battleLog.getDocument().getLength());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI());
    }
}