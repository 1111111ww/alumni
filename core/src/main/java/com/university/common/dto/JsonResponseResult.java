package com.university.common.dto;



public class  JsonResponseResult<T> {

    private Integer code;

    private String msg;

    private T response;

    public Integer getCode() {
        return code;
    }

    public JsonResponseResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getResponse() {
        return response;
    }

    public JsonResponseResult<T> setResponse(T response) {
        this.response = response;
        return this;
    }

    public JsonResponseResult<T> createSuccess() {
        return this.setCode(0).setMsg("success");
    }

    public static JsonResponseResult Success() {
        return new JsonResponseResult().setCode(0).setMsg("success");
    }

    public static JsonResponseResult Success(Object response) {
        return JsonResponseResult.Success().setResponse(response);
    }

    public static JsonResponseResult Error(Integer errorCode, String message) {
        return new JsonResponseResult().setCode(errorCode).setMsg(message);
    }
}
