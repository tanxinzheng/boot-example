package com.github.tanxinzheng.framework.web.rest;

import com.github.tanxinzheng.framework.exception.BusinessException;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

/**
 * Created by Jeng on 15/11/29.
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Log logger = LogFactory.getLog(RestExceptionHandler.class);

//    @Value(value = "${spring.servlet.multipart.max-file-size}")
    private Long maxUploadSize;

    @ExceptionHandler(value = {
            Exception.class,
            MalformedJwtException.class
    })
    @ResponseBody
    public RestResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        logger.error(ex.getMessage(), ex);
        RestResponse restError = RestResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        if(ex instanceof BindException){
            BindException bindException = (BindException) ex;
            restError = handleBindException(bindException.getBindingResult(), bindException);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            restError = handleBindException(methodArgumentNotValidException.getBindingResult(), methodArgumentNotValidException);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof IllegalArgumentException ||
                ex instanceof BusinessException){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            restError.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof DuplicateKeyException){
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            restError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            restError.setMessage("保存失败，存在重复关键字段");
        }else if(ex instanceof MaxUploadSizeExceededException){
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            restError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            restError.setMessage(MessageFormat.format("文件上传限制最大不能超过{0}M" , (maxUploadSize/1024)/1024));
        }else if(ex instanceof AccessDeniedException){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            restError.setStatus(HttpStatus.FORBIDDEN.value());
            restError.setMessage(ex.getMessage());
        }else if(ex instanceof MalformedJwtException){
            restError.setStatus(HttpStatus.UNAUTHORIZED.value());
            restError.setMessage("token校验失败");
        }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restError.setMessage(ex.getMessage());
        }
        return restError;
    }

    protected RestResponse handleBindException(BindingResult bindingResult, Exception ex) {
        RestResponse restError = RestResponse.failed(HttpStatus.BAD_REQUEST, "非法请求参数，校验请求参数不合法");
        BindingResult result = bindingResult;
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        List<FieldError> fieldErrorList = new ArrayList<FieldError>();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            FieldError error = new FieldError();
            error.setMessage(fieldError.getDefaultMessage());
            error.setField(fieldError.getField());
            error.setRejectedValue(fieldError.getRejectedValue());
            error.setObjectName(fieldError.getObjectName());
            fieldErrorList.add(error);
        }
        if(!CollectionUtils.isEmpty(fieldErrorList)){
            FieldError fieldError = fieldErrorList.get(0);
            if(StringUtils.trimToNull(fieldError.getField()) != null && StringUtils.trimToNull(fieldError.getMessage()) != null ){
                restError.setMessage(fieldError.getMessage());
            }
        }
        return restError;
    }

}
