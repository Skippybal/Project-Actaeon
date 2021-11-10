package nl.bioinf.knotebomer.actaeon;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import weka.classifiers.trees.LMT;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Classification {
    private RandomForest sterolPresentModel;
    private RandomForest tailClassifierModel;
    private LMT sterolConcentrationModel;
    private Instances scaledInstances;
    private Instances scaledInstancesLMT;
    private Instances labeledInstances;

    /**
     * Load the models when the class is constructed
     */
    public Classification() {
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

            InputStream isSterolPresentModel = getClass().getClassLoader().getResourceAsStream(sterolPresentModelLocation);
            InputStream isTailClassifierModel = getClass().getClassLoader().getResourceAsStream(tailModelLocation);
            InputStream isSterolConcentrationModel = getClass().getClassLoader().getResourceAsStream(sterolConcentrationModelLocation);

            this.sterolPresentModel = (RandomForest) SerializationHelper.read(isSterolPresentModel);
            this.tailClassifierModel = (RandomForest) SerializationHelper.read(isTailClassifierModel);
            this.sterolConcentrationModel = (LMT) SerializationHelper.read(isSterolConcentrationModel);

            assert isSterolPresentModel != null;
            isSterolPresentModel.close();
            assert isTailClassifierModel != null;
            isTailClassifierModel.close();
            assert isSterolConcentrationModel != null;
            isSterolConcentrationModel.close();

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
        Normalization normalization = new Normalization();
        Instances scaledData = normalization.normalize(instances);
        //System.out.println(scaledData);

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
