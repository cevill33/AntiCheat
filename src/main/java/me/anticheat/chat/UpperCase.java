package me.anticheat.chat;

/**
 * Created by Jakob on 26.03.2016.
 */
public class UpperCase {

    public static boolean isUpperCase(String msg) {
        int i = 0;

        for(Character chars : msg.toCharArray()) {
            if(Character.isUpperCase(chars)) {
                i++;
            }

            if(i > 5) {
                return true;
            }

        }

        if(i > 5) {
            return true;
        }




        return false;
    }
}
