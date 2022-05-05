package com.example.myapplicationh;

public class Player {
    private String name;
    private String score;
    private double lat;
    private double lon;

    public Player(){
    }

    public double getLat() {
        return lat;
    }

    public Player setLat(double lat){
        this.lat = lat;
        return this;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Player setLon(double lon) {
        this.lon = lon;
        return this;
    }
    public String getScore() {
        return score;
    }

    public Player setScore(String score) {
        this.score = score;
        return this;
    }
}
