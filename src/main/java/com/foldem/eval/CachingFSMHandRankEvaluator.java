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
import gnu.trove.map.hash.TLongIntHashMap;

public class CachingFSMHandRankEvaluator implements HandRankEvaluator {

  private TLongIntHashMap cache = new TLongIntHashMap();
  private FSMHandRankEvaluator eval = new FSMHandRankEvaluator();

  @Override
  public int evaluate(Card... cards) throws EvaluationFailedException {
    return eval.evaluate(cards);
  }

  @Override
  public int evaluate(int... cardOrdinals) throws EvaluationFailedException {
    return eval.evaluate(cardOrdinals);
  }

  @Override
  public int evaluate(long cards) throws EvaluationFailedException {
    if (cache.containsKey(cards)) {
      return cache.get(cards);
    }
    int retVal = eval.evaluate(cards);
    cache.put(cards, retVal);
    return retVal;
  }

}
