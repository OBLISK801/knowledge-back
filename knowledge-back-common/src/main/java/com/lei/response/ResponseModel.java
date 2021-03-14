package com.lei.response;

import com.lei.enums.ResponseCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author LEI
 * @Date 2021/1/19 15:17
 * @Description 统一响应类
 */
@Data
public class ResponseModel<T> {

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    //构造方法私有，保护属性
    private ResponseModel() {

    }

    public static <T> ResponseModel<T> success(){
        ResponseModel<T> result = new ResponseModel<>();
        result.setCode(ResponseCode.SUCCESS.code());
        result.setMessage(ResponseCode.SUCCESS.message());
        return result;
    }

    public static <T> ResponseModel<T> error(T data){
        ResponseModel<T> result = new ResponseModel<>();
        result.setCode(ResponseCode.ERROR.code());
        result.setMessage(ResponseCode.ERROR.message());
        result.setData(data);
        return result;
    }

    public static <T> ResponseModel<T> success(T data){
        ResponseModel<T> result = new ResponseModel<>();
        result.setCode(ResponseCode.SUCCESS.code());
        result.setMessage(ResponseCode.SUCCESS.message());
        result.setData(data);
        return result;
    }

}
