package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HTMLUtils {

    private final String csvFilePath;
    private final String htmlFilePath;

    public HTMLUtils(String csvFilePath,String htmlFilePath) {
        this.csvFilePath = csvFilePath;
        this.htmlFilePath = htmlFilePath;
    }

    public  void generateHTMLFromCSV() {
        try {
            // Read CSV file
            List<String[]> csvData = readCSV(csvFilePath);

            // Convert to HTML
            String htmlTable = generateHTMLTable(csvData,csvFilePath);

            // Write HTML to file
            writeHTMLToFile(htmlTable, htmlFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String[]> readCSV(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }

    private static String generateHTMLTable(List<String[]> data, String csvFilePath) {
        StringBuilder html = new StringBuilder();
        String title = csvFilePath.replace(".", " ");
        html.append(String.format("<html><head>" +
                "<title>%s</title>" +
                "<meta charset=\"UTF-8\">" +
                "</head><body>",title));
        html.append("<table border='1'>");

        // Add table header
        html.append("<tr>");
        for (String header : data.get(0)) {
            html.append("<th>").append(header).append("</th>");
        }
        html.append("</tr>");

        // Add table rows
        for (int i = 1; i < data.size(); i++) {
            html.append("<tr>");
            for (String cell : data.get(i)) {
                html.append("<td>").append(cell).append("</td>");
            }
            html.append("</tr>");
        }

        html.append("</table>");
        html.append("</body></html>");

        return html.toString();
    }

    private static void writeHTMLToFile(String htmlContent, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(htmlContent);
        }
    }
}
