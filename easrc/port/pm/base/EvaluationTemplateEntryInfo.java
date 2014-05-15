package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class EvaluationTemplateEntryInfo extends AbstractEvaluationTemplateEntryInfo implements Serializable 
{
    public EvaluationTemplateEntryInfo()
    {
        super();
    }
    protected EvaluationTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
}