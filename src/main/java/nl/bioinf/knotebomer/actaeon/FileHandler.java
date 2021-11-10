package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;

import java.io.IOException;

public interface FileHandler {

    /**
     * Read the given input
     * @param input file or map
     * @return Instances object containing the data
     * @throws IOException
     */
    Instances readFile(String input) throws IOException;

    /**
     * Write results to file
     * @param labeledData Instances object with the results
     * @param outputPath, specified output file location
     * @throws IOException
     */
    void writeFile(Instances labeledData, String outputPath) throws IOException;
}
