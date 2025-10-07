public class PreparedWheels implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        PartWheels wheels = new PartWheels();
        System.out.println("Колеса подготовлены");
        return wheels;
    }
}
