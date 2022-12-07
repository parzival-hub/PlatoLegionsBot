package Main;

import LegionCards.Card;
import LegionCards.CardSocket;

public class Move {

	private Card card;
	private int xIndex, yIndex;
	private int power;
	private int indexInCardHand;

	public Move(Card card, CardSocket cardSocket, int indexInCardHand) {
		this.card = card;
		xIndex = cardSocket.getXIndex();
		yIndex = cardSocket.getYIndex();
	}

	public void setUpgrade(int power) {
		this.power = power;
	}

	public int getIndexInCardHand() {
		return indexInCardHand;
	}

	public int getUpgrade() {
		return power;
	}

	public int getXIndex() {
		return xIndex;
	}

	public int getYIndex() {
		return yIndex;
	}

	public Card getCard() {
		return card;
	}

	@Override
	public String toString() {
		return card.getBezeichner() + " with " + card.statsToString() + " " + xIndex + " " + yIndex;
	}
}
