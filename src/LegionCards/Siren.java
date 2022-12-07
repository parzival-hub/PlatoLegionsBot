package LegionCards;

import java.awt.Color;

public class Siren extends Card {

	// Sirens: Pulls cards from each direction

	private int[] cardStats1 = new int[] { 7, 4, 4, 5 };
	private int[] cardStats2 = new int[] { 5, 7, 4, 4 };
	private int[] cardStats3 = new int[] { 4, 5, 7, 4 };
	private int[] cardStats4 = new int[] { 4, 4, 5, 7 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Siren(Color pTeamColor) {
		super(pTeamColor);
		range = 4;
	}

	public Siren(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
		range = 4;
	}

	@Override
	public void bePlayed() {
		// Pull cards
		for (Card card : getCardsInRange()) {
			while (card.getXIndex() - getXIndex() > 1)
				card.move(-1, 0);
			while (card.getXIndex() - getXIndex() < -1)
				card.move(1, 0);
			while (card.getYIndex() - getYIndex() > 1)
				card.move(0, -1);
			while (card.getYIndex() - getYIndex() < -1)
				card.move(0, 1);
		}
		super.bePlayed();
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Siren";
	}

	@Override
	public Card clone() {
		return new Siren(stats.clone(), new Color(getTeam().getRGB()));
	}
}
