package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        // Value of rankings
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
    
        // Compare hand rankings
        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        }
    
        // If hand rankings are the same, compare high cards
        p1.sortAllCards();
        p2.sortAllCards();
    
        ArrayList<Card> p1Cards = p1.getAllCards();
        ArrayList<Card> p2Cards = p2.getAllCards();
    
        // Compare highest cards one by one from highest to lowest
        for (int i = p1Cards.size() - 1; i >= 0; i--) {
            int p1Value = Utility.getRankValue(p1Cards.get(i).getRank());
            int p2Value = Utility.getRankValue(p2Cards.get(i).getRank());
    
            if (p1Value > p2Value) {
                return "Player 1 wins!";
            } else if (p2Value > p1Value) {
                return "Player 2 wins!";
            }
        }
    
        // If everything is the same, it's a tie
        return "Tie!";
    }    

    public static void play(){ //simulate card playing
    
    }
        
        

}