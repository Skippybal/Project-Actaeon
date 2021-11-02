package nl.bioinf.knotebomer.actaeon;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Map;

public class CMDHandler implements  FileHandler<Map<String, Double>, String>{

    /**
     * Read input from command line
     * @param input, map with membrane characteristic and value
     * @return Instances object of the input values
     */
    @Override
    public Instances readFile(Map<String, Double> input) {
        ArrayList<Attribute> attributes = new ArrayList<>(2);
        attributes.add(new Attribute("APL"));
        attributes.add(new Attribute("thickness"));
        attributes.add(new Attribute("bending"));
        attributes.add(new Attribute("tilt"));
        attributes.add(new Attribute("zorder"));
        attributes.add(new Attribute("compress"));
        Instances instances = new Instances("Temp", attributes, 0);


        double[] instanceValues = {input.get("APL"), input.get("thickness"), input.get("bending"), input.get("tilt"), input.get("zorder"), input.get("compress")};
        instances.add(new DenseInstance(1.0, instanceValues));


        return instances;

    }

    /**
     * Write output to command line
     * @param labeledData Instances object with the results
     * @param outputPath, specified output file location
     */
    @Override
    public void writeFile(Instances labeledData, String outputPath) {
        System.out.println(labeledData);

    }
}
