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

package com.foldem;

import static com.foldem.Rank.ACE;
import static com.foldem.Rank.EIGHT;
import static com.foldem.Rank.FIVE;
import static com.foldem.Rank.FOUR;
import static com.foldem.Rank.JACK;
import static com.foldem.Rank.KING;
import static com.foldem.Rank.NINE;
import static com.foldem.Rank.QUEEN;
import static com.foldem.Rank.SEVEN;
import static com.foldem.Rank.SIX;
import static com.foldem.Rank.TEN;
import static com.foldem.Rank.THREE;
import static com.foldem.Rank.TWO;
import static com.foldem.Suit.CLUBS;
import static com.foldem.Suit.DIAMONDS;
import static com.foldem.Suit.HEARTS;
import static com.foldem.Suit.SPADES;

/**
 * Enumeration of possible cards used in Texas hold'em.
 *
 * @author Robert Strack
 */
public enum Card {

  TWO_CLUBS(TWO, CLUBS),
  THREE_CLUBS(THREE, CLUBS),
  FOUR_CLUBS(FOUR, CLUBS),
  FIVE_CLUBS(FIVE, CLUBS),
  SIX_CLUBS(SIX, CLUBS),
  SEVEN_CLUBS(SEVEN, CLUBS),
  EIGHT_CLUBS(EIGHT, CLUBS),
  NINE_CLUBS(NINE, CLUBS),
  TEN_CLUBS(TEN, CLUBS),
  JACK_CLUBS(JACK, CLUBS),
  QUEEN_CLUBS(QUEEN, CLUBS),
  KING_CLUBS(KING, CLUBS),
  ACE_CLUBS(ACE, CLUBS),

  TWO_DIAMONDS(TWO, DIAMONDS),
  THREE_DIAMONDS(THREE, DIAMONDS),
  FOUR_DIAMONDS(FOUR, DIAMONDS),
  FIVE_DIAMONDS(FIVE, DIAMONDS),
  SIX_DIAMONDS(SIX, DIAMONDS),
  SEVEN_DIAMONDS(SEVEN, DIAMONDS),
  EIGHT_DIAMONDS(EIGHT, DIAMONDS),
  NINE_DIAMONDS(NINE, DIAMONDS),
  TEN_DIAMONDS(TEN, DIAMONDS),
  JACK_DIAMONDS(JACK, DIAMONDS),
  QUEEN_DIAMONDS(QUEEN, DIAMONDS),
  KING_DIAMONDS(KING, DIAMONDS),
  ACE_DIAMONDS(ACE, DIAMONDS),

  TWO_HEARTS(TWO, HEARTS),
  THREE_HEARTS(THREE, HEARTS),
  FOUR_HEARTS(FOUR, HEARTS),
  FIVE_HEARTS(FIVE, HEARTS),
  SIX_HEARTS(SIX, HEARTS),
  SEVEN_HEARTS(SEVEN, HEARTS),
  EIGHT_HEARTS(EIGHT, HEARTS),
  NINE_HEARTS(NINE, HEARTS),
  TEN_HEARTS(TEN, HEARTS),
  JACK_HEARTS(JACK, HEARTS),
  QUEEN_HEARTS(QUEEN, HEARTS),
  KING_HEARTS(KING, HEARTS),
  ACE_HEARTS(ACE, HEARTS),

  TWO_SPADES(TWO, SPADES),
  THREE_SPADES(THREE, SPADES),
  FOUR_SPADES(FOUR, SPADES),
  FIVE_SPADES(FIVE, SPADES),
  SIX_SPADES(SIX, SPADES),
  SEVEN_SPADES(SEVEN, SPADES),
  EIGHT_SPADES(EIGHT, SPADES),
  NINE_SPADES(NINE, SPADES),
  TEN_SPADES(TEN, SPADES),
  JACK_SPADES(JACK, SPADES),
  QUEEN_SPADES(QUEEN, SPADES),
  KING_SPADES(KING, SPADES),
  ACE_SPADES(ACE, SPADES);

  /**
   * Card rank.
   */
  private Rank rank;

  /**
   * Card suit.
   */
  private Suit suit;

  private Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * Returns the name of the card (ie. "As" or "Td").
   * 
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return rank.toString() + suit.toString();
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

}
