package com.mhurd.scratch;

import static org.junit.Assert.assertTrue;

public class BitwiseStorage {

    private enum LegProperty {

        HOLD_TOMS_REPORTING(1),
        SKIP_CRITICAL_AMEND_CHECK(2);

        private int bit;

        LegProperty(int bit) {
            assert(bit % 2 == 0);
            this.bit = bit;
        }

        public int getMask() {
            return bit;
        }

        public static boolean hasProperties(int value, LegProperty... properties) {
            for (LegProperty property : properties) {
                if ((value & property.getMask()) != property.getMask()) {
                    return false;
                }
            }
            return true;
        }

    }

    public static void main(String[] args) {

        final int legPropertiesNone = 0; // None
        final int legProperties1 = 1; // HOLD_TOMS_REPORTING
        final int legProperties2 = 2; // SKIP_CRITICAL_AMEND_CHECK
        final int legPropertiesAll = 3; // HOLD_TOMS_REPORTING & SKIP_CRITICAL_AMEND_CHECK
        final int legPropertiesExtra = 7; // HOLD_TOMS_REPORTING & SKIP_CRITICAL_AMEND_CHECK + extra 4 set

        assertTrue(!LegProperty.hasProperties(legPropertiesNone, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(!LegProperty.hasProperties(legPropertiesNone, LegProperty.HOLD_TOMS_REPORTING,
            LegProperty.SKIP_CRITICAL_AMEND_CHECK));

        assertTrue(LegProperty.hasProperties(legProperties1, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(!LegProperty.hasProperties(legProperties1, LegProperty.HOLD_TOMS_REPORTING,
            LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(!LegProperty.hasProperties(legProperties1, LegProperty.SKIP_CRITICAL_AMEND_CHECK));

        assertTrue(LegProperty.hasProperties(legProperties2, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(!LegProperty.hasProperties(legProperties2, LegProperty.HOLD_TOMS_REPORTING,
            LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(!LegProperty.hasProperties(legProperties2, LegProperty.HOLD_TOMS_REPORTING));

        assertTrue(LegProperty.hasProperties(legPropertiesAll, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(LegProperty.hasProperties(legPropertiesAll, LegProperty.HOLD_TOMS_REPORTING,
            LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(LegProperty.hasProperties(legPropertiesAll, LegProperty.SKIP_CRITICAL_AMEND_CHECK));

        assertTrue(LegProperty.hasProperties(legPropertiesExtra, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(LegProperty.hasProperties(legPropertiesExtra, LegProperty.HOLD_TOMS_REPORTING, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(LegProperty.hasProperties(legPropertiesExtra, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
    }

}
