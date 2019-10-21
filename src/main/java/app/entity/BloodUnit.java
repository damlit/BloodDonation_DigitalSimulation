package app.entity;

public class BloodUnit {

    private static int globalBloodsId;
    private int bloodId;
    private double expirationDate;

    public BloodUnit(double time) {
        expirationDate = time;
        bloodId = globalBloodsId;
        globalBloodsId++;
    }

    public int getBloodId() {
        return bloodId;
    }

    public double getExpirationDate() {
        return expirationDate;
    }

    public static int getGlobalBloodsId() {
        return globalBloodsId;
    }
}
