package org.qilin.handler.exception;


import lombok.extern.slf4j.Slf4j;
import org.qilin.domain.ResponseResult;
import org.qilin.enums.AppHttpCodeEnum;
import org.qilin.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {

        log.error("出现了异常 {}", e);

        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {

        log.error("出现了异常 {}", e);

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, e.getMessage());
    }
}
