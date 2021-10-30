package nl.bioinf.knotebomer.actaeon;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CMDHandler implements  FileHandler<HashMap<String, Double>, String>{
    @Override
    public Instances readFile(HashMap<String, Double> input) {
        ArrayList<Attribute> atts = new ArrayList<>(2);
        atts.add(new Attribute("APL"));
        atts.add(new Attribute("thickness"));
        atts.add(new Attribute("bending"));
        atts.add(new Attribute("tilt"));
        atts.add(new Attribute("zorder"));
        atts.add(new Attribute("compress"));
        Instances instances = new Instances("Temp", atts, 0);


        double[] instanceValues = {input.get("APL"), input.get("thickness"), input.get("bending"), input.get("tilt"), input.get("zorder"), input.get("compress")};
        instances.add(new DenseInstance(1.0, instanceValues));


        return instances;

    }

    @Override
    public void writeFile(Instances labeledData, String outputPath) {
        System.out.println(labeledData);

    }
}
