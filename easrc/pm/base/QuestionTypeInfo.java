package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class QuestionTypeInfo extends AbstractQuestionTypeInfo implements Serializable 
{
    public QuestionTypeInfo()
    {
        super();
    }
    protected QuestionTypeInfo(String pkField)
    {
        super(pkField);
    }
}