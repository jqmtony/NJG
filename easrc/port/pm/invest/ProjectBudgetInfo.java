package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;

public class ProjectBudgetInfo extends AbstractProjectBudgetInfo implements Serializable 
{
    public ProjectBudgetInfo()
    {
        super();
    }
    protected ProjectBudgetInfo(String pkField)
    {
        super(pkField);
    }
}