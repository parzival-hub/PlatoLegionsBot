package LegionCards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import Main.GameMain;

public abstract class Card implements Cloneable {

	private CardSocket socket;
	private int xKoordInHand = -1, yKoordInHand = -1;
	private Color teamColor;
	protected int[] stats = new int[4];
	protected int[] originalStats = new int[4];
	protected int range = 1;
	public static int width = 100, height = 150;

	public Card(Color pTeamColor) {
		stats = new int[] { 0, 0, 0, 0 };
		teamColor = pTeamColor;
	}

	public Card(int[] pStats, Color pTeamColor) {
		stats = pStats.clone();
		originalStats = pStats.clone();
		teamColor = pTeamColor;
	}

	public void setSocket(CardSocket pSocket) {
		socket = pSocket;
	}

	public void setXKoordInHand(int i) {
		xKoordInHand = i;
	}

	public void setYKoordInHand(int i) {
		yKoordInHand = i;
	}

	public int getXIndex() {
		return socket.getXIndex();
	}

	public int getYIndex() {
		return socket.getYIndex();
	}

	public int getStat(int i) {
		return stats[i];
	}

	public void initStats(int[] ints) {
		stats = ints.clone();
		originalStats = ints.clone();
	}

	public Color getTeam() {
		return teamColor;
	}

	public ArrayList<Card> getCardsInRange() {
		ArrayList<Card> rList = new ArrayList<Card>();
		boolean oben = false;
		boolean rechts = false;
		boolean unten = false;
		boolean links = false;

		CardSocket[][] spielfeld = getSpielfeld();
		for (int i = 1; i <= range; i++) {
			// Oben
			if (isCardOnKoords(getXIndex(), getYIndex() - i) && !oben) {
				rList.add(spielfeld[getXIndex()][getYIndex() - i].getCard());
				oben = true;
			}

			// Rechts
			if (isCardOnKoords(getXIndex() + i, getYIndex()) && !rechts) {
				rList.add(spielfeld[getXIndex() + i][getYIndex()].getCard());
				rechts = true;
			}

			// Unten
			if (isCardOnKoords(getXIndex(), getYIndex() + i) && !unten) {
				rList.add(spielfeld[getXIndex()][getYIndex() + i].getCard());
				unten = true;
			}
			// Links
			if (isCardOnKoords(getXIndex() - i, getYIndex()) && !links) {
				rList.add(spielfeld[getXIndex() - i][getYIndex()].getCard());
				links = true;
			}
		}
		return rList;
	}

	protected CardSocket[][] getSpielfeld() {
		return socket.getParent();
	}

	protected boolean isCardOnKoords(int testIndexX, int testIndexY) {
		if (koordsValid(testIndexX, testIndexY))
			if (getSpielfeld()[testIndexX][testIndexY].isEmpty() == false)
				return true;
		return false;
	}

	protected boolean koordsValid(int testIndexX, int testIndexY) {
		if (testIndexX >= 0 && testIndexX < 5 && testIndexY >= 0 && testIndexY < 4)
			return true;
		return false;
	}

	public abstract int[][] getClassStats();

	public abstract String getBezeichner();

	@Override
	public abstract Card clone();

	public void bePlayed() {
		attack(true);
	}

	public void attack(boolean sameMoeglich) {
		ArrayList<Card> cardsInRange = getCardsInRange();
		ArrayList<Card> sameStatsCards = new ArrayList<Card>();
		ArrayList<Card> converted = new ArrayList<Card>();

		if (sameMoeglich) {
			// same?
			for (Card card : cardsInRange) {
				if (isAttackAndDefenceSame(card))
					sameStatsCards.add(card);
			}

			// same attack
			if (sameStatsCards.size() > 1) {
				for (Card card : sameStatsCards) {
					if (!card.getTeam().equals(teamColor)) {
						convertCard(card);
						converted.add(card);
					}
				}

				for (Card card : converted) {
					card.attack(false);
				}
			}
		}
		// normal attack
		for (Card card : cardsInRange) {
			if (!card.teamColor.equals(teamColor)) {
				if (canConvertCard(card)) {
					convertCard(card);
				}
			}

		}
	}

	protected void convertCard(Card card) {
		card.convert();
	}

	public void move(int x, int y) throws ArrayIndexOutOfBoundsException {
		if (!koordsValid(getXIndex() + x, getYIndex() + y)
				|| !getSpielfeld()[getXIndex() + x][getYIndex() + y].isEmpty()) {
			System.err.println("Koordinaten fuer Move nicht zulässig!");
			return;
		}
		getSpielfeld()[getXIndex() + x][getYIndex() + y].setCard(this);
		getSpielfeld()[getXIndex() - x][getYIndex() - y].clearCard();
	}

	protected boolean isAttackAndDefenceSame(Card othercard) {
		int defenceOther = 0;
		int attackSelf = 0;

		// Ueber dieser Karte
		if (othercard.getYIndex() < getYIndex()) {
			defenceOther = othercard.defend(this, 2);
			attackSelf = stats[0];
		}

		// Rechts von dieser Karte
		if (othercard.getXIndex() > getXIndex()) {
			defenceOther = othercard.defend(this, 3);
			attackSelf = stats[1];
		}

		// Unter dieser Karte
		if (othercard.getYIndex() > getYIndex()) {
			defenceOther = othercard.defend(this, 0);
			attackSelf = stats[2];
		}

		// Links von dieser Karte
		if (othercard.getXIndex() < getXIndex()) {
			defenceOther = othercard.defend(this, 1);
			attackSelf = stats[3];
		}
		if (defenceOther == attackSelf)
			return true;
		else
			return false;
	}

	protected boolean canConvertCard(Card card) {
		int defenceOther = 0;
		int attackSelf = 0;

		// Ueber dieser Karte
		if (card.getYIndex() < getYIndex()) {
			defenceOther = card.defend(this, 2);
			attackSelf = stats[0];
		}

		// Rechts von dieser Karte
		if (card.getXIndex() > getXIndex()) {
			defenceOther = card.defend(this, 3);
			attackSelf = stats[1];
		}

		// Unter dieser Karte
		if (card.getYIndex() > getYIndex()) {
			defenceOther = card.defend(this, 0);
			attackSelf = stats[2];
		}

		// Links von dieser Karte
		if (card.getXIndex() < getXIndex()) {
			defenceOther = card.defend(this, 1);
			attackSelf = stats[3];
		}

		if (attackSelf == 10 && defenceOther == 1)
			return false;

		else if (attackSelf == 1 && defenceOther == 10)
			return true;

		if (attackSelf > defenceOther)
			return true;
		else
			return false;
	}

	public int defend(Card attacker, int seite) {
		return stats[seite];
	}

	public void convert() {
		if (teamColor.equals(Color.blue)) {
			teamColor = Color.red;
		} else {
			teamColor = Color.blue;
		}
	}

	public void draw(Graphics g) {
		Color tempColor = g.getColor();
		Font tempFont = g.getFont();

		g.setColor(teamColor);
		g.fillRect(getXIndex() * Card.width + Card.width / 20, getYIndex() * Card.height + Card.height / 30,
				Card.width - Card.width / 10, Card.height - Card.height / 15);

		g.setFont(new Font("Normal", Font.BOLD, 20));
		g.setColor(Color.white);
		// Oben
		String statObenString = (stats[0] == 10) ? "A" : String.valueOf(stats[0]);
		g.drawString(statObenString, getXIndex() * Card.width + Card.width / 2 - Card.width / 20,
				getYIndex() * Card.height + Card.height / 6);

		// Rechts
		String statRechtsString = (stats[1] == 10) ? "A" : String.valueOf(stats[1]);
		g.drawString(statRechtsString, getXIndex() * Card.width + Card.width - Card.width / 5,
				getYIndex() * Card.height + Card.height / 2);

		// Unten
		String statUntenString = (stats[2] == 10) ? "A" : String.valueOf(stats[2]);
		g.drawString(statUntenString, getXIndex() * Card.width + Card.width / 2 - Card.width / 20,
				getYIndex() * Card.height + Card.height - Card.height / 10);

		// Links
		String statLinksString = (stats[3] == 10) ? "A" : String.valueOf(stats[3]);
		g.drawString(statLinksString, getXIndex() * Card.width + Card.width / 10,
				getYIndex() * Card.height + Card.height / 2);

		// Klasse in die Mitte
		g.setFont(new Font("Normal", Font.BOLD, 8));
		g.drawString(String.valueOf(getBezeichner()), getXIndex() * Card.width + Card.width / 4,
				getYIndex() * Card.height + Card.height / 2);

		// Restore color and font
		g.setColor(tempColor);
		g.setFont(tempFont);
	}

	public void draw(Graphics g, int x, int y) {
		Color tempColor = g.getColor();
		Font tempFont = g.getFont();

		if (GameMain.selectedCard != null && this.equals(GameMain.selectedCard))
			g.setColor(Color.gray);
		else
			g.setColor(getTeam());
		g.fillRect(x, y, Card.width - Card.width / 10, Card.height - Card.height / 15);

		if (GameMain.selectedCard != null && this.equals(GameMain.selectedCard)) {
			g.setColor(Color.green);
			g.drawRect(x, y, Card.width - Card.width / 10, Card.height - Card.height / 15);
		}

		g.setFont(new Font("Normal", Font.BOLD, 20));
		g.setColor(Color.white);

		// Draw stats
		// Oben
		String statObenString = (stats[0] == 10) ? "A" : String.valueOf(stats[0]);
		g.drawString(statObenString, x + Card.width / 2 - Card.width / 20, y + Card.height / 6);

		// Rechts
		String statRechtsString = (stats[1] == 10) ? "A" : String.valueOf(stats[1]);
		g.drawString(statRechtsString, x + Card.width - Card.width / 5, y + Card.height / 2);

		// Unten
		String statUntenString = (stats[2] == 10) ? "A" : String.valueOf(stats[2]);
		g.drawString(statUntenString, x + Card.width / 2 - Card.width / 20, y + Card.height - Card.height / 10);

		// Links
		String statLinksString = (stats[3] == 10) ? "A" : String.valueOf(stats[3]);
		g.drawString(statLinksString, x + Card.width / 10, y + Card.height / 2);

		// Klasse in die Mitte
		g.setFont(new Font("Normal", Font.BOLD, 8));
		g.drawString(String.valueOf(getBezeichner()), x + Card.width / 4, y + Card.height / 2);

		// Restore color and font
		g.setColor(tempColor);
		g.setFont(tempFont);
	}

	public void downgrade(int amount) {
		for (int i = 0; i < stats.length; i++) {
			stats[i] -= amount;
			if (stats[i] < 1) {
				stats[i] = 1;
				break;
			}
		}
	}

	public void upgrade(int amount) {
		for (int i = 0; i < stats.length; i++) {
			stats[i] += amount;
			if (stats[i] > 10) {
				stats[i] = 10;
				break;
			}
		}
	}

	public void flipStatsY() {
		int temp = stats[0];
		stats[0] = stats[2];
		stats[2] = temp;
	}

	public void flipStatsX() {
		int temp = stats[1];
		stats[1] = stats[3];
		stats[3] = temp;
	}

	public boolean istInBereich(Point pBetrachtet) {
		// Nord betrachtung
		if (pBetrachtet.getX() > xKoordInHand) {
			if (pBetrachtet.getY() > yKoordInHand) {
				if (pBetrachtet.getX() < xKoordInHand + Card.width) {
					if (pBetrachtet.getY() < yKoordInHand + Card.height) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card) {
			Card c = (Card) obj;
			if (c.getTeam().equals(getTeam())) {
				if (c.getBezeichner().equals(getBezeichner())) {
					if (c.getStat(0) == getStat(0) && c.getStat(1) == getStat(1) && c.getStat(2) == getStat(2)
							&& c.getStat(3) == getStat(3))
						return true;
				}
			}
			return false;
		} else
			return super.equals(obj);

	}

	public String statsToString() {
		return "[ " + stats[0] + ", " + stats[1] + ", " + stats[2] + ", " + stats[3] + " ]";
	}

	@Override
	public String toString() {
		return getBezeichner().toString() + " at [ " + getXIndex() + " | " + getYIndex() + " ] with " + statsToString();
	}
}
