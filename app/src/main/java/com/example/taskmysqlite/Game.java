package com.example.taskmysqlite;

public class Game {
    private int id;
    private byte[] gamePicture;
    private String namaGame;
    private String priceGame;
    private String typeGame;

    public Game(int id, byte[] gamePicture, String namaGame, String priceGame, String typeGame) {
        this.id = id;
        this.gamePicture = gamePicture;
        this.namaGame = namaGame;
        this.priceGame = priceGame;
        this.typeGame = typeGame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getGamePicture() {
        return gamePicture;
    }

    public void setGamePicture(byte[] gamePicture) {
        this.gamePicture = gamePicture;
    }

    public String getNamaGame() {
        return namaGame;
    }

    public void setNamaGame(String namaGame) {
        this.namaGame = namaGame;
    }

    public String getPriceGame() {
        return priceGame;
    }

    public void setPriceGame(String priceGame) {
        this.priceGame = priceGame;
    }

    public String getTypeGame() {
        return typeGame;
    }

    public void setTypeGame(String typeGame) {
        this.typeGame = typeGame;
    }
}
