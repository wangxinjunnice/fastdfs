package com.cn.fastdfs.utils;

public class Response {
    private String code;
    private String msg;
    private Object data;

    public Response() {
    }

    public Response(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Response ok(){
            return new Response("1","操作成功",null);
    }
    public static Response ok(Object data){
        return new Response("1","操作成功",data);
    }
    public static Response ok(String msg, Object data){
        return new Response("1",msg,data);
    }
    public static Response error(String msg){
        return new Response("0",msg,null);
    }
    public static Response error(String msg, Object data){
        return new Response("0",msg,data);
    }

    public static Response store(String msg, Object data){
        return new Response("1",msg,data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
