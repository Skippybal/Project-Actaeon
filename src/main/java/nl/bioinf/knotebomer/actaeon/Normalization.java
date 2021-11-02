package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Normalize;


public class Normalization {

    /**
     * Normalize the data
     * @param instances, Instances object of input data
     * @return Instances object with normalized data
     * @throws Exception
     */
    public Instances normalize(Instances instances) throws Exception {
        CSVHandler loader = new CSVHandler();
        Instances train = loader.readFile("data/dataFrame_all_sims.csv");

        Normalize filter = new Normalize();
        filter.setInputFormat(train);

        for (int i = 0; i < train.numInstances(); i++){
            filter.input(train.instance(i));
        }
        filter.batchFinished();

        Instances normalizedInstances = filter.getOutputFormat();
        for (int i = 0; i < instances.numInstances(); i++){
            filter.input(instances.instance(i));
            normalizedInstances.add(filter.output());
        }

        return normalizedInstances;

    }
}
