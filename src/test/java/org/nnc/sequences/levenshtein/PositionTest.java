package org.nnc.sequences.levenshtein;

import org.testng.annotations.Test;

import static org.nnc.sequences.levenshtein.FuzzyTestUtils.position;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class PositionTest {
    @Test
    public void string() {
        assertEquals(position(1, 2).toString(), "1#2");
        assertEquals(position(3, 7).toString(), "3#7");
        assertEquals(position(0, 5).toString(), "0#5");
    }

    @Test
    public void equals() {
        assertEquals(position(1, 2), position(1, 2));
        assertEquals(position(3, 7), position(3, 7));

        assertNotEquals(position(3, 7), position(1, 7));
        assertNotEquals(position(3, 7), position(3, 6));
    }
}
