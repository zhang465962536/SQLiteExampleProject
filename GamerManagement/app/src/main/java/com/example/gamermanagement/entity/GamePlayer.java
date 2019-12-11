package com.example.gamermanagement.entity;

//【3】创建玩家实体类
public class GamePlayer {
    private int id;
    private String player;
    private int score;
    private int level;

    public GamePlayer(int id, String player, int score, int level) {
        this.id = id;
        this.player = player;
        this.score = score;
        this.level = level;
    }
    public GamePlayer( String player, int score, int level) {
        this.player = player;
        this.score = score;
        this.level = level;
    }
    public GamePlayer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "GamePlayer{"+
                "id="+id+
                ",player='"+player+"\'"+
                ",score='"+score+"\'"+
                ",level='"+level+"\'"+
                "}";
    }
}
