package com.kingdee.eas.fdc.material;

import java.io.Serializable;

public class MaterialIndexInfo extends AbstractMaterialIndexInfo implements Serializable 
{
    public MaterialIndexInfo()
    {
        super();
    }
    protected MaterialIndexInfo(String pkField)
    {
        super(pkField);
    }
}