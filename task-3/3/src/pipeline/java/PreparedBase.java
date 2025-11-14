package pipeline.java;

public class PreparedBase implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        PartBase base = new PartBase();
        System.out.println("Кузов подготовлен");
        return base;
    }
}
