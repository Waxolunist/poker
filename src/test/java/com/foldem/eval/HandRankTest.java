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

import static com.foldem.eval.HandType.FLUSH;
import static com.foldem.eval.HandType.FOUR_OF_A_KIND;
import static com.foldem.eval.HandType.FULL_HOUSE;
import static com.foldem.eval.HandType.HIGH_CARD;
import static com.foldem.eval.HandType.PAIR;
import static com.foldem.eval.HandType.STRAIGHT;
import static com.foldem.eval.HandType.STRAIGHT_FLUSH;
import static com.foldem.eval.HandType.THREE_OF_A_KIND;
import static com.foldem.eval.HandType.TWO_PAIR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HandRankTest {

	public static final int UNIQUE_HAND_RANK_NUMBER = 7462;

	@Test
	public void testGetHandType() {
		assertEquals(new HandRank(0).getHandType(), HIGH_CARD);
		assertEquals(new HandRank(1276).getHandType(), HIGH_CARD);
		assertEquals(new HandRank(1277).getHandType(), PAIR);
		assertEquals(new HandRank(4136).getHandType(), PAIR);
		assertEquals(new HandRank(4137).getHandType(), TWO_PAIR);
		assertEquals(new HandRank(4994).getHandType(), TWO_PAIR);
		assertEquals(new HandRank(4995).getHandType(), THREE_OF_A_KIND);
		assertEquals(new HandRank(5852).getHandType(), THREE_OF_A_KIND);
		assertEquals(new HandRank(5853).getHandType(), STRAIGHT);
		assertEquals(new HandRank(5862).getHandType(), STRAIGHT);
		assertEquals(new HandRank(5863).getHandType(), FLUSH);
		assertEquals(new HandRank(7139).getHandType(), FLUSH);
		assertEquals(new HandRank(7140).getHandType(), FULL_HOUSE);
		assertEquals(new HandRank(7295).getHandType(), FULL_HOUSE);
		assertEquals(new HandRank(7296).getHandType(), FOUR_OF_A_KIND);
		assertEquals(new HandRank(7451).getHandType(), FOUR_OF_A_KIND);
		assertEquals(new HandRank(7452).getHandType(), STRAIGHT_FLUSH);
		assertEquals(new HandRank(7461).getHandType(), STRAIGHT_FLUSH);
	}

	@Test
	public void testEquals() throws Exception {
		assertTrue(new HandRank(0).equals(new HandRank(0)));
		assertFalse(new HandRank(0).equals(new HandRank(1)));
		assertFalse(new HandRank(0).equals(new HandRank(-1)));
	}

	@Test
	public void testCompareTo() throws Exception {
		assertEquals(new HandRank(0).compareTo(new HandRank(1)), -1);
		assertEquals(new HandRank(0).compareTo(new HandRank(0)), 0);
		assertEquals(new HandRank(0).compareTo(new HandRank(-1)), 1);
	}

}
