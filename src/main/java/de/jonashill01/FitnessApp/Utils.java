package de.jonashill01.FitnessApp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    /**
     * Rounds a double
     * @param value The double that gets rounded.
     * @param places the amount of decimal places the value should get rounded to.
     * */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal(Double.toString(value)).setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}
