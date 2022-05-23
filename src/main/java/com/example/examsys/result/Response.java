package com.example.examsys.result;

/**
 * @author: ximo
 * @date: 2022/3/22 10:27
 * @description:
 */
public class Response {
    private String rspCode = "200";
    private String rspMsg = "操作成功";

    public Response() {
    }

    public Response(ExceptionMsg msg) {
        this.rspCode = msg.getCode();
        this.rspMsg = msg.getMsg();
    }

    public Response(String rspCode) {
        this.rspCode = rspCode;
        this.rspMsg = "";
    }

    public Response(String rspCode, String rspMsg) {
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "rspCode='" + rspCode + '\'' +
                ", rspMsg='" + rspMsg + '\'' +
                '}';
    }
}
