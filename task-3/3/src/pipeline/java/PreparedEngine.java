package pipeline.java;

public class PreparedEngine implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        PartEngine engine = new PartEngine();
        System.out.println("Двигатель подготовлен");
        return engine;
    }
}
