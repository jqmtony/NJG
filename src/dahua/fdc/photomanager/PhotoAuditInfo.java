package com.kingdee.eas.fdc.photomanager;

import java.io.Serializable;

public class PhotoAuditInfo extends AbstractPhotoAuditInfo implements Serializable 
{
    public PhotoAuditInfo()
    {
        super();
    }
    protected PhotoAuditInfo(String pkField)
    {
        super(pkField);
    }
}