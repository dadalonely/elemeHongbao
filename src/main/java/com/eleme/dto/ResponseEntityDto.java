package com.eleme.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString(callSuper = true)
public class ResponseEntityDto<T> implements Serializable {

    private static final long serialVersionUID = -720807478055084231L;

    private String status;
    private String error;
    private String msg;
    private T data;

    private String pageCount;

    private String code;
    public ResponseEntityDto(){

    }

    public ResponseEntityDto(String status){
        this.status = status;
    }

    public ResponseEntityDto(String status, String error){
        this.status = status;
        this.error = error;
    }

    public ResponseEntityDto(String status, T data){
        this.status = status;
        this.data = data;
    }

    public ResponseEntityDto(String status, T data, String pageCount){
        this.status = status;
        this.data = data;
        this.pageCount = pageCount;
    }

    public ResponseEntityDto(String status, String error, String msg, T data){
        this.status = status;
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public ResponseEntityDto<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public ResponseEntityDto<T> setError(String error) {
        this.error = error;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseEntityDto<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseEntityDto<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}
