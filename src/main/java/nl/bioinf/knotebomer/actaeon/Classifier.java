package nl.bioinf.knotebomer.actaeon;

import java.io.FileInputStream;
import java.util.ArrayList;

import weka.classifiers.trees.LMT;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Classifier {
    private RandomForest sterolPresentModel;
    private RandomForest tailClassifierModel;
    private LMT sterolConcentrationModel;
    private Instances scaledInstances;
    private Instances scaledInstancesLMT;
    private Instances labeledInstances;

    public Classifier() {
        loadModel();
    }

    /**
     * Load all the models for classification
     */
    private void loadModel(){
        try{

            String sterolPresentModelLocation = "models/RandomForest_Sterol_Present.model";
            String tailModelLocation = "models/RandomForest_Tails.model";
            String sterolConcentrationModelLocation = "models/LMT_Sterol_Concentration.model";

            this.sterolPresentModel = (RandomForest) SerializationHelper.read(new FileInputStream(sterolPresentModelLocation));
            this.tailClassifierModel = (RandomForest) SerializationHelper.read(new FileInputStream(tailModelLocation));
            this.sterolConcentrationModel = (LMT) SerializationHelper.read(new FileInputStream(sterolConcentrationModelLocation));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Classify all the instances in the given dataset
     * @param instances (a dataset)
     * @return LabeledInstances
     * @throws Exception
     */
    public Instances classifyInstances(Instances instances) throws Exception {

        makeDataSet(instances);

        for (int i = 0; i < labeledInstances.numInstances(); i++) {
            labeledInstances.setClassIndex(labeledInstances.numAttributes() - 3);
            scaledInstances.setClassIndex(scaledInstances.numAttributes() - 3);
            double labelSterolPresent = sterolPresentModel.classifyInstance(scaledInstances.instance(i));
            //String clsLabelSterolPresent = labeled.classAttribute().value((int)labelSterolPresent);
            labeledInstances.instance(i).setClassValue(labelSterolPresent);

            labeledInstances.setClassIndex(labeledInstances.numAttributes() - 2);
            scaledInstances.setClassIndex(scaledInstances.numAttributes() - 2);
            double labelTails = tailClassifierModel.classifyInstance(scaledInstances.instance(i));
            //String clsLabelTails = labeled.classAttribute().value((int)labelTails);
            labeledInstances.instance(i).setClassValue(labelTails);

            labeledInstances.setClassIndex(labeledInstances.numAttributes() - 1);
            double labelSterolConcentration = sterolConcentrationModel.classifyInstance(scaledInstancesLMT.instance(i));
            //String clsLabelSterolConcentration = instances.classAttribute().value((int)labelSterolConcentration);
            labeledInstances.instance(i).setClassValue(labelSterolConcentration);
        }
        System.out.println(labeledInstances);
        return labeledInstances;
    }

    /**
     * Creates the necessary datasets for classification
     * @param instances (a dataset)
     * @throws Exception
     */
    private void makeDataSet(Instances instances) throws Exception {

        // Create the needed attributes
        ArrayList<String> sterolPresentValues = new ArrayList<>(2);
        sterolPresentValues.add("yes");
        sterolPresentValues.add("no");
        Attribute sterolPresent = new Attribute("sterol.present", sterolPresentValues);

        ArrayList<String> tailValues = new ArrayList<>(5);
        tailValues.add("DI");
        tailValues.add("DO");
        tailValues.add("DP");
        tailValues.add("PI");
        tailValues.add("PO");
        Attribute tails = new Attribute("tails", tailValues);

        ArrayList<String> sterolConcentrationValues = new ArrayList<>(2);
        sterolConcentrationValues.add("0");
        sterolConcentrationValues.add("10");
        sterolConcentrationValues.add("20");
        sterolConcentrationValues.add("30");
        Attribute sterolConcentration = new Attribute("sterol.conc", sterolConcentrationValues);

        //Standardize the instances
        MinMax minMax = new MinMax();
        Instances scaledData = minMax.minMaxer(instances);

        //Build a special dataset for the LMT tree classification
        Instances scaledDataLMT = new Instances(scaledData);
        scaledDataLMT.insertAttributeAt(sterolConcentration, scaledDataLMT.numAttributes());
        scaledDataLMT.setClassIndex(scaledDataLMT.numAttributes() - 1);

        //Add attributes
        scaledData.insertAttributeAt(sterolPresent, scaledData.numAttributes());
        scaledData.insertAttributeAt(tails, scaledData.numAttributes());
        scaledData.insertAttributeAt(sterolConcentration, scaledData.numAttributes());

        //Create dataset where all of the labels can be inserted
        Instances labeled = new Instances(instances);

        labeled.insertAttributeAt(sterolPresent, labeled.numAttributes());
        labeled.insertAttributeAt(tails, labeled.numAttributes());
        labeled.insertAttributeAt(sterolConcentration, labeled.numAttributes());

        //Set data as class variables
        this.scaledInstances = scaledData;
        this.scaledInstancesLMT = scaledDataLMT;
        this.labeledInstances = labeled;
    }
}
