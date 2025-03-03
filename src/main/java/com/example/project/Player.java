package com.example.project;
import java.util.ArrayList;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        
    }

    public String playHand(ArrayList<Card> communityCards){      
        return "Nothing";
    }

     public void SortCards(){
        for (int i = 1; i < allCards.size(); i++) {
            int k = Utility.getRankValue(allCards.get(i).getRank());
            int j = i - 1;
   
            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > k) {
                allCards.set(j + 1, allCards.get(j));
                j--;
            }
           
            allCards.set(j + 1, allCards.get(k));
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> freqList = new ArrayList<Integer>();
        for (String string : Utility.getRanks()) {
            int count = 0;
            for (int i = 0; i < allCards.size(); i++) {
                if (Utility.getRankValue(allCards.get(i).getRank()) == Integer.parseInt(string)) {
                    count += 1;
                }
            }
            freqList.add(count);
        }

        return freqList; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> freqList = new ArrayList<Integer>();
        for (String string : Utility.getSuits()) {
            int count = 0;
            for (int i = 0; i < allCards.size(); i++) {
                if (allCards.get(i).getSuit().equals(string)) {
                    count += 1;
                }
            }
            freqList.add(count);
        }      

        return freqList; 
    }
   
    @Override
    public String toString(){
        return hand.toString();
    }




}
