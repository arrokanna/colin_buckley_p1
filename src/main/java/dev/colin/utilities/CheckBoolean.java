package dev.colin.utilities;

public class CheckBoolean {
    private final boolean check;
    private final int status;

    public CheckBoolean(boolean check, int status) {
        this.check = check;
        this.status = status;
    }

    public boolean isCheck() {
        return check;
    }

    public int getStatus() {
        return status;
    }

}
