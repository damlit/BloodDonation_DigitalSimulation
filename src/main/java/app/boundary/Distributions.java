package app.boundary;

public class Distributions {
    private static final double FINAL_SQRT_FOR_NORMAL = Math.sqrt(100.00 / 12);

    private static double getUniform() {
        double value = KernelsGenerator.generate();
        return  (value / 100) % 1;
    }

    public static double getUniformFromValue(int value) {
        return  ((double) value / 100) % 1;
    }

    public static int getUniformToBloodForScientificPurposes() {
        int value = KernelsGenerator.generate();
        return  (value % 6) + 5;
    }

    public static double getExponential(int average) {
        double value = getUniform();
        while ( value == 0 || value < 0) {
            value = getUniform();
        }
        return - average * Math.log(value);
    }

    private static boolean getBernouli(int randomVariable) {
        return getUniformFromValue(randomVariable) < 0.2;
    }

    public static int getGeometric() {
        int i = 0;
        boolean geometricFlag = false;
        int geometricValue = KernelsGenerator.generate();
        while (!geometricFlag) {
            geometricFlag = getBernouli(geometricValue);
            if (!geometricFlag) {
                i++;
                geometricValue = KernelsGenerator.generate();
            }
        }
        return i;
    }

    public static double getNormal() {
        double sum = 0;
        for(int i = 0; i < 10; i++) {
            double r = KernelsGenerator.generate();
            r = (r / 100) % 1;
            sum = sum + r;
        }
        return (((sum - 5) / FINAL_SQRT_FOR_NORMAL) + 500);
    }
}