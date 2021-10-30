package nl.bioinf.knotebomer.actaeon;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

import java.io.File;
import java.io.IOException;

public class CSVHandler implements FileHandler<String, String> {

    @Override
    public Instances readFile(String filePath) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filePath));
        Instances instances = loader.getDataSet();
        instances.removeIf((Instance::hasMissingValue));
        return instances;
    }

    @Override
    public void writeFile(Instances labeledData, String outputPath) throws IOException {
        CSVSaver saver = new CSVSaver();
        saver.setInstances(labeledData);
        saver.setFile(new File(outputPath));
        saver.writeBatch();
    }
}
