package LegionCards;

import java.awt.Color;

public class Keeper extends Card {

	// Keepers: Threaten cards at range

	private int[] cardStats1 = new int[] { 9, 5, 1, 5 };
	private int[] cardStats2 = new int[] { 5, 9, 5, 1 };
	private int[] cardStats3 = new int[] { 1, 5, 9, 5 };
	private int[] cardStats4 = new int[] { 5, 1, 5, 9 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Keeper(Color pTeamColor) {
		super(pTeamColor);
		range = 4;
	}

	public Keeper(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
		range = 4;
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Keepers";
	}

	@Override
	public Card clone() {
		return new Keeper(stats.clone(), new Color(getTeam().getRGB()));
	}
}
