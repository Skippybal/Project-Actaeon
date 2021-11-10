package nl.bioinf.knotebomer.actaeon;

import org.apache.commons.cli.ParseException;
import weka.core.Instances;

public class ActaeonLauncher {
    public static void main(String[] args) {

        try {
            ActaeonLauncher launcher = new ActaeonLauncher();
            OptionProvider optionProvider = new CLIOptionsProvider(args);
            launcher.start(optionProvider);
        } catch (ParseException e) {
            /*
            If there is and exception print the stacktrace,
            not the cleanest for end users.
            But user-friendliness is not the main concern here.
             */
            //e.printStackTrace();
            System.err.println("A critical error occurred while parsing\nStopping program");
        } catch (Exception e){
            /*
            Catch errors that couldn't be handled locally and print the stacktrace.
            This catch should theoretically never be reached due to argument handling
            in parsing and classification.
             */
            e.printStackTrace();
        }

    }

    /**
     * Start the program
     * @param optionProvider, provider of options
     * @throws Exception, exceptions that could not be handled locally
     */

    void start(OptionProvider optionProvider) throws Exception {

        Instances instances;
        String inputFile = optionProvider.getFilePath();
        Boolean printToCommandLine = optionProvider.getPrintToCMD();
        FileHandler inputHandler;

        if (inputFile != null) {
            String fileType = optionProvider.getInputFileExtension();

            inputHandler = switch (fileType) {
                case "csv" -> new CSVHandler();
                case "arff" -> new ArffHandler();
                default -> throw new IllegalStateException("Unexpected value: " + fileType);
            };
            instances = inputHandler.readFile(inputFile);
        } else if (optionProvider.getCharacteristics() != null){
                CMDHandler cmdHandler = new CMDHandler();
                instances = cmdHandler.readCommandLine(optionProvider.getCharacteristics());
        } else {
            throw new IllegalArgumentException("No valid arguments found");
        }

        Classification classification = new Classification();
        Instances labeled = classification.classifyInstances(instances);
        if (printToCommandLine) {
            CMDHandler cmdHandler = new CMDHandler();
            cmdHandler.writeCommandLine(labeled);
        }

        FileHandler outputHandler;
        String outFile = optionProvider.getOutputFile();
        if (outFile != null){
            String outFileType = optionProvider.getOutputFileExtension();
            if (outFileType.equals("csv")) {
                outputHandler = new CSVHandler();
            } else {
                outputHandler = new ArffHandler();
            }
            outputHandler.writeFile(labeled, optionProvider.getOutputFile());
        }

    }
}
