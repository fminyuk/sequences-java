package org.nnc.sequences.levenshtein;

import org.testng.annotations.Test;

import static org.nnc.sequences.levenshtein.FuzzyTestUtils.position;
import static org.nnc.sequences.levenshtein.FuzzyTestUtils.state;
import static org.testng.Assert.assertEquals;

public class StateCalcTest {
    @Test
    public void first() {
        final StateCalc calc = new StateCalc(0);

        assertEquals(calc.first(), state(0, position(0, 0)));
    }

    @Test
    public void costN0() {
        final StateCalc calc = new StateCalc(0);

        final State s1 = calc.first();
        assertEquals(calc.cost(s1, -1), 1);
        assertEquals(calc.cost(s1, 0), 0);
        assertEquals(calc.cost(s1, 1), 1);

        final State s2 = s1.addI(1);
        assertEquals(calc.cost(s2, 0), 1);
        assertEquals(calc.cost(s2, 1), 0);
        assertEquals(calc.cost(s2, 2), 1);
    }

    @Test
    public void costN1() {
        final StateCalc calc = new StateCalc(1);

        final State s1 = state(0, position(0, 0));
        assertEquals(calc.cost(s1, 0), 0);
        assertEquals(calc.cost(s1, 1), 1);
        assertEquals(calc.cost(s1, 2), 2);

        final State s2 = state(0, position(0, 1), position(1, 1));
        assertEquals(calc.cost(s2, 0), 1);
        assertEquals(calc.cost(s2, 1), 1);
        assertEquals(calc.cost(s2, 2), 2);

        final State s3 = state(1, position(0, 1), position(2, 1));
        assertEquals(calc.cost(s3, 0), 1);
        assertEquals(calc.cost(s3, 1), 2);
        assertEquals(calc.cost(s3, 2), 1);
    }

    @Test
    public void costN2() {
        final StateCalc calc = new StateCalc(2);

        final State s1 = state(0, position(0, 1), position(1, 1));
        assertEquals(calc.cost(s1, 0), 1);
        assertEquals(calc.cost(s1, 1), 1);
        assertEquals(calc.cost(s1, 2), 2);

        final State s2 = state(2, position(0, 2), position(2, 1));
        assertEquals(calc.cost(s2, 0), 2);
        assertEquals(calc.cost(s2, 1), 2);
        assertEquals(calc.cost(s2, 2), 1);
        assertEquals(calc.cost(s2, 3), 2);
        assertEquals(calc.cost(s2, 4), 3);
    }

    @Test
    public void nextN0() {
        final StateCalc calc = new StateCalc(0);

        final State s = calc.first();

        final State s0 = calc.next(s, 0b0);
        final State s1 = calc.next(s, 0b1);

        assertEquals(s0.getPositions().size(), 0);
        assertEquals(s1.getPositions().size(), 1);
        assertEquals(s1.getPositions().get(0), position(1, 0));
    }

    @Test
    public void nextN1() {
        final StateCalc calc = new StateCalc(1);

        final State s = calc.first();

        final State s000 = calc.next(s, 0b000).addI(-1);
        final State s001 = calc.next(s, 0b001).addI(-1);
        final State s010 = calc.next(s, 0b010).addI(-1);
        final State s011 = calc.next(s, 0b011).addI(-1);
        final State s100 = calc.next(s, 0b100).addI(-1);
        final State s101 = calc.next(s, 0b101).addI(-1);
        final State s110 = calc.next(s, 0b110).addI(-1);
        final State s111 = calc.next(s, 0b111).addI(-1);

        assertEquals(s010.getPositions().size(), 1);
        assertEquals(s000.getPositions().size(), 2);
        assertEquals(s001.getPositions().size(), 3);

        assertEquals(s000, s100);
        assertEquals(s001, s101);
        assertEquals(s010, s011);
        assertEquals(s010, s110);
        assertEquals(s010, s111);
        assertEquals(s010, s);

        final State s000n000 = calc.next(s000, 0b000).addI(-1);
        final State s000n001 = calc.next(s000, 0b001).addI(-1);
        final State s000n010 = calc.next(s000, 0b010).addI(-1);
        final State s000n011 = calc.next(s000, 0b011).addI(-1);
        final State s000n100 = calc.next(s000, 0b100).addI(-1);
        final State s000n101 = calc.next(s000, 0b101).addI(-1);
        final State s000n110 = calc.next(s000, 0b110).addI(-1);
        final State s000n111 = calc.next(s000, 0b111).addI(-1);

        assertEquals(s000n000.getPositions().size(), 0);
        assertEquals(s000n001.getPositions().size(), 0);
        assertEquals(s000n010.getPositions().size(), 1);
        assertEquals(s000n011.getPositions().size(), 1);
        assertEquals(s000n100.getPositions().size(), 1);
        assertEquals(s000n101.getPositions().size(), 1);
        assertEquals(s000n110.getPositions().size(), 2);
        assertEquals(s000n111.getPositions().size(), 2);

        final State s001n000 = calc.next(s001, 0b000).addI(-1);
        final State s001n001 = calc.next(s001, 0b001).addI(-1);
        final State s001n010 = calc.next(s001, 0b010).addI(-1);
        final State s001n011 = calc.next(s001, 0b011).addI(-1);
        final State s001n100 = calc.next(s001, 0b100).addI(-1);
        final State s001n101 = calc.next(s001, 0b101).addI(-1);
        final State s001n110 = calc.next(s001, 0b110).addI(-1);
        final State s001n111 = calc.next(s001, 0b111).addI(-1);

        assertEquals(s001n000.getPositions().size(), 0);
        assertEquals(s001n001.getPositions().size(), 1);
        assertEquals(s001n010.getPositions().size(), 1);
        assertEquals(s001n011.getPositions().size(), 2);
        assertEquals(s001n100.getPositions().size(), 1);
        assertEquals(s001n101.getPositions().size(), 2);
        assertEquals(s001n110.getPositions().size(), 2);
        assertEquals(s001n111.getPositions().size(), 3);

        final State s100n000 = calc.next(s100, 0b000).addI(-1);
        final State s100n001 = calc.next(s100, 0b001).addI(-1);
        final State s100n010 = calc.next(s100, 0b010).addI(-1);
        final State s100n011 = calc.next(s100, 0b011).addI(-1);
        final State s100n100 = calc.next(s100, 0b100).addI(-1);
        final State s100n101 = calc.next(s100, 0b101).addI(-1);
        final State s100n110 = calc.next(s100, 0b110).addI(-1);
        final State s100n111 = calc.next(s100, 0b111).addI(-1);

        assertEquals(s100n000.getPositions().size(), 0);
        assertEquals(s100n001.getPositions().size(), 0);
        assertEquals(s100n010.getPositions().size(), 1);
        assertEquals(s100n011.getPositions().size(), 1);
        assertEquals(s100n100.getPositions().size(), 1);
        assertEquals(s100n101.getPositions().size(), 1);
        assertEquals(s100n110.getPositions().size(), 2);
        assertEquals(s100n111.getPositions().size(), 2);
    }

    @Test
    public void nextN2() {
        final StateCalc calc = new StateCalc(2);

        final State s = calc.first();

        final State s00000 = calc.next(s, 0b00000).addI(-1);
        final State s00001 = calc.next(s, 0b00001).addI(-1);
        final State s00010 = calc.next(s, 0b00010).addI(-1);
        final State s00011 = calc.next(s, 0b00011).addI(-1);
        final State s00100 = calc.next(s, 0b00100).addI(-1);

        assertEquals(s00000.getPositions().size(), 2);
        assertEquals(s00000.getPositions().get(0), position(-1, 1));
        assertEquals(s00000.getPositions().get(1), position(0, 1));

        assertEquals(s00001.getPositions().size(), 3);
        assertEquals(s00001.getPositions().get(0), position(-1, 1));
        assertEquals(s00001.getPositions().get(1), position(0, 1));
        assertEquals(s00001.getPositions().get(2), position(2, 2));

        assertEquals(s00010.getPositions().size(), 3);
        assertEquals(s00010.getPositions().get(0), position(-1, 1));
        assertEquals(s00010.getPositions().get(1), position(0, 1));
        assertEquals(s00010.getPositions().get(2), position(1, 1));

        assertEquals(s00011, s00010);
        assertEquals(s00100, s);
    }
}
