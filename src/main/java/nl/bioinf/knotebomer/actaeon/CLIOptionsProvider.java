package nl.bioinf.knotebomer.actaeon;

import org.apache.commons.cli.*;

public class CLIOptionsProvider implements OptionProvider{
    private Options options;
    private CommandLine cmd;

    public CLIOptionsProvider(String[] args){
        init();
        parseArgs(args);
    }

    private void parseArgs(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            this.cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Something went wrong while parsing" + e.getCause());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ant", options );
            // e.printStackTrace();
        }
    }

    private void init() {
        this.options = new Options();
        options.addOption(new Option("i", "inputtype", true, "Input type to use - expects csv or line"));
        options.addOption(new Option("f", "infile", true, "The input file - expects columned data"));
        options.addOption(new Option("a", "apl", true, "The area per lipid of the membrane - expects double"));
        options.addOption(new Option("t", "thickness", true, "The thickness of the membrane - expects double"));
        options.addOption(new Option("b", "bending", true, "The bending rigidity - expects double"));
        options.addOption(new Option("t", "tilt", true, "The tilt angle - expects double between 0 and 360"));
        options.addOption(new Option("z", "zorder", true, "The z-order - expects double"));
        options.addOption(new Option("c", "compress", true, "The compressibility - expects double"));
    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public String getInputType() {
        return null;
    }

    @Override
    public String getOutputFIle() {
        return null;
    }

    @Override
    public double getAPL() {
        return 0;
    }

    @Override
    public double getThickness() {
        return 0;
    }

    @Override
    public double getBinding() {
        return 0;
    }

    @Override
    public double getTilt() {
        return 0;
    }

    @Override
    public double getZorder() {
        return 0;
    }

    @Override
    public double getCompressibility() {
        return 0;
    }
}
