package nl.bioinf.knotebomer.actaeon;

import weka.core.Instances;

public class ActaeonLauncher {
    public static void main(String[] args) {

        ActaeonLauncher launcher = new ActaeonLauncher();
        OptionProvider optionProvider = new CLIOptionsProvider(args);
        launcher.start(optionProvider);

    }


    /**
     * Start the program
     * @param optionProvider, provider of options
     */
    void start(OptionProvider optionProvider) {
        try {
            Instances instances;
            String inputFile = optionProvider.getFilePath();
            Boolean printToCommandLine = optionProvider.getPrintToCMD();
            FileHandler<String, String> inputHandler;

            if (inputFile != null) {
                String fileType = optionProvider.getInputFileExtention();

                if (fileType.equals("csv")) {
                    inputHandler = new CSVHandler();
                } else {
                    inputHandler = new ArffHandler();
                }

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
                outputHandler.writeFile(labeled, optionProvider.getOutputFile());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
