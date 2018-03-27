package org.nnc.sequences.levenshtein;

import org.testng.annotations.Test;

import static org.nnc.sequences.levenshtein.FuzzyTestUtils.position;
import static org.nnc.sequences.levenshtein.FuzzyTestUtils.state;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class StateTest {
    @Test
    public void string() {
        final State s = state(3, position(1, 3), position(3, 2), position(4, 2));

        assertEquals(s.toString(), "3:{3#2,4#2,1#3}");
    }

    @Test
    public void equals() {
        final State s1 = state(3, position(1, 3), position(3, 2), position(4, 2));
        final State s2 = state(3, position(4, 2), position(1, 3), position(3, 2));

        assertEquals(s1, s2);
    }

    @Test
    public void reduce() {
        final State s1 = state(0,
                position(0, 0),
                position(1, 2),
                position(-1, 1),
                position(-2, 5),
                position(2, 1)
        );

        assertEquals(s1, state(0, position(0, 0)));

        final State s2 = state(1,
                position(1, 2),
                position(2, 3),
                position(3, 3)
        );

        assertEquals(s2, state(1, position(1, 2), position(3, 3)));
    }

    @Test
    public void addI() {
        final State s1 = state(3, position(1, 3), position(3, 2), position(4, 2));
        final State s2 = s1.addI(10);
        final State s3 = s2.addI(2).addI(-12);

        assertNotEquals(s1, s2);
        assertNotEquals(s2, s3);
        assertEquals(s1, s3);
    }
}
