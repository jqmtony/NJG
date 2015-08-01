package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ProjectIndexDataEntryInfo extends AbstractProjectIndexDataEntryInfo implements Serializable 
{
    public ProjectIndexDataEntryInfo()
    {
        super();
    }
    protected ProjectIndexDataEntryInfo(String pkField)
    {
        super(pkField);
    }
}