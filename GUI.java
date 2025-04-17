import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class GUI {

	// Window components
	JFrame frame = new JFrame();
	Container con = frame.getContentPane();
	JLayeredPane backgroundLayeredPane = new JLayeredPane();
	JPanel nameScreenPanel, battleDescriptionPanel;
	JPanel startGameButtonPanel, startBattleButtonPanel;
	Font buttonFont, normalFont, battleFont, battleButtonFont, moveFont, mediumFont, bigFont;
	JLabel backgroundImgLabel = new JLabel();

	int screenWidth, screenHeight;

	Player player1 = new Player();
	Player player2 = new Player();

	public static void main(String[] args) {
		new GUI();
	}

	public GUI() { // Creating a basic frame
		screenWidth = 1200;
		screenHeight = 700;
		frame.setSize(screenWidth + 14, screenHeight + 36);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit
		frame.setTitle("Pokémon FireRed PvP"); // Create GUI title
		frame.setLocationRelativeTo(null); // Not specify the location of the window, setting it to middle
		frame.setVisible(true);
		frame.setLayout(null); // Set default layout
		con.setLayout(null);
		creatFont();

		// Set background layered pane and image background
		backgroundLayeredPane.setLayout(null);
		backgroundLayeredPane.setBounds(0, 0, screenWidth, screenHeight);
		backgroundLayeredPane.setBackground(new Color(197, 213, 228));
		backgroundLayeredPane.setOpaque(true);
		backgroundLayeredPane.add(backgroundImgLabel, Integer.valueOf(0));
		backgroundImgLabel.setBounds(0, 0, screenWidth, screenHeight);
		con.add(backgroundLayeredPane);

		getMenuScreen();
	}

	public void getMenuScreen() {
		// Dimension and coordinate variables for scaling
		int sbp_x = (int) Math.round(screenWidth * 0.306);
		int sbp_y = (int) Math.round(screenHeight * 0.7);
		int sbp_w = (int) Math.round(screenWidth * 0.12);
		int sbp_h = (int) Math.round(screenHeight * 0.1);

		setImage("images/Starting_Screen.jpg", screenWidth, screenHeight, backgroundImgLabel);

		// Make a start button panel
		startGameButtonPanel = new JPanel();
		startGameButtonPanel.setBounds(sbp_x, sbp_y, sbp_w, sbp_h);
		startGameButtonPanel.setBackground(new Color(0, 0, 0, 0));

		// Dimension of button
		int sb_w = (int) Math.round(screenWidth * 0.1);
		int sb_h = (int) Math.round(screenHeight * 0.08);

		// Make a start button
		JButton startGameButton = new JButton("START");
		startGameButton.setBackground(Color.black);
		startGameButton.setForeground(Color.white);
		startGameButton.setPreferredSize(new Dimension(sb_w, sb_h)); // Setting button size
		startGameButton.setFont(buttonFont);
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getNameScreen();
			}
		});

		startGameButtonPanel.add(startGameButton);
		backgroundLayeredPane.add(startGameButtonPanel, Integer.valueOf(1));
	}

	public void getNameScreen() {
		// Disabling startButtonPanel
		startGameButtonPanel.setVisible(false);

		// Set background image
		setImage("images/Pokebox.png", screenWidth, screenHeight, backgroundImgLabel);

		// Make a getNameScreen Panel
		nameScreenPanel = createPanel(100, 180, 1000, 480, 255, 0, 0, 0);
		backgroundLayeredPane.add(nameScreenPanel, Integer.valueOf(1));

		// Create 6 display panel for each player pokemon
		JPanel[] p1PokemonDisplayPanel = new JPanel[6];
		JLabel[] p1PokemonDisplayLabel = new JLabel[6];

		for (int i = 0; i < 6; i++) {
			int space = 142 * i;
			int x = 90 + space, y = 90, w = 100, h = 100, r = 255, b = 255, g = 255, a = 255;
			p1PokemonDisplayPanel[i] = createPanel(x, y, w, h, r, b, g, a);
			p1PokemonDisplayPanel[i].setLayout(null);
			p1PokemonDisplayPanel[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
			nameScreenPanel.add(p1PokemonDisplayPanel[i]);
			p1PokemonDisplayLabel[i] = createLabel(0, 0, 100, 100);
			p1PokemonDisplayPanel[i].add(p1PokemonDisplayLabel[i]);
		}

		JPanel[] p2PokemonDisplayPanel = new JPanel[6];
		JLabel[] p2PokemonDisplayLabel = new JLabel[6];

		for (int i = 0; i < 6; i++) {
			int space = 142 * i;
			int x = 90 + space, y = 290, w = 100, h = 100, r = 255, b = 255, g = 255, a = 255;
			p2PokemonDisplayPanel[i] = createPanel(x, y, w, h, r, b, g, a);
			p2PokemonDisplayPanel[i].setLayout(null);
			p2PokemonDisplayPanel[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
			nameScreenPanel.add(p2PokemonDisplayPanel[i]);
			p2PokemonDisplayLabel[i] = createLabel(0, 0, 100, 100);
			p2PokemonDisplayPanel[i].add(p2PokemonDisplayLabel[i]);
		}

		// Make start battle button after enter name
		JButton startBattleButton = createButton("Let's Battle!", 400, 420, 180, 40, 0, 0, 0, 255, 255, 255, 255, 255,
				buttonFont);
		startBattleButton.setEnabled(false);
		nameScreenPanel.add(startBattleButton);

		int[] count = new int[1];

		// Prompting name for player 1
		JLabel p1NameLabel = createLabel("Enter Player 1 Name: ", 90, 15, 250, 40, 0, 0, 0, 255, normalFont);
		nameScreenPanel.add(p1NameLabel);

		JTextField p1TextField = new JTextField(40);
		p1TextField.setBounds(350, 15, 200, 40);
		nameScreenPanel.add(p1TextField);

		// Enter name Button for player 1
		JButton p1NameButton = createButton("Enter", 800, 15, 100, 40, 0, 0, 0, 255, 255, 255, 255, 255, buttonFont);
		// Set name and get 6 pokemons
		p1NameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count[0]++;
				player1.setName(p1TextField.getText());
				try {
					player1.getRandomPokemon();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < 6; i++) {
					setPokemonSprite(player1, "front", i, p1PokemonDisplayLabel[i], 100, 100);
				}

				p1NameButton.setEnabled(false);
				if (count[0] == 2) {
					startBattleButton.setEnabled(true);
				}
			}
		});
		nameScreenPanel.add(p1NameButton);

		// Prompting name for player 2
		JLabel p2NameLabel = createLabel("Enter Player 2 Name: ", 90, 220, 250, 40, 0, 0, 0, 255, normalFont);
		nameScreenPanel.add(p2NameLabel);

		JTextField p2TextField = new JTextField(40);
		p2TextField.setBounds(350, 220, 200, 40);
		nameScreenPanel.add(p2TextField);

		// Enter name button for player 2
		JButton p2NameButton = createButton("Enter", 800, 220, 100, 40, 0, 0, 0, 255, 255, 255, 255, 255, buttonFont);
		// Set name and get 6 pokemons
		p2NameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count[0]++;
				player2.setName(p2TextField.getText());
				try {
					player2.getRandomPokemon();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				for (int i = 0; i < 6; i++) {
					setPokemonSprite(player2, "front", i, p2PokemonDisplayLabel[i], 100, 100);
				}
				p2NameButton.setEnabled(false);
				if (count[0] == 2) {
					startBattleButton.setEnabled(true);
				}
			}
		});

		startBattleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainBattle();
				// Hide previous screen, change background
				nameScreenPanel.setVisible(false);
				// Choose a random battle background image
				Random rand = new Random();
                int n = rand.nextInt(10)+1;
				String backgroundImg = "images/Battle_Background/Battle_Background_" + String.valueOf(n) + ".png";
				setImage(backgroundImg, screenWidth, 460, backgroundImgLabel);
				// Move the image up
				backgroundImgLabel.setLocation(0, -115);
			}	
		});
		nameScreenPanel.add(p2NameButton);
	}

	int playerTurn = 2; // Set to 2 first so it change to 1 at the first turn
	int p1PokemonChoice = 0;
	int p2PokemonChoice = 0;
	PlayerDecisionData p1Data = new PlayerDecisionData();
	PlayerDecisionData p2Data = new PlayerDecisionData();

	public void mainBattle() {
		// turn decides which player turn, 1 = player1, 2 = player2
		// If this turn is p1, next is p2
		if (playerTurn == 1) {
			playerTurn = 2;
		}
		// If this turn is p2, next is p1
		else if (playerTurn == 2) {
			playerTurn = 1;
		}

		if (playerTurn == 1) {
			mainBattleMenu(player1, player2, p1Data, p2Data, false, -1);
		} else if (playerTurn == 2) {
			mainBattleMenu(player2, player1, p2Data, p1Data, false, -1);
		}
	}

	public void mainBattleMenu(Player p1, Player p2, PlayerDecisionData playerData, PlayerDecisionData oppData,
			boolean fainted,
			int faintedPokemon) {
		int inTurnPokemonChoice = 0; // inTurnPokemonChoice follow the in turn pokemon, ragardless of p1 or p2

		// If the pokemon is fainted, display that on screen
		if (fainted == true) {
			inTurnPokemonChoice = faintedPokemon;
		} else if (playerTurn == 1) {
			inTurnPokemonChoice = p1PokemonChoice;
		} else {
			inTurnPokemonChoice = p2PokemonChoice;
		}

		// Set up Pokemon Sprite (Constant, always player1 vs player2)
		JPanel p1PokemonPanel = createPanel(190, 255, 225, 210, 255, 0, 0, 0);
		backgroundLayeredPane.add(p1PokemonPanel, Integer.valueOf(1));
		JLabel p1PokemonLabel = createLabel(0, 0, p1PokemonPanel.getWidth(), p1PokemonPanel.getHeight());
		setPokemonSprite(player1, "back", p1PokemonChoice, p1PokemonLabel, p1PokemonPanel.getWidth(),
				p1PokemonPanel.getHeight());
		p1PokemonPanel.add(p1PokemonLabel);

		JPanel p2PokemonPanel = createPanel(770, 70, 210, 230, 255, 0, 0, 0);
		backgroundLayeredPane.add(p2PokemonPanel, Integer.valueOf(1));
		JLabel p2PokemonLabel = createLabel(0, 0, p2PokemonPanel.getWidth(), p2PokemonPanel.getHeight());
		setPokemonSprite(player2, "front", p2PokemonChoice, p2PokemonLabel, p2PokemonPanel.getWidth(),
				p2PokemonPanel.getHeight());
		p2PokemonPanel.add(p2PokemonLabel);

		// Set up player1 pokemon health bar (Constant, always player1 vs player2)
		JPanel p1PokemonHealthPanel = createPanel(730, 310, 410, 150, 255, 0, 0, 0);
		backgroundLayeredPane.add(p1PokemonHealthPanel, Integer.valueOf(1));
		JLabel p1PokemonHealthLabel = createLabel(0, 0, p1PokemonHealthPanel.getWidth(),
				p1PokemonHealthPanel.getHeight());
		setImage("images/Pokemon_In_Battle_Status.png", p1PokemonHealthPanel.getWidth(),
				p1PokemonHealthPanel.getHeight(), p1PokemonHealthLabel);
		p1PokemonHealthPanel.add(p1PokemonHealthLabel);
		// Create player 1 pokemon info
		JLabel p1PokemonNameTextLabel = createLabel(player1.getPokemon(p1PokemonChoice).getName(), 25, 17, 200, 50, 0,
				0, 0, 255, battleFont);
		JLabel p1PokemonLevelTextLabel = createLabel("Lv: " + player1.getPokemon(p1PokemonChoice).getLevel(), 250, 20,
				200, 40, 0, 0, 0, 255, battleFont);
		JPanel p1PokemonHealthBarPanel = createPanel(23, 80, 330, 30, 0, 0, 0, 0);
		JLabel p1PokemonHealthLeftLabel = new JLabel();
		setPokemonHealthBar(p1PokemonHealthBarPanel, p1PokemonHealthLeftLabel, player1, p1PokemonChoice);
		p1PokemonHealthLabel.add(p1PokemonNameTextLabel);
		p1PokemonHealthLabel.add(p1PokemonLevelTextLabel);
		p1PokemonHealthLabel.add(p1PokemonHealthBarPanel);

		// Set up player2 pokemon health bar (Constant, always player1 vs player2)
		JPanel p2PokemonHealthPanel = createPanel(110, 60, 410, 150, 255, 0, 0, 0);
		backgroundLayeredPane.add(p2PokemonHealthPanel, Integer.valueOf(1));
		JLabel p2PokemonHealthLabel = createLabel(0, 0, p2PokemonHealthPanel.getWidth(),
				p2PokemonHealthPanel.getHeight());
		setImage("images/Pokemon_In_Battle_Status.png", p2PokemonHealthPanel.getWidth(),
				p2PokemonHealthPanel.getHeight(),
				p2PokemonHealthLabel);
		p2PokemonHealthPanel.add(p2PokemonHealthLabel);
		// Create player 2 pokemon info
		JLabel p2PokemonNameTextLabel = createLabel(player2.getPokemon(p2PokemonChoice).getName(), 25, 17, 200, 50, 0,
				0, 0, 255, battleFont);
		JLabel p2PokemonLevelTextLabel = createLabel("Lv: " + player2.getPokemon(p2PokemonChoice).getLevel(), 250, 20,
				200, 40, 0, 0, 0, 255, battleFont);
		JPanel p2PokemonHealthBarPanel = createPanel(23, 80, 330, 30, 0, 0, 0, 0);
		JLabel p2PokemonHealthLeftLabel = new JLabel();
		setPokemonHealthBar(p2PokemonHealthBarPanel, p2PokemonHealthLeftLabel, player2, p2PokemonChoice);
		p2PokemonHealthLabel.add(p2PokemonNameTextLabel);
		p2PokemonHealthLabel.add(p2PokemonLevelTextLabel);
		p2PokemonHealthLabel.add(p2PokemonHealthBarPanel);

		// Battle GUI
		// Set up battle description
		battleDescriptionPanel = createPanel(0, 465, 1200, 234, 0, 0, 0, 0);
		backgroundLayeredPane.add(battleDescriptionPanel, Integer.valueOf(1));
		JLabel battleDescriptionLabel = createLabel(0, 0, 1200, 234);
		battleDescriptionPanel.add(battleDescriptionLabel);
		setImage("images/Battle_Description.png", 1200, 234, battleDescriptionLabel);
		// Introduction text
		JLabel battleDescriptionTextLabel = createLabel("<html>What would " + p1.getName() + " do?</html>", 60, 510,
				630, 150, 255, 255, 255, 255, battleFont);
		battleDescriptionTextLabel.setVerticalAlignment(JLabel.TOP);
		battleDescriptionTextLabel.setVerticalTextPosition(JLabel.TOP);
		backgroundLayeredPane.add(battleDescriptionTextLabel, Integer.valueOf(2));

		// Set up fight menu
		JPanel fightMenuPanel = createPanel(0, 465, battleDescriptionPanel.getWidth(),
				battleDescriptionPanel.getHeight(), 0, 0, 0, 0);
		backgroundLayeredPane.add(fightMenuPanel, Integer.valueOf(3));
		JLabel fightMenuLabel = createLabel(0, 0, battleDescriptionPanel.getWidth(),
				battleDescriptionPanel.getHeight());
		setImage("images/Move_Menu.png", battleDescriptionPanel.getWidth(), battleDescriptionPanel.getHeight(),
				fightMenuLabel);
		fightMenuPanel.add(fightMenuLabel);
		fightMenuPanel.setVisible(false);
		// Set up move buttons
		JButton[] moveButton = new JButton[4];
		moveButton[0] = createButton(p1.getPokemon(inTurnPokemonChoice).getMove(0).getName(), 30, 32, 365, 80,
				255, 255, 255, 255, 0, 0, 0, 255, moveFont);
		fightMenuLabel.add(moveButton[0]);
		moveButton[1] = createButton(p1.getPokemon(inTurnPokemonChoice).getMove(1).getName(), 405, 32, 365, 80,
				255, 255, 255, 255, 0, 0, 0, 255, moveFont);
		fightMenuLabel.add(moveButton[1]);
		moveButton[2] = createButton(p1.getPokemon(inTurnPokemonChoice).getMove(2).getName(), 30, 120, 365, 80,
				255, 255, 255, 255, 0, 0, 0, 255, moveFont);
		fightMenuLabel.add(moveButton[2]);
		moveButton[3] = createButton(p1.getPokemon(inTurnPokemonChoice).getMove(3).getName(), 405, 120, 365,
				80, 255, 255, 255, 255, 0, 0, 0, 255, moveFont);
		fightMenuLabel.add(moveButton[3]);

		// Color move button borders by type
		for (int i = 0; i < 4; i++) {
			TypeColor tc = new TypeColor();
			Color color = tc.getTypeColor(p1, inTurnPokemonChoice, i);
			moveButton[i].setBorder(BorderFactory.createLineBorder(color, 4));
		}

		// Set up p1 pokemon 4 move information
		JPanel[] moveInfoPanel = new JPanel[4];
		for (int i = 0; i < 4; i++) {
			moveInfoPanel[i] = createPanel(830, 30, 310, 170, 255, 0, 0, 0);
			fightMenuLabel.add(moveInfoPanel[i]);
			JLabel moveName = createLabel(p1.getPokemon(inTurnPokemonChoice).getMove(i).getName(), 10, 10, 250, 50, 0,
					0, 0, 255, battleFont);
			moveInfoPanel[i].add(moveName);
			JLabel moveType = createLabel(p1.getPokemon(inTurnPokemonChoice).getMove(i).getType(), 10, 55, 150, 50, 0,
					0, 0, 255, battleFont);
			// Set Color
			TypeColor tc = new TypeColor();
			Color color = tc.getTypeColor(p1, inTurnPokemonChoice, i);
			moveType.setForeground(color);
			moveInfoPanel[i].add(moveType);

			JLabel moveCategory = createLabel(p1.getPokemon(inTurnPokemonChoice).getMove(i).getCategory(), 150, 55, 150,
					50, 0, 0, 0, 255, battleFont);
			moveInfoPanel[i].add(moveCategory);
			JLabel movePower = createLabel("Power: " + p1.getPokemon(inTurnPokemonChoice).getMove(i).getPower(), 10,
					100, 180, 50, 0, 0, 0, 255, battleFont);
			moveInfoPanel[i].add(movePower);
			JLabel movePP = createLabel("PP: " + p1.getPokemon(inTurnPokemonChoice).getMove(i).getPP(), 200, 100, 150,
					50, 0, 0, 0, 255, battleFont);
			// Red if out of PP
			if (p1.getPokemon(inTurnPokemonChoice).getMove(i).getPP() == 0) {
				movePP.setForeground(new Color(200, 0, 0));
			}
			moveInfoPanel[i].add(movePP);
			moveInfoPanel[i].setVisible(false);
		}

		// Create return button
		JButton returnButton = createButton("", 1145, 630, 25, 40, 0, 0, 0, 0, 0, 0, 0, 0, battleButtonFont);
		JLabel returnButtonLabel = createLabel(0, 0, returnButton.getWidth(), returnButton.getHeight());
		returnButton.setLayout(null);
		returnButtonLabel.setLayout(null);
		setImage("images/Return_Button.png", returnButton.getWidth(), returnButton.getHeight(), returnButtonLabel);
		returnButton.add(returnButtonLabel);
		returnButton.setVisible(false);
		backgroundLayeredPane.add(returnButton, Integer.valueOf(5));

		// Create switch pokemon menu
		JPanel pokemonMenuPanel = createPanel(500, 100, 700, 600, 255, 0, 0, 0);
		JLabel pokemonMenuLabel = createLabel(0, 0, pokemonMenuPanel.getWidth(), pokemonMenuPanel.getHeight());
		pokemonMenuLabel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		setImage("images/Pokemon_Menu.png", pokemonMenuPanel.getWidth(), pokemonMenuPanel.getHeight(),
				pokemonMenuLabel);
		pokemonMenuPanel.add(pokemonMenuLabel);
		pokemonMenuPanel.setVisible(false);
		backgroundLayeredPane.add(pokemonMenuPanel, Integer.valueOf(4));
		// Create 6 pokemons for menu
		JButton[] pokemonInMenuButton = new JButton[6];
		for (int i = 0; i < 6; i++) {
			int space = 90 * i;
			JPanel pokemonInMenuPanel = createPanel(70, 40 + space, 450, 80, 79, 160, 230, 200);
			pokemonInMenuPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			pokemonMenuLabel.add(pokemonInMenuPanel);
			JLabel pokemonInMenuIconLabel = createLabel(10, 0, 80, 80);
			setPokemonSprite(p1, "front", i, pokemonInMenuIconLabel, pokemonInMenuIconLabel.getWidth(),
					pokemonInMenuIconLabel.getHeight());
			pokemonInMenuPanel.add(pokemonInMenuIconLabel);
			pokemonInMenuButton[i] = createButton("SWITCH", 530, 40 + space, 100, 80, 255, 255, 255, 255, 0, 0, 0, 255,
					normalFont);
			pokemonInMenuButton[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
			pokemonMenuLabel.add(pokemonInMenuButton[i]);
			JLabel pokemonName = createLabel(p1.getPokemon(i).getName(), 110, 0, 250, 40, 255, 255, 255, 255,
					mediumFont);
			pokemonInMenuPanel.add(pokemonName);
			JLabel pokemonLevel = createLabel("Lv: " + p1.getPokemon(i).getLevel(), 320, 0, 250, 40, 255, 255, 255, 255,
					mediumFont);
			pokemonInMenuPanel.add(pokemonLevel);
			JPanel pokemonHealthBarPanel = createPanel(110, 45, 290, 25, 0, 0, 0, 0);
			JLabel pokemonHealthLeftLabel = new JLabel();
			setPokemonInMenuHealthBar(pokemonHealthBarPanel, pokemonHealthLeftLabel, p1, i);
			pokemonInMenuPanel.add(pokemonHealthBarPanel);
			pokemonInMenuPanel.add(pokemonHealthLeftLabel);

			// Disable fainted pokemon
			if (p1.getPokemon(i).isFaint() == true) {
				pokemonInMenuPanel.setBackground(new Color(255, 48, 48, 200));
				pokemonInMenuButton[i].setEnabled(false);
			}
			// Disable current on screen pokemon
			if (i == inTurnPokemonChoice) {
				pokemonInMenuButton[i].setEnabled(false);
			}
		}

		// Create Battle Option
		JPanel battleOptionPanel = createPanel(700, 465, 500, 235, 255, 0, 0, 255);
		backgroundLayeredPane.add(battleOptionPanel, Integer.valueOf(2));
		JLabel battleOptionLabel = createLabel(0, 0, battleOptionPanel.getWidth(), battleOptionPanel.getHeight());
		setImage("images/Battle_Menu.png", battleOptionLabel.getWidth(), battleOptionLabel.getHeight(),
				battleOptionLabel);
		battleOptionPanel.add(battleOptionLabel);

		// Create Battle Option Buttons
		// Switch to fight screen
		JButton fightButton = createButton("FIGHT", 25, 33, 222, 80, 255, 255, 255, 255, 0, 0, 0, 255,
				battleButtonFont);
		fightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				battleOptionPanel.setVisible(false);
				fightMenuPanel.setVisible(true);
				returnButton.setVisible(true);
			}
		});
		battleOptionLabel.add(fightButton);

		// Switch to pokemons screen
		JButton switchButton = createButton("SWITCH", 250, 33, 225, 80, 255, 255, 255, 255, 0, 0, 0, 255,
				battleButtonFont);
		switchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				battleOptionPanel.setVisible(false);
				pokemonMenuPanel.setVisible(true);
				returnButton.setVisible(true);
			}
		});
		battleOptionLabel.add(switchButton);

		// Give function to return button
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				battleOptionPanel.setVisible(true);
				fightMenuPanel.setVisible(false);
				pokemonMenuPanel.setVisible(false);
				returnButton.setVisible(false);
			}
		});

		int[] moveButtonClickCount = { 0, 0, 0, 0 };

		// Add functions to 4 move buttons
		moveButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset click counter for all other buttons, show move information screen
				if (moveButtonClickCount[0] == 0) {
					moveButtonClickCount[0] = 1;
					moveButtonClickCount[1] = 0;
					moveButtonClickCount[2] = 0;
					moveButtonClickCount[3] = 0;
					moveInfoPanel[0].setVisible(true);
					moveInfoPanel[1].setVisible(false);
					moveInfoPanel[2].setVisible(false);
					moveInfoPanel[3].setVisible(false);
				}
				// Attack
				else if (moveButtonClickCount[0] == 1) {
					playerData.setPriority(1);
					playerData.setMove(0);
					try {
						checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
								battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
								pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
								p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
								p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
								p2PokemonHealthLeftLabel, fainted);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		moveButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset click counter for all other buttons, show move information screen
				if (moveButtonClickCount[1] == 0) {
					moveButtonClickCount[0] = 0;
					moveButtonClickCount[1] = 1;
					moveButtonClickCount[2] = 0;
					moveButtonClickCount[3] = 0;
					moveInfoPanel[0].setVisible(false);
					moveInfoPanel[1].setVisible(true);
					moveInfoPanel[2].setVisible(false);
					moveInfoPanel[3].setVisible(false);
				}
				// Attack
				else if (moveButtonClickCount[1] == 1) {
					playerData.setPriority(1);
					playerData.setMove(1);
					try {
						checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
								battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
								pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
								p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
								p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
								p2PokemonHealthLeftLabel, fainted);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		moveButton[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset click counter for all other buttons, show move information screen
				if (moveButtonClickCount[2] == 0) {
					moveButtonClickCount[0] = 0;
					moveButtonClickCount[1] = 0;
					moveButtonClickCount[2] = 1;
					moveButtonClickCount[3] = 0;
					moveInfoPanel[0].setVisible(false);
					moveInfoPanel[1].setVisible(false);
					moveInfoPanel[2].setVisible(true);
					moveInfoPanel[3].setVisible(false);
				}
				// Attack
				else if (moveButtonClickCount[2] == 1) {
					playerData.setPriority(1);
					playerData.setMove(2);
					try {
						checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
								battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
								pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
								p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
								p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
								p2PokemonHealthLeftLabel, fainted);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		moveButton[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset click counter for all other buttons, show move information screen
				if (moveButtonClickCount[3] == 0) {
					moveButtonClickCount[0] = 0;
					moveButtonClickCount[1] = 0;
					moveButtonClickCount[2] = 0;
					moveButtonClickCount[3] = 1;
					moveInfoPanel[0].setVisible(false);
					moveInfoPanel[1].setVisible(false);
					moveInfoPanel[2].setVisible(false);
					moveInfoPanel[3].setVisible(true);
				}
				// Attack
				else if (moveButtonClickCount[3] == 1) {
					playerData.setPriority(1);
					playerData.setMove(3);
					try {
						checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
								battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
								pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
								p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
								p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
								p2PokemonHealthLeftLabel, fainted);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// Add function to switch pokemon button
		pokemonInMenuButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerData.setPriority(2);
				playerData.setPokemonChoice(0);
				try {
					checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
							p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
							p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
							p2PokemonHealthLeftLabel, fainted);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		pokemonInMenuButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerData.setPriority(2);
				playerData.setPokemonChoice(1);
				try {
					checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
							p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
							p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
							p2PokemonHealthLeftLabel, fainted);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		pokemonInMenuButton[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerData.setPriority(2);
				playerData.setPokemonChoice(2);
				try {
					checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
							p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
							p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
							p2PokemonHealthLeftLabel, fainted);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		pokemonInMenuButton[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerData.setPriority(2);
				playerData.setPokemonChoice(3);
				try {
					checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
							p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
							p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
							p2PokemonHealthLeftLabel, fainted);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		pokemonInMenuButton[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerData.setPriority(2);
				playerData.setPokemonChoice(4);
				try {
					checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
							p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
							p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
							p2PokemonHealthLeftLabel, fainted);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		pokemonInMenuButton[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerData.setPriority(2);
				playerData.setPokemonChoice(5);
				try {
					checkPriority(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonLabel, p2PokemonLabel, p1PokemonNameTextLabel,
							p2PokemonNameTextLabel, p1PokemonLevelTextLabel, p2PokemonLevelTextLabel,
							p1PokemonHealthBarPanel, p2PokemonHealthBarPanel, p1PokemonHealthLeftLabel,
							p2PokemonHealthLeftLabel, fainted);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Run (Forfeit)
		JButton runButton = createButton("RUN", 25, 123, 225, 80, 255, 255, 255, 255, 0, 0, 0, 255, battleButtonFont);
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playerTurn == 1) {
					player1.setLost(true);
				} else {
					player2.setLost(true);
				}
				// Delete screen and switch screen
				resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
						p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
						battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
				getVictoryScreen();
			}
		});
		battleOptionLabel.add(runButton);

		// If current pokemon is fainted, force to switch out
		if (fainted == true) {
			battleDescriptionTextLabel.setText(null);
			battleDescriptionTextLabel.setText("<html>" +
					p1.getPokemon(inTurnPokemonChoice).getName()
					+ " is fainted!<br/>What would " + p1.getName() + " do?</html>");
			fightButton.setEnabled(false);
			runButton.setEnabled(false);
		}

		// Check for losing
		if (player1.isLost() == true || player2.isLost() == true) {
			// Delete screen and switch screen
			resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
					p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
					battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
			getVictoryScreen();
		}
	}

	public void getVictoryScreen() {
		// Change background
		// If player1 win, red background
		if (player2.getLost() == true) {
			setImage("images/Victory_Red_Background.png", screenWidth, screenHeight, backgroundImgLabel);
		}
		// If player2 win, blue background
		else {
			setImage("images/Victory_Blue_Background.png", screenWidth, screenHeight, backgroundImgLabel);
		}
		backgroundImgLabel.setLocation(0, 0);
		// Winner
		JPanel winnerPanel = createPanel(455, 60, 290, 120, 255, 255, 255, 120);
		winnerPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
		backgroundLayeredPane.add(winnerPanel, Integer.valueOf(2));

		JLabel winnerLabel = createLabel("WINNER", 0, 0, winnerPanel.getWidth(), winnerPanel.getHeight(), 238, 216, 25,
				255, bigFont);
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerLabel.setVerticalAlignment(SwingConstants.CENTER);
		winnerLabel.setFont(bigFont.deriveFont((bigFont.getStyle() | Font.BOLD)));
		winnerPanel.add(winnerLabel);

		JLabel winnerPlayerLabel = createLabel(null, 400, 200, 400, 100, 0, 0, 0, 255, moveFont);
		winnerPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerPlayerLabel.setVerticalAlignment(SwingConstants.CENTER);
		winnerPlayerLabel.setOpaque(true);
		winnerPlayerLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
		backgroundLayeredPane.add(winnerPlayerLabel, Integer.valueOf(2));

		// Loser
		JPanel loserPanel = createPanel(455, 360, 290, 120, 255, 255, 255, 120);
		loserPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
		backgroundLayeredPane.add(loserPanel, Integer.valueOf(2));

		JLabel loserLabel = createLabel("LOSER", 0, 0, loserPanel.getWidth(), loserPanel.getHeight(), 160, 160, 160,
				255, bigFont);
		loserLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loserLabel.setVerticalAlignment(SwingConstants.CENTER);
		loserLabel.setFont(bigFont.deriveFont((bigFont.getStyle() | Font.BOLD)));
		loserPanel.add(loserLabel);

		JLabel loserPlayerLabel = createLabel(null, 400, 500, 400, 100, 0, 0, 0, 255, moveFont);
		loserPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loserPlayerLabel.setVerticalAlignment(SwingConstants.CENTER);
		loserPlayerLabel.setOpaque(true);
		loserPlayerLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 4));
		backgroundLayeredPane.add(loserPlayerLabel, Integer.valueOf(2));

		// Set text in case player1 wins
		if (player2.getLost() == true) {
			winnerPlayerLabel.setText("<html>" + player1.getName() + "</html>");
			loserPlayerLabel.setText("<html>" + player2.getName() + "</html>");
		}
		// Set text in case player2 wins
		else {
			winnerPlayerLabel.setText("<html>" + player2.getName() + "</html>");
			loserPlayerLabel.setText("<html>" + player1.getName() + "</html>");
		}
	}

	// Setting priority, who moves first
	public void checkPriority(JPanel p1PokemonPanel, JPanel p2PokemonPanel, JPanel p1PokemonHealthPanel,
			JPanel p2PokemonHealthPanel, JLabel battleDescriptionTextLabel, JPanel battleDescriptionPanel,
			JPanel battleOptionPanel, JPanel fightMenuPanel, JPanel pokemonMenuPanel, JButton returnButton,
			JLabel p1PokemonLabel, JLabel p2PokemonLabel, JLabel p1PokemonNameTextLabel, JLabel p2PokemonNameTextLabel,
			JLabel p1PokemonLevelTextLabel, JLabel p2PokemonLevelTextLabel, JPanel p1PokemonHealthBarPanel,
			JPanel p2PokemonHealthBarPanel, JLabel p1PokemonHealthLeftLabel, JLabel p2PokemonHealthLeftLabel,
			boolean faintedToSwitch)
			throws FileNotFoundException {
		boolean fainted = false;
		// Turn off everything but battleDescriptionPanel
		battleOptionPanel.setVisible(false);
		fightMenuPanel.setVisible(false);
		pokemonMenuPanel.setVisible(false);
		returnButton.setVisible(false);
		battleDescriptionTextLabel.setSize(1090, 150);
		// Create continueButton
		JButton continueButton = new JButton();
		continueButton.setBounds(1055, 610, 100, 50);
		continueButton.setLayout(null);
		continueButton.setOpaque(true);
		backgroundLayeredPane.add(continueButton, Integer.valueOf(3));
		continueButton.setVisible(false);
		// Set image for continueButton
		JLabel continueButtonImgLabel = createLabel(0, 0, continueButton.getWidth(), continueButton.getHeight());
		setImage("images/Next_Button.png",continueButton.getWidth(), continueButton.getHeight(), continueButtonImgLabel);
		continueButton.add(continueButtonImgLabel);
		if (playerTurn == 1) {
			battleDescriptionTextLabel.setSize(630, 150);
			resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
					p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
					battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
			mainBattle();
		}
		// Need to switch from fainted pokemon
		else if (faintedToSwitch == true) {
			// player1 pokemon fainted
			if (player1.getPokemon(p1PokemonChoice).isFaint() == true) {
				p1PokemonChoice = p1Data.getPokemonChoice();
				switchPokemon(player1, "back", p1PokemonChoice, p1PokemonPanel, p1PokemonLabel,
						p1PokemonNameTextLabel, p1PokemonLevelTextLabel, p1PokemonHealthBarPanel,
						p1PokemonHealthLeftLabel, battleDescriptionTextLabel);
			}
			// player2 pokemon fainted
			else if (player2.getPokemon(p2PokemonChoice).isFaint() == true) {
				p2PokemonChoice = p2Data.getPokemonChoice();
				switchPokemon(player2, "front", p2PokemonChoice, p2PokemonPanel, p2PokemonLabel,
						p2PokemonNameTextLabel, p2PokemonLevelTextLabel, p2PokemonHealthBarPanel,
						p2PokemonHealthLeftLabel, battleDescriptionTextLabel);
			}
			continueButton.setVisible(true);
			continueButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					battleDescriptionTextLabel.setText(null);
					backgroundLayeredPane.remove(continueButton);
					resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
							p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
							battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
					mainBattle();
				}
			});
			// priority = 1: attack/status, priority = 2: swap
		} else if (playerTurn == 2) {
			// *For attacking*
			if (p1Data.getPriority() == 1 && p2Data.getPriority() == 1) {
				// player 1 pokemon is faster than player 2
				if (player1.getPokemon(p1PokemonChoice).getSpeed() >= player2.getPokemon(p2PokemonChoice).getSpeed()) {
					// Player 1
					checkAttack(player1, player2, p1PokemonChoice, p2PokemonChoice, p1Data.getMove(),
							battleDescriptionTextLabel, p2PokemonHealthBarPanel, p2PokemonHealthLeftLabel);

					// Check for player2 pokemon fainting
					if (player2.getPokemon(p2PokemonChoice).isFaint() == true) {
						fainted = true;
					}
					// Player 2
					// Add function to button
					continueButtonFunction(continueButton, player2, player1, p2PokemonChoice, p1PokemonChoice,
							p2Data, p1Data, p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
							p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel,
							fightMenuPanel, pokemonMenuPanel, returnButton, p1PokemonHealthBarPanel,
							p1PokemonHealthLeftLabel, fainted);
				}
				// player 2 pokemon is faster than player 1
				else {
					// Player 2
					checkAttack(player2, player1, p2PokemonChoice, p1PokemonChoice, p2Data.getMove(),
							battleDescriptionTextLabel, p1PokemonHealthBarPanel, p1PokemonHealthLeftLabel);

					// Check for player1 pokemon fainting
					if (player1.getPokemon(p1PokemonChoice).isFaint() == true) {
						fainted = true;
					}
					// Player 1
					// Add function to button
					continueButtonFunction(continueButton, player1, player2, p1PokemonChoice, p2PokemonChoice,
							p1Data, p2Data, p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
							p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel,
							fightMenuPanel, pokemonMenuPanel, returnButton, p2PokemonHealthBarPanel,
							p2PokemonHealthLeftLabel, fainted);
				}
			}
			// *For swapping*
			// Swap for both players
			else if (p1Data.getPriority() == 2 && p2Data.getPriority() == 2) {
				p1PokemonChoice = p1Data.getPokemonChoice();
				p2PokemonChoice = p2Data.getPokemonChoice();
				// Player 1
				switchPokemon(player1, "back", p1PokemonChoice, p1PokemonPanel, p1PokemonLabel,
						p1PokemonNameTextLabel, p1PokemonLevelTextLabel, p1PokemonHealthBarPanel,
						p1PokemonHealthLeftLabel, battleDescriptionTextLabel);
				String battleText = "<html>" + player1.getName() + " switches in "
						+ player1.getPokemon(p1PokemonChoice).getName() + ".<br/>";
				// Player 2
				switchPokemon(player2, "front", p2PokemonChoice, p2PokemonPanel, p2PokemonLabel,
						p2PokemonNameTextLabel, p2PokemonLevelTextLabel, p2PokemonHealthBarPanel,
						p2PokemonHealthLeftLabel, battleDescriptionTextLabel);
				battleText += player2.getName() + " switches in " + player2.getPokemon(p2PokemonChoice).getName()
						+ ".</html>";
				battleDescriptionTextLabel.setText(null);
				battleDescriptionTextLabel.setText(battleText);
				continueButton.setVisible(true);
				continueButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						battleDescriptionTextLabel.setSize(630, 150);
						battleDescriptionTextLabel.setText(null);
						backgroundLayeredPane.remove(continueButton);
						resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
								p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
								battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
						mainBattle();
					}

				});
			}
			// Swap for 1 player only
			else {
				// Swap for player1
				if (p1Data.getPriority() == 2) {
					p1PokemonChoice = p1Data.getPokemonChoice();
					switchPokemon(player1, "back", p1PokemonChoice, p1PokemonPanel, p1PokemonLabel,
							p1PokemonNameTextLabel, p1PokemonLevelTextLabel, p1PokemonHealthBarPanel,
							p1PokemonHealthLeftLabel, battleDescriptionTextLabel);
					// Player 2 attack
					// Add function to button
					continueButtonFunction(continueButton, player2, player1, p2PokemonChoice, p1PokemonChoice, p2Data,
							p1Data, p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p1PokemonHealthBarPanel,
							p1PokemonHealthLeftLabel, fainted);
				}
				// Swap for player2
				else if (p2Data.getPriority() == 2) {
					p2PokemonChoice = p2Data.getPokemonChoice();
					switchPokemon(player2, "front", p2PokemonChoice, p2PokemonPanel, p2PokemonLabel,
							p2PokemonNameTextLabel, p2PokemonLevelTextLabel, p2PokemonHealthBarPanel,
							p2PokemonHealthLeftLabel, battleDescriptionTextLabel);
					// Player 1 attack
					// Add function to button
					continueButtonFunction(continueButton, player1, player2, p1PokemonChoice, p2PokemonChoice, p1Data,
							p2Data, p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel, p2PokemonHealthPanel,
							battleDescriptionTextLabel, battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
							pokemonMenuPanel, returnButton, p2PokemonHealthBarPanel,
							p2PokemonHealthLeftLabel, fainted);
				}
			}
		}
	}

	public void continueButtonFunction(JButton continueButton, Player player, Player opponent, int playerPokemon,
			int oppPokemon, PlayerDecisionData playerData, PlayerDecisionData oppData, JPanel p1PokemonPanel,
			JPanel p2PokemonPanel, JPanel p1PokemonHealthPanel, JPanel p2PokemonHealthPanel,
			JLabel battleDescriptionTextLabel, JPanel battleDescriptionPanel, JPanel battleOptionPanel,
			JPanel fightMenuPanel, JPanel pokemonMenuPanel, JButton returnButton, JPanel oppPokemonHealthBarPanel,
			JLabel oppPokemonHealthLeftLabel, boolean fainted) {
		int[] clickCount = { 0 };
		boolean[] recentFainted = { false };
		continueButton.setVisible(true);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check if player pokemon is fainted
				if (fainted == true) {
					if (clickCount[0] == 0) {
						battleDescriptionTextLabel.setText(null);
						battleDescriptionTextLabel.setText("<html>" + player.getPokemon(playerPokemon).getName()
								+ " is fainted!<br/>" + player.getName() + " switches to a new pokemon</html>");
					}
					// Switch screen
					else {
						backgroundLayeredPane.remove(continueButton);
						resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
								p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
								battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
						mainBattleMenu(player, opponent, playerData, oppData, true, playerPokemon);
					}
				} else {
					if (clickCount[0] == 0) {
						try {
							checkAttack(player, opponent, playerPokemon, oppPokemon,
									playerData.getMove(), battleDescriptionTextLabel,
									oppPokemonHealthBarPanel, oppPokemonHealthLeftLabel);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						// Check for fainting
						if (opponent.getPokemon(oppPokemon).isFaint() == true) {
							recentFainted[0] = true;
						}
					}
					// Switch screen
					else {
						// if fainted, recall the function
						if (recentFainted[0] == true) {
							continueButtonFunction(continueButton, opponent, player, oppPokemon,
									playerPokemon, oppData, playerData, p2PokemonPanel, p1PokemonPanel,
									p2PokemonHealthPanel, p1PokemonHealthPanel, battleDescriptionTextLabel,
									battleDescriptionPanel, battleOptionPanel, fightMenuPanel,
									pokemonMenuPanel, returnButton, oppPokemonHealthBarPanel,
									oppPokemonHealthLeftLabel, true);
						} else if (recentFainted[0] == false) {
							// Delete text
							battleDescriptionTextLabel.setSize(630, 150);
							battleDescriptionTextLabel.setText(null);
							backgroundLayeredPane.remove(continueButton);
							resetMainBattleMenu(p1PokemonPanel, p2PokemonPanel, p1PokemonHealthPanel,
									p2PokemonHealthPanel, battleDescriptionTextLabel, battleDescriptionPanel,
									battleOptionPanel, fightMenuPanel, pokemonMenuPanel, returnButton);
							mainBattle();
						}
					}
				}
				clickCount[0]++;
			}
		});
	}

	public void switchPokemon(Player player, String side, int pokemonChoice, JPanel pokemonPanel, JLabel pokemonLabel,
			JLabel pokemonName, JLabel pokemonLevel, JPanel pokemonHealthBarPanel, JLabel pokemonHeathLeftLabel,
			JLabel battleDescriptionTextLabel) {
		battleDescriptionTextLabel.setText(null);
		battleDescriptionTextLabel
				.setText(player.getName() + " switches in " + player.getPokemon(pokemonChoice).getName() + "!");
		// Change pokemon sprite
		setPokemonSprite(player, side, pokemonChoice, pokemonLabel, pokemonPanel.getWidth(), pokemonPanel.getHeight());
		// Change pokemon info
		pokemonName.setText(null);
		pokemonName.setText(player.getPokemon(pokemonChoice).getName());
		pokemonLevel.setText(null);
		pokemonLevel.setText("Lv: " + player.getPokemon(pokemonChoice).getLevel());
		updatePokemonHealthBar(pokemonHealthBarPanel, pokemonHeathLeftLabel, player, pokemonChoice);
	}

	public void resetMainBattleMenu(JPanel p1PokemonPanel, JPanel p2PokemonPanel, JPanel p1PokemonHealthPanel,
			JPanel p2PokemonHealthPanel, JLabel battleDescriptionTextLabel, JPanel battleDescriptionPanel,
			JPanel battleOptionPanel, JPanel fightMenuPanel, JPanel pokemonMenuPanel, JButton returnButton) {
		battleDescriptionTextLabel.setText(null);
		backgroundLayeredPane.remove(p1PokemonPanel);
		backgroundLayeredPane.remove(p2PokemonPanel);
		backgroundLayeredPane.remove(p1PokemonHealthPanel);
		backgroundLayeredPane.remove(p2PokemonHealthPanel);
		backgroundLayeredPane.remove(battleDescriptionPanel);
		backgroundLayeredPane.remove(battleOptionPanel);
		backgroundLayeredPane.remove(fightMenuPanel);
		backgroundLayeredPane.remove(pokemonMenuPanel);
		backgroundLayeredPane.remove(returnButton);
	}

	public void checkAttack(Player player, Player opponent, int playerPokemon, int oppPokemon, int move,
			JLabel battleDescriptionTextLabel, JPanel oppPokemonHealthBarPanel,
			JLabel oppPokemonHealthLeftLabel) throws FileNotFoundException {
		// Check for pp
		if (player.getPokemon(playerPokemon).getMove(move).getPP() <= 0) {
			battleDescriptionTextLabel.setText(null);
			battleDescriptionTextLabel.setText("<html>" + player.getPokemon(playerPokemon).getMove(move).getName()
					+ " is out of PP!<br/>Pick a different move.<html>");
			// Remove pp by 1 after each move
			player.getPokemon(playerPokemon).getMove(move).decreasePP();
			return;
		}

		// Check for accuracy
		Random rand = new Random();
		int accuracy = rand.nextInt(100) + 1;
		// If accuracy doesn't fall between 0 - move accuracy (eg: 0 - 80), the pokemon
		// misses
		if (!(accuracy <= player.getPokemon(playerPokemon).getMove(move).getAccuracy())) {
			battleDescriptionTextLabel.setText(null);
			battleDescriptionTextLabel
					.setText("<html>" + player.getPokemon(playerPokemon).getName() + " uses "
							+ player.getPokemon(playerPokemon).getMove(move).getName() + ". It misses!<html>");
			// Remove pp by 1 after each move
			player.getPokemon(playerPokemon).getMove(move).decreasePP();
			return;
		}

		// Reset battleDescriptionTextLabel
		battleDescriptionTextLabel.setText(null);

		String battleText = "";
		battleText += "<html>" + player.getPokemon(playerPokemon).getName() + " uses "
				+ player.getPokemon(playerPokemon).getMove(move).getName() + ".<br/>";

		// Status move
		if (player.getPokemon(playerPokemon).getMove(move).getCategory().equals("Status")) {
			StatusMove sm = new StatusMove();
			battleText += sm.useStatusMove(player, opponent, playerPokemon, oppPokemon, move);
		}
		// Attack move
		else {
			// Type Matching
			TypeMatching t = new TypeMatching();
			double eff = t.typeMatch(player.getPokemon(playerPokemon).getMove(move).getType(),
					opponent.getPokemon(oppPokemon));
			if (eff == 2.0 || eff == 4.0) {
				battleText += "It's super effective!<br/>";
			} else if (eff == 0.5 || eff == 0.25) {
				battleText += "It's not really effective...<br/>";
			} else if (eff == 0) {
				battleText += "It has no effect.<br/>";
			}

			// Check for physical or special attack
			int damage = 0;
			if (player.getPokemon(playerPokemon).getMove(move).getCategory().equals("Physical")) {
				damage = player.getPokemon(playerPokemon).attack(opponent.getPokemon(oppPokemon), move, eff);
			} else if (player.getPokemon(playerPokemon).getMove(move).getCategory().equals("Special")) {
				damage = player.getPokemon(playerPokemon).spAttack(opponent.getPokemon(oppPokemon), move, eff);
			}

			// Decrease health
			opponent.getPokemon(oppPokemon).decreaseHealth(damage);
			battleText += player.getPokemon(playerPokemon).getName() + " deals " + damage + " damage to "
					+ opponent.getPokemon(oppPokemon).getName() + ".</html>";
		}
		// Remove pp by 1 after each move
		player.getPokemon(playerPokemon).getMove(move).decreasePP();
		// Print out description
		battleDescriptionTextLabel.setText(battleText);
		// Update opponent health bar
		updatePokemonHealthBar(oppPokemonHealthBarPanel, oppPokemonHealthLeftLabel, opponent, oppPokemon);
	}

	public void setPokemonHealthBar(JPanel healthBarPanel, JLabel healthLeftLabel, Player player, int pokemonChoice) {
		JLayeredPane healthBarLayeredPane = new JLayeredPane();
		healthBarLayeredPane.setBounds(0, 0, healthBarPanel.getWidth(), healthBarPanel.getHeight());
		healthBarPanel.add(healthBarLayeredPane);

		// Scaling to proportion	
		int heatlh_x = (int) (healthBarPanel.getWidth() * 0.23);
		int health_y = (int) (healthBarPanel.getHeight() * 0.26);
		int healthTotal_w = (int) (healthBarPanel.getWidth() * 0.75);
		int health_h = (int) (healthBarPanel.getHeight() * 0.48);
		double healthLeftRatio = (double) player.getPokemon(pokemonChoice).getHPLeft()
				/ player.getPokemon(pokemonChoice).getHPTotal();
		int healthLeft_w = (int) (healthTotal_w * healthLeftRatio);

		// If pokemon health is 0, set healthLeft_w to 1
		if (player.getPokemon(pokemonChoice).getHPLeft() <= 0) {
			healthLeft_w = 1;
		} else if (player.getPokemon(pokemonChoice).getHPLeft() <= 3) {
			healthLeft_w = 4;
		}

		// Set health color according to HPLeft
		// 100% - 50%: green
		// 50% - 15%: yellow
		// 15% - 1%: red
		String healthType = "";
		if ((healthLeftRatio * 100) >= 50) {
			healthType = "images/HealthBar/Health_Green.png";
		} else if (((healthLeftRatio * 100) >= 15) && ((healthLeftRatio * 100) < 50)) {
			healthType = "images/HealthBar/Health_Yellow.png";
		} else {
			healthType = "images/HealthBar/Health_Red.png";
		}

		JLabel healthTotalLabel = createLabel(heatlh_x, health_y, healthTotal_w, health_h);
		setImage("images/HealthBar/Health_Gray.png", healthTotalLabel.getWidth(), healthTotalLabel.getHeight(),
				healthTotalLabel);
		healthBarLayeredPane.add(healthTotalLabel, Integer.valueOf(1));

		healthLeftLabel.setBounds(heatlh_x, health_y, healthLeft_w, health_h);
		setImage(healthType, healthLeftLabel.getWidth(), healthLeftLabel.getHeight(),
				healthLeftLabel);
		healthBarLayeredPane.add(healthLeftLabel, Integer.valueOf(2));

		JLabel healthBarImgLabel = createLabel(0, 0, healthBarPanel.getWidth(), healthBarPanel.getHeight());
		setImage("images/HealthBar/HealthBar_Empty.png", healthBarPanel.getWidth(), healthBarPanel.getHeight(),
				healthBarImgLabel);
		healthBarLayeredPane.add(healthBarImgLabel, Integer.valueOf(3));

		// healthBarImgLabel.setVisible(false);
	}

	public void setPokemonInMenuHealthBar(JPanel healthBarPanel, JLabel healthLeftLabel, Player player,
			int pokemonChoice) {
		JLayeredPane healthBarLayeredPane = new JLayeredPane();
		healthBarLayeredPane.setBounds(0, 0, healthBarPanel.getWidth(), healthBarPanel.getHeight());
		healthBarPanel.add(healthBarLayeredPane);

		// Scaling to proportion
		int healthTotal_w = (int) (healthBarPanel.getWidth() * 0.75);
		int health_h = (int) (healthBarPanel.getHeight() * 0.48);
		double healthLeftRatio = (double) player.getPokemon(pokemonChoice).getHPLeft()
				/ player.getPokemon(pokemonChoice).getHPTotal();
		int healthLeft_w = (int) (healthTotal_w * healthLeftRatio);

		// If pokemon health is 0, set healthLeft_w to 1
		if (player.getPokemon(pokemonChoice).getHPLeft() <= 0) {
			healthLeft_w = 1;
		} else if (player.getPokemon(pokemonChoice).getHPLeft() <= 3) {
			healthLeft_w = 3;
		}

		// Set health color according to HPLeft
		// 100% - 50%: green
		// 50% - 15%: yellow
		// 15% - 1%: red
		String healthType = "";
		if ((healthLeftRatio * 100) >= 50) {
			healthType = "images/HealthBar/Health_Green.png";
		} else if (((healthLeftRatio * 100) >= 15) && ((healthLeftRatio * 100) < 50)) {
			healthType = "images/HealthBar/Health_Yellow.png";
		} else {
			healthType = "images/HealthBar/Health_Red.png";
		}

		JLabel healthTotalLabel = createLabel(178, 53, healthTotal_w, health_h);
		setImage("images/HealthBar/Health_Gray.png", healthTotalLabel.getWidth(), healthTotalLabel.getHeight(),
				healthTotalLabel);
		healthTotalLabel.setVisible(true);
		healthBarLayeredPane.add(healthTotalLabel, Integer.valueOf(1));

		healthLeftLabel.setBounds(healthTotalLabel.getX(), healthTotalLabel.getY(), healthLeft_w, health_h);
		setImage(healthType, healthLeftLabel.getWidth(), healthLeftLabel.getHeight(),
				healthLeftLabel);
		healthBarLayeredPane.add(healthLeftLabel, Integer.valueOf(2));

		JLabel healthBarImgLabel = createLabel(0, 0, healthBarPanel.getWidth(), healthBarPanel.getHeight());
		setImage("images/HealthBar/HealthBar_Empty.png", healthBarPanel.getWidth(), healthBarPanel.getHeight(),
				healthBarImgLabel);
		healthBarLayeredPane.add(healthBarImgLabel, Integer.valueOf(3));
	}

	public void updatePokemonHealthBar(JPanel healthBarPanel, JLabel healthLeftLabel, Player player,
			int pokemonChoice) {
		// Scaling to proportion
		int healthTotal_w = (int) (healthBarPanel.getWidth() * 0.75);
		double healthLeftRatio = (double) player.getPokemon(pokemonChoice).getHPLeft()
				/ player.getPokemon(pokemonChoice).getHPTotal();
		int healthLeft_w = (int) (healthTotal_w * healthLeftRatio);

		// If pokemon health is 0, set healthLeft_w to 1
		if (player.getPokemon(pokemonChoice).getHPLeft() <= 0) {
			healthLeft_w = 1;
		} else if (player.getPokemon(pokemonChoice).getHPLeft() <= 3) {
			healthLeft_w = 4;
		}

		// Set health color according to HPLeft
		// 100% - 50%: green
		// 50% - 15%: yellow
		// 15% - 1%: red
		String healthType = "";
		if ((healthLeftRatio * 100) >= 50) {
			healthType = "images/HealthBar/Health_Green.png";
		} else if (((healthLeftRatio * 100) >= 15) && ((healthLeftRatio * 100) < 50)) {
			healthType = "images/HealthBar/Health_Yellow.png";
		} else {
			healthType = "images/HealthBar/Health_Red.png";
		}

		healthLeftLabel.setSize(new Dimension(healthLeft_w, healthLeftLabel.getHeight()));
		setImage(healthType, healthLeftLabel.getWidth(), healthLeftLabel.getHeight(),
				healthLeftLabel);
	}

	public void setPokemonSprite(Player p, String side, int pokemonChoice, JLabel label, int w, int h) {
		String path = "pokemon_sprites/";
		if (side.equals("front")) {
			path += p.getPokemon(pokemonChoice).getFrontSprite();
		} else if (side.equals("back")) {
			path += p.getPokemon(pokemonChoice).getBackSprite();
		}
		ImageIcon sprite = new ImageIcon(path);
		// Scaling image
		Image imgScale = (sprite.getImage()).getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon scaledSprite = new ImageIcon(imgScale);
		label.setIcon(scaledSprite);
		label.updateUI();
	}

	public void setImage(String img, int width, int height, JLabel label) {
		ImageIcon imgIcon = new ImageIcon(img);
		// Scaling image
		Image imgScale = (imgIcon.getImage()).getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledBackground = new ImageIcon(imgScale);
		label.setIcon(scaledBackground);
	}

	public void creatFont() {
		try {
			buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf")).deriveFont(36f);
			normalFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf")).deriveFont(35f);
			battleFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf")).deriveFont(50f);
			battleButtonFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf"))
					.deriveFont(70f);
			moveFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf")).deriveFont(60f);
			mediumFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf")).deriveFont(43f);
			bigFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/pokemon_fire_red.ttf")).deriveFont(110f);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}

	// Creating Panel method
	public JPanel createPanel(int x, int y, int w, int h, int r, int b, int g, int a) {
		JPanel panel = new JPanel();
		panel.setBounds(x, y, w, h);
		panel.setBackground(new Color(r, b, g, a));
		panel.setLayout(null);
		return panel;
	}

	// Creating Label method
	public JLabel createLabel(String text, int x, int y, int w, int h, int r, int b, int g, int a, Font font) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, w, h);
		label.setForeground(new Color(r, b, g, a));
		label.setFont(font);
		return label;
	}

	public JLabel createLabel(int x, int y, int w, int h) {
		JLabel label = new JLabel();
		label.setBounds(x, y, w, h);
		label.setLayout(null);
		label.setBackground(new Color(0, 0, 0, 0));
		return label;
	}

	// Create Button method
	public JButton createButton(String text, int x, int y, int w, int h, int br, int bb, int bg, int ba, int fr, int fb,
			int fg, int fa, Font font) {
		JButton button = new JButton(text);
		button.setBounds(x, y, w, h);
		button.setBackground(new Color(br, bb, bg, ba));
		button.setForeground(new Color(fr, fb, fg, fa));
		button.setFont(font);
		return button;
	}
}