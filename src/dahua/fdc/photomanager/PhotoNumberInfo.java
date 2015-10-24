package com.kingdee.eas.fdc.photomanager;

import java.io.Serializable;

public class PhotoNumberInfo extends AbstractPhotoNumberInfo implements Serializable 
{
    public PhotoNumberInfo()
    {
        super();
    }
    protected PhotoNumberInfo(String pkField)
    {
        super(pkField);
    }
}