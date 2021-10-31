package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class ActaeonLauncher {
    public static void main(String[] args) {
        // TODO
//        try{
//        CSVHandler file = new CSVHandler();
//        Instances instances = file.readFile("data/clean_data.csv");
//        // System.out.println(instances);
//
//        Classifier classifier = new Classifier();
//        Instances labeled = classifier.classifyInstances(instances);
//        // System.out.println(labeled);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        ActaeonLauncher launcher = new ActaeonLauncher();
        OptionProvider optionProvider = new CLIOptionsProvider(args);
        launcher.start(optionProvider);

    }

    void start(OptionProvider optionProvider) {
        try {
            Instances instances;
            String inputFile = optionProvider.getFilePath();
            Boolean printToCommandLine = optionProvider.getPrintToCMD();
            FileHandler<String, String> inputHandler;
            //System.out.println(optionProvider.getCharacteristics());
            if (inputFile != null) {
                //String fileType = FilenameUtils.getExtension(inputFile);
                String fileType = optionProvider.getInputFileExtention();

                if (fileType.equals("csv")) {
                    inputHandler = new CSVHandler();
                } else {
                    inputHandler = new ArffHandler();
                }

                //assert inputHandler != null;
                System.out.println(inputFile);
                instances = inputHandler.readFile(inputFile);
            } else if (optionProvider.getCharacteristics() != null){
                    CMDHandler cmdHandler = new CMDHandler();
                    instances = cmdHandler.readFile(optionProvider.getCharacteristics());
            } else {
                throw new IllegalArgumentException("No valid arguments found");
            }

            Classifier classifier = new Classifier();
            Instances labeled = classifier.classifyInstances(instances);
            if (printToCommandLine) {
                CMDHandler cmdHandler = new CMDHandler();
                cmdHandler.writeFile(labeled, "");
            }

            FileHandler<String, String> outputHandler;
            String outFile = optionProvider.getOutputFile();
            if (outFile != null){
                String outFileType = optionProvider.getOutputFileExtention();
                if (outFileType.equals("csv")) {
                    outputHandler = new CSVHandler();
                } else {
                    outputHandler = new ArffHandler();
                }
                System.out.println(outFile);
                outputHandler.writeFile(labeled, optionProvider.getOutputFile());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
