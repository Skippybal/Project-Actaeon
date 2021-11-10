package nl.bioinf.knotebomer.actaeon;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Map;

public class CMDHandler{

    /**
     * Read input from command line
     * @param input, map with membrane characteristic and value
     * @return Instances object of the input values
     */
    public Instances readCommandLine(Map<String, Double> input) {

        ArrayList<Attribute> attributes = new ArrayList<>(2);

        String[] attributeNames = {"APL", "thickness", "bending", "tilt", "zorder", "compress"};
        for (String name : attributeNames){
            attributes.add(new Attribute(name));
        }

        Instances instances = new Instances("Temp", attributes, 0);

        double[] instanceValues = {input.get("APL"), input.get("thickness"), input.get("bending"), input.get("tilt"), input.get("zorder"), input.get("compress")};
        instances.add(new DenseInstance(1.0, instanceValues));

        return instances;

    }

    /**
     * Write output to command line
     * @param labeledData Instances object with the results
     */
    public void writeCommandLine(Instances labeledData) {
        System.out.println(labeledData);

    }
}
