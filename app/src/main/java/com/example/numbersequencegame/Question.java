package com.example.numbersequencegame;

public class Question {
    private String data [][];
        private int hidNumber;

    public Question(String[][] data, int hidNumber) {
        this.data = data;
        this.hidNumber = hidNumber;
    }


    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public int getHidNumber() {
        return hidNumber;
    }

    public void setHidNumber(int hidNumber) {
        this.hidNumber = hidNumber;
    }
}
