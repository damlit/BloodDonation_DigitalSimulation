package app.boundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KernelsGenerator {

    private static final String PATH = "src/main/resources/Kernels.txt";
    private static final List<String> KERNELS = getKernelsFromFile();
    private static final int Q = 127_773;
    private static final int A = 16_807;
    private static final int R = 2_836;
    private static int iterator = 20_001;

    public static int generate() {
        int x = Integer.parseUnsignedInt(KERNELS.get(iterator));
        int h = x / Q;
        iterator++;
        return A * (x - (Q * h)) - R * h;
    }

    private static List<String> getKernelsFromFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH));
            String lineFromFile;
            List<String> linesFromFile = new ArrayList<>();
            while ((lineFromFile = bufferedReader.readLine()) != null) {
                linesFromFile.add(lineFromFile);
            }
            bufferedReader.close();
            return linesFromFile;
        } catch (IOException ex) {
            System.out.println("File with kernels not found");
        }
        return Collections.emptyList();
    }
}