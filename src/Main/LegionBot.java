package Main;

import java.awt.Color;
import java.util.ArrayList;

import LegionCards.Card;
import LegionCards.CardSocket;

public class LegionBot {

	private GameMain gameMain;

	public LegionBot(GameMain main) {
		gameMain = main;
	}

	public ArrayList<Move> getPossibleMoves(Object[] cards, CardSocket[][] startSpielfeld) {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		int nextMove = 0;
		for (CardSocket[] cardSockets : startSpielfeld) {
			for (CardSocket cardSocket : cardSockets) {
				if (cardSocket.isEmpty()) {
					for (int i = 0; i < cards.length; i++) {
						possibleMoves.add(new Move((Card) cards[i], cardSocket, i));
					}
				}
			}
		}

		return possibleMoves;
	}

	public int getBlueScore(CardSocket[][] startSpielfeld) {
		int rValue = 0;
		for (int x = 0; x < startSpielfeld.length; x++) {
			for (int y = 0; y < startSpielfeld[x].length; y++) {
				if (!startSpielfeld[x][y].isEmpty() && startSpielfeld[x][y].getCard().getTeam().equals(Color.blue))
					rValue++;
			}
		}
		return rValue;
	}

	public int getRedScore(CardSocket[][] startSpielfeld) {
		int rValue = 0;
		for (int x = 0; x < startSpielfeld.length; x++) {
			for (int y = 0; y < startSpielfeld[x].length; y++) {
				if (!startSpielfeld[x][y].isEmpty() && startSpielfeld[x][y].getCard().getTeam().equals(Color.red))
					rValue++;
			}
		}
		return rValue;
	}

	public int getPossibleMovePower(Card[] cards, CardSocket[][] spielfeld, Color team, Color turn) {
		ArrayList<Move> possibleMoves = getPossibleMoves(cards, spielfeld);
		int power = 0;

		if (possibleMoves.isEmpty()) {
			int punkteBlau = getBlueScore(spielfeld);
			int punkteRot = getRedScore(spielfeld);

			if (team.equals(Color.blue))
				power = getBlueScore(spielfeld) - punkteRot;
			else
				power = getRedScore(spielfeld) - punkteBlau;
			return power;
		}

		for (Move move : possibleMoves) {

			// Copy Spielfeld anlegen
			CardSocket[][] spielFeldCopy = new CardSocket[5][4];
			for (int x = 0; x < spielfeld.length; x++) {
				for (int y = 0; y < spielfeld[x].length; y++) {
					spielFeldCopy[x][y] = spielfeld[x][y].clone();
					spielFeldCopy[x][y].setParent(spielFeldCopy);
				}
			}

			// Karte ausprobieren
			CardSocket socket = spielFeldCopy[move.getXIndex()][move.getYIndex()];
			gameMain.placeCard(move.getCard(), socket);

			// Power ermitteln
			if (team.equals(Color.blue))
				power += getPossibleMovePower(cards, spielFeldCopy, team, Color.red);
			else {
				power += getPossibleMovePower(cards, spielFeldCopy, team, Color.blue);
			}

			// Tear down
			gameMain.changeTurns();
		}

		return power;
	}

	public Move getBestMove(Card[] cards, CardSocket[][] startSpielfeld) {

		Move currentBestMove = null;
		ArrayList<Move> possibleMoves = getPossibleMoves(cards, startSpielfeld);

		if (possibleMoves.isEmpty())
			return null;

		// Copy Spielfeld anlegen
		CardSocket[][] spielFeldCopy = new CardSocket[5][4];
		for (int x = 0; x < startSpielfeld.length; x++) {
			for (int y = 0; y < startSpielfeld[x].length; y++) {
				spielFeldCopy[x][y] = startSpielfeld[x][y].clone();
				spielFeldCopy[x][y].setParent(spielFeldCopy);
			}
		}

		int punkteBlauVorher = getBlueScore(startSpielfeld);
		int punkteRotVorher = getRedScore(startSpielfeld);
		int improvement = 0;

		// Power von jedem Move bestimmen
		for (Move move : possibleMoves) {
			if (move == null)
				break;

			// Karte ausprobieren
			CardSocket socket = spielFeldCopy[move.getXIndex()][move.getYIndex()];
			gameMain.placeCard(move.getCard(), socket);

			// Auswirkungen speichern
			if (move.getCard().getTeam().equals(Color.blue))
				improvement = getBlueScore(spielFeldCopy) - punkteBlauVorher;
			else
				improvement = getRedScore(spielFeldCopy) - punkteRotVorher;
			// System.out.println(move + " Upgrade:" + improvement);
			move.setUpgrade(improvement);

			// Tear down
			gameMain.addCardToHand(move.getIndexInCardHand(), move.getCard());
			gameMain.changeTurns();
			deepCopy(startSpielfeld, spielFeldCopy);
		} // Ende Power move bestimmung

		// Besten move auswählen
		for (Move move : possibleMoves) {
			if (move == null)
				break;

			if (currentBestMove == null || move.getUpgrade() > currentBestMove.getUpgrade()) {
				currentBestMove = move;
			}
		}
		return currentBestMove;
	}

	public Move getBestMoveGraphical(Object[] Objcards, int delay) {
		Card[] cards = new Card[Objcards.length];
		for (int i = 0; i < Objcards.length; i++) {
			cards[i] = (Card) Objcards[i];
		}

		Move currentBestMove = null;
		ArrayList<Move> possibleMoves = getPossibleMoves(cards, GameMain.spielfeld);

		// Copy Spielfeld anlegen
		CardSocket[][] spielFeldCopy = new CardSocket[5][4];
		for (int x = 0; x < GameMain.spielfeld.length; x++) {
			for (int y = 0; y < GameMain.spielfeld[x].length; y++) {
				spielFeldCopy[x][y] = GameMain.spielfeld[x][y].clone();
				spielFeldCopy[x][y].setParent(spielFeldCopy);
			}
		}

		int punkteBlauVorher = gameMain.getBlueScore();
		int punkteRotVorher = gameMain.getRedScore();
		int improvement = 0;

		// Power von jedem Move bestimmen
		for (Move move : possibleMoves) {
			if (move == null)
				break;

			// Auswirkungen speichern
			gameMain.placeCard(move.getCard(), move.getXIndex(), move.getYIndex());
			if (move.getCard().getTeam().equals(Color.blue))
				improvement = gameMain.getBlueScore() - punkteBlauVorher;
			else
				improvement = gameMain.getRedScore() - punkteRotVorher;

			GameMain.sleep(delay);
			move.setUpgrade(improvement);

			// Tear down
			gameMain.addCardToHand(move.getIndexInCardHand(), move.getCard());
			gameMain.changeTurns();
			deepCopy(spielFeldCopy, GameMain.spielfeld);
		} // Ende Power move bestimmung

		// Besten move auswählen
		for (Move move : possibleMoves) {
			if (move == null)
				break;

			if (currentBestMove == null || move.getUpgrade() > currentBestMove.getUpgrade()) {
				currentBestMove = move;
			}
		}
		return currentBestMove;
	}

	private void deepCopy(CardSocket[][] from, CardSocket[][] to) {
		for (int x = 0; x < from.length; x++) {
			for (int y = 0; y < from[x].length; y++) {
				// if (!from[x][y].isUntouched()) {
				to[x][y] = from[x][y].clone();
				// }
				to[x][y].setParent(to);
			}
		}
	}
}
