package com.company;

public class Team {
    private String name;
    private boolean isHome;

    public Team(String name, boolean isHome) {
        this.name = name;
        this.isHome = isHome;
    }

    public String getName() {
        return name;
    }

    public boolean isHome() {
        return isHome;
    }
}
