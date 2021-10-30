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

//        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(3);
//        Attribute tails = new Attribute("tails");
//        Attribute sterolPresent = new Attribute("sterolPresent");
//        Attribute sterolConsentration = new Attribute("sterolConsentration");

//        attributeList.add(tails);
//        attributeList.add(sterolPresent);
//        attributeList.add(sterolConsentration);
//        ArffLoader loader = new ArffLoader();
//        loader.setSource(new File("data/scaled_data_tails.arff"));
//        Instances data = loader.getDataSet();

//        RandomForest treeClassifier = (RandomForest) SerializationHelper.read(new FileInputStream("models/Help.model"));
//        //instances.insertAttributeAt(new Attribute("tails"), instances.numAttributes());
//        data.setClassIndex(data.numAttributes() - 1);
//        Instance next = data.instance(0);
//        System.out.println(next);
//        double pred = treeClassifier.classifyInstance(next);
//
//        String prediction = data.classAttribute().value((int)pred);
//        System.out.println(prediction);


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



        //instances.insertAttributeAt(tails, instances.numAttributes());

        //instances.setClassIndex(data.numAttributes() - 1);

//        double value = instances.instance(0).classValue();
//        double pred1 = treeClassifier.classifyInstance(instances.instance(0));
//        System.out.println(pred1);

        Instances labeled = new Instances(instances);

        labeled.insertAttributeAt(sterolPresent, labeled.numAttributes());
        labeled.insertAttributeAt(tails, labeled.numAttributes());
        labeled.insertAttributeAt(sterolConcentration, labeled.numAttributes());

        instances.insertAttributeAt(sterolConcentration, instances.numAttributes());
        instances.setClassIndex(instances.numAttributes() - 1);

//        if (labeled.classIndex() == -1)
//            labeled.setClassIndex(labeled.numAttributes() - 1);
//        if (instances.classIndex() == -1)
//            instances.setClassIndex(instances.numAttributes() - 1);

        //System.out.println(instances);

//        double labelSterolConcentration = sterolConcentrationModel.classifyInstance(instances.instance(0));
//        String clsLabelSterolConcentration = instances.classAttribute().value((int)labelSterolConcentration);
//        System.out.println(clsLabelSterolConcentration);
        //labeled.instance(0).setClassValue(clsLabelSterolConcentration);

//        double labelTails = tailClassifierModel.classifyInstance(labeled.instance(1));
//        String clsLabelTails = labeled.classAttribute().value((int)labelTails);
//        System.out.println(clsLabelTails);

        //double value = instances.instance(0).classValue();
        //double pred1 = sterolPresentModel.classifyInstance(labeled.instance(0));
//        String prediction = labeled.classAttribute().value((int)pred1);
//        System.out.println(prediction);

        //System.out.println(labeled);

        //System.out.println(labeled);
//        treeClassifier.getCapabilities();
        //double labelTails = tailClassifierModel.cla

        for (int i = 0; i < instances.numInstances(); i++) {
            labeled.setClassIndex(labeled.numAttributes() - 3);
            double labelSterolPresent = sterolPresentModel.classifyInstance(labeled.instance(i));
            //String clsLabelSterolPresent = labeled.classAttribute().value((int)labelSterolPresent);
            labeled.instance(i).setClassValue(labelSterolPresent);
//
            labeled.setClassIndex(labeled.numAttributes() - 2);
            double labelTails = tailClassifierModel.classifyInstance(labeled.instance(i));
            //String clsLabelTails = labeled.classAttribute().value((int)labelTails);
            labeled.instance(i).setClassValue(labelTails);
//
            labeled.setClassIndex(labeled.numAttributes() - 1);
            double labelSterolConcentration = sterolConcentrationModel.classifyInstance(instances.instance(i));
            //String clsLabelSterolConcentration = instances.classAttribute().value((int)labelSterolConcentration);
            labeled.instance(i).setClassValue(labelSterolConcentration);



            //double labelTails = tailClassifierModel.classifyInstance(labeled.instance(i));
//            double labelSterolPresent = sterolPresentModel.classifyInstance(labeled.instance(i));
//            double labelSterolConcetration = sterolConcentrationModel.classifyInstance(labeled.instance(i));
            //System.out.println(instances.instance(i));
            //double labelSterolConcetration = sterolConcentrationModel.classifyInstance(labeled.instance(i));
            //System.out.println(labelSterolConcetration);

            //treeClassifier.classifyInstance(i);
            //labeled.instance(i).setClassValue(clsLabel);

            //String prediction = labeled.classAttribute().value((int)labelTails);
            //labeled.instance(i).setValue(labeled.numAttributes() - 3, labelTails);
            //labeled.instance(i).setValue(labeled.numAttributes() - 2, labelSterolPresent);
            //labeled.instance(i).setClassValue(labelSterolConcetration);
        }
        System.out.println(labeled);
        return labeled;
    }
}
