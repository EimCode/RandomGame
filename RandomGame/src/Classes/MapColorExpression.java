package Classes;

import java.awt.Color;

public class MapColorExpression implements Expression {

    public Color color;

    public MapColorExpression() {
        this.color = Color.BLUE;
    }

    @Override
    public void interpret(String context) {
        String[] contextSplit = context.split(" ");
        if(contextSplit.length < 2) return;
        switch (contextSplit[1].toLowerCase()) {
            case "brown":
                color = new Color(109,95,62);
                break;
            case "yellow":
                color = new Color(240,236,116);
                break;
            case "green":
                color = new Color(65,97,87);
                break;
            default:
                color = new Color(65,95,2);
                break;
        }
    }
}
