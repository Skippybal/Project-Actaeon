package nl.bioinf.knotebomer.actaeon;

import java.util.HashMap;
import java.util.Map;

public interface OptionProvider {
    String getFilePath();
    String getOutputFile();
    String getInputFileExtention();
    String getOutputFileExtention();
    Boolean getPrintToCMD();
    Map<String, Double> getCharacteristics();
}
