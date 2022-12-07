package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import LegionCards.Card;
import LegionCards.CardSocket;
import LegionCards.Keeper;
import LegionCards.Siren;
import LegionCards.Titan;

public class GameMain {

	// TODO
	// Game:
	// - Abwechselnde Runden
	// - Karten auswählen menu
	// - Spiel starten und beenden
	// Bot:
	// - neue Klasse Bot
	// - finden des besten zuges durch backtracking
	// Speed verbessern:
	// - ArrayListen durch Array ersetzen

	public static CardSocket[][] spielfeld = new CardSocket[5][4];
	private ArrayList<Card> blueCards = new ArrayList<Card>();
	private ArrayList<Card> redCards = new ArrayList<Card>();
	public static Card selectedCard = null;
	public static CardSocket selectedCardSocket = null;
	public static GamePanel gamePanel;
	private PlayerHandPanel redHandPanel, blueHandPanel;
	private JFrame frame;
	private JLabel lbBlueScore, lbRedScore, lbCurrentTurn;
	public int scoreRed = 0, scoreBlue = 1; // Blau startet mit +1
	public static Color currentTurn = Color.blue;

	// Bot
	private LegionBot bot = new LegionBot(this);
	private ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		new GameMain(false);
	}

	public GameMain(boolean isTested) {
		if (!isTested) {
			addStatCardsToHand(new Keeper(Color.red), new Titan(Color.red));
			addStatCardsToHand(new Siren(Color.blue), new Keeper(Color.blue));
		}

		createWindow();
		frame.setVisible(true);

		placeCard(blueCards.get(0), spielfeld[0][0]);
		placeCard(redCards.get(0), spielfeld[0][1]);
		placeCard(blueCards.get(0), spielfeld[0][2]);
		placeCard(redCards.get(0), spielfeld[0][3]);

		placeCard(blueCards.get(0), spielfeld[1][0]);
		placeCard(redCards.get(0), spielfeld[1][1]);
		placeCard(blueCards.get(0), spielfeld[1][2]);
		placeCard(redCards.get(0), spielfeld[1][3]);

		placeCard(blueCards.get(0), spielfeld[2][0]);
		placeCard(redCards.get(0), spielfeld[2][1]);
		placeCard(blueCards.get(0), spielfeld[2][2]);
		placeCard(redCards.get(0), spielfeld[2][3]);

	}

	public void createWindow() {
		// Frame
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setBounds(500, 50, 500 + 18 + 220 * 2, 600 + 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

		// TopPanelCenter
		JPanel topPanelCenter = new JPanel();
		topPanelCenter.setLayout(new BorderLayout());
		topPanelCenter.setPreferredSize(new Dimension(200, 100));
		topPanel.add(topPanelCenter);
		frame.add(topPanel, BorderLayout.NORTH);

		Font scoreFont = new Font("Score", 0, 25);

		// Blue score
		lbBlueScore = new JLabel("blue: 0");
		lbBlueScore.setFont(scoreFont);
		Border border = lbBlueScore.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		lbBlueScore.setPreferredSize(new Dimension(250, 50));
		lbBlueScore.setBorder(new CompoundBorder(border, margin));
		topPanel.add(lbBlueScore, BorderLayout.WEST);

		// Red score
		lbRedScore = new JLabel("red: 0");
		lbRedScore.setFont(scoreFont);
		border = lbRedScore.getBorder();
		margin = new EmptyBorder(10, 10, 10, 10);
		lbRedScore.setBorder(new CompoundBorder(border, margin));
		lbRedScore.setPreferredSize(new Dimension(250, 50));
		topPanel.add(lbRedScore, BorderLayout.EAST);

		// Action Button
		JButton btAction = new JButton("Make bot move");
		btAction.setFont(scoreFont);
		btAction.addActionListener(x -> click_btAction());
		topPanelCenter.add(btAction, BorderLayout.NORTH);

		// Current turn label
		lbCurrentTurn = new JLabel("Turn: " + colorConvert(currentTurn));
		lbCurrentTurn.setFont(scoreFont);
		border = lbCurrentTurn.getBorder();
		margin = new EmptyBorder(10, 10, 10, 10);
		lbCurrentTurn.setBorder(new CompoundBorder(border, margin));
		lbCurrentTurn.setPreferredSize(new Dimension(250, 50));
		topPanelCenter.add(lbCurrentTurn, BorderLayout.SOUTH);

		// Gamepanel
		gamePanel = new GamePanel();
		frame.add(gamePanel, BorderLayout.CENTER);

		initSpielfeld();

		// Bluecards hand
		blueHandPanel = new PlayerHandPanel(blueCards);
		blueHandPanel.setPreferredSize(new Dimension(220, frame.getHeight()));
		frame.add(blueHandPanel, BorderLayout.WEST);

		// Redcards hand
		redHandPanel = new PlayerHandPanel(redCards);
		redHandPanel.setPreferredSize(new Dimension(220, frame.getHeight()));
		frame.add(redHandPanel, BorderLayout.EAST);
	}

	public void initSpielfeld() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				spielfeld[i][j] = new CardSocket(i, j, GameMain.spielfeld);
			}
		}
	}

	public void clearHands() {
		blueCards.clear();
		redCards.clear();
	}

	private void click_btAction() {
		makeBotMove();
	}

	private String colorConvert(Color c) {
		if (c.equals(Color.red))
			return "red";
		else if (c.equals(Color.blue))
			return "blue";
		else
			return "null";
	}

	private void makeBotMove() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				ArrayList<Card> cardsList = new ArrayList<Card>();

				if (currentTurn.equals(Color.red)) {
					for (Card card : redCards) {
						cardsList.add(card.clone());
					}
				} else {
					for (Card card : blueCards) {
						cardsList.add(card.clone());
					}
				}

				Object[] objCard = cardsList.toArray();
				Card[] cards = new Card[objCard.length];
				for (int i = 0; i < objCard.length; i++) {
					cards[i] = (Card) objCard[i];
				}

				Move bestMove = bot.getBestMove(cards, GameMain.spielfeld);
				if (bestMove == null)
					JOptionPane.showMessageDialog(null, "Best Move ist Null!");
				else {
					placeCard(bestMove.getCard(), bestMove.getXIndex(), bestMove.getYIndex());
				}
				lock.unlock();
			}
		});
		thread.start();
	}

	public void addStatCardsToHand(Card card1, Card card2) {
		addStatCardsToHand(card1);
		addStatCardsToHand(card2);
	}

	public void addStatCardsToHand(Card card) {
		for (int[] cardStats : card.getClassStats()) {
			card.initStats(cardStats);
			if (card.getTeam().equals(Color.blue))
				blueCards.add(card.clone());
			else {
				redCards.add(card.clone());
			}
		}
	}

	public void addCardToHand(int index, Card card) {
		if (card.getTeam().equals(Color.blue))
			blueCards.add(index, card.clone());
		else {
			redCards.add(index, card.clone());
		}
	}

	public void changeTurns() {
		if (currentTurn.equals(Color.blue))
			currentTurn = Color.red;
		else {
			currentTurn = Color.blue;
		}

		lbCurrentTurn.setText("Turn: " + colorConvert(currentTurn));
	}

	public void updateScore() {
		scoreBlue = 1; // Blau startet mit +1
		scoreRed = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (!spielfeld[i][j].isEmpty())
					if (spielfeld[i][j].getCard().getTeam().equals(Color.red))
						scoreRed++;
					else if (spielfeld[i][j].getCard().getTeam().equals(Color.blue))
						scoreBlue++;
			}
		}

		lbBlueScore.setText("blue: " + scoreBlue);
		lbRedScore.setText("red: " + scoreRed);
		lbCurrentTurn.setText("Turn: " + colorConvert(currentTurn));
	}

	public void placeCard(Card card, CardSocket cardSocket) {
		if (cardSocket == null || !cardSocket.isEmpty())
			System.err.println("Konnte nicht platzieren: Card oder Socket null");

		if (!card.getTeam().equals(currentTurn)) {
			System.err.println("Konnte nicht platzieren: Falsches Team ist dran!");
		}

		selectedCard = null;
		selectedCardSocket = null;

		// Karte auf Socket setzen
		cardSocket.setCard(card);

		// Hand Anzeige refresh
		if (card.getTeam().equals(Color.blue))
			blueCards.remove(card);
		else
			redCards.remove(card);

		for (int i = 0; i < blueCards.size(); i++) {
			// wenn i gerade
			if (i % 2 == 0) {
				blueCards.get(i).setXKoordInHand(10);
				blueCards.get(i).setYKoordInHand(10 + (Card.height + 10) * (i / 2));
			} else {
				blueCards.get(i).setXKoordInHand(20 + Card.width);
				blueCards.get(i).setYKoordInHand(10 + (Card.height + 10) * (i / 2));
			}
		}

		for (int i = 0; i < redCards.size(); i++) {
			// wenn i gerade
			if (i % 2 == 0) {
				redCards.get(i).setXKoordInHand(10);
				redCards.get(i).setYKoordInHand(10 + (Card.height + 10) * (i / 2));
			} else {
				redCards.get(i).setXKoordInHand(20 + Card.width);
				redCards.get(i).setYKoordInHand(10 + (Card.height + 10) * (i / 2));
			}
		}

		// Card ausspielen
		card.bePlayed();

		updateScore();
		changeTurns();
		drawAll();
	}

	public void placeCard(Card card, int xKoord, int yKoord) {
		placeCard(card, spielfeld[xKoord][yKoord]);
	}

	public void removeCard(CardSocket socket) {
		socket.setCard(null);
		drawAll();
	}

	public Card getCardFromHand(Card card) {

		if (card.getTeam().equals(Color.blue)) {
			for (Card c : blueCards) {
				if (c.equals(card))
					return c;
			}
		} else {
			for (Card c : redCards) {
				if (c.equals(card))
					return c;
			}
		}

		return null;
	}

	public int getBlueScore() {
		return scoreBlue;
	}

	public int getRedScore() {
		return scoreRed;
	}

	public void drawAll() {
		blueHandPanel.repaint();
		redHandPanel.repaint();
		gamePanel.repaint();
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void drawHand(Graphics g, ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			// wenn i gerade
			if (i % 2 == 0)
				cards.get(i).draw(g, 10, 10 + (Card.height + 10) * (i / 2));
			else
				cards.get(i).draw(g, 20 + Card.width, 10 + (Card.height + 10) * (i / 2));
		}
	}

	public void drawPlayfield(Graphics g) {
		for (CardSocket[] cardSockets : spielfeld) {
			for (CardSocket cardSocket : cardSockets) {
				cardSocket.draw(g);
			}
		}
	}

	public class GamePanel extends JPanel {
		private static final long serialVersionUID = -5672175494771966729L;

		public GamePanel() {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for (CardSocket[] cardSockets : spielfeld) {
						for (CardSocket cardSocket : cardSockets) {
							if (cardSocket.istInBereich(new Point(e.getX(), e.getY()))) {
								if (cardSocket.equals(selectedCardSocket))
									selectedCardSocket = null;
								else {
									selectedCardSocket = cardSocket;
									if (selectedCard != null) {
										placeCard(selectedCard, cardSocket);
									}
								}
							}
						}
					}
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawPlayfield(g);
		}
	}

	public class PlayerHandPanel extends JPanel {
		private static final long serialVersionUID = -5672175494771966729L;
		public ArrayList<Card> cards;

		public PlayerHandPanel(ArrayList<Card> list) {
			cards = list;

			for (int i = 0; i < cards.size(); i++) {
				// wenn i gerade
				if (i % 2 == 0) {
					cards.get(i).setXKoordInHand(10);
					cards.get(i).setYKoordInHand(10 + (Card.height + 10) * (i / 2));
				} else {
					cards.get(i).setXKoordInHand(20 + Card.width);
					cards.get(i).setYKoordInHand(10 + (Card.height + 10) * (i / 2));
				}
			}

			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for (int i = 0; i < cards.size(); i++) {
						Card card = cards.get(i);
						if (card.istInBereich(new Point(e.getX(), e.getY()))) {
							if (card.equals(selectedCard))
								selectedCard = null;
							else {
								selectedCard = card;
							}
						}
					}
					redHandPanel.repaint();
					blueHandPanel.repaint();
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawHand(g, cards);
		}
	}
}
