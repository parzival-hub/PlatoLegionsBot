package Tests;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import LegionCards.Keeper;
import LegionCards.Ravager;
import LegionCards.Saboteur;
import LegionCards.Siren;
import LegionCards.Titan;
import Main.GameMain;

public class TestEngine {
	private GameMain main = new GameMain(true);

	public void tearDown() {
		main.initSpielfeld();
		main.clearHands();
		main.scoreRed = 0;
		main.scoreBlue = 1;
	}

	@Test
	public void test() {
		// SETUP
		main.addStatCardsToHand(new Keeper(Color.red), new Titan(Color.red));
		main.addStatCardsToHand(new Siren(Color.blue), new Keeper(Color.blue));

		main.placeCard(main.getCardFromHand(new Siren(new int[] { 4, 4, 5, 7 }, Color.blue)), GameMain.spielfeld[4][0]);
		main.placeCard(main.getCardFromHand(new Titan(new int[] { 3, 6, 4, 7 }, Color.red)), GameMain.spielfeld[3][0]);
		main.placeCard(main.getCardFromHand(new Siren(new int[] { 7, 4, 4, 5 }, Color.blue)), GameMain.spielfeld[3][3]);
		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 5, 1, 5, 9 }, Color.red)), GameMain.spielfeld[3][0]);

		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 5, 1, 5, 9 }, Color.blue)), GameMain.spielfeld[3][1]);
		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 5, 9, 5, 1 }, Color.red)), GameMain.spielfeld[4][1]);
		main.placeCard(main.getCardFromHand(new Siren(new int[] { 5, 7, 4, 4 }, Color.blue)), GameMain.spielfeld[4][3]);
		main.placeCard(main.getCardFromHand(new Titan(new int[] { 7, 3, 6, 4 }, Color.red)), GameMain.spielfeld[4][1]);

		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 5, 9, 5, 1 }, Color.blue)), GameMain.spielfeld[1][2]);
		main.placeCard(main.getCardFromHand(new Titan(new int[] { 4, 7, 3, 6 }, Color.red)), GameMain.spielfeld[2][2]);
		main.placeCard(main.getCardFromHand(new Siren(new int[] { 4, 5, 7, 4 }, Color.blue)), GameMain.spielfeld[1][1]);
		main.placeCard(main.getCardFromHand(new Titan(new int[] { 6, 4, 7, 3 }, Color.red)), GameMain.spielfeld[3][1]);

		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 1, 5, 9, 5 }, Color.blue)), GameMain.spielfeld[2][0]);
		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 1, 5, 9, 5 }, Color.red)), GameMain.spielfeld[1][0]);
		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 9, 5, 1, 5 }, Color.blue)), GameMain.spielfeld[0][1]);
		main.placeCard(main.getCardFromHand(new Keeper(new int[] { 9, 5, 1, 5 }, Color.red)), GameMain.spielfeld[1][3]);

		assertTrue(GameMain.spielfeld[1][0].getCard().equals(new Keeper(new int[] { 1, 5, 9, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[2][0].getCard().equals(new Keeper(new int[] { 1, 5, 9, 5 }, Color.blue)));
		assertTrue(GameMain.spielfeld[3][0].getCard().equals(new Keeper(new int[] { 5, 1, 5, 9 }, Color.red)));
		assertTrue(GameMain.spielfeld[4][0].getCard().equals(new Siren(new int[] { 5, 7, 4, 4 }, Color.red)));

		assertTrue(GameMain.spielfeld[0][1].getCard().equals(new Keeper(new int[] { 9, 5, 1, 5 }, Color.blue)));
		assertTrue(GameMain.spielfeld[1][1].getCard().equals(new Siren(new int[] { 4, 5, 7, 4 }, Color.blue)));
		assertTrue(GameMain.spielfeld[2][1].getCard().equals(new Keeper(new int[] { 5, 1, 5, 9 }, Color.blue)));
		assertTrue(GameMain.spielfeld[3][1].getCard().equals(new Titan(new int[] { 6, 4, 7, 3 }, Color.red)));
		assertTrue(GameMain.spielfeld[4][1].getCard().equals(new Titan(new int[] { 7, 4, 6, 3 }, Color.red)));

		assertTrue(GameMain.spielfeld[1][2].getCard().equals(new Keeper(new int[] { 5, 1, 5, 9 }, Color.red)));
		assertTrue(GameMain.spielfeld[2][2].getCard().equals(new Titan(new int[] { 4, 7, 3, 6 }, Color.red)));
		assertTrue(GameMain.spielfeld[3][2].getCard().equals(new Titan(new int[] { 4, 7, 3, 6 }, Color.red)));
		assertTrue(GameMain.spielfeld[4][2].getCard().equals(new Keeper(new int[] { 5, 9, 5, 1 }, Color.red)));

		assertTrue(GameMain.spielfeld[1][3].getCard().equals(new Keeper(new int[] { 9, 5, 1, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[3][3].getCard().equals(new Siren(new int[] { 7, 4, 4, 5 }, Color.blue)));
		assertTrue(GameMain.spielfeld[4][3].getCard().equals(new Siren(new int[] { 5, 7, 4, 4 }, Color.blue)));

		assertTrue(main.getBlueScore() == 7);
		assertTrue(main.getRedScore() == 10);

		// TEAR DOWN
		tearDown();
	}

	@Test
	public void test2() {
		// SETUP
		main.addStatCardsToHand(new Saboteur(Color.red), new Ravager(Color.red));
		main.addStatCardsToHand(new Siren(Color.blue), new Saboteur(Color.blue));

		main.placeCard(main.getCardFromHand(new Siren(new int[] { 7, 4, 4, 5 }, Color.blue)), GameMain.spielfeld[2][0]);
		main.placeCard(main.getCardFromHand(new Ravager(new int[] { 2, 6, 4, 8 }, Color.red)), GameMain.spielfeld[1][0]);
		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 6, 5, 4, 5 }, Color.blue)), GameMain.spielfeld[1][1]);
		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 5, 4, 5, 6 }, Color.red)), GameMain.spielfeld[2][1]);

		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 5, 6, 5, 4 }, Color.blue)), GameMain.spielfeld[3][1]);
		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 4, 5, 6, 5 }, Color.red)), GameMain.spielfeld[3][0]);
		main.placeCard(main.getCardFromHand(new Siren(new int[] { 5, 7, 4, 4 }, Color.blue)), GameMain.spielfeld[0][0]);
		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 5, 6, 5, 4 }, Color.red)), GameMain.spielfeld[0][1]);

		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 5, 4, 5, 6 }, Color.blue)), GameMain.spielfeld[2][3]);
		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 6, 5, 4, 5 }, Color.red)), GameMain.spielfeld[4][3]);
		main.placeCard(main.getCardFromHand(new Saboteur(new int[] { 4, 5, 6, 5 }, Color.blue)), GameMain.spielfeld[0][3]);
		main.placeCard(main.getCardFromHand(new Ravager(new int[] { 6, 4, 8, 2 }, Color.red)), GameMain.spielfeld[0][2]);

		main.placeCard(main.getCardFromHand(new Siren(new int[] { 4, 4, 5, 7 }, Color.blue)), GameMain.spielfeld[3][3]);
		main.placeCard(main.getCardFromHand(new Ravager(new int[] { 4, 8, 2, 6 }, Color.red)), GameMain.spielfeld[3][1]);
		main.placeCard(main.getCardFromHand(new Siren(new int[] { 4, 5, 7, 4 }, Color.blue)), GameMain.spielfeld[4][1]);
		main.placeCard(main.getCardFromHand(new Ravager(new int[] { 8, 2, 6, 4 }, Color.red)), GameMain.spielfeld[4][3]);

		assertTrue(GameMain.spielfeld[0][0].getCard().equals(new Siren(new int[] { 5, 7, 4, 4 }, Color.red)));
		assertTrue(GameMain.spielfeld[1][0].getCard().equals(new Ravager(new int[] { 4, 8, 6, 10 }, Color.blue)));
		assertTrue(GameMain.spielfeld[2][0].getCard().equals(new Siren(new int[] { 7, 4, 4, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[3][0].getCard().equals(new Saboteur(new int[] { 3, 4, 5, 4 }, Color.red)));

		assertTrue(GameMain.spielfeld[0][1].getCard().equals(new Saboteur(new int[] { 4, 5, 4, 3 }, Color.red)));
		assertTrue(GameMain.spielfeld[1][1].getCard().equals(new Saboteur(new int[] { 6, 5, 4, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[2][1].getCard().equals(new Saboteur(new int[] { 4, 3, 4, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[3][1].getCard().equals(new Ravager(new int[] { 4, 8, 2, 6 }, Color.red)));
		assertTrue(GameMain.spielfeld[4][1].getCard().equals(new Siren(new int[] { 3, 4, 6, 3 }, Color.blue)));

		assertTrue(GameMain.spielfeld[0][2].getCard().equals(new Ravager(new int[] { 5, 3, 7, 2 }, Color.red)));
		assertTrue(GameMain.spielfeld[3][2].getCard().equals(new Saboteur(new int[] { 3, 4, 3, 2 }, Color.red)));
		assertTrue(GameMain.spielfeld[4][2].getCard().equals(new Saboteur(new int[] { 5, 4, 3, 4 }, Color.red)));

		assertTrue(GameMain.spielfeld[0][3].getCard().equals(new Saboteur(new int[] { 4, 5, 6, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[2][3].getCard().equals(new Saboteur(new int[] { 5, 4, 5, 6 }, Color.blue)));
		assertTrue(GameMain.spielfeld[3][3].getCard().equals(new Siren(new int[] { 2, 2, 3, 5 }, Color.red)));
		assertTrue(GameMain.spielfeld[4][3].getCard().equals(new Ravager(new int[] { 10, 4, 8, 6 }, Color.red)));

		assertTrue(GameMain.spielfeld[4][0].getBombs() == 1);
		assertTrue(GameMain.spielfeld[1][2].getBombs() == 1);
		assertTrue(GameMain.spielfeld[2][2].getBombs() == 2);
		assertTrue(GameMain.spielfeld[1][3].getBombs() == 2);

		assertTrue(main.getBlueScore() == 4);
		assertTrue(main.getRedScore() == 13);

		// TEAR DOWN
		tearDown();
	}

}
