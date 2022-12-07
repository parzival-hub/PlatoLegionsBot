package LegionCards;

import java.awt.Color;
import java.util.ArrayList;

public class Lancer extends Card {

	private int[] cardStats1 = new int[] { 6, 4, 6, 4 };
	private int[] cardStats2 = new int[] { 4, 6, 4, 6 };
	private int[] cardStats3 = new int[] { 6, 4, 6, 4 };
	private int[] cardStats4 = new int[] { 4, 6, 4, 6 };
	private int[][] classStats = new int[][] { cardStats1, cardStats2, cardStats3, cardStats4 };

	public Lancer(Color pTeamColor) {
		super(pTeamColor);
	}

	public Lancer(int[] pStats, Color pTeamColor) {
		super(pStats, pTeamColor);
	}

	@Override
	public ArrayList<Card> getCardsInRange() {
		ArrayList<Card> rList = new ArrayList<Card>();

		// Oben
		if (isCardOnKoords(getXIndex(), getYIndex() - 1)) {
			Card cardInRange = getSpielfeld()[getXIndex()][getYIndex() - 1].getCard();
			rList.add(cardInRange);
			if (canConvertCard(cardInRange) && !cardInRange.getTeam().equals(getTeam())) {
				if (isCardOnKoords(getXIndex(), getYIndex() - 2))
					rList.add(getSpielfeld()[getXIndex()][getYIndex() - 2].getCard());

			}
		}

		// Rechts
		if (isCardOnKoords(getXIndex() + 1, getYIndex())) {
			Card cardInRange = getSpielfeld()[getXIndex() + 1][getYIndex()].getCard();
			rList.add(cardInRange);
			if (canConvertCard(cardInRange) && !cardInRange.getTeam().equals(getTeam())) {
				if (isCardOnKoords(getXIndex() + 2, getYIndex()))
					rList.add(getSpielfeld()[getXIndex() + 2][getYIndex()].getCard());

			}
		}

		// Unten
		if (isCardOnKoords(getXIndex(), getYIndex() + 1)) {
			Card cardInRange = getSpielfeld()[getXIndex()][getYIndex() + 1].getCard();
			rList.add(cardInRange);
			if (canConvertCard(cardInRange) && !cardInRange.getTeam().equals(getTeam())) {
				if (isCardOnKoords(getXIndex(), getYIndex() + 2))
					rList.add(getSpielfeld()[getXIndex()][getYIndex() + 2].getCard());

			}
		}

		// Links
		if (isCardOnKoords(getXIndex() - 1, getYIndex())) {
			Card cardInRange = getSpielfeld()[getXIndex() - 1][getYIndex()].getCard();
			rList.add(cardInRange);
			if (canConvertCard(cardInRange) && !cardInRange.getTeam().equals(getTeam())) {
				if (isCardOnKoords(getXIndex() - 2, getYIndex()))
					rList.add(getSpielfeld()[getXIndex() - 2][getYIndex()].getCard());

			}
		}

		return rList;
	}

	@Override
	public String getBezeichner() {
		return "Speermann";
	}

	@Override
	public int[][] getClassStats() {
		return classStats;
	}

	@Override
	public Card clone() {
		return new Lancer(stats.clone(), new Color(getTeam().getRGB()));
	}

}
