package app.entity;

import app.boundary.Distributions;
import app.control.BloodDonationPoint;
import app.control.Process;

public class BloodForScientificPurposes extends Process {

    private double time;
    private int amountOfBloodUnits;
    private static int id;

    public BloodForScientificPurposes(BloodDonationPoint system) {
        super(system);
        this.activate(bloodDonationPoint.getTuTime());
        amountOfBloodUnits = Distributions.getUniformToBloodForScientificPurposes();
        bloodDonationPoint.setBloodForScientificFlag(true);
    }

    @Override
    public void execute() {
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
        if(bloodDonationPoint.getBloodUnitList().size() > bloodDonationPoint.getTbLevel()) {
            startPhase0();
        }
        else {
            phase = 1;
        }
    }

    private void startPhase0() {
        bloodDonationPoint.setBloodForScientificFlag(true);
        if((bloodDonationPoint.getSystemTime() - time) > bloodDonationPoint.getTuTime()) {
            startBloodGiving();
        }
    }

    private void startBloodGiving() {
        System.out.println("-> Oddanie krwi na cele naukowe (rozszerzenie): ");
        for(int i = 0; i < amountOfBloodUnits; i++) {
            bloodDonationPoint.removeBloodForScientific();
        }
        if (bloodDonationPoint.getSystemTime() > bloodDonationPoint.getInitialPhase()){
            id++;
        }
        phase = 1;
    }

    private void runPhase1() {
        bloodDonationPoint.setBloodForScientificFlag(false);
    }

    public static int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "-> Oddanie krwi na cele naukowe | czas: " + processEvent.getEventTime();
    }
}