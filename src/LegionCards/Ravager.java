package LegionCards;

import java.awt.Color;

public class Ravager extends Card {

	// Ravagers: Captures add+1 to all ranks

	private int[] cardStats1 = new int[] { 2, 6, 4, 8 };
	private int[] cardStats2 = new int[] { 6, 4, 8, 2 };
	private int[] cardStats3 = new int[] { 4, 8, 2, 6 };
	private int[] cardStats4 = new int[] { 8, 2, 6, 4 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Ravager(Color pTeamColor) {
		super(pTeamColor);
	}

	public Ravager(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
	protected void convertCard(Card card) {
		super.convertCard(card);
		upgrade(1);
	}

	@Override
	public void convert() {
		super.convert();
		upgrade(1);
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Ravager";
	}

	@Override
	public Card clone() {
		return new Ravager(stats.clone(), new Color(getTeam().getRGB()));
	}
}
