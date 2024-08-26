package com.equipment_maintenance.utils;

import lombok.Data;

import static com.equipment_maintenance.enums.StatusCodeEnum.FAIL;
import static com.equipment_maintenance.enums.StatusCodeEnum.SUCCESS;

/**
 * @author ys
 * @date： 2024/8/26 21:25
 */
@Data
public class Result<T> {

    /**
     * 返回状态
     */
    private Boolean flag;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public static <T> Result<T> success() {
        return buildResult(true, null, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> Result<T> success(T data) {
        return buildResult(true, data, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> Result<T> fail(String message) {
        return buildResult(false, null, FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return buildResult(false, null, code, message);
    }

    private static <T> Result<T> buildResult(Boolean flag, T data, Integer code, String message) {
        Result<T> r = new Result<>();
        r.setFlag(flag);
        r.setData(data);
        r.setCode(code);
        r.setMsg(message);
        return r;
    }

}
