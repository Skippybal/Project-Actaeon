package nl.bioinf.knotebomer.actaeon;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;

public class ArffHandler implements FileHandler<String, String>{
    @Override
    public Instances readFile(String filePath) throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setSource(new File(filePath));
        Instances instances = loader.getDataSet();
        instances.removeIf((Instance::hasMissingValue));
        return instances;
    }

    @Override
    public void writeFile(Instances labeledData, String outputPath) throws IOException {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(labeledData);
        saver.setFile(new File(outputPath));
        saver.writeBatch();
    }
}
