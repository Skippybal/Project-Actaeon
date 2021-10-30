package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;

import java.util.ArrayList;

public interface WekaClassifierModel {
    public double classifyInstances(Instances instances) throws Exception;
}
