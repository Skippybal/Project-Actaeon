package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.IOException;

public class MinMax {

    public Instances minMaxer(Instances instances) throws Exception {
        CSVHandler loader = new CSVHandler();
        Instances train = loader.readFile("data/dataFrame_all_sims.csv");

        Normalize filter = new Normalize();
        filter.setInputFormat(train);

        //System.out.println(scaledInstances);

        return Filter.useFilter(instances, filter);
    }
}
