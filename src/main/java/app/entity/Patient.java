package app.entity;

import app.boundary.Distributions;
import app.control.BloodDonationPoint;
import app.control.Process;

import java.util.List;

public class Patient extends Process {

    private static final int ACTIVATE_PATIENT_TIME = 250;

    private static int patientId;
    private int bloodNeeded;
    private int id;
    private boolean active;

    public Patient(BloodDonationPoint system) {
        super(system);
        bloodNeeded = Distributions.getGeometric();
        if (bloodNeeded == 0) {
            bloodNeeded = 1;
        }
        id = patientId;
        if (bloodDonationPoint.getSystemTime() > bloodDonationPoint.getInitialPhase()) {
            patientId += 1;
        }
    }

    @Override
    public void execute() {
        active = true;
        while (active) {
            runPatientProcess();
        }
    }

    private void runPatientProcess() {
        List<BloodUnit> bloodUnitList = bloodDonationPoint.getBloodUnitList();

        switch (phase) {
            case 0: {
                runPhase0();
                break;
            }
            case 1: {
                runPhase1(bloodUnitList);
                break;
            }
            case 2: {
                runPhase2(bloodUnitList);
                break;
            }
            case 3: {
                runPhase3(bloodUnitList);
                break;
            }
            case 4: {
                runPhase4();
                break;
            }
        }
    }

    private void runPhase0() {
        new Patient(bloodDonationPoint).activate(Distributions.getExponential(ACTIVATE_PATIENT_TIME));
        bloodDonationPoint.getPatientQueue().add(this);
        phase = 1;
        System.out.println("-> Dodano do kolejki pacjenta nr: " + id);
    }

    private void runPhase1(List<BloodUnit> bloodUnitList) {
        if (!bloodUnitList.isEmpty()) {
            checkBloodExpirationDate(bloodUnitList);
        }
        runProcessForScientificPurposesBlood(bloodUnitList);

        if (bloodUnitList.size() < bloodDonationPoint.getMinimalBlood()) {
            new Blood(bloodDonationPoint).activate(0.0); //standard blood order
        }
        phase = 2;
    }

    private void checkBloodExpirationDate(List<BloodUnit> bloodUnitList) {
        while (bloodUnitList.get(0).getExpirationDate() < bloodDonationPoint.getSystemTime()) {
            bloodDonationPoint.removeBlood();
            if (bloodUnitList.isEmpty()) {
                break;
            }
        }
    }

    private void runProcessForScientificPurposesBlood(List<BloodUnit> bloodUnitList) {
        if ((bloodUnitList.size() > bloodDonationPoint.getTbLevel())
                && !bloodDonationPoint.isBloodForScientificFlag()) {
            bloodDonationPoint.setBloodForScientificPurposes(new BloodForScientificPurposes(bloodDonationPoint));
        }
        if (bloodDonationPoint.getBloodForScientificPurposes() != null) {
            bloodDonationPoint.getBloodForScientificPurposes().execute();
        }
    }

    private void runPhase2(List<BloodUnit> bloodUnitList) {
        if (bloodUnitList.size() < bloodNeeded) {
            orderEmergencyBlood();
        }
        phase = 3;
    }

    private void orderEmergencyBlood() {
        new EmergencyBlood(bloodDonationPoint, this).activate(0.0);
        active = false;
        System.out.println("-> Zamówiono awaryjnie krew dla pacjenta nr id: " + id
                + " | Potrzebna krew: " + bloodNeeded + " | czas: " + bloodDonationPoint.getSystemTime());
    }

    private void runPhase3(List<BloodUnit> bloodUnitList) {
        if (bloodNeeded < bloodUnitList.size()) {
            giveBloodToThePatient();
        } else {
            orderEmergencyBlood();
        }
    }

    private void giveBloodToThePatient() {
        for (int i = 0; i < bloodNeeded; i++) {
            bloodDonationPoint.removeBlood();
        }
        System.out.println("-> Pacjent nr: " + id + " otrzymał " + bloodNeeded + " jednostek krwi.");
        active = false;
        phase = 4;
        System.out.println("-> Pacjent nr: " + id + " wyszedł.");
    }

    private void runPhase4() {
        active = !bloodDonationPoint.getPatientQueue().isEmpty();
    }

    public static int getPatientId() {
        return patientId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "-> Pacjent o nr id: " + id + " | czas: " + bloodDonationPoint.getSystemTime();
    }
}