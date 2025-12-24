package garageV4.view;

import java.util.Scanner;

public class MenuController {
    Builder builder;
    Navigator navigator;

    public MenuController() {
        builder = new Builder();
        builder.buildMenu();
        navigator = new Navigator(builder.getRootMenu());
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            navigator.printMenu();
            Integer index = sc.nextInt();
            navigator.navigate(index);
        }
    }
}
