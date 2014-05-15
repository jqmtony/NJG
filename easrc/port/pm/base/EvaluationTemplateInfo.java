package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class EvaluationTemplateInfo extends AbstractEvaluationTemplateInfo implements Serializable 
{
    public EvaluationTemplateInfo()
    {
        super();
    }
    protected EvaluationTemplateInfo(String pkField)
    {
        super(pkField);
    }
}