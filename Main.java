import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main implements Runnable, ActionListener{

  // Determines the colour of a box. This is used to determine to winner of the game.
  public static String horiCurrentCol(int number, JButton[] group) {
    String answer = "undefined";
    if (group[number].getBackground().equals(Color.WHITE)) {
      answer = "white";
    } else if (group[number].getBackground().equals(Color.RED)) {
      answer = "red";
    } else if (group[number].getBackground().equals(Color.YELLOW)) {
      answer = "yellow";
    }
    return answer;
  }

  // Places the coin in the group that the user has clicked on. Gravity is considered.
  public static void place(int number, String turn, JButton[] group) {
    // Find out what colour's turn it is, to put in the right colour coin.
    if (turn == "Yellow") {
      group[number].setBackground(Color.YELLOW);
      ImageIcon imageIcon = new ImageIcon("yellow.png"); 
      Image image = imageIcon.getImage(); 
      Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg);
      group[number].setIcon(imageIcon);
    } else if (turn == "Red") {
      group[number].setBackground(Color.RED);
      ImageIcon imageIcon = new ImageIcon("red.png"); 
      Image image = imageIcon.getImage(); 
      Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
      imageIcon = new ImageIcon(newimg);
      group[number].setIcon(imageIcon);
    }
  }

  // Determines the colour of a box. This is used to determine to winner of the game.
  public static String boxColour(int number, JButton[] group) {
    String answer = "undefined";
    if (number < 0) {
      return answer;
    } else if (group[number] == null) {
      return answer;
    } 
    if (group[number].getBackground().equals(Color.RED)) {
      answer = "red";
    } else if (group[number].getBackground().equals(Color.YELLOW)) {
      answer = "yellow";
    }
    return answer;
  }

  // This checks if there are 4 same colour coins in a row horizontally.
  public static String horizontalCheck(String[] row) {
    String answer = "undefined";
    for (int i = 0; i < 7; i++) {
        if (!row[i].equals("white")) {
          if (!row[i].equals("undefined")) {
            if (row[i].equals(row[i+1])) {
              if (row[i+1].equals(row[i+2])) {
                if (row[i+2].equals(row[i+3])) {
                  answer = row[i];
              }
            }
          }   
        }
      }
    }
    return answer;
  }

  // This checks if there are 4 same colour coins in a row horizontally.
  public void winner(String colour, boolean auto) {
    String winner = "undefined";
    if (colour.toLowerCase().equals("red")) {
      if (player1Name.getText().length() > 0 && player1Name.getText().length() < 7) {
        winner = player1Name.getText();
      } else {
        winner = colour.toUpperCase();
      }
    } else if (colour.toLowerCase().equals("yellow")) {
      if (auto == false) {
        if (player2Name.getText().length() > 0 && player2Name.getText().length() < 7) {
          winner = player2Name.getText();
        } else {
          winner = colour.toUpperCase();
        }
      } else {
        winner = "computer";
      }
    } 
    if (colour.toLowerCase().equals("red")) {
      instructions.setText(winner.toUpperCase() + " WON!");
    } else if (colour.toLowerCase().equals("yellow")) {
      instructions.setText(winner.toUpperCase() + " WON!");
    } else if (colour.toLowerCase().equals("draw")) {
      instructions.setText("DRAW!");
      instructions.setBounds(120, 37, 500, 75);
    }

    if (winner.equals("yellow")) {
      instructions.setBounds(84, 37, 500, 75);
    } else if (winner.equals("red")) {
      instructions.setBounds(128, 37, 500, 75);
    } else if (winner.length() < 4) {
      instructions.setBounds(128, 37, 500, 75);
    } else if (winner.length() > 4 && winner.length() < 7) {
      instructions.setBounds(90, 37, 500, 75);
    } else if (winner.length() > 6) {
      instructions.setBounds(60, 37, 500, 75);
    }
    gameover = true;
    instructions.setVisible(true);
    instructions.setForeground(Color.BLACK);
    restart.setVisible(true);
  }

  // Class Variables  
  JButton[] group1 = new JButton[10];
  JButton[] group2 = new JButton[10];
  JButton[] group3 = new JButton[10];
  JButton[] group4 = new JButton[10];
  JButton[] group5 = new JButton[10];
  JButton[] group6 = new JButton[10];
  JButton[] group7 = new JButton[10];
  JTextField instructions;
  JButton mainLogo;
  Font buttonFont;
  Font instructionFont;
  JButton menuBg;
  JButton restart;
  JButton redPfp;
  JButton yellowPfp;
  JButton redGlow;
  JButton yellowGlow;
  JButton forfeit;
  JButton onePlayer = new JButton();
  JButton twoPlayer = new JButton();
  JButton settings;
  JTextField enterPlayer1;
  JTextField enterPlayer2;
  JTextField player1Name;
  JTextField player2Name;
  JTextField maxNameLength;

  // game Variables
  String turn = "Yellow";
  boolean gameover = false;
  boolean menu = true;
  boolean auto = false;
  String groupPlace = "undefined";
  boolean run = true;
  int chooseChar = 0;

  // Method to assemble our GUI
  public void run(){
    // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click on the X
    JFrame frame = new JFrame("Connect 4");
    // Makes the X button close the program
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // makes the windows 800 pixel wide by 600 pixels tall
    frame.setSize(500,600);
    // shows the window
    frame.setVisible(true);
 
    // Create the main panel for the game
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(null);

    // Create the fonts for the instructions (name, style, size)
    instructionFont = new Font("Calibri", Font.BOLD, 40);

    // creating the instructions textfield
    instructions = new JTextField();
    instructions.setBounds(84, 37, 500, 75);
    instructions.setFont(instructionFont);
    instructions.setForeground(Color.RED);
    instructions.setVisible(false);
    instructions.setBorder(null);
    instructions.setOpaque(false);

    // instructions.setBounds(128, 37, 500, 75);
    // instructions.setBounds(84, 37, 500, 75);

    // Build the image which is used to indicate an empty box in the game. (no coin)
    ImageIcon imageBg = new ImageIcon("default.png"); 
    Image image = imageBg.getImage(); 
    Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
    imageBg = new ImageIcon(newimg);

    // Create all the buttons for group 1
    for (int i = 0; i < 6; i++) {
      group1[i] = new JButton();
      group1[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group1[i].setBounds(x + 75, y + 125, 50, 50);
      // add action command and listener 
      group1[i].addActionListener(this);
      group1[i].setActionCommand("groupOne-" + i);
      group1[i].setFocusable(false);
      group1[i].setBorder(null);
      group1[i].setIcon(imageBg);
      group1[i].setVisible(false);
      group1[i].setOpaque(false);
      group1[i].setContentAreaFilled(false);
      group1[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group1[i]);
    }
    // Create all the buttons for group 2
    for (int i = 0; i < 6; i++) {
      group2[i] = new JButton();
      group2[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group2[i].setBounds(x + 125, y + 125, 50, 50);
      // add action command and listener 
      group2[i].addActionListener(this);
      group2[i].setActionCommand("groupTwo-" + i);
      group2[i].setFocusable(false);
      group2[i].setBorder(null);
      group2[i].setIcon(imageBg);
      group2[i].setVisible(false);
      group2[i].setOpaque(false);
      group2[i].setContentAreaFilled(false);
      group2[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group2[i]);
    }
    // Create all the buttons for group 3
    for (int i = 0; i < 6; i++) {
      group3[i] = new JButton();
      group3[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group3[i].setBounds(x + 175, y + 125, 50, 50);
      // add action command and listener 
      group3[i].addActionListener(this);
      group3[i].setActionCommand("groupThree-" + i);
      group3[i].setFocusable(false);
      group3[i].setBorder(null);
      group3[i].setIcon(imageBg);
      group3[i].setVisible(false);
      group3[i].setOpaque(false);
      group3[i].setContentAreaFilled(false);
      group3[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group3[i]);
    }
    // Create all the buttons for group 4
    for (int i = 0; i < 6; i++) {
      group4[i] = new JButton();
      group4[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group4[i].setBounds(x + 225, y + 125, 50, 50);
      // add action command and listener 
      group4[i].addActionListener(this);
      group4[i].setActionCommand("groupFour-" + i);
      group4[i].setFocusable(false);
      group4[i].setBorder(null);
      group4[i].setIcon(imageBg);
      group4[i].setVisible(false);
      group4[i].setOpaque(false);
      group4[i].setContentAreaFilled(false);
      group4[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group4[i]);
    }
    // Create all the buttons for group 5
    for (int i = 0; i < 6; i++) {
      group5[i] = new JButton();
      group5[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group5[i].setBounds(x + 275, y + 125, 50, 50);
      // add action command and listener 
      group5[i].addActionListener(this);
      group5[i].setActionCommand("groupFive-" + i);
      group5[i].setFocusable(false);
      group5[i].setBorder(null);
      group5[i].setIcon(imageBg);
      group5[i].setVisible(false);
      group5[i].setOpaque(false);
      group5[i].setContentAreaFilled(false);
      group5[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group5[i]);
    }
    // Create all the buttons for group 6
    for (int i = 0; i < 6; i++) {
      group6[i] = new JButton();
      group6[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group6[i].setBounds(x + 325, y + 125, 50, 50);
      // add action command and listener 
      group6[i].addActionListener(this);
      group6[i].setActionCommand("groupSix-" + i);
      group6[i].setFocusable(false);
      group6[i].setBorder(null);
      group6[i].setIcon(imageBg);
      group6[i].setVisible(false);
      group6[i].setOpaque(false);
      group6[i].setContentAreaFilled(false);
      group6[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group6[i]);
    }
    // Create all the buttons for group 7
    for (int i = 0; i < 6; i++) {
      group7[i] = new JButton();
      group7[i].setBackground(Color.WHITE);
      // Determine where the buttons should go
      int x = (i%1)*50;
      int y = (i/1)*50;
      group7[i].setBounds(x + 375, y + 125, 50, 50);
      // add action command and listener 
      group7[i].addActionListener(this);
      group7[i].setActionCommand("groupSeven-" + i);
      group7[i].setFocusable(false);
      group7[i].setBorder(null);
      group7[i].setIcon(imageBg);
      group7[i].setVisible(false);
      group7[i].setOpaque(false);
      group7[i].setContentAreaFilled(false);
      group7[i].setBorderPainted(false);
      // add to panel 
      mainPanel.add(group7[i]);
    }

    // Build the image which is used as a button to forfeit the game.
    ImageIcon forfeitImg = new ImageIcon("forfeit.png"); 
    Image imageForfeit = forfeitImg.getImage(); 
    Image newForfeit = imageForfeit.getScaledInstance(84, 32,  java.awt.Image.SCALE_SMOOTH);
    forfeitImg = new ImageIcon(newForfeit);
    // Creating the forfeit button.
    forfeit = new JButton(forfeitImg);
    forfeit.setBackground(Color.WHITE);
    forfeit.setBounds(410, 4, 84, 32);
    forfeit.setForeground(Color.RED);
    forfeit.setBorder(null);
    forfeit.setOpaque(false);
    forfeit.setVisible(false);
    forfeit.addActionListener(this);
    forfeit.setActionCommand("forfeit");

    // Build the image which is used to select singleplayer in the menu.
    ImageIcon player1img = new ImageIcon("1player.png"); 
    Image imagee = player1img.getImage(); 
    Image newimge = imagee.getScaledInstance(192, 73,  java.awt.Image.SCALE_SMOOTH);
    player1img = new ImageIcon(newimge);
    // Creating the singleplayer button.
    onePlayer = new JButton(player1img);
    onePlayer.setBackground(Color.WHITE);
    onePlayer.setBounds(50, 275, 192, 73);
    onePlayer.setForeground(Color.RED);
    onePlayer.setBorder(null);
    onePlayer.setOpaque(false);
    onePlayer.addActionListener(this);
    onePlayer.setActionCommand("1player");

    // Build the image which is used to select multiplayer in the menu.
    ImageIcon player2img = new ImageIcon("2player.png"); 
    Image imageee = player2img.getImage(); 
    Image newimgee = imageee.getScaledInstance(192, 73,  java.awt.Image.SCALE_SMOOTH);
    player2img = new ImageIcon(newimgee);
    // Creating the multiplayer button.
    twoPlayer = new JButton(player2img);
    twoPlayer.setBackground(Color.WHITE);
    twoPlayer.setBounds(265, 275, 192, 73);
    twoPlayer.setForeground(Color.RED);
    twoPlayer.setBorder(null);
    twoPlayer.setOpaque(false);
    twoPlayer.addActionListener(this);
    twoPlayer.setActionCommand("2player");

    // Build the image which is used in the main menu as a logo.
    ImageIcon logo = new ImageIcon("icon.png"); 
    Image logCheck = logo.getImage(); 
    Image newLogo = logCheck.getScaledInstance(401, 128,  java.awt.Image.SCALE_SMOOTH);
    logo = new ImageIcon(newLogo);
    // Creating the logo image.
    mainLogo = new JButton(logo);
    mainLogo.setBounds(45, 60, 401, 128);
    mainLogo.setBorder(null);
    mainLogo.setOpaque(false);
    mainLogo.setContentAreaFilled(false);
    mainLogo.setBorderPainted(false);

    // Build the image which is used as the background of this game.
    ImageIcon menuBgg = new ImageIcon("bg.jpg"); 
    Image bgCheck = menuBgg.getImage(); 
    Image newBg = bgCheck.getScaledInstance(626, 626,  java.awt.Image.SCALE_SMOOTH);
    menuBgg = new ImageIcon(newBg);
    // Creating the background image.
    menuBg = new JButton(menuBgg);
    menuBg.setBounds(0, 0, 626, 626);
    menuBg.setBorder(null);
    menuBg.setOpaque(false);
    menuBg.setContentAreaFilled(false);
    menuBg.setBorderPainted(false);

    // Build the image which is used to reset the game.
    ImageIcon resetBtn = new ImageIcon("reset.png"); 
    Image resetCheck = resetBtn.getImage(); 
    Image newReset = resetCheck.getScaledInstance(200, 77,  java.awt.Image.SCALE_SMOOTH);
    resetBtn = new ImageIcon(newReset);
    // Creating the reset button.
    restart = new JButton(resetBtn);
    restart.setBackground(Color.WHITE);
    restart.setBorder(null);
    restart.setBounds(150, 475, 200, 77);
    restart.addActionListener(this);
    restart.setActionCommand("reset");
    restart.setVisible(false);
    restart.setOpaque(false);
    restart.setContentAreaFilled(false);
    restart.setBorderPainted(false);

    // Build the image which is used to change the settigns of the game.
    ImageIcon settingBtn = new ImageIcon("settings.png"); 
    Image settingsCheck = settingBtn.getImage(); 
    Image newSettings = settingsCheck.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
    settingBtn = new ImageIcon(newSettings);
    // Creating the reset button.
    settings = new JButton(settingBtn);
    settings.setBackground(Color.WHITE);
    settings.setBorder(null);
    settings.setBounds(0, 0, 30, 30);
    settings.addActionListener(this);
    settings.setActionCommand("settings");
    settings.setOpaque(false);
    settings.setContentAreaFilled(false);
    settings.setBorderPainted(false);

    // Creating the player red image.
    redPfp = new JButton();
    redPfp.setBorder(null);
    redPfp.setBounds(10, 450, 67, 220);
    redPfp.setOpaque(false);
    redPfp.setContentAreaFilled(false);
    redPfp.setBorderPainted(false);

    // Creating the player yellow image.
    yellowPfp = new JButton();
    yellowPfp.setBorder(null);
    yellowPfp.setBounds(425, 450, 67, 220);
    yellowPfp.setOpaque(false);
    yellowPfp.setContentAreaFilled(false);
    yellowPfp.setBorderPainted(false);

    // Creating the glow behind player red.
    ImageIcon glowRed = new ImageIcon("redGlow.png"); 
    Image glowRedCheck = glowRed.getImage(); 
    Image redr = glowRedCheck.getScaledInstance(233, 203,  java.awt.Image.SCALE_SMOOTH);
    glowRed = new ImageIcon(redr);
    redGlow = new JButton(glowRed);
    redGlow.setBorder(null);
    redGlow.setBounds(-75, 450, 233, 203);
    redGlow.setOpaque(false);
    redGlow.setVisible(false);
    redGlow.setContentAreaFilled(false);
    redGlow.setBorderPainted(false);

    // Creating the glow behind player yellow.
    ImageIcon glowYellow = new ImageIcon("yellowGlow.png"); 
    Image glowYellowCheck = glowYellow.getImage(); 
    Image rer = glowYellowCheck.getScaledInstance(233, 203,  java.awt.Image.SCALE_SMOOTH);
    glowYellow = new ImageIcon(rer);
    yellowGlow = new JButton(glowYellow);
    yellowGlow.setBorder(null);
    yellowGlow.setBounds(350, 450, 233, 203);
    yellowGlow.setOpaque(false);
    yellowGlow.setVisible(false);
    yellowGlow.setContentAreaFilled(false);
    yellowGlow.setBorderPainted(false);

    // Creating the text that tells the user what the textfield is.
    enterPlayer1 = new JTextField("Enter a username for Player 1");
    enterPlayer1.setBounds(40, 260, 250, 30);
    enterPlayer1.setBorder(null);
    enterPlayer1.setOpaque(false);
    enterPlayer1.setForeground(Color.BLACK);
    enterPlayer1.setVisible(false);

    // Creating the text that tells the user what the textfield is.
    enterPlayer2 = new JTextField("Enter a username for Player 2");
    enterPlayer2.setBounds(280, 260, 250, 30);
    enterPlayer2.setBorder(null);
    enterPlayer2.setOpaque(false);
    enterPlayer2.setForeground(Color.BLACK);
    enterPlayer2.setVisible(false);

    // Creating textfield where the user can enter their name which will be displaced instead of the generic names. (red, player 1)
    player1Name = new JTextField();
    player1Name.setBounds(80, 300, 100, 30);
    player1Name.setVisible(false);

    // Creating textfield where the user can enter their name which will be displaced instead of the generic names. (yellow,player 2)
    player2Name = new JTextField();
    player2Name.setBounds(320, 300, 100, 30);
    player2Name.setVisible(false);

    // Creating the text that tells the user that they can only enter up to 6 letters in the TextField.
    maxNameLength = new JTextField("Max of 6 letters!");
    maxNameLength.setBounds(64, 200, 400, 50);
    maxNameLength.setForeground(Color.BLACK);
    maxNameLength.setBorder(null);
    maxNameLength.setOpaque(false);
    maxNameLength.setFont(instructionFont);
    maxNameLength.setVisible(false);
   
    // add the components to the panel
    mainPanel.add(maxNameLength);
    mainPanel.add(enterPlayer1);
    mainPanel.add(enterPlayer2);
    mainPanel.add(player1Name);
    mainPanel.add(player2Name); 
    mainPanel.add(settings);
    mainPanel.add(forfeit);
    mainPanel.add(redPfp);
    mainPanel.add(yellowPfp); 
    mainPanel.add(redGlow);
    mainPanel.add(yellowGlow);
    mainPanel.add(restart);
    mainPanel.add(instructions);
    mainPanel.add(onePlayer);
    mainPanel.add(twoPlayer);
    mainPanel.add(mainLogo);
    mainPanel.add(menuBg);

    // add the panel to the frame 
    frame.add(mainPanel);
  }
  
  // method called when a button is pressed
  public void actionPerformed(ActionEvent e){
    run = true;

    // When run is true, code runs. 
    while (run) {
    // get the command from the action
    String command = e.getActionCommand();

    // If the user presses the settings button, bring up the settings menu.
    if (command.equals("settings")) {
      if (mainLogo.isVisible() == false) {
        enterPlayer1.setVisible(false);
        enterPlayer2.setVisible(false);
        player1Name.setVisible(false);
        player2Name.setVisible(false);
        onePlayer.setVisible(true);
        twoPlayer.setVisible(true);
        mainLogo.setVisible(true);
        maxNameLength.setVisible(false);
      } else {
        enterPlayer1.setVisible(true);
        enterPlayer2.setVisible(true);
        player1Name.setVisible(true);
        player2Name.setVisible(true);
        onePlayer.setVisible(false);
        twoPlayer.setVisible(false);
        mainLogo.setVisible(false);
        maxNameLength.setVisible(true);
      }
      return;
    }

    // Building the image for each possible character.
    ImageIcon char1 = new ImageIcon("character1.png"); 
    Image char1Check = char1.getImage(); 
    Image newChar1 = char1Check.getScaledInstance(67, 220,   java.awt.Image.SCALE_SMOOTH);
    char1 = new ImageIcon(newChar1);
    ImageIcon char2 = new ImageIcon("character2.png"); 
    Image char2Check = char2.getImage(); 
    Image newChar2 = char2Check.getScaledInstance(67, 220,    java.awt.Image.SCALE_SMOOTH);
    char2 = new ImageIcon(newChar2);
    ImageIcon char3 = new ImageIcon("character3.png"); 
    Image char3Check = char3.getImage(); 
    Image newChar3 = char3Check.getScaledInstance(67, 220,    java.awt.Image.SCALE_SMOOTH);
    char3 = new ImageIcon(newChar3);
    ImageIcon char4 = new ImageIcon("character4.png"); 
    Image char4Check = char4.getImage(); 
    Image newChar4 = char4Check.getScaledInstance(67, 220,    java.awt.Image.SCALE_SMOOTH);
    char4 = new ImageIcon(newChar4);
    ImageIcon char5 = new ImageIcon("character5.png"); 
    Image char5Check = char5.getImage(); 
    Image newChar5 = char5Check.getScaledInstance(67, 220,    java.awt.Image.SCALE_SMOOTH);
    char5 = new ImageIcon(newChar5);
    ImageIcon char6= new ImageIcon("character6.png"); 
    Image char6Check = char6.getImage(); 
    Image newChar6 = char6Check.getScaledInstance(67, 220,    java.awt.Image.SCALE_SMOOTH);
    char6 = new ImageIcon(newChar6);
    // Determine a random character for each player.
    if (chooseChar == 0) {
      // Generate random number between 0-1
      double num = Math.random();
      if (num <= 0.167) {
        redPfp.setIcon(char1);
        yellowPfp.setIcon(char2);
      } else if (num > 0.167 && num <= 0.33) {
        redPfp.setIcon(char2);
        yellowPfp.setIcon(char3);
      } else if (num > 0.33 && num <= 0.5) {
        redPfp.setIcon(char3);
        yellowPfp.setIcon(char4);
      } else if (num > 0.5 && num < 0.667) {
        redPfp.setIcon(char4);
        yellowPfp.setIcon(char5);
      } else if (num >= 0.667 && num < 0.83) {
        redPfp.setIcon(char5);
        yellowPfp.setIcon(char6);
      } if (num >= 0.83) {
        redPfp.setIcon(char6);
        yellowPfp.setIcon(char1);
      }
      // Prevent this to run again until next game.
      chooseChar++;
    }

    // Build the image which is used to indicate an empty box in the game. (no coin)
    ImageIcon imageBg = new ImageIcon("default.png"); 
    Image image = imageBg.getImage(); 
    Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
    imageBg = new ImageIcon(newimg);

    // Executes once one of the player presses the forfeit button
    if (command.equals("forfeit")) {
      // If it was yellow's turn, then yellow forfeit
      if (turn.equals("Yellow")) {
        winner("red", auto);
      } 
      // If it was red's turn, then red forfeit 
      if (turn.equals("Red")) {
        winner("yellow", auto);
      }
    }

    // Executes once one of the player presses the reset button
    if (command.equals("reset")) {
      // Sets everything to default settings + brings the graphics to the menu screen.
      restart.setVisible(false);
      onePlayer.setVisible(true);
      twoPlayer.setVisible(true);
      mainLogo.setVisible(true);
      gameover = false;
      turn = "Yellow";
      menu = true;
      auto = false;
      groupPlace = "undefined";
      run = true;
      chooseChar = 0;
      yellowGlow.setVisible(false);
      redGlow.setVisible(false);
      yellowPfp.setVisible(false);
      redPfp.setVisible(false);
      instructions.setVisible(false);
      forfeit.setVisible(false);
      settings.setVisible(true);
      for (int i = 0; i < 6; i++) {
        group1[i].setVisible(false);
        group2[i].setVisible(false);
        group3[i].setVisible(false);
        group4[i].setVisible(false);
        group5[i].setVisible(false);
        group6[i].setVisible(false);
        group7[i].setVisible(false);
        group1[i].setIcon(imageBg);
        group2[i].setIcon(imageBg);
        group3[i].setIcon(imageBg);
        group4[i].setIcon(imageBg);
        group5[i].setIcon(imageBg);
        group6[i].setIcon(imageBg);
        group7[i].setIcon(imageBg);
        group1[i].setBackground(Color.WHITE);
        group2[i].setBackground(Color.WHITE);
        group3[i].setBackground(Color.WHITE);
        group4[i].setBackground(Color.WHITE);
        group5[i].setBackground(Color.WHITE);
        group6[i].setBackground(Color.WHITE);
        group7[i].setBackground(Color.WHITE);
      }
      return;
    }

    // checks what the user chooses in the main menu
    if (command.equals("2player")) {
      menu = false;
    } else if (command.equals("1player")) {
      menu = false;
      auto = true;
    }

    // Check if the game is over 
    if (gameover == true) {
      return;
    }

    // If the player made their choice in the menu, change the graphics to in-game.
    if (!menu) {
      onePlayer.setVisible(false);
      twoPlayer.setVisible(false);
      mainLogo.setVisible(false);
      redGlow.setVisible(true);
      yellowPfp.setVisible(true);
      redPfp.setVisible(true);
      forfeit.setVisible(true);
      settings.setVisible(false);
      for (int i = 0; i < 6; i++) {
        group1[i].setVisible(true);
        group2[i].setVisible(true);
        group3[i].setVisible(true);
        group4[i].setVisible(true);
        group5[i].setVisible(true);
        group6[i].setVisible(true);
        group7[i].setVisible(true);
      }
    } else {
      return;
    }

    // Find what group the user has pressed in
    String Strgroup = command.substring(0, command.length()-2);
    
    // Define a variable in int for what group the user has pressed on
    int group = 0;
    // If singleplayer, let's the bot do it's turn.
    if (auto) {
      if (turn.equals("Yellow")) {
        if (!groupPlace.equals("undefined")) {
          Strgroup = groupPlace;
        }
      }
    } 
    if (Strgroup.equals("groupOne")) {
      group = 1;
    } else if (Strgroup.equals("groupTwo")) {
      group = 2;
    } else if (Strgroup.equals("groupThree")) {
      group = 3;
    } else if (Strgroup.equals("groupFour")) {
      group = 4;
    } else if (Strgroup.equals("groupFive")) {
      group = 5;
    } else if (Strgroup.equals("groupSix")) {
      group = 6;
    } else if (Strgroup.equals("groupSeven")) {
      group = 7;
    }

    // Check if the group isn't fully filled already, if it is, don't accept the input
    // Decide wether the AI can place the coin there, having in mind that a group could be filled up.
    boolean checkAutoInput = true;
    while (checkAutoInput) {
      if (!auto) {
        checkAutoInput = false;
      }
      if (turn.equals("Red")) {
        checkAutoInput = false;
      }
      if (group == 0) {
        checkAutoInput = false;
      }
      if (group == 1) {
        if (!group1[0].getBackground().equals(Color.WHITE)) {
          if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      } else if (group == 2) {
        if (!group2[0].getBackground().equals(Color.WHITE)) {
          if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      } else if (group == 3) {
        if (!group3[0].getBackground().equals(Color.WHITE)) {
          if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      } else if (group == 4) {
        if (!group4[0].getBackground().equals(Color.WHITE)) {
          if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      } else if (group == 5) {
        if (!group5[0].getBackground().equals(Color.WHITE)) {
          if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      } else if (group == 6) {
      if (!group6[0].getBackground().equals(Color.WHITE)) {
        if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      } else if (group == 7) {
      if (!group7[0].getBackground().equals(Color.WHITE)) {
        if (auto) {
            if (turn.equals("Yellow")) {
              double num = Math.random();
              if (num < 0.5) {
                group++;
              } else {
                group--;
              }
            } else {
              return;
            }
          } else {
            return;
          }
        } else {
          checkAutoInput = false;
        }
      }
    }

    // A for loop that will run from the bottom of the boxes until it finds a empty box in the group and place the coin in there.
    for (int i = 5; i > -1; i--) {
      if (group1[i].getBackground().equals(Color.WHITE)) {
        if (group == 1) {
          place(i, turn, group1);
          break;
        }
      } 
      if (group2[i].getBackground().equals(Color.WHITE)) {
        if (group == 2) {
          place(i, turn, group2);
          break;
        }
      }
      if (group3[i].getBackground().equals(Color.WHITE)) {
        if (group == 3) {
          place(i, turn, group3);
          break;
        }
      }
      if (group4[i].getBackground().equals(Color.WHITE)) {
        if (group == 4) {
          place(i, turn, group4);
          break;
        }
      }
      if (group5[i].getBackground().equals(Color.WHITE)) {
        if (group == 5) {
          place(i, turn, group5);
          break;
        }
      }
      if (group6[i].getBackground().equals(Color.WHITE)) {
        if (group == 6) {
          place(i, turn, group6);
          break;
        }
      }
      if (group7[i].getBackground().equals(Color.WHITE)) {
        if (group == 7) {
          place(i, turn, group7);
          break;
        }
      }
    }

    // Make arrays for each rows of the game holding the colour value.
    String[] row0 = new String[10];
    String[] row1 = new String[10];
    String[] row2 = new String[10];
    String[] row3 = new String[10];
    String[] row4 = new String[10];
    String[] row5 = new String[10];
    JButton[] accGroup = new JButton[10];
    for (int i = 0; i <= 6; i++) {
      if (i == 0) {
        accGroup = group1;
      } else if (i == 1) {
        accGroup = group2;
      } else if (i == 2) {
        accGroup = group3;
      } else if (i == 3) {
        accGroup = group4;
      } else if (i == 4) {
        accGroup = group5;
      } else if (i == 5) {
        accGroup = group6;
      } else if (i == 6) {
        accGroup = group7;
      }
      row0[i] = boxColour(0, accGroup);
      row1[i] = boxColour(1, accGroup);
      row2[i] = boxColour(2, accGroup);
      row3[i] = boxColour(3, accGroup);
      row4[i] = boxColour(4, accGroup);
      row5[i] = boxColour(5, accGroup);
    }
    // for (int i = 0; i < 7; i++) {
    //   System.out.println(row5[i]);
    // }

    // Detect 4 in a row horizontal and end the game if found for each row in the board.
    if (!horizontalCheck(row5).equals("undefined")) {
      if (horizontalCheck(row5).equals("red")) {
        winner("red", auto);
      } else if (horizontalCheck(row5).equals("yellow")) {
        winner("yellow", auto);
      }
    }
    if (!horizontalCheck(row4).equals("undefined")) {
      if (horizontalCheck(row4).equals("red")) {
        winner("red", auto);
      } else if (horizontalCheck(row4).equals("yellow")) {
        winner("yellow", auto);
      }
    }
    if (!horizontalCheck(row3).equals("undefined")) {
      if (horizontalCheck(row3).equals("red")) {
        winner("red", auto);
      } else if (horizontalCheck(row3).equals("yellow")) {
        winner("yellow", auto);
      }
    }
    if (!horizontalCheck(row2).equals("undefined")) {
      if (horizontalCheck(row2).equals("red")) {
        winner("red", auto);
      } else if (horizontalCheck(row2).equals("yellow")) {
        winner("yellow", auto);
      }
    }
    if (!horizontalCheck(row1).equals("undefined")) {
      if (horizontalCheck(row1).equals("red")) {
        winner("red", auto);
      } else if (horizontalCheck(row1).equals("yellow")) {
        winner("yellow", auto);
      }
    }
    if (!horizontalCheck(row0).equals("undefined")) {
      if (horizontalCheck(row0).equals("red")) {
        winner("red", auto);
      } else if (horizontalCheck(row0).equals("yellow")) {
        winner("yellow", auto);
      }
    }

    // Detect 4 in a row vertically and end the game if found.
    String[] colour = new String[10];
    for (int i = 0; i < 6; i++) {
      String currentColour = "undefined";
      for (int j = 0; j < 6; j++) {
      }
      if (group == 1) {
        currentColour = horiCurrentCol(i, group1);
      }
      if (group == 2) {
        currentColour = horiCurrentCol(i, group2);
      }
      if (group == 3) {
        currentColour = horiCurrentCol(i, group3);
      }
      if (group == 4) {
        currentColour = horiCurrentCol(i, group4);
      }
      if (group == 5) {
        currentColour = horiCurrentCol(i, group5);
      }
      if (group == 6) {
        currentColour = horiCurrentCol(i, group6);
      }
      if (group == 7) {
        currentColour = horiCurrentCol(i, group7);
      }
      colour[i] = currentColour;
    }
    for (int i = 0; i < 6; i++) {
      if (colour[i].equals(colour[i+1])) {
        if (colour[i+1].equals(colour[i+2])) {
          if (colour[i+2].equals(colour[i+3])) {
            if (!colour[i].equals("white")) {
              if (!colour[i].equals("undefined")) {
                winner(colour[i], auto);
              }
            }
          }
        }   
      }
    }

    // Find 4 in a row in slants and end the game 
    JButton[] behindGroup = new JButton[10];
    JButton[] behindGroup1 = new JButton[10];
    JButton[] behindGroup2 = new JButton[10];
    JButton[] onGroup = new JButton[10];
    JButton[] infrontGroup = new JButton[10];
    JButton[] infrontGroup1 = new JButton[10];
    JButton[] infrontGroup2 = new JButton[10];
    JButton[] blank = new JButton[10];
    for (int i = 0; i < 7; i++) {
      if (i == 0) {
        behindGroup2 = blank;
        behindGroup1 = blank;
        behindGroup = blank;
        onGroup = group1;
        infrontGroup = group2;
        infrontGroup1 = group3;
        infrontGroup2 = group4;
      } else if (i == 1) {
        behindGroup2 = blank;
        behindGroup1 = blank;
        behindGroup = group1;
        onGroup = group2;
        infrontGroup = group3;
        infrontGroup1 = group4;
        infrontGroup2 = group5;
      } else if (i == 2) {
        behindGroup2 = blank;
        behindGroup1 = group1;
        behindGroup = group2;
        onGroup = group3;
        infrontGroup = group4;
        infrontGroup1 = group5;
        infrontGroup2 = group6;
      } else if (i == 3) {
        behindGroup2 = group1;
        behindGroup1 = group2;
        behindGroup = group3;
        onGroup = group4;
        infrontGroup = group5;
        infrontGroup1 = group6;
        infrontGroup2 = group7;
      } else if (i == 4) {
        behindGroup2 = group2;
        behindGroup1 = group3;
        behindGroup = group4;
        onGroup = group5;
        infrontGroup = group6;
        infrontGroup1 = group7;
        infrontGroup2 = blank;
      } else if (i == 5) {
        behindGroup2 = group3;
        behindGroup1 = group4;
        behindGroup = group5;
        onGroup = group6;
        infrontGroup = group7;
        infrontGroup1 = blank;
        infrontGroup2 = blank;
      } else if (i == 6) {
        behindGroup2 = group4;
        behindGroup1 = group5;
        behindGroup = group6;
        onGroup = group7;
        infrontGroup = blank;
        infrontGroup1 = blank;
        infrontGroup2 = blank;
      }
      for (int j = 0; j < 6; j++) {
        if (boxColour(j, onGroup).equals(boxColour(j+1, infrontGroup))) {
          if (boxColour(j+1, infrontGroup).equals(boxColour(j+2, infrontGroup1))) {
            if (boxColour(j+2, infrontGroup1).equals(boxColour(j+3, infrontGroup2))) {
              if (!boxColour(j, onGroup).equals("undefined")) {
                if (boxColour(j, onGroup).equals(boxColour(j+3, infrontGroup2))) {
                  winner(boxColour(j, onGroup), auto);
                }
              }
            }
          }
        } else if (boxColour(j, onGroup).equals(boxColour(j+1, behindGroup))) {
          if (boxColour(j+1, behindGroup).equals(boxColour(j+2, behindGroup1))) {
            if (boxColour(j+2, behindGroup1).equals(boxColour(j+3, behindGroup2))) {
              if (!boxColour(j, onGroup).equals("undefined")) {
                  if (boxColour(j, onGroup).equals(boxColour(j+3, behindGroup2))) {
                    winner(boxColour(j, onGroup), auto);
                }
              }
            }
          }
        } else if (boxColour(j, onGroup).equals(boxColour(j-1, infrontGroup))) {
          if (boxColour(j-1, infrontGroup).equals(boxColour(j-2, infrontGroup2))) {
            if (boxColour(j-2, infrontGroup1).equals(boxColour(j-3, infrontGroup2))) {
              if (!boxColour(j, onGroup).equals("undefined")) {
                if (boxColour(j, onGroup).equals(boxColour(j-3, infrontGroup2))) {
                  winner(boxColour(j, onGroup), auto);
                }
              }
            }
          }
        } else if (boxColour(j, onGroup).equals(boxColour(j-1, behindGroup))) {
          if (boxColour(j-1, behindGroup).equals(boxColour(j-2, behindGroup1))) {
            if (boxColour(j-2, behindGroup1).equals(boxColour(j-3, behindGroup2))) {
              if (!boxColour(j, onGroup).equals("undefined")) {
                if (boxColour(j, onGroup).equals(boxColour(j-3, behindGroup2))) {
                  winner(boxColour(j, onGroup), auto);
                }
              }
            }
          }
        }
      }
    }

    // End the game if it's a draw
    if (!group1[0].getBackground().equals(Color.WHITE)) {
      if (!group2[0].getBackground().equals(Color.WHITE)) {
        if (!group3[0].getBackground().equals(Color.WHITE)) {
          if (!group4[0].getBackground().equals(Color.WHITE)) {
            if (!group5[0].getBackground().equals(Color.WHITE)) {
              if (!group6[0].getBackground().equals(Color.WHITE)) {
                if (!group7[0].getBackground().equals(Color.WHITE)) {
                  winner("draw", auto);
                }
              }   
            }
          }
        }
      }
    }

    // If the game ended, make sure the reset button becomes visible and indicate who won.
    if (gameover) {
      instructions.setVisible(true);
      restart.setVisible(true);
      return;
    }

    // Switch turns
    if (turn.equals("Red")) {
      turn = "Yellow";
      yellowGlow.setVisible(true);
      redGlow.setVisible(false);
    } else {
      turn = "Red";
      yellowGlow.setVisible(false);
      redGlow.setVisible(true);
    }

    // Decide where the bot should place it's piece.
    if (auto) {
      double num = Math.random();
      double num2 = Math.random();
      int tempAmount = 1;
      if(num <= 0.33) {
        tempAmount = group-1;
        if (group < 1) {
          if (num2 < 0.5) {
            tempAmount = group;
          } else {
            tempAmount = group+1;
          }
        }
      } else if (num > 0.33 && num < 0.66) {
        tempAmount = group;
      } else if (num >= 0.66) {
        tempAmount = group+1;
        if (group > 7) {
          if (num2 < 0.5) {
            tempAmount = group;
          } else {
            tempAmount = group-1;
          }
        }
      }
      if (tempAmount == 1) {
        groupPlace = "groupOne";
      } else if (tempAmount == 2) {
        groupPlace = "groupTwo";
      } else if (tempAmount == 3) {
        groupPlace = "groupThree";
      } else if (tempAmount == 4) {
        groupPlace = "groupFour";
      } else if (tempAmount == 5) {
        groupPlace = "groupFive";
      } else if (tempAmount == 6) {
        groupPlace = "groupSix";
      } else if (tempAmount == 7) {
        groupPlace = "groupSeven";
      }
    }
    if (!auto) {
      run = false;
    } else if (turn.equals("Red")) {
      run = false;
    }
  }
}
  // Main method to start our program
  public static void main(String[] args){
  // Creates an instance of our program
  Main gui = new Main();
  // Lets the computer know to start it in the event thread
  SwingUtilities.invokeLater(gui);

  System.out.println("                                                                                   ______");
  System.out.println("   _______      _____     ___     _    ___     _   _____    ________    _______   /      |");
  System.out.println(" /  ____  \\    /  _  \\   |   \\   | |  |   \\   | | |  ___|  /  ____  \\  |___ ___| /  _    |");
  System.out.println("/  /    \\__\\  /  / \\  \\  | |\\ \\  | |  | |\\ \\  | | | |_    /  /    \\__\\    | |   /  / |   |");
  System.out.println("|  |     __   |  | |  |  | | \\ \\ | |  | | \\ \\ | | |  _|   |  |     __     | |  /  /__|   |_");
  System.out.println("\\  \\____/  /  \\  \\_/  /  | |  \\ \\| |  | |  \\ \\| | | |___  \\  \\____/  /    | | /______     _|");
  System.out.println(" \\________/    \\_____/   |_|   \\___|  |_|   \\___| |_____|  \\________/     |_|        |___|");
  } 
}