package utils;

import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import response.ProductResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtils {
    private final Logger LOG=Logger.getLogger(CSVUtils.class);

    private final String filePath;

    public CSVUtils(String filePath) {
        this.filePath = filePath;
        initializeFile();
        writeHeader();
    }

    private void initializeFile() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                // this will clear existing file
                new FileWriter(file, false).close();
            } else {
                // if file doesnot exist, it will create one
                file.createNewFile();
            }
        } catch (IOException e) {
            LOG.error("ERROR when initializing file for CSV");
            e.printStackTrace();
        }
    }

    public void writeHeader() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath,true))) {
            String[] heading = {"Title of the product", "Description of the product", "Price of the product", "Condition of the product", "Ad Posted Date", "Name of Seller"};
            writer.writeNext(heading);
        } catch (IOException e) {
            LOG.error("ERROR when writing header for CSV");
            e.printStackTrace();
        }
    }

    public void writeToCSV(List<ProductResponse> productResponseList) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath,true))) {
            for (ProductResponse product : productResponseList) {
                LOG.info("Writing to CSV File");
                String[] productArray = new String[]{product.title, product.description, product.price,
                        product.condition,product.time, product.userName};
                writer.writeNext(productArray);
            }
        } catch (IOException e) {
            LOG.error("ERROR when writing product list to CSV");
            e.printStackTrace();
        }
    }
}
