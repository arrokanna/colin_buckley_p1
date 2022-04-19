package dev.colin.entities;

public class CheckBoolean {
    private boolean check;
    private int status;

    CheckBoolean() {

    }

    public CheckBoolean(boolean check, int status) {
        this.check = check;
        this.status = status;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
