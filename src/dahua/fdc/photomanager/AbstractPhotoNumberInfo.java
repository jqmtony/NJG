package com.kingdee.eas.fdc.photomanager;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPhotoNumberInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPhotoNumberInfo()
    {
        this("id");
    }
    protected AbstractPhotoNumberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 快照编码 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3DFFE29");
    }
}