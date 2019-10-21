package app.entity;

import app.boundary.Distributions;
import app.control.BloodDonationPoint;
import app.control.Process;

public class Blood extends Process {

    private static final int ACTIVATE_BLOOD_TIME = 1700;

    private int expirationTime;
    private static int amountOfStandardOrders;
    private boolean active;

    public Blood(BloodDonationPoint system) {
        super(system);
        expirationTime = 800;
    }

    @Override
    public void execute() {
        active = true;
        while(active){
            runBloodProcess();
        }
    }

    private void runBloodProcess() {
        switch(phase){
            case 0: {
                runPhase0();
                break;
            }
            case 1: {
                runPhase1();
                break;
            }

        }
    }

    private void runPhase0() {
        double exp = Distributions.getExponential(ACTIVATE_BLOOD_TIME);
        this.activate(exp);
        phase = 1;
        active = false;
        System.out.println("-> Złożono standardowe zamówienie krwi.");
        if(bloodDonationPoint.getSystemTime() > bloodDonationPoint.getInitialPhase()) {
            amountOfStandardOrders++;
        }
    }

    private void runPhase1() {
        for(int i=0; i < bloodDonationPoint.getAmountOfBloodUnitsInStandardOrders(); i++) {
            bloodDonationPoint.addBlood(new BloodUnit(bloodDonationPoint.getSystemTime() + expirationTime));
        }
        active = false;
        System.out.println("-> Dostarczono standardowe zamówienie.");
        System.out.println("-> Aktualny stan jednostek krwi: " + bloodDonationPoint.getBloodUnitList().size());
    }

    public static int getAmountOfStandardOrders() {
        return amountOfStandardOrders;
    }

    @Override
    public String toString() {
        return "-> Standardowe zamówienie | czas: " + processEvent.getEventTime();
    }
}