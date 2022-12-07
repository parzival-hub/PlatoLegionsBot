package LegionCards;

import java.awt.Color;

public class Saboteur extends Card {

	// Saboteurs: Place bombs on adjacent empty tiles

	private int[] cardStats1 = new int[] { 6, 5, 4, 5 };
	private int[] cardStats2 = new int[] { 5, 6, 5, 4 };
	private int[] cardStats3 = new int[] { 4, 5, 6, 5 };
	private int[] cardStats4 = new int[] { 5, 4, 5, 6 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Saboteur(Color pTeamColor) {
		super(pTeamColor);
	}

	public Saboteur(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
	public void bePlayed() {
		super.bePlayed();
		// Oben
		if (koordsValid(getXIndex(), getYIndex() - 1) && getSpielfeld()[getXIndex()][getYIndex() - 1].isEmpty())
			getSpielfeld()[getXIndex()][getYIndex() - 1].placeBomb();
		// Rechts
		if (koordsValid(getXIndex() + 1, getYIndex()) && getSpielfeld()[getXIndex() + 1][getYIndex()].isEmpty())
			getSpielfeld()[getXIndex() + 1][getYIndex()].placeBomb();
		// Unten
		if (koordsValid(getXIndex(), getYIndex() + 1) && getSpielfeld()[getXIndex()][getYIndex() + 1].isEmpty())
			getSpielfeld()[getXIndex()][getYIndex() + 1].placeBomb();
		// Links
		if (koordsValid(getXIndex() - 1, getYIndex()) && getSpielfeld()[getXIndex() - 1][getYIndex()].isEmpty())
			getSpielfeld()[getXIndex() - 1][getYIndex()].placeBomb();
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public String getBezeichner() {
		return "Saboteurs";
	}

	@Override
	public Card clone() {
		return new Saboteur(stats.clone(), new Color(getTeam().getRGB()));
	}
}
