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
        options.addOption(new Option("d", "debug", true, "Turn on debug."));
        options.addOption(new Option("f", "file", true, "The iput file - expects col"));
    }

    @Override
    public String getFileName() {
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
