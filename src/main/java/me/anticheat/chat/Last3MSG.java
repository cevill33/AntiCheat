package me.anticheat.chat;

/**
 * Created by Jakob on 26.03.2016.
 */
public class Last3MSG {

    private String one;
    private String two;
    private String three;


    public Last3MSG(String one, String two, String three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public String getOne() {
        return one;
    }
    public String getTwo() {
        return two;
    }
    public String getThree() {
        return three;
    }

    public void addMSG(String msg) {
        one = this.two;
        two = this.three;
        this.three = msg;
    }

    public boolean isTwoSame() {
        if(two == null) {
            return false;
        }
        if(three.equals(two)) {
            return true;
        }
        return false;
    }



    public boolean isSame() {
        if(one == null) {
            return false;
        }

        if(one.equals(three)) {
            return true;
        }
        return false;
    }
}
