package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.File;
import java.util.Objects;


public class Normalization {

    /**
     * Normalize the data
     * @param instances, Instances object of input data
     * @return Instances object with normalized data
     * @throws Exception
     */
    public Instances normalize(Instances instances) throws Exception {

        // Load training instances from the resources
        ClassLoader classLoader = getClass().getClassLoader();
        File trainingDataFile = new File(Objects.requireNonNull(classLoader.getResource("data/dataFrame_all_sims.csv")).getFile());

        CSVLoader loader = new CSVLoader();
        loader.setSource(trainingDataFile);
        Instances trainingInstances = loader.getDataSet();

        Normalize filter = new Normalize();
        filter.setInputFormat(trainingInstances);

        // Create the filter
        for (int i = 0; i < trainingInstances.numInstances(); i++){
            filter.input(trainingInstances.instance(i));
        }
        filter.batchFinished();

        // Apply the filter to the given instances
        Instances normalizedInstances = filter.getOutputFormat();
        for (int i = 0; i < instances.numInstances(); i++){
            filter.input(instances.instance(i));
            normalizedInstances.add(filter.output());
        }

        return normalizedInstances;

    }
}
