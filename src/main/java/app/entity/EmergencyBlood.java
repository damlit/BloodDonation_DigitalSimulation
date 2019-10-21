package app.entity;

import app.boundary.Distributions;
import app.control.BloodDonationPoint;
import app.control.Process;

public class EmergencyBlood extends Process {

    private Patient orderedPatient;
    private int expirationTime;
    private static int amountOfEmergency;
    private boolean active;

    public EmergencyBlood(BloodDonationPoint system, Patient orderedPatient) {
        super(system);
        this.orderedPatient = orderedPatient;
        expirationTime = 800;
    }

    @Override
    public void execute() {
        active = true;
        while(active) {
            runEmergencyBloodProcess();
        }
    }

    private void runEmergencyBloodProcess() {
        switch(phase) {
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
        this.activate(Distributions.getNormal());
        phase = 1;
        active = false;
        System.out.println("-> Zamówiono krew awaryjnie.");
        if(bloodDonationPoint.getSystemTime() > bloodDonationPoint.getInitialPhase()) {
            amountOfEmergency++;
        }
    }

    private void runPhase1() {
        for(int i = 0; i < bloodDonationPoint.getAmountOfBloodUnitsInEmergencyOrders(); i++) {
            bloodDonationPoint.addBlood(new BloodUnit(bloodDonationPoint.getSystemTime() + expirationTime));
        }
        orderedPatient.activate(0.0);
        active = false;
        System.out.println("-> Dojechało awaryjne zamówienie dla pacjenta nr " + orderedPatient.getId());
    }

    public static int getAmountOfEmergency() {
        return amountOfEmergency;
    }

    @Override
    public String toString() {
        return "-> Awaryjne zamówienie pacjenta o ID: " + orderedPatient.getId() + " | czas: " + processEvent.getEventTime();
    }
}