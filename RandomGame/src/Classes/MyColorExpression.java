package Classes;

import java.awt.Color;

public class MyColorExpression implements  Expression {
    public String photo;

    public MyColorExpression() {
        this.photo = "Images/green.png";
    }

    @Override
    public void interpret(String context) {
        String[] contextSplit = context.split(" ");
        if(contextSplit.length < 2) return;
        switch (contextSplit[1].toLowerCase()) {
            case "blue":
                photo = "Images/blue.png";
                break;
            case "red":
                photo = "Images/red.png";
                break;
            default:
                photo = "Images/green.png";
                break;
        }
    }
}
