package nl.bioinf.knotebomer.actaeon;

import java.util.HashMap;

public interface OptionProvider {
    String getFilePath();
    String getOutputFile();
    String getInputFileExtention();
    String getOutputFileExtention();
    Boolean getPrintToCMD();
    HashMap<String, Double> getCharacteristics();
}
