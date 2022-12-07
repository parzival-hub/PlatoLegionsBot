package LegionCards;

import java.awt.Color;

public class Slayer extends Card {

	// Slayers: Exchange ranks with other card before attacking
	private int[] cardStats1 = new int[] { 1, 6, 7, 6 };
	private int[] cardStats2 = new int[] { 6, 1, 6, 7 };
	private int[] cardStats3 = new int[] { 7, 6, 1, 6 };
	private int[] cardStats4 = new int[] { 6, 7, 6, 1 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Slayer(Color pTeamColor) {
		super(pTeamColor);
	}

	public Slayer(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
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
			return true;

		else if (attackSelf == 1 && defenceOther == 10)
			return false;

		if (attackSelf < defenceOther)
			return true;
		else
			return false;
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Slayer";
	}

	@Override
	public Card clone() {
		return new Slayer(stats.clone(), new Color(getTeam().getRGB()));
	}
}
