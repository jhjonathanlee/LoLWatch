package com.lee2384.jonathan.lcsfantasytracker;

import org.json.JSONObject;

/**
 * Created by Jonathan on 7/15/2014.
 */
public class LcsMatch {
    /*
    private Team teamOne;
    private Team teamTwo;
*/
    private String name;
    private String dateTime;

    private int round;

    public LcsMatch(String name, String dateTime, int round) {
        this.name = name;
        this.dateTime = dateTime;
        this.round = round;
    }
/*
    public Team getTeamOne() {
        return teamOne;
    }

    public Team getTeamTwo() {
        return teamTwo;
    }
*/
    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getRound() {
        return round;
    }
/*
    private class LcsTeam implements Team {

        private String name;
        private int id;

        public LcsTeam(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }*/
    @Override
    public String toString() {
        return name;
    }
}
