import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Functions {
    public static int  rankValue(String rank) {//Method to turn rank into int
        return switch (rank) {
            case "TWO" -> 2;
            case "THREE" -> 3;
            case "FOUR" -> 4;
            case "FIVE" -> 5;
            case "SIX" -> 6;
            case "SEVEN" -> 7;
            case "EIGHT" -> 8;
            case "NINE" -> 9;
            case "TEN" -> 10;
            case "JACK" -> 11;
            case "QUEEN" -> 12;
            case "KING" -> 13;
            case "ACE" -> 14;
            default -> 0;
        };
    }


    public static void sortHands(List<List<String>> hands) {
        for (List<String> hand : hands) {
            hand.sort(new CardComparator());
        }
    }

    // Функция для проверки победителя
    public static void checkWinner(List<String> playerList, List<String> botList, List<String> durakList) {
        if (playerList.isEmpty()) {
            ResultHandler.who(1);
            new CustomDialogExample(2);
        } else if (botList.isEmpty() || durakList.isEmpty()) {
            ResultHandler.who(2);
            new CustomDialogExample(2);
        }
    }

    // Основной метод для сортировки и проверки
    public static void sortAndCheck(List<String> playerList, List<String> botList, List<String> durakList) {
        sortHands(Arrays.asList(playerList, botList, durakList));
        checkWinner(playerList, botList, durakList);  // Проверяем победителя
    }

    //Method to end game when one of lists is empty
    public static void endGame(List<?> playerList, List<?> botList, List<?> durak, boolean isMain) {
        if (playerList.isEmpty()) {
            if (isMain) {
                MainBeta2.who(true);
                new CustomDialogExample(1);
            } else {
                ResultHandler.who(1);
                new CustomDialogExample(2);
            }
        } else if (botList.isEmpty() || (durak != null && durak.isEmpty())) {
            if (isMain) {
                MainBeta2.who(false);
                new CustomDialogExample(1);
            } else {
                ResultHandler.who(2);
                new CustomDialogExample(2);
            }
        }
    }



    public static void give(Deck deck, List<String> list) {
        if (deck != null && deck.size() > 0) {
            Card card = deck.drawCard();
            list.add(String.valueOf(card));
        }

    }

    //Method to sort cards
    public static class CardComparator implements Comparator<String> {
        @Override
        public int compare(String card1, String card2) {//Checks which card is trumps
            boolean isTrump1 = card1.contains(Variables.getTrump());
            boolean isTrump2 = card2.contains(Variables.getTrump());

            if (isTrump1 && isTrump2) {
                return compareRank(card1, card2);
            } else if (isTrump1) {
                return 1;
            } else if (isTrump2) {
                return -1;
            } else {
                return compareRank(card1, card2);
            }
        }

        private int compareRank(String card1, String card2) {//Compares ranks
            //splits the cards into rank and suit
            String[] parts1 = card1.split("\\s+");
            String[] parts2 = card2.split("\\s+");
            String rank1 = parts1[0];
            String rank2 = parts2[0];
            String suit1 = parts1[1];
            String suit2 = parts2[1];

            //comparing ranks
            int rankComparison = Integer.compare(rankValue(rank1), rankValue(rank2));
            if (rankComparison != 0) {
                return rankComparison;
            } else {
                return suit1.compareTo(suit2);
            }
        }


    }



    public static void refresh(boolean isMain) {
        if (isMain) {
            MainBeta2.updateImages();
            MainBeta2.waitForNextClick();
        } else {
            GameUI.updateImages();
            Game52.waitForNextClick();
        }
    }
    public static void drawCardIfNeeded(List<String> hand, Deck deck) {
        if (hand.size() < 6 && deck.size() > 0) {
            Card card = deck.drawCard();
            hand.add(String.valueOf(card));
        }
    }
    public static int getCardRank(String card) {
        String[] parts = card.split("\\s+");
        return rankValue(parts[0]);
    }

    public static String getCardSuit(String card) {
        return card.split("\\s+")[1];
    }

    public static boolean canBeat(String attackerCard, String defenderCard, String trumpSuit) {
        String attackerSuit = getCardSuit(attackerCard);
        String defenderSuit = getCardSuit(defenderCard);
        int attackerRank = getCardRank(attackerCard);
        int defenderRank = getCardRank(defenderCard);

        if (defenderSuit.equals(attackerSuit)) {
            return defenderRank > attackerRank;
        }

        // Defender can beat if it's trump and attacker is not
        return !attackerSuit.equals(trumpSuit) && defenderSuit.equals(trumpSuit);
    }

    public static ImageIcon getCardIcon(String card) {
        if (card == null || card.isEmpty()) {
            return null;
        }
        String[] parts = card.split("\\s+");
        String rank = parts[0];
        String suit = parts[1];
        String fileName = rank+" "+suit+".png";
        String path = "C:\\Users\\falco\\Desktop\\java\\cards\\" + fileName; // Убедись, что картинки лежат в resources/cards/

        File imgFile = new File(path);
        if (imgFile.exists()) {
            return new ImageIcon(path); // Убираем использование getResource, передаем путь напрямую
        } else {
            System.err.println("Не удалось найти изображение: " + path);
            return null;
        }
    }

}
