package nl.bioinf.knotebomer.actaeon;

import java.util.Map;

public interface OptionProvider {
    /**
     * Get the path to input file
     * @return input file path
     */
    String getFilePath();

    /**
     * Get the output file path
     * @return output file path
     */
    String getOutputFile();

    /**
     * Get the extension of the given input file
     * This can be used to create the right filehandler
     * @return input file extension
     */
    String getInputFileExtension();

    /**
     * Get extension of the given output file
     * This can be used to create the right filehandler
     * @return output file extension
     */
    String getOutputFileExtension();

    /**
     * Get option to print results to the command line
     * @return boolean
     */
    Boolean getPrintToCMD();

    /**
     * Get the membrane characteristics given by the command line
     * @return map with the characteristic and the value
     */
    Map<String, Double> getCharacteristics();
}
