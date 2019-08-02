package com.github.tanxinzheng.framework.web.exceptions;

import com.github.tanxinzheng.framework.web.model.RestResponse;
import com.github.tanxinzheng.framework.web.rest.WebCommonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tanxinzheng on 16/12/10.
 */
public abstract class AbstractRestExceptionHandler {

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
        if(HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            WebRequest webRequest = (WebRequest) request;
            webRequest.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if(WebCommonUtils.isJSON(request)){
            if(body instanceof RestResponse){
                return new ResponseEntity(body, headers, status);
            }
            RestResponse restError = RestResponse.failed(status, ex.getMessage());
            return new ResponseEntity(restError, headers, status);
        }
        return new ResponseEntity(body, headers, status);
    }
}
