package mhurd.scratch;

import static org.junit.Assert.assertTrue;

public class BitwiseStorage {

    private enum LegProperty {

        HOLD_TOMS_REPORTING(1),
        SKIP_CRITICAL_AMEND_CHECK(2);

        private int bit;

        private LegProperty(int bit) {
            assert(bit % 2 == 0);
            this.bit = bit;
        }

        public int getMask() {
            return bit;
        }

        public static boolean hasAllProperties(int value, LegProperty... properties) {
            for (LegProperty property : properties) {
                if ((value & property.getMask()) != property.getMask()) {
                    return false;
                }
            }
            return true;
        }

    }

    public static int legPropertiesNone = 0; // None
    public static int legProperties1 = 1; // HOLD_TOMS_REPORTING
    public static int legProperties2 = 2; // SKIP_CRITICAL_AMEND_CHECK
    public static int legPropertiesAll = 3; // HOLD_TOMS_REPORTING & SKIP_CRITICAL_AMEND_CHECK
    public static int legPropertiesExtra = 7; // HOLD_TOMS_REPORTING & SKIP_CRITICAL_AMEND_CHECK + extra 4 set

    public static void main(String[] args) {
        assertTrue(!LegProperty.hasAllProperties(legPropertiesNone, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(!LegProperty.hasAllProperties(legPropertiesNone, LegProperty.HOLD_TOMS_REPORTING, LegProperty.SKIP_CRITICAL_AMEND_CHECK));

        assertTrue(LegProperty.hasAllProperties(legProperties1, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(!LegProperty.hasAllProperties(legProperties1, LegProperty.HOLD_TOMS_REPORTING, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(!LegProperty.hasAllProperties(legProperties1, LegProperty.SKIP_CRITICAL_AMEND_CHECK));

        assertTrue(LegProperty.hasAllProperties(legProperties2, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(!LegProperty.hasAllProperties(legProperties2, LegProperty.HOLD_TOMS_REPORTING, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(!LegProperty.hasAllProperties(legProperties2, LegProperty.HOLD_TOMS_REPORTING));

        assertTrue(LegProperty.hasAllProperties(legPropertiesAll, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(LegProperty.hasAllProperties(legPropertiesAll, LegProperty.HOLD_TOMS_REPORTING, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(LegProperty.hasAllProperties(legPropertiesAll, LegProperty.SKIP_CRITICAL_AMEND_CHECK));

        assertTrue(LegProperty.hasAllProperties(legPropertiesExtra, LegProperty.HOLD_TOMS_REPORTING));
        assertTrue(LegProperty.hasAllProperties(legPropertiesExtra, LegProperty.HOLD_TOMS_REPORTING, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
        assertTrue(LegProperty.hasAllProperties(legPropertiesExtra, LegProperty.SKIP_CRITICAL_AMEND_CHECK));
    }

}
