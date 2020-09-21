package Classes;

import java.awt.*;

public class OpponentColorExpression implements  Expression{
    public String photo;

    public OpponentColorExpression() {
        this.photo = "Images/red.png";
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
