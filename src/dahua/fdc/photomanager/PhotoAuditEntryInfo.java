package com.kingdee.eas.fdc.photomanager;

import java.io.Serializable;

public class PhotoAuditEntryInfo extends AbstractPhotoAuditEntryInfo implements Serializable 
{
    public PhotoAuditEntryInfo()
    {
        super();
    }
    protected PhotoAuditEntryInfo(String pkField)
    {
        super(pkField);
    }
}