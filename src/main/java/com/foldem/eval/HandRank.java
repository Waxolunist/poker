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

import java.io.Serializable;

import lombok.ToString;

/**
 * Representation of the poker hand strength.
 *
 * @author Robert Strack
 */
@ToString
public class HandRank implements Comparable<HandRank>, Serializable {

  private static final long serialVersionUID = 6897360347770643227L;

  /**
   * Numbers of distinct hand types (high card, pair, two pair, etc.).
   */
  private static final int[] TYPE_COUNT = { 1277, 2860, 858, 858, 10,
      1277, 156, 156, 10 };

  /**
   * Id of the rank (the higher the id is the stronger is the rank).
   */
  private final int id;

  public HandRank(int handRankId) {
    super();
    this.id = handRankId;
  }

  /**
   * Compares the strength of the hand (the values of the ids).
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public final int compareTo(HandRank rank) {
    return id < rank.id ? -1 : (id == rank.id ? 0 : 1);
  }

  @Override
  public final int hashCode() {
    return id;
  }

  @Override
  public final boolean equals(Object object) {
    return (object instanceof HandRank) && (id == ((HandRank) object).id);
  }

  public final int getId() {
    return id;
  }

  /**
   * Calculates the hand type.
   * 
   * @return hand type
   */
  public final HandType getHandType() {
    for (int i = 0, total = 0; i < TYPE_COUNT.length; i++) {
      total += TYPE_COUNT[i];
      if (total > id) {
        return HandType.values()[i];
      }
    }
    throw new EvaluationFailedException("unknown hand type");
  }

}
