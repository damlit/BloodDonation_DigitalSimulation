package app.control;

import app.entity.BloodForScientificPurposes;
import app.entity.BloodUnit;
import app.entity.Patient;

import java.util.*;

public class BloodDonationPoint implements Comparator<BloodUnit> {

    private double systemTime;
    private int amountOfBloodUnitsInStandardOrders;
    private int minimalBlood;
    private int amountOfBloodUnitsInEmergencyOrders;
    private int tbLevel;
    private int tuTime;
    private Schedule schedule;
    private List<Patient> patientQueue;
    private List<BloodUnit> bloodUnitList;
    private BloodForScientificPurposes bloodForScientificPurposes;
    private boolean bloodForScientificFlag;
    private int utilizedBlood;
    private int bloodForScience;
    private int initialPhase;

    public BloodDonationPoint() {
        initialPhase = 40_000;
        systemTime = 0.0;
        schedule = new Schedule();
        patientQueue = new ArrayList<>();
        bloodUnitList = new ArrayList<>();
        bloodForScientificFlag = false;
        tbLevel = 30;
        tuTime = 300;
        amountOfBloodUnitsInStandardOrders = 20;
        minimalBlood = 70;
        amountOfBloodUnitsInEmergencyOrders = 11;
        utilizedBlood = 0;
        bloodForScience = 0;
    }

    public void addBlood(BloodUnit bloodUnit) {
        bloodUnitList.add(bloodUnit);
        bloodUnitList.sort(this);
    }

    public int compare(BloodUnit blood1, BloodUnit blood2) {
        return Double.compare(blood1.getExpirationDate(), blood2.getExpirationDate());
    }

    public void removeBlood() {
        System.out.println("-> Usunięto jednostkę krwi o ID: " + bloodUnitList.get(0).getBloodId());
        if (systemTime > initialPhase) {
            utilizedBlood++;
        }
        bloodUnitList.remove(0);
        bloodUnitList.sort(this);
    }

    public void removeBloodForScientific() {
        System.out.println("-> Usunięto jednostkę krwi o ID: " + bloodUnitList.get(0).getBloodId());
        if (systemTime > initialPhase) {
            bloodForScience++;
        }
        bloodUnitList.remove(0);
        bloodUnitList.sort(this);
    }

    public double getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(double systemTime) {
        this.systemTime = systemTime;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Patient> getPatientQueue() {
        return patientQueue;
    }

    public List<BloodUnit> getBloodUnitList() {
        return bloodUnitList;
    }

    public int getInitialPhase() {
        return initialPhase;
    }

    public int getTbLevel() {
        return tbLevel;
    }

    public boolean isBloodForScientificFlag() {
        return bloodForScientificFlag;
    }

    public void setBloodForScientificFlag(boolean bloodForScientificFlag) {
        this.bloodForScientificFlag = bloodForScientificFlag;
    }

    public BloodForScientificPurposes getBloodForScientificPurposes() {
        return bloodForScientificPurposes;
    }

    public void setBloodForScientificPurposes(BloodForScientificPurposes bloodForScientificPurposes) {
        this.bloodForScientificPurposes = bloodForScientificPurposes;
    }

    public int getMinimalBlood() {
        return minimalBlood;
    }

    public int getAmountOfBloodUnitsInStandardOrders() {
        return amountOfBloodUnitsInStandardOrders;
    }

    public int getAmountOfBloodUnitsInEmergencyOrders() {
        return amountOfBloodUnitsInEmergencyOrders;
    }

    public int getTuTime() {
        return tuTime;
    }

    public int getBloodForScience() {
        return bloodForScience;
    }

    public int getUtilizedBlood() {
        return utilizedBlood;
    }
}