package com.mxdl.springboot.bean;

public class AddUserResponse extends BaseResponse {
    private User user;

    public AddUserResponse(String msg, int code) {
        super(msg, code);
    }

    public AddUserResponse(String msg, int code, User user) {
        super(msg, code);
        this.user = user;
    }

    @Override
    public String toString() {
        return "AddUserResponse{" +
                "user=" + user +
                '}';
    }
}
