package i.gishreloaded.gishcode.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
	
    public static int getMiddle(int i, int j) {

        return (i + j) / 2;
    }

    public static double getMiddleDouble(int i, int j) {

        return ((double) i + (double) j) / 2.0;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static float getAngleDifference(float direction, float rotationYaw)
    {
      float phi = Math.abs(rotationYaw - direction) % 360.0F;
      float distance = phi > 180.0F ? 360.0F - phi : phi;
      return distance;
    }


}
