package nl.bioinf.knotebomer.actaeon;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

import java.io.File;
import java.io.IOException;

public class FileHandler {

    public Instances readCSVToInstances(String filePath) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filePath));
        Instances instances = loader.getDataSet();
        instances.removeIf((Instance::hasMissingValue));
        return instances;
    }

    public Instances readArffToInstances(String filePath) throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setSource(new File(filePath));
        Instances instances = loader.getDataSet();
        instances.removeIf((Instance::hasMissingValue));
        return instances;
    }

    public void writeLabeledToCSV(Instances labeledData, String outputPath) throws IOException {
        CSVSaver saver = new CSVSaver();
        saver.setInstances(labeledData);
        saver.setFile(new File(outputPath));
        saver.writeBatch();
    }

    public void writeLabeledToArff(Instances labeledData, String outputPath) throws IOException {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(labeledData);
        saver.setFile(new File(outputPath));
        saver.writeBatch();
    }

}
