package main.java;

public class Pipeline implements IAssemblyLine {
    private ILineStep step_one;
    private ILineStep step_two;
    private ILineStep step_three;

    Pipeline(ILineStep step_one, ILineStep step_two, ILineStep step_three) {
        this.step_one = step_one;
        this.step_two = step_two;
        this.step_three = step_three;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("Старт сборки");

        IProductPart part_one = step_one.buildProductPart();
        product.installFirstPart(part_one);

        IProductPart part_two = step_two.buildProductPart();
        product.installSecondPart(part_two);

        IProductPart part_three = step_three.buildProductPart();
        product.installThirdPart(part_three);

        System.out.println("Машина готова");
        return product;
    }
}
