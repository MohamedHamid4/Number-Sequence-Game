package com.example.numbersequencegame;

public class GameVeribel {

    private  int id;
    private  String name;
    private  int score;
    private  String date;

    public GameVeribel(int id, String name, int score, String date) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.date = date;
    }
    public GameVeribel(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}