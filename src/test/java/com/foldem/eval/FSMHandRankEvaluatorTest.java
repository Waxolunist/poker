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

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

import com.foldem.Card;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test for Finite State Machine hand rank evaluator.
 * 
 * @author Robert Strack
 */
@Slf4j
public class FSMHandRankEvaluatorTest {

	private static final int PERF_REP_NUM = 20_000_000;

	private static final Map<String, Card> CARDS;

	static {
		CARDS = new HashMap<String, Card>();
		for (Card card : Card.values()) {
			CARDS.put(card.toString(), card);
		}
	}

	@Test
	public void testRankTypes() {
		HandRankEvaluator evaluator = new FSMHandRankEvaluator();
		int rank = 0;

		rank = evaluator.evaluate(getCards("AsKhQdJc9s8h7d"));
		assertEquals(1276, rank);

		rank = evaluator.evaluate(getCards("QsAhKdJcAs9h8d"));
		assertEquals(4136, rank);

		rank = evaluator.evaluate(getCards("QsAhKdJcAs9hKs"));
		assertEquals(4994, rank);

		rank = evaluator.evaluate(getCards("QsAhKdJcAsAc9s"));
		assertEquals(5852, rank);

		rank = evaluator.evaluate(getCards("2s4h3d5c8s6c8s"));
		assertEquals(5853, rank);

		rank = evaluator.evaluate(getCards("QsAhKdJcAsAcTs"));
		assertEquals(5862, rank);

		rank = evaluator.evaluate(getCards("2s4s3s5sAh7sAc"));
		assertEquals(5863, rank);

		rank = evaluator.evaluate(getCards("QhAhKhJhAs9hTs"));
		assertEquals(7139, rank);

		rank = evaluator.evaluate(getCards("2s3h3s5s2c7s2d"));
		assertEquals(7140, rank);

		rank = evaluator.evaluate(getCards("Ks5sAc7sAdAsKh"));
		assertEquals(7295, rank);

		rank = evaluator.evaluate(getCards("AsAhAdAc9s8hKd"));
		assertEquals(7451, rank);

		rank = evaluator.evaluate(getCards("3h4cAc2h6h5h4h"));
		assertEquals(7452, rank);

		rank = evaluator.evaluate(getCards("AcQc5hTcJc2cKc"));
		assertEquals(7461, rank);
	}

	protected Card[] getCards(String desc) {
		Card[] cards = new Card[desc.length() / 2];
		for (int i = 0; i < desc.length(); i += 2) {
			cards[i / 2] = CARDS.get(desc.substring(i, i + 2));
		}
		return cards;
	}

	@Test
	public void testPerformance() {
		HandRankEvaluator evaluator = new FSMHandRankEvaluator();
		Card[] cards = getCards("AcKcQcJcTc9c8c");
		long start = currentTimeMillis();
		for (int i = 0; i < PERF_REP_NUM; i++) {
			evaluator.evaluate(cards);
		}
		long stop = currentTimeMillis();

		log.info("Performance: {}", stop - start);
	}

}
