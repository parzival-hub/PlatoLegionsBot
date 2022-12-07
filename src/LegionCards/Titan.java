package LegionCards;

import java.awt.Color;

public class Titan extends Card {

	// Titans; Flip ranks of adjacent cards permanently

	private int[] cardStats1 = new int[] { 7, 3, 6, 4 };
	private int[] cardStats2 = new int[] { 4, 7, 3, 6 };
	private int[] cardStats3 = new int[] { 6, 4, 7, 3 };
	private int[] cardStats4 = new int[] { 3, 6, 4, 7 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Titan(Color pTeamColor) {
		super(pTeamColor);
	}

	public Titan(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
	public void bePlayed() {
		for (Card card : getCardsInRange()) {
			// Vertical
			if (card.getYIndex() != getYIndex()) {
				card.flipStatsY();
			}

			// Horizontal
			else if (card.getXIndex() != getXIndex()) {
				card.flipStatsX();
			}
		}
		super.bePlayed();
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Titan";
	}

	@Override
	public Card clone() {
		return new Titan(stats.clone(), new Color(getTeam().getRGB()));
	}
}
