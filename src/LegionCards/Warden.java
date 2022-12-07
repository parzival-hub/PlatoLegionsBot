package LegionCards;

import java.awt.Color;

public class Warden extends Card {

	private int[] cardStats1 = new int[] { 6, 6, 4, 4 };
	private int[] cardStats2 = new int[] { 4, 6, 6, 4 };
	private int[] cardStats3 = new int[] { 4, 4, 6, 6 };
	private int[] cardStats4 = new int[] { 6, 4, 4, 6 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Warden(Color pTeamColor) {
		super(pTeamColor);
	}

	public Warden(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
	public int defend(Card attacker, int seite) {
		if (attacker.getTeam().equals(getTeam()))
			return super.defend(attacker, seite);
		else {
			return super.defend(attacker, seite) + 1;
		}
	}

	@Override
	public String getBezeichner() {
		return "Warden";
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public Card clone() {
		return new Warden(stats.clone(), new Color(getTeam().getRGB()));
	}
}
