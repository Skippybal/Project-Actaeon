package nl.bioinf.knotebomer.actaeon;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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

        int numAttributes = labeledData.numAttributes();

        for (int i = 0; i < numAttributes; i++) {
            System.out.println("Attribute " + i + " = " + labeledData.attribute(i));
        }
        System.out.println("Label indices = 6, 7, 8");

        System.out.println();

        Enumeration<Instance> instanceEnumeration = labeledData.enumerateInstances();
        while (instanceEnumeration.hasMoreElements()) {
            Instance instance = instanceEnumeration.nextElement();
            double[] instanceArray = instance.toDoubleArray();
            double[] instanceValues = Arrays.copyOfRange(instanceArray, 0, 6);
            String[] instanceLabelsString = Arrays.copyOfRange(instance.toString().split(","), 6, 9);

            System.out.println("Instance: " + Arrays.toString(instanceValues) + ", Classified as: " + Arrays.toString(instanceLabelsString));
        }

    }
}
