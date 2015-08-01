package com.kingdee.eas.fdc.material;

import java.io.Serializable;

public class PartAMaterialInfo extends AbstractPartAMaterialInfo implements Serializable 
{
    public PartAMaterialInfo()
    {
        super();
    }
    protected PartAMaterialInfo(String pkField)
    {
        super(pkField);
    }
    public void  setEntrys(com.kingdee.eas.fdc.material.PartAMaterialEntryCollection entrys)
    {
    	put("entrys", entrys);
    }
}