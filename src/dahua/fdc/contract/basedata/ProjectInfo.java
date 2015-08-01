package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ProjectInfo extends AbstractProjectInfo implements Serializable 
{
    public ProjectInfo()
    {
        super();
    }
    protected ProjectInfo(String pkField)
    {
        super(pkField);
    }
}