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

/**
 * Evaluator of the Texas Hold'em poker hands. Evalutators need not to be thread safe.
 * 
 * @author Robert Strack
 */
public interface HandRankEvaluator {

  /**
   * Evaluates hand rank. Caller must pass exactly 7 unique cards.
   * 
   * @param cards
   *          exactly 7 unique cards
   * @return hand rank for particular hand
   * @throws EvaluationFailedException
   *           if hand evaluation was impossible for some reason
   */
  int evaluate(Card... cards) throws EvaluationFailedException;

  int evaluate(int... cardOrdinals) throws EvaluationFailedException;

  int evaluate(long cards) throws EvaluationFailedException;
}
