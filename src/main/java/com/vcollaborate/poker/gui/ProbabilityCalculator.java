/*
 * Copyright (C) 2012-2015 Christian Sterzl <christian.sterzl@gmail.com>
 *
 * This file is part of poker.
 *
 * V-Collaborate Poker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * V-Collaborate Poker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with V-Collaborate Poker.  If not, see <http://www.gnu.org/licenses/>.
 */
        
package com.vcollaborate.poker.gui;

import com.foldem.Card;
import com.foldem.eval.CachingFSMHandRankEvaluator;
import com.foldem.eval.HandRankEvaluator;
import com.vcollaborate.arrays.ArrayUtils;
import com.vcollaborate.arrays.LongPredicate;
import com.vcollaborate.bitwise.BinaryUtils;
import com.vcollaborate.math.CombinatoricUtils;
import lombok.extern.slf4j.Slf4j;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Slf4j
public class ProbabilityCalculator extends JPanel {

  private static final long serialVersionUID = -811781038681843203L;

  private static final Map<String, Card> CARDS;

  static {
    CARDS = new HashMap<String, Card>();
    for (Card card : Card.values()) {
      CARDS.put(card.toString(), card);
    }
  }

  // long[] unfiltered = BinaryUtils.getAllPermutations(52, 7);
  long[] unfiltered = BinaryUtils.getAllPermutations(2, 52);
  HandRankEvaluator eval = new CachingFSMHandRankEvaluator();

  private JPanel basePanel;

  private JTextField handTF;
  private JTextField riverTF;
  private JButton resetButton;
  private JButton calcButton;

  private JLabel resultW;
  private JLabel resultD;
  private JLabel resultL;

  public ProbabilityCalculator() {
    init();
  }

  private void init() {
    basePanel = new JPanel();
    basePanel.setLayout(new GridLayout(6, 2, 20, 5));

    handTF = new JTextField();
    riverTF = new JTextField();
    basePanel.add(new JLabel("Your hand:"));
    basePanel.add(handTF);
    basePanel.add(new JLabel("River:"));
    basePanel.add(riverTF);

    resetButton = new JButton("Reset");
    resetButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        handTF.setText("");
        riverTF.setText("");
      }
    });

    calcButton = new JButton("Calc");
    calcButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        resultW.setText("");
        resultD.setText("");
        resultL.setText("");

        calc2();
      }
    });

    basePanel.add(resetButton);
    basePanel.add(calcButton);

    resultW = new JLabel("0%");
    resultD = new JLabel("0%");
    resultL = new JLabel("0%");

    basePanel.add(new JLabel("Winning probability:"));
    basePanel.add(resultW);
    basePanel.add(new JLabel("Draw probability:"));
    basePanel.add(resultD);
    basePanel.add(new JLabel("Loosing probability:"));
    basePanel.add(resultL);

    this.add(basePanel, BorderLayout.CENTER);
  }

  protected Card[] getCards(String desc) {
    Card[] cards = new Card[desc.length() / 2];
    for (int i = 0; i < desc.length(); i += 2) {
      cards[i / 2] = CARDS.get(desc.substring(i, i + 2));
    }
    return cards;
  }

  private void calc2() {
    Card[] handCards = getCards(handTF.getText());
    Card[] riverCards = getCards(riverTF.getText());

    BitSet handBS = new BitSet(52);
    BitSet riverBS = new BitSet(52);

    for (int i = 0; i < handCards.length; i++) {
      handBS.set(handCards[i].ordinal());
    }
    for (int i = 0; i < riverCards.length; i++) {
      riverBS.set(riverCards[i].ordinal());
    }

    final long hand = BinaryUtils.fromBitSet(handBS);
    final long river = BinaryUtils.fromBitSet(riverBS);

    // log.debug("Hand: {}", Long.toBinaryString(hand));
    // log.debug("River: {}", Long.toBinaryString(river));
    // log.debug("Together: {}", Long.toBinaryString(hand | river));

    // long[] unfiltered = BinaryUtils.getAllPermutations(52, 2);

    long start = System.currentTimeMillis();

    // log.debug("Duration: {}", System.currentTimeMillis() - start);
    // log.debug("Combinations in total: {}", unfiltered.length); // 600ms

    final long yourcards = hand | river;
    long[] all2Cards =
        ArrayUtils.filter(unfiltered, (int) CombinatoricUtils.combinations(47, 2),
            new LongPredicate() {

              @Override
              public boolean apply(final long input) {
                // log.info("input: {} - {}", Long.toBinaryString(input),
                // BinaryUtils.getHammingDistance(yourcards, input));
                return BinaryUtils.getHammingDistance(yourcards, input) == 7;
              }
            });

    // log.debug("All2Cards options: {}", all2Cards.length);

    // log.debug("Duration: {}", System.currentTimeMillis() - start); // 100ms
    // log.debug("Calculate ranks");

    long w = 0L, d = 0L, l = 0L;

    for (int i = 0; i < all2Cards.length; i++) {

      long flopturn = all2Cards[i];
      long shared = river | flopturn;
      int y = eval.evaluate(hand | shared);

      for (int j = 0; j < all2Cards.length; j++) {
        if (j != i && BinaryUtils.getHammingDistance(all2Cards[i], all2Cards[j]) == 4) {
          // log.info("shared: {}", Long.toBinaryString(shared));
          // log.info("opponentcards: {}", Long.toBinaryString(shared | all2Cards[j]));
          int o = eval.evaluate(shared | all2Cards[j]);
          if (y == o)
            d++;
          else if (y > o)
            w++;
          else
            l++;
        }
      }
    }

    long total = w + l + d;

    // log.debug("Duration: {}", System.currentTimeMillis() - start);
    // log.debug("Wins: {}", w);
    // log.debug("Draws: {}", d);
    // log.debug("Losses: {}", l);
    // log.debug("Total: {}", total);

    double pw = (double) w / (double) total;
    double pd = (double) d / (double) total;
    double pl = (double) l / (double) total;

    log.debug("Duration: {}", System.currentTimeMillis() - start);

    log.debug("Winning probability: {}", pw);
    log.debug("Draw probability: {}", pd);
    log.debug("Loosing probability: {}", pl);

    resultW.setText(String.format("%,.2f%%", pw * 100));
    resultD.setText(String.format("%,.2f%%", pd * 100));
    resultL.setText(String.format("%,.2f%%", pl * 100));
  }

}
