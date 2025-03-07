package com.example.project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Game {
    private static Deck deck;
    private static Player player1;
    private static Player player2;
    private static ArrayList<Card> communityCards;
    private static JPanel player1Panel;
    private static JPanel player2Panel;
    private static JPanel communityPanel;
    private static JLabel resultLabel;

    // Map suit symbols to full suit names
    private static String getFullSuitName(String suitSymbol) {
        switch (suitSymbol) {
            case "♠": return "spades";
            case "♥": return "hearts";
            case "♣": return "clubs";
            case "♦": return "diamonds";
            default: return "";
        }
    }

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

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Poker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.getContentPane().setBackground(Color.GREEN); // Set background to green

        // Set the layout to GridBagLayout for precise component positioning
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Load the logo image (ace card)
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Pchel\\Desktop\\poker\\u7p1-poker-simplified-joshf80\\src\\main\\java\\com\\example\\project\\dep\\start.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setOpaque(false); // Make the label transparent

        // Position the logo in the center of the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Add some space below the logo
        frame.add(logoLabel, gbc);

        // Create the START GAME button
        JButton startButton = new JButton("START GAME");
        startButton.setBackground(Color.GREEN); // Set button background color
        startButton.setForeground(Color.BLACK); // Set button text color

        // Position the button below the logo
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(startButton, gbc);

        // Add ActionListener to the START GAME button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the frame
                frame.getContentPane().removeAll();
                frame.repaint();

                // Set up the game screen
                setupGameScreen(frame);
                frame.getContentPane().setBackground(Color.GREEN); // Ensure background is green
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void setupGameScreen(JFrame frame) {
        // Initialize game components
        deck = new Deck();
        player1 = new Player();
        player2 = new Player();
        communityCards = new ArrayList<>();

        // Deal initial cards
        for (int i = 0; i < 2; i++) {
            player1.addCard(deck.drawCard());
            player2.addCard(deck.drawCard());
        }

        // Deal community cards
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.drawCard());
        }

        // Set the layout for the game screen
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create panels for player and community cards
        player1Panel = new JPanel();
        player1Panel.setBackground(Color.GREEN); // Set panel background to green
        player1Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Add spacing between cards

        player2Panel = new JPanel();
        player2Panel.setBackground(Color.GREEN); // Set panel background to green
        player2Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Add spacing between cards

        communityPanel = new JPanel();
        communityPanel.setBackground(Color.GREEN); // Set panel background to green
        communityPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Add spacing between cards

        // Add panels to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        frame.add(player2Panel, gbc); // Player 2 cards at the top

        gbc.gridy = 1;
        frame.add(communityPanel, gbc); // Community cards in the center

        gbc.gridy = 2;
        frame.add(player1Panel, gbc); // Player 1 cards at the bottom

        // Create a label to display the result
        resultLabel = new JLabel("");
        resultLabel.setForeground(Color.BLACK); // Set text color to black
        resultLabel.setOpaque(false); // Make label transparent
        gbc.gridy = 3;
        frame.add(resultLabel, gbc); // Result label below the cards

        // Create a panel for the game controls
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new FlowLayout());
        gamePanel.setBackground(Color.GREEN); // Set panel background to green

        // Add buttons for game actions
        JButton revealButton = new JButton("Reveal Hands");
        JButton restartButton = new JButton("Restart Game");

        // Style buttons
        revealButton.setBackground(Color.GREEN);
        revealButton.setForeground(Color.BLACK);
        restartButton.setBackground(Color.GREEN);
        restartButton.setForeground(Color.BLACK);

        // Add buttons to the game panel
        gamePanel.add(revealButton);
        gamePanel.add(restartButton);

        // Add the game panel to the frame
        gbc.gridy = 4;
        frame.add(gamePanel, gbc); // Buttons at the very bottom

        // Add ActionListener to the revealButton
        revealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reveal players' hands and community cards
                displayCards(player1.getHand(), player1Panel, "Player 1");
                displayCards(player2.getHand(), player2Panel, "Player 2");
                displayCards(communityCards, communityPanel, "Community Cards");

                // Determine the best hand for each player
                String p1Hand = player1.playHand(communityCards);
                String p2Hand = player2.playHand(communityCards);

                // Determine the winner
                String result = determineWinner(player1, player2, p1Hand, p2Hand, communityCards);
                resultLabel.setText(result);

                // Refresh the frame
                frame.revalidate();
                frame.repaint();
            }
        });

        // Add ActionListener to the restartButton
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset the game
                frame.getContentPane().removeAll();
                frame.repaint();
                setupGameScreen(frame);
            }
        });

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }

    private static void displayCards(ArrayList<Card> cards, JPanel panel, String title) {
        panel.removeAll(); // Clear the panel

        // Add a title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(false);
        panel.add(titleLabel);

        // Add card images to the panel
        for (Card card : cards) {
            String suitName = getFullSuitName(card.getSuit()); // Convert suit symbol to full name
            String imagePath = "C:\\Users\\Pchel\\Desktop\\poker\\u7p1-poker-simplified-joshf80\\src\\main\\java\\com\\example\\project\\dep\\" +
                    suitName + "_" + card.getRank().toLowerCase() + ".png";
            ImageIcon cardIcon = new ImageIcon(imagePath);

            // Resize the card image
            Image cardImage = cardIcon.getImage();
            Image resizedCardImage = cardImage.getScaledInstance(80, 120, Image.SCALE_SMOOTH); // Resize to 80x120 pixels
            ImageIcon resizedCardIcon = new ImageIcon(resizedCardImage);

            JLabel cardLabel = new JLabel(resizedCardIcon);
            panel.add(cardLabel);
        }

        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }
}
