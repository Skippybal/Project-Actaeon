package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;

import java.io.IOException;

public interface FileHandler<T, T2> {

    /**
     * Read the given input
     * @param input file or map
     * @return Instances object containing the data
     * @throws IOException
     */
    Instances readFile(T input) throws IOException;

    /**
     * Write results to file
     * @param labeledData Instances object with the results
     * @param outputPath, specified output file location
     * @throws IOException
     */
    void writeFile(Instances labeledData, T2 outputPath) throws IOException;
}
