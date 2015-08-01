package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class IndexMemoInfo extends AbstractIndexMemoInfo implements Serializable 
{
    public IndexMemoInfo()
    {
        super();
    }
    protected IndexMemoInfo(String pkField)
    {
        super(pkField);
    }
}