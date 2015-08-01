package com.kingdee.eas.fdc.material;

import java.io.Serializable;

public class MaterialIndexValueInfo extends AbstractMaterialIndexValueInfo implements Serializable 
{
    public MaterialIndexValueInfo()
    {
        super();
    }
    protected MaterialIndexValueInfo(String pkField)
    {
        super(pkField);
    }
}