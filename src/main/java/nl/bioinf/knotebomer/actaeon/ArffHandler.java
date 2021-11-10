package nl.bioinf.knotebomer.actaeon;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;

public class ArffHandler implements FileHandler {

    /**
     * Read a .arff file
     * @param filePath, input file location
     * @return Instances object containing instances from the input file
     * @throws IOException
     */
    @Override
    public Instances readFile(String filePath) throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setSource(new File(filePath));
        Instances instances = loader.getDataSet();
        instances.removeIf((Instance::hasMissingValue));
        return instances;
    }

    /**
     * Write results to .csv file
     * @param labeledData Instances object with the results
     * @param outputPath, specified output file location
     * @throws IOException
     */
    @Override
    public void writeFile(Instances labeledData, String outputPath) throws IOException {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(labeledData);
        saver.setFile(new File(outputPath));
        saver.writeBatch();
    }
}
