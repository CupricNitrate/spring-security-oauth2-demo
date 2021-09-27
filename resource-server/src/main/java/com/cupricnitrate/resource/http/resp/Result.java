package com.cupricnitrate.resource.http.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 硝酸铜
 * @date 2021/9/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private T result;

    private Integer code;

    public static <Q> Result success(Q q){
        return new Result(q,200);
    }

    public static Result success(){
        return new Result(null,200);
    }

    public static Result error(String message,Integer code){
        return new Result(message,code);
    }

    public static Result error(){
        return new Result("Internal Server Error",500);
    }

    public static Result error(Exception e){
        return new Result(e,500);
    }

    public static <Q> Result error(Q q){
        return new Result(q,500);
    }

}
