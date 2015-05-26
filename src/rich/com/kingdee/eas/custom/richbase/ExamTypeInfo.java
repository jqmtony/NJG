package com.kingdee.eas.custom.richbase;

import java.io.Serializable;

public class ExamTypeInfo extends AbstractExamTypeInfo implements Serializable 
{
    public ExamTypeInfo()
    {
        super();
    }
    protected ExamTypeInfo(String pkField)
    {
        super(pkField);
    }
}