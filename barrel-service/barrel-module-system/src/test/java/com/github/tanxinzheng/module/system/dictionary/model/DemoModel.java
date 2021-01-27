package com.github.tanxinzheng.module.system.dictionary.model;

import com.github.tanxinzheng.framework.web.annotation.AccountField;
import com.github.tanxinzheng.framework.web.annotation.DictionaryTransfer;
import lombok.Data;

/**
 * Created by tanxinzheng on 2018/11/15.
 */
@Data
public class DemoModel {

    private String id;
    @AccountField
    private String userId;
    @DictionaryTransfer(type = "SEX")
    private String sex;
    @DictionaryTransfer(type = "DISABLE", fieldName = "disableName")
    private String disable;
}
