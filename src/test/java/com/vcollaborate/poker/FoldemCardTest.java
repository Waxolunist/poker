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
        
package com.vcollaborate.poker;

import static org.junit.Assert.assertEquals;

import com.foldem.eval.FSMHandRankEvaluator;
import com.foldem.eval.HandRank;
import com.foldem.eval.HandRankEvaluator;
import com.vcollaborate.arrays.ArrayUtils;
import com.vcollaborate.arrays.LongPredicate;
import com.vcollaborate.bitwise.BinaryStringUtils;
import com.vcollaborate.bitwise.BinaryUtils;
import com.vcollaborate.math.CombinatoricUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class FoldemCardTest {

  @Test
  public void testConvertBinaryToCards() {
    HandRankEvaluator eval = new FSMHandRankEvaluator();

    final long hand = BinaryUtils.allOnes(2);
    final long river = BinaryUtils.allOnes(3) << 2;
    final long flop = BinaryUtils.allOnes(2) << 8;
    long val = hand | river | flop;
    int[] bitsSet = BinaryUtils.getBitsSet(val);
    HandRank hr = new HandRank(eval.evaluate(bitsSet));
    log.info("HandRank: {} ({})", hr, hr.getHandType());

    final long royalflush = BinaryUtils.allOnes(7) << 45;
    log.info("{}", BinaryStringUtils.prettyPrint(Long.toBinaryString(royalflush)));
    int[] bitsSet2 = BinaryUtils.getBitsSet(royalflush);
    HandRank hr2 = new HandRank(eval.evaluate(bitsSet2));
    log.info("HandRank 2: {} ({})", hr2, hr2.getHandType());

  }

  @Test
  public void testProbabilities() {
    final long hand = BinaryUtils.allOnes(2);
    final long river = BinaryUtils.allOnes(3) << 2;
    log.debug("Hand: {}", Long.toBinaryString(hand));
    log.debug("River: {}", Long.toBinaryString(river));
    log.debug("Together: {}", Long.toBinaryString(hand | river));

    long start = System.currentTimeMillis();

    long[] unfiltered = BinaryUtils.getAllPermutations(52, 7);

    log.debug("Duration: {}", System.currentTimeMillis() - start);
    log.debug("Combinations in total: {}", unfiltered.length); // 600ms

    long[] youroptions =
        ArrayUtils.filter(unfiltered, (int) CombinatoricUtils.combinations(47, 2),
            new LongPredicate() {

              @Override
              public boolean apply(final long input) {
                return BinaryUtils.getHammingDistance(hand | river, input) == 2;
              }
            });

    long[] opponentoptions =
        ArrayUtils.filter(unfiltered, (int) CombinatoricUtils.combinations(47, 4),
            new LongPredicate() {

              @Override
              public boolean apply(final long input) {
                return BinaryUtils.getHammingDistance(hand, input) == 9
                    && BinaryUtils.getHammingDistance(river, input) == 4;
              }
            });

    log.debug("Your options: {}", youroptions.length);
    log.debug("Opponent options: {}", opponentoptions.length);

    log.debug("Duration: {}", System.currentTimeMillis() - start); // 300ms
    log.debug("Calculate ranks");

    HandRankEvaluator eval = new FSMHandRankEvaluator();

    // should be parallelized
    long[][] yourranks = new long[youroptions.length][2];
    for (int i = 0; i < yourranks.length; i++) {
      int[] bitsSet = BinaryUtils.getBitsSet(youroptions[i]);
      yourranks[i][0] = youroptions[i];
      yourranks[i][1] = eval.evaluate(bitsSet);
    }

    long[][] opponentranks = new long[opponentoptions.length][2];
    for (int i = 0; i < opponentranks.length; i++) {
      int[] bitsSet = BinaryUtils.getBitsSet(opponentoptions[i]);
      opponentranks[i][0] = opponentoptions[i];
      opponentranks[i][1] = eval.evaluate(bitsSet);
    }

    log.debug("Duration: {}", System.currentTimeMillis() - start); // 250ms
    log.debug("Compare all");

    // should be parallelized
    long w = 0L, d = 0L, l = 0L;
    for (int i = 0; i < yourranks.length; i++) {
      long y = yourranks[i][1];
      for (int j = 0; j < opponentranks.length; j++) {
        long o = opponentranks[j][1];
        if (y == o)
          d++;
        else if (y > o)
          w++;
        else
          l++;
      }
    }

    long total = w + l + d;

    log.debug("Duration: {}", System.currentTimeMillis() - start);
    log.debug("Wins: {}", w);
    log.debug("Draws: {}", d);
    log.debug("Losses: {}", l);
    log.debug("Total: {}", total);

    double pw = (double) w / (double) total;
    double pd = (double) d / (double) total;
    double pl = (double) l / (double) total;

    log.debug("Winning probability: {}", pw);
    log.debug("Draw probability: {}", pd);
    log.debug("Loosing probability: {}", pl);

    log.debug("Duration: {}", System.currentTimeMillis() - start);

    assertEquals(1.0, pw + pd + pl, 0.0);
  }

  @Test
  public void testProbabilities2() {
    final long hand = 0b11L;
    final long river = 0b11100L;
    log.debug("Hand: {}", Long.toBinaryString(hand));
    log.debug("River: {}", Long.toBinaryString(river));
    log.debug("Together: {}", Long.toBinaryString(hand | river));

    long start = System.currentTimeMillis();

    long[] unfiltered = BinaryUtils.getAllPermutations(52, 2);

    log.debug("Duration: {}", System.currentTimeMillis() - start);
    log.debug("Combinations in total: {}", unfiltered.length); // 600ms

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

    log.debug("All2Cards options: {}", all2Cards.length);

    log.debug("Duration: {}", System.currentTimeMillis() - start); // 100ms
    log.debug("Calculate ranks");

    HandRankEvaluator eval = new FSMHandRankEvaluator();
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

    log.debug("Duration: {}", System.currentTimeMillis() - start);
    log.debug("Wins: {}", w);
    log.debug("Draws: {}", d);
    log.debug("Losses: {}", l);
    log.debug("Total: {}", total);

    double pw = (double) w / (double) total;
    double pd = (double) d / (double) total;
    double pl = (double) l / (double) total;

    log.debug("Winning probability: {}", pw);
    log.debug("Draw probability: {}", pd);
    log.debug("Loosing probability: {}", pl);

    log.debug("Duration: {}", System.currentTimeMillis() - start);
  }

}
