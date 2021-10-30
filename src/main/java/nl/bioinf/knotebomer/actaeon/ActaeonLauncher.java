package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;

public class ActaeonLauncher {
    public static void main(String[] args) {
        // TODO
        try{
        FileHandler file = new FileHandler();
        Instances instances = file.readCSVToInstances("data/clean_data.csv");
        // System.out.println(instances);

        Classifier classifier = new Classifier();
        Instances labeled = classifier.classifyInstances(instances);
        // System.out.println(labeled);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    void start(OptionProvider optionProvider) {
        String inputFile = optionProvider.getFileName();

    }
}
