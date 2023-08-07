package com.example.numbersequencegame;

public class Util {
    public static Question generteQuestion() {

        String[][] x = new String[3][3];
        int startnumber = (int) (Math.random() * 10) + 1;
        int incstartnumber = (int) (Math.random() * 5) + 1;
        int strednumber;
        int number = -1;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {


                strednumber = startnumber + incstartnumber;
                if (i == 1 && j == 1) {
                    x[i][j] = "??";
                    number = strednumber;

                } else {
                    x[i][j] = strednumber + "";
                }

                incstartnumber += 2;
                startnumber = strednumber;

            }
        }
        return new Question(x, number);
    }

}
