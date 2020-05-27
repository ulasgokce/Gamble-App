package com.example.gambleapp;

import java.util.List;

public class LuckyNumbers {
    private String gameId;
    private List<Integer> arr;

    public LuckyNumbers() {
    }

    public LuckyNumbers(String gameId, List<Integer> arr) {
        this.gameId = gameId;
        this.arr = arr;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Integer> getArr() {
        return arr;
    }

    public void setArr(List<Integer> arr) {
        this.arr = arr;
    }
}
