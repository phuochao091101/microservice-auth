package com.study.apigateway.response;

import com.study.apigateway.common.WrapResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public  class  WrapResponse<T> {
    private Integer code;
    private String messageError;
    private T data;

    public  static <T> WrapResponse ok(T data){
        WrapResponse res = new WrapResponse();
        res.setCode(WrapResponseStatus.SUCCESS);
        res.setData(data);
        res.setMessageError("");
        return  res;
    }

    public  static <T> WrapResponse error(Integer code, String messageError, T data){
        WrapResponse res = new WrapResponse();
        res.setCode(code);
        res.setData(data);
        res.setMessageError(messageError);
        return res;
    }
    public  static <T> WrapResponse error(Integer code, String messageError){
        WrapResponse res = new WrapResponse();
        res.setCode(code);
        res.setMessageError(messageError);
        return res;
    }

}
