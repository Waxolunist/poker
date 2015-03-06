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

package com.foldem.eval;

import com.foldem.Card;
import com.foldem.util.ResourceReader;
import com.vcollaborate.bitwise.BinaryUtils;

/**
 * Hand rank evaluator implementation. Uses Finite State Machines for evaluation.
 * 
 * @author Robert Strack
 */
public class FSMHandRankEvaluator implements HandRankEvaluator {

  /**
   * Path to the transition table used for evaluation "rank" hand types.
   */
  public static final String RANKS_TRANSITIONS_RESOURCE_NAME = "/rank.states.dat";

  /**
   * Path to the transition table used for evaluation "suit hand types" (flushes).
   */
  public static final String SUIT_TRANSITIONS_RESOURCE_NAME = "/suit.states.dat";

  /**
   * Path to the table holding strength of the "suit hand types" (flushes).
   */
  public static final String SUIT_VALUES_RESOURCE_NAME = "/suit.trans.dat";

  /**
   * Transition table used for evaluation "rank" hand types.
   */
  private static final short[][] RANKS_TRANSITIONS;

  /**
   * Transition table used for evaluation "suit hand types" (flushes).
   */
  private static final short[][] SUIT_TRANSITIONS;

  /**
   * Table holding strength of the "suit hand types" (flushes).
   */
  private static final short[][] SUIT_VALUES;

  static {
    // initialization
    RANKS_TRANSITIONS = ResourceReader.loadResource(RANKS_TRANSITIONS_RESOURCE_NAME);
    SUIT_TRANSITIONS = ResourceReader.loadResource(SUIT_TRANSITIONS_RESOURCE_NAME);
    SUIT_VALUES = ResourceReader.loadResource(SUIT_VALUES_RESOURCE_NAME);
  }

  @Override
  public final int evaluate(int... cardOrdinals) {
    Card[] cards = new Card[cardOrdinals.length];
    for (int i = 0; i < cardOrdinals.length; i++) {
      cards[i] = Card.values()[cardOrdinals[i]];
    }
    return evaluate(cards);
  }

  @Override
  public final int evaluate(long cards) {
    return evaluate(BinaryUtils.getBitsSet(cards));
  }

  @Override
  public final int evaluate(Card... cards) {
    int rankState = 0;
    int[] suitValue = new int[4];
    int[] suitState = new int[4];

    int rank, suit;

    // following code looks ugly, but is about 10% faster than a single loop
    for (int i = 0; i < 4; i++) {
      rank = cards[i].getRank().ordinal();
      suit = cards[i].getSuit().ordinal();
      rankState = RANKS_TRANSITIONS[rankState][rank];
      suitState[suit] = SUIT_TRANSITIONS[suitState[suit]][rank];
    }

    for (int i = 4; i < 6; i++) {
      rank = cards[i].getRank().ordinal();
      suit = cards[i].getSuit().ordinal();
      rankState = RANKS_TRANSITIONS[rankState][rank];
      suitValue[suit] = SUIT_VALUES[suitState[suit]][rank];
      suitState[suit] = SUIT_TRANSITIONS[suitState[suit]][rank];
    }

    rank = cards[6].getRank().ordinal();
    suit = cards[6].getSuit().ordinal();
    rankState = RANKS_TRANSITIONS[rankState][rank];
    suitValue[suit] = SUIT_VALUES[suitState[suit]][rank];

    int value = suitValue[0] | suitValue[1] | suitValue[2] | suitValue[3];
    return rankState > value ? rankState : value;
  }

}
