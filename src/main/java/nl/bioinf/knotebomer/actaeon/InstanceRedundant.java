package nl.bioinf.knotebomer.actaeon;

public class InstanceRedundant {
    private double apl;
    private double thickness;
    private double binding;
    private double tilt;
    private double zorder;
    private double compressibility;

    private String sterolType;
    private String tail;
    private double sterolConcentration;

    private void classifyInstance(){

    }

    public void setSterolType(String sterolType) {
        this.sterolType = sterolType;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public void setSterolConcentration(double sterolConcentration) {
        this.sterolConcentration = sterolConcentration;
    }
}
