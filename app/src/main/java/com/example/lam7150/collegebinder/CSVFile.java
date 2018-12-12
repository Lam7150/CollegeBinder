package com.example.lam7150.collegebinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**public class for reading csv file which contains all or colleges from the API.
 * website for reference:
 * https://stackoverflow.com/questions/19974708/reading-csv-file-in-resources-folder-android */

public class CSVFile {
    InputStream inputStream;

    /**constructor for inputStream, InputStream allows us to read files
     * in this case it will be CSV */
    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    /**reads line of CSV file, luckily we do not have to parse.
     * We just read line by line and get a String value for that line and add to collegeList */
    public List read() {
        List returnList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                returnList.add(csvLine);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " +ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return returnList;
    }
}
