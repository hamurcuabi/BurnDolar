package com.emrehmrc.burndolar;

public class UserModel {
    private String nickName;
    private int burnCount;


    public UserModel(String nickName, int burnCount) {

        this.nickName = nickName;
        this.burnCount = burnCount;

    }

    public UserModel() {
    }


    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getBurnCount() {
        return burnCount;
    }

    public void setBurnCount(int burnCount) {
        this.burnCount = burnCount;
    }
}
