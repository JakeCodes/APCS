import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class StatsCalculator {

    public static void main(String[] args) {
        try {
            // Read the numbers from the file
            ArrayList<Integer> numbers = readNumbersFromFile("numbers.txt");

            // Calculate the average, standard deviation, and mode of the numbers
            BigDecimal average = calculateAverage(numbers);
            BigDecimal standardDeviation = calculateStandardDeviation(numbers, average);
            ArrayList<Integer> mode = calculateMode(numbers);

            // Print the results
            System.out.println("Average: " + average.setScale(2, RoundingMode.HALF_UP));
            System.out.println("Standard Deviation: " + standardDeviation.setScale(2, RoundingMode.HALF_UP));
            System.out.println("Mode: " + mode);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static ArrayList<Integer> readNumbersFromFile(String filename) throws FileNotFoundException {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim(); // Trim leading/trailing whitespace
            int number = Integer.parseInt(line);
            numbers.add(number);
        }
        scanner.close();
        return numbers;
    }


    public static BigDecimal calculateAverage(ArrayList<Integer> numbers) {
        BigDecimal sum = new BigDecimal(0);
        for (int number : numbers) {
            sum = sum.add(new BigDecimal(number));
        }
        return sum.divide(new BigDecimal(numbers.size()), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateStandardDeviation(ArrayList<Integer> numbers, BigDecimal average) {
        BigDecimal sumOfSquaredDifferences = new BigDecimal(0);
        for (int number : numbers) {
            BigDecimal difference = new BigDecimal(number).subtract(average);
            sumOfSquaredDifferences = sumOfSquaredDifferences.add(difference.pow(2));
        }
        BigDecimal variance = sumOfSquaredDifferences.divide(new BigDecimal(numbers.size() - 1), 2, RoundingMode.HALF_UP);
        return variance.sqrt(new MathContext(3));
    }

    public static ArrayList<Integer> calculateMode(ArrayList<Integer> numbers) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (int number : numbers) {
            if (frequencyMap.containsKey(number)) {
                frequencyMap.put(number, frequencyMap.get(number) + 1);
            } else {
                frequencyMap.put(number, 1);
            }
        }
        int maxFrequency = Collections.max(frequencyMap.values());
        ArrayList<Integer> mode = new ArrayList<>();
        for (int number : frequencyMap.keySet()) {
            if (frequencyMap.get(number) == maxFrequency) {
                mode.add(number);
            }
        }
        return mode;
    }

}
