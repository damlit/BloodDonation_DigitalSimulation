package app.control;

import app.entity.*;

import java.io.IOException;

public class Program {

    public static void main(String[] args) throws IOException {
        BloodDonationPoint bloodDonationPoint = new BloodDonationPoint();
        new Patient(bloodDonationPoint).activate(1.0);
        new Donor(bloodDonationPoint).activate(1.0);
        boolean stepMode = false;
        int unitsOfBloodAfterInitialyPhase = 0;
        boolean subsidiaryFlag = true;

        System.out.println("|??| Przeprowadzić symulację krokowo(1) czy ciągle(2)?");
        int choice = System.in.read();
        if(choice == 49) {
            stepMode = true;
        }

        while(Patient.getPatientId() < 5000) {
            if(bloodDonationPoint.getSystemTime() > 9999 && subsidiaryFlag) {
                unitsOfBloodAfterInitialyPhase = BloodUnit.getGlobalBloodsId();
                subsidiaryFlag = false;
            }
            executeCurrentProcess(bloodDonationPoint);

            if(stepMode){
                System.in.read();
            }
        }
        printResult(bloodDonationPoint, unitsOfBloodAfterInitialyPhase);
    }

    private static void executeCurrentProcess(BloodDonationPoint bloodDonationPoint) {
        Process currentProcess = bloodDonationPoint.getSchedule().getFirstEvent().getProcess();
        bloodDonationPoint.setSystemTime(currentProcess.getProcessEvent().getEventTime());
        System.out.println("-> Czas: " + bloodDonationPoint.getSystemTime());

        currentProcess.execute();

        printNextEvents(bloodDonationPoint);
    }

    private static void printNextEvents(BloodDonationPoint bloodDonationPoint) {
        System.out.println();
        System.out.println("Kolejne: ");
        bloodDonationPoint.getSchedule().print();
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }

    private static void printResult(BloodDonationPoint bloodDonationPoint, int unitsOfBloodAfterInitialyPhase) {
        System.out.println("Czas na fazę początkową = " + bloodDonationPoint.getInitialPhase());
        System.out.println("Krew na badania naukowe oddano " + BloodForScientificPurposes.getId() +
                " razy, w sumie na badania oddano " + bloodDonationPoint.getBloodForScience() + " jednostek krwi.");
        System.out.println("Obsłużono " + Patient.getPatientId() + " pacjentów");
        System.out.println("Zgłosiło się " + Donor.getDonorId() + " dawców");
        System.out.println("Przez symulację było " + (BloodUnit.getGlobalBloodsId() - unitsOfBloodAfterInitialyPhase)
                + " różnych jednostek krwi");
        System.out.println("Aż " + bloodDonationPoint.getUtilizedBlood() + " jednostek krwi zostało zutylizowanych");
        System.out.println("Krew zamówiono awaryjnie " + EmergencyBlood.getAmountOfEmergency()
                + " razy, a standardowo " + Blood.getAmountOfStandardOrders() + " razy.");
    }


}
