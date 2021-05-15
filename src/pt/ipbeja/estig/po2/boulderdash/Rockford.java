package pt.ipbeja.estig.po2.boulderdash;

/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 *
 */

public class Rockford {

    private static Rockford instace = null;

    protected Rockford() {}

    public static Rockford getInstance() {
        if(instace == null) {
            instace = new Rockford();
        }
        return instace;
    }
}
