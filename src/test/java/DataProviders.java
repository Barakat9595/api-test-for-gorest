
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviders {

    @DataProvider(name = "csvData")
    public static Object[][] readCSV() throws IOException, CsvValidationException {
        String filePath = "E:\\private\\ITI's\\introduction to SW engineering\\simpleAPITest\\New Microsoft Excel Worksheet.csv"; // Path to CSV file
        CSVReader reader = new CSVReader(new FileReader(filePath));
        List<Object[]> testData = new ArrayList<>();

        String[] nextLine;
        boolean skipHeader = true; // Skip the first line (header)
        while ((nextLine = reader.readNext()) != null) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }
            if (nextLine.length >= 4) { // Ensure at least 4 columns exist
                testData.add(new Object[]{nextLine[0], nextLine[1], nextLine[2], nextLine[3]});
            }
        }
        reader.close();
        return testData.toArray(new Object[0][]);
    }
}