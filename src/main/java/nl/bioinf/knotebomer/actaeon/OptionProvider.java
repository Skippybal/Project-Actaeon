package nl.bioinf.knotebomer.actaeon;

public interface OptionProvider {
    String getFileName();
    String getInputType();
    String getOutputFIle();
    double getAPL();
    double getThickness();
    double getBinding();
    double getTilt();
    double getZorder();
    double getCompressibility();
}
