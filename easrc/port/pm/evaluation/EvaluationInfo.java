package com.kingdee.eas.port.pm.evaluation;

import java.io.Serializable;

public class EvaluationInfo extends AbstractEvaluationInfo implements Serializable 
{
    public EvaluationInfo()
    {
        super();
    }
    protected EvaluationInfo(String pkField)
    {
        super(pkField);
    }
}