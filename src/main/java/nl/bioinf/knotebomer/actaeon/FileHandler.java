package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;

import java.io.IOException;

public interface FileHandler<T, T2> {
    Instances readFile(T input) throws IOException;
    void writeFile(Instances labeledData, T2 outputPath) throws IOException;
}
