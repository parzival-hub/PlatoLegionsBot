package LegionCards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Main.GameMain;

public class CardSocket {

	private Card card;
	private int xIndex, yIndex;
	private int bombCount;
	private CardSocket[][] parent;

	public CardSocket(int px, int py, CardSocket[][] pParent) {
		xIndex = px;
		yIndex = py;
		parent = pParent;
	}

	public void clearCard() {
		card = null;
	}

	public void setCard(Card pcard) {
		card = pcard;
		card.setSocket(this);
		if (bombCount > 0)
			card.downgrade(bombCount);
		removeBombs();
	}

	public Card getCard() {
		return card;
	}

	public boolean isEmpty() {
		return (card == null);
	}

	public void setBombs(int i) {
		bombCount = i;
		;
	}

	public void placeBomb() {
		bombCount++;
	}

	public int getBombs() {
		return bombCount;
	}

	public void removeBombs() {
		bombCount = 0;
	}

	public int getXIndex() {
		return xIndex;
	}

	public int getYIndex() {
		return yIndex;
	}

	public CardSocket[][] getParent() {
		return parent;
	}

	public void setParent(CardSocket[][] parent) {
		this.parent = parent;
	}

	public boolean istInBereich(Point pBetrachtet) {
		// Nord betrachtung
		if (pBetrachtet.getX() > xIndex * Card.width) {
			if (pBetrachtet.getY() > yIndex * Card.height) {
				if (pBetrachtet.getX() < xIndex * Card.width + Card.width) {
					if (pBetrachtet.getY() < yIndex * Card.height + Card.height) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isUntouched() {
		return (isEmpty() && getBombs() == 0);
	}

	@Override
	public CardSocket clone() {
		CardSocket socketClone = new CardSocket(xIndex, yIndex, null);
		if (!isEmpty())
			socketClone.setCard(card.clone());
		socketClone.setBombs(bombCount);
		return socketClone;
	}

	public void draw(Graphics g) {
		Color tempColor = g.getColor();
		g.drawRect(xIndex * Card.width, yIndex * Card.height, Card.width, Card.height);

		if (GameMain.selectedCardSocket != null && GameMain.selectedCardSocket.equals(this)) {
			g.setColor(Color.gray);
			g.fillRect(xIndex * Card.width, yIndex * Card.height, Card.width, Card.height);
		}

		// Draw card
		g.setColor(tempColor);
		if (card != null)
			card.draw(g);

		// Draw Bombs
		g.setColor(Color.black);
		if (bombCount > 0)
			g.drawString("Bombs: " + String.valueOf(bombCount), xIndex * Card.width + Card.width / 4,
					yIndex * Card.height + Card.height - 5);
		g.setColor(tempColor);
	}

	@Override
	public String toString() {
		return "X: " + xIndex + " Y: " + yIndex;
	}
}
