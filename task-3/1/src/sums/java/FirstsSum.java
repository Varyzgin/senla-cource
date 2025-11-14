package sums.java;

public class FirstsSum {

    static int rand3xNum() {
        int num = 0;
        while (num < 100) {
            num = (new java.util.Random()).nextInt(999);
        }
        return  num;
    }

    public static void main(String[] args) {
        int a_num = rand3xNum();
        int b_num = rand3xNum();
        int c_num = rand3xNum();
        System.out.println("3 случайно сгенерированных трёхзначных числа: " + a_num + ", " + b_num + ", " + c_num);

        int res = 0;
        res += (int) a_num / 100;
        res += (int) b_num / 100;
        res += (int) c_num / 100;
        System.out.println("Сумма их первых цифр: " + res);
    }
}