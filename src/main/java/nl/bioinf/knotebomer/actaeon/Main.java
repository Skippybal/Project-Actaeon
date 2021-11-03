package nl.bioinf.knotebomer.actaeon;

public class Main {
    public static void main(String[] args) {

        ActaeonLauncher launcher = new ActaeonLauncher();
        OptionProvider optionProvider = new CLIOptionsProvider(args);
        launcher.start(optionProvider);

    }
}
