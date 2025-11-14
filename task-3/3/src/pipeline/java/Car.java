package pipeline.java;

public class Car implements IProduct {
    private IProductPart firstPart;
    private IProductPart secondPart;
    private IProductPart thirdPart;

    Car() {
        System.out.println("Машина создана");
    }

    @Override
    public void installFirstPart(IProductPart part) {
        this.firstPart = part;
        System.out.println("Кузов установлен");
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.secondPart = part;
        System.out.println("Колеса установлены");
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.thirdPart = part;
        System.out.println("Двигатель установлен");
    }
}
