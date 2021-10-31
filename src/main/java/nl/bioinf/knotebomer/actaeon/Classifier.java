package nl.bioinf.knotebomer.actaeon;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import weka.classifiers.trees.LMT;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class Classifier {
    private RandomForest sterolPresentModel;
    private RandomForest tailClassifierModel;
    private LMT sterolConcentrationModel;

    public Classifier() {
        loadModel();
    }

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

    public Instances classifyInstances(Instances instances) throws Exception {

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


        Instances labeled = new Instances(instances);

        labeled.insertAttributeAt(sterolPresent, labeled.numAttributes());
        labeled.insertAttributeAt(tails, labeled.numAttributes());
        labeled.insertAttributeAt(sterolConcentration, labeled.numAttributes());

        instances.insertAttributeAt(sterolConcentration, instances.numAttributes());
        instances.setClassIndex(instances.numAttributes() - 1);

        for (int i = 0; i < instances.numInstances(); i++) {
            labeled.setClassIndex(labeled.numAttributes() - 3);
            double labelSterolPresent = sterolPresentModel.classifyInstance(labeled.instance(i));
            //String clsLabelSterolPresent = labeled.classAttribute().value((int)labelSterolPresent);
            labeled.instance(i).setClassValue(labelSterolPresent);

            labeled.setClassIndex(labeled.numAttributes() - 2);
            double labelTails = tailClassifierModel.classifyInstance(labeled.instance(i));
            //String clsLabelTails = labeled.classAttribute().value((int)labelTails);
            labeled.instance(i).setClassValue(labelTails);

            labeled.setClassIndex(labeled.numAttributes() - 1);
            double labelSterolConcentration = sterolConcentrationModel.classifyInstance(instances.instance(i));
            //String clsLabelSterolConcentration = instances.classAttribute().value((int)labelSterolConcentration);
            labeled.instance(i).setClassValue(labelSterolConcentration);
        }
        //System.out.println(labeled);
        return labeled;
    }
}
