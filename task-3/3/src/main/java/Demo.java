public class Demo {
    public static void main(String[] args) {
        ILineStep step_A = new PreparedBase();
        ILineStep step_B = new PreparedWheels();
        ILineStep step_C = new PreparedEngine();

        Car car = new Car();
        Pipeline pipeline = new Pipeline(step_A, step_B, step_C);
        pipeline.assembleProduct(car);
    }
}
