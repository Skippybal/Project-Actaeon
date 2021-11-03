package nl.bioinf.knotebomer.actaeon;

import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

public class CLIOptionsProvider implements OptionProvider{
    private Options options;
    private CommandLine cmd;
    private String filePath;
    private String outPath = "data/output.csv";
    private Boolean printToCommandLine = false;
    private final Map<String, Double> input = new HashMap<>();

    /**
     * Parse the command line arguments and verify them
     * @param args from the command line
     */
    public CLIOptionsProvider(String[] args){
        init();
        parseArgs(args);
    }

    /**
     * Parse the command line arguments
     * @param args from the command line
     */
    private void parseArgs(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            this.cmd = parser.parse(options, args);
            if (cmd.hasOption('h')) {
                printHelp();
            }
            verify();
        } catch (ParseException e) {
            System.err.println("Something went wrong while parsing " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ant", options );
             //e.printStackTrace();
        }
    }

    /**
     * Print help of the commons CLI
     */
    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "ActaeonLauncher.jar [options]", options );
    }

    /**
     * Define possible command line arguments
     */
    private void init() {
        this.options = new Options();
        options.addOption(new Option("f", "infile",
                true, "The input file - expects columned data, either .csv or .arff"));
        options.addOption(new Option("o", "outfile",
                true, "The output file - expects wanted output file location, either .csv or .arff;" +
                "Default: data.output.csv"));
        options.addOption(new Option("p", "print",
                false, "Boolean, has no given value - add to print output to the command line"));
        options.addOption(new Option("a", "apl",
                true, "The area per lipid of the membrane - expects double"));
        options.addOption(new Option("t", "thickness",
                true, "The thickness of the membrane - expects double"));
        options.addOption(new Option("b", "bending",
                true, "The bending rigidity - expects double"));
        options.addOption(new Option("i", "tilt",
                true, "The tilt angle - expects double between 0 and 360"));
        options.addOption(new Option("z", "zorder",
                true, "The z-order - expects double"));
        options.addOption(new Option("c", "compress",
                true, "The compressibility - expects double"));
        options.addOption(new Option("h", "help",
                false, "Prints the help"));
    }

    /**
     * Verify command line input
     * @throws ParseException
     */
    private void verify() throws ParseException {
        if (cmd.hasOption('p')) {
            this.printToCommandLine = true;
        }
        if (cmd.hasOption('o')) {
            String outputFileType = FilenameUtils.getExtension(cmd.getOptionValue('o'));
            if (outputFileType.equals("csv") | outputFileType.equals("arff")){
                this.outPath = cmd.getOptionValue('o');
            } else {
                throw new ParseException("Invalid output file type " + outputFileType);
            }
        }
        //System.out.println(cmd.hasOption("-f"));
        if (cmd.hasOption("f")) {
            String outputFileType = FilenameUtils.getExtension(cmd.getOptionValue('f'));
            if (outputFileType.equals("csv") | outputFileType.equals("arff")){
                this.filePath = cmd.getOptionValue('f');
            } else {
                throw new ParseException("Invalid input file type " + outputFileType);
            }

        } else if (cmd.hasOption('a') & cmd.hasOption('t')
                & cmd.hasOption('b') & cmd.hasOption('i')
                & cmd.hasOption('z') & cmd.hasOption('c')){
            try {
                //ystem.out.println(Double.parseDouble(cmd.getOptionValue('a').replace(",", ".")));

//                String aplStr = cmd.getOptionValue('a').replace(",", ".");
//                String thicknessStr = cmd.getOptionValue('t').replace(",", ".");
//                String bendingStr = cmd.getOptionValue('b').replace(",", ".");
//                String tiltStr = cmd.getOptionValue('i').replace(",", ".");
//                String zorederStr = cmd.getOptionValue('z').replace(",", ".");
//                String comperssibilityStr = cmd.getOptionValue('c').replace(",", ".");

                //System.out.println(Double.parseDouble(cmd.getOptionValue('a')));
                //System.out.println("g");


                double apl = Double.parseDouble(cmd.getOptionValue('a').replace(",", "."));
                double thickness =Double.parseDouble(cmd.getOptionValue('t').replace(",", "."));
                double bending = Double.parseDouble(cmd.getOptionValue('b').replace(",", "."));
                double tilt = Double.parseDouble(cmd.getOptionValue('i').replace(",", "."));
                double zorder =Double.parseDouble(cmd.getOptionValue('z').replace(",", "."));
                double compressibility = Double.parseDouble(cmd.getOptionValue('c').replace(",", "."));

                if (apl < 0) {
                    throw new ParseException("Number is below zero: " + apl);
                }
                if (thickness < 0) {
                    throw new ParseException("Number is below zero: " + thickness);
                }
                if (bending < 0) {
                    throw new ParseException("Number is below zero: " + bending);
                }
                if (tilt < 0 | tilt >= 360) {
                    throw new ParseException("Not a valid tilt angle: " + tilt);
                }
                if (zorder < 0) {
                    throw new ParseException("Number is below zero: " + zorder);
                }
                if (compressibility < 0) {
                    throw new ParseException("Number is below zero: " + compressibility);
                }

                input.put("APL", apl);
                input.put("thickness", thickness);
                input.put("bending", bending);
                input.put("tilt", tilt);
                input.put("zorder", zorder);
                input.put("compress", compressibility);


            } catch (NumberFormatException nfe) {
                throw new ParseException("One of the inputs could not be parsed.");
            }

        } else {
            throw new ParseException("Not a valid input");
        }

    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public String getOutputFile() {
        return outPath;
    }

    @Override
    public String getInputFileExtention() {
        return FilenameUtils.getExtension(filePath);
    }

    @Override
    public String getOutputFileExtention() {
        return FilenameUtils.getExtension(outPath);
    }

    @Override
    public Boolean getPrintToCMD() {
        return printToCommandLine;
    }

    @Override
    public Map<String, Double> getCharacteristics() {
        return input;
    }
}
