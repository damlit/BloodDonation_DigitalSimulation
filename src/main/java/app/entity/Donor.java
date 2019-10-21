package app.entity;

import app.boundary.Distributions;
import app.control.BloodDonationPoint;
import app.control.Process;

public class Donor extends Process {

    private static final int DONOR_ACTIVATE_TIME = 950;

    private static int donorId;
    private boolean active;
    private int expirationTime;

    public Donor(BloodDonationPoint system) {
        super(system);
        expirationTime = 1000;
    }

    @Override
    public void execute() {
        active = true;
        while(active) {
            runDonorProcess();
        }
    }

    private void runDonorProcess() {
        if(bloodDonationPoint.getSystemTime() > bloodDonationPoint.getInitialPhase()) {
            donorId++;
        }
        System.out.println("-> Pojawienie się dawcy nr " + donorId + " w systemie.");
        new Donor(bloodDonationPoint).activate(Distributions.getExponential(DONOR_ACTIVATE_TIME));
        bloodDonationPoint.addBlood(new BloodUnit(bloodDonationPoint.getSystemTime() + expirationTime));
        System.out.println("-> Aktualny stan jednostek krwi: " + bloodDonationPoint.getBloodUnitList().size());
        active = false;
    }

    public static int getDonorId() {
        return donorId;
    }

    @Override
    public String toString() {
        return "-> Dawca o nr ID: " + donorId + " | czas: " + processEvent.getEventTime();
    }
}
