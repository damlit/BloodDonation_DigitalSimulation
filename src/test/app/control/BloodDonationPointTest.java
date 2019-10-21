package app.control;

import app.entity.BloodUnit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BloodDonationPointTest {
    private static final BloodDonationPoint BLOOD_DONATION_POINT = new BloodDonationPoint();
    private static final double EXPIRATION_TIME_1 = 1;
    private static final double EXPIRATION_TIME_2 = 2;
    private static final double EXPIRATION_TIME_3 = 3;
    private static final int FIRST_BLOOD_ID = 2;
    private static final int SECOND_BLOOD_ID = 0;
    private static final int THIRD_BLOOD_ID = 1;
    private static final int BLOODS_AMOUNT = 3;

    @Before
    public void before() {
        BLOOD_DONATION_POINT.addBlood(new BloodUnit(EXPIRATION_TIME_2));
        BLOOD_DONATION_POINT.addBlood(new BloodUnit(EXPIRATION_TIME_3));
        BLOOD_DONATION_POINT.addBlood(new BloodUnit(EXPIRATION_TIME_1));
    }

    @Test
    public void shouldAddNewBloodsAndSortAll() {
        List<BloodUnit> bloodUnitList = BLOOD_DONATION_POINT.getBloodUnitList();

        assertNotNull(bloodUnitList);
        assertEquals(BLOODS_AMOUNT, bloodUnitList.size());
        assertEquals(FIRST_BLOOD_ID, bloodUnitList.get(0).getBloodId());
        assertEquals(SECOND_BLOOD_ID, bloodUnitList.get(1).getBloodId());
        assertEquals(THIRD_BLOOD_ID, bloodUnitList.get(2).getBloodId());
    }

    @Test
    public void shouldRemoveBlood() {

    }
}
