package com.example.ttcn2etest.controller;

import com.example.ttcn2etest.constant.ErrorCodeDefs;
import com.example.ttcn2etest.response.BaseItemResponse;
import com.example.ttcn2etest.response.BaseListItemResponse;
import com.example.ttcn2etest.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BaseController {
    protected <T> ResponseEntity<?> buildItemResponse(T data) {
        BaseItemResponse<T> response = new BaseItemResponse<>();
        response.setData(data);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildListItemResponse(List<T> data, long total) {
        BaseListItemResponse<T> response = new BaseListItemResponse<>();
        response.setResult(data, total);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<?> buildResponse() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(false);
        baseResponse.setFailed(ErrorCodeDefs.SERVER_ERROR, ErrorCodeDefs.getErrMsg(ErrorCodeDefs.SERVER_ERROR));
        return ResponseEntity.ok(baseResponse);
    }
}
