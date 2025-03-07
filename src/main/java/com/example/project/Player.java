package com.example.project;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand; // Player's private hand
    private ArrayList<Card> allCards; // Combined hand and community cards
    String[] suits = Utility.getSuits();
    String[] ranks = Utility.getRanks();

    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() { return hand; }
    public ArrayList<Card> getAllCards() { return allCards; }

    // Adds a card to the player's hand
    public void addCard(Card c) {
        hand.add(c);
    }

    // Determines the player's best possible hand ranking
    public String playHand(ArrayList<Card> communityCards) {      
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards(); 
        String handRank = "Nothing"; // default

        // Check for pairs, two pairs, three of a kind, and full house
        for (Integer count : findRankingFrequency()) {
            if (count == 2) {
                if (handRank.equals("Three of a Kind")) {
                    handRank = "Full House";
                    break;
                }
                if (handRank.equals("A Pair") || handRank.equals("Two Pair")) {
                    handRank = "Two Pair";
                } else if (handRank.equals("Nothing")) {
                    handRank = "A Pair";
                }
            } else if (count == 3) {
                if (handRank.equals("A Pair")) {
                    handRank = "Full House";
                    break;
                }
                handRank = "Three of a Kind";
            }
        }
    
        // Check for a straight
        if (areConsecutive(allCards)) {
            handRank = "Straight";
        }
        
        // Check for a flush
        boolean hasFlush = false;
        for (int count : findSuitFrequency()) {
            if (count == 5) {
                hasFlush = true;
                break;
            }
        }
        if (hasFlush) {
            if (handRank.equals("Straight")) { // if straight + flush
                handRank = "Straight Flush";
            } else {
                handRank = "Flush";
            }
        }

        // Check for four of a kind
        for (Integer count : findRankingFrequency()) {
            if (count == 4 && Utility.getHandRanking(handRank) < Utility.getHandRanking("Four of a Kind")) {
                handRank = "Four of a Kind";
            }
        }
         
        // Check for a royal flush
        for (Card card : allCards) {
            if (Utility.getRankValue(card.getRank()) == 14 && handRank.equals("Straight Flush")) {
                handRank = "Royal Flush";
            }
        }

        // Determine if the highest card belongs to the player or the community
        Card highestCard = allCards.get(allCards.size() - 1);
        boolean highestCardInHand = false;
        for (Card card : hand) {
            if (card.equals(highestCard)) {
                highestCardInHand = true;
                break;
            }
        }
        if (handRank.equals("Nothing")) {
            handRank = highestCardInHand ? "High Card" : "Nothing";
        }
    
        return handRank;
    }
    
    // Checks if the cards form a consecutive sequence (straight)
    private boolean areConsecutive(ArrayList<Card> handRanks) {
        for (int i = 1; i < handRanks.size(); i++) {
            if (Utility.getRankValue(handRanks.get(i).getRank()) != Utility.getRankValue(handRanks.get(i - 1).getRank()) + 1) {
                return false;
            }
        }
        return true;
    }

    // Sorts allCards in ascending order based on rank
    public void sortAllCards() {
        for (int i = 1; i < allCards.size(); i++) {
            Card currentCard = allCards.get(i);
            int currentRankValue = Utility.getRankValue(currentCard.getRank());
            int j = i - 1;

            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > currentRankValue) {
                allCards.set(j + 1, allCards.get(j));
                j--;
            }

            allCards.set(j + 1, currentCard);
        }
    }

    // Returns a list of counts representing how frequently each rank appears
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> freqList = new ArrayList<>();
        for (String rank : ranks) {
            int count = 0;
            for (Card card : allCards) {
                if (card.getRank().equals(rank)) {
                    count++;
                }
            }
            freqList.add(count);
        }
        return freqList; 
    }

    // Returns a list of counts representing how frequently each suit appears
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> freqList = new ArrayList<>();
        for (String suit : suits) {
            int count = 0;
            for (Card card : allCards) {
                if (card.getSuit().equals(suit)) {
                    count++;
                }
            }
            freqList.add(count);
        }      
        return freqList; 
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
