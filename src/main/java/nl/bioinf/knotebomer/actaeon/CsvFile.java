package nl.bioinf.knotebomer.actaeon;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

public class CsvFile {

    public Instances readCSVToInstances(String filepath) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filepath));
        Instances instances = loader.getDataSet();
        instances.removeIf((Instance::hasMissingValue));
        return instances;

    }
}
