package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildNumberInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildNumberInfo()
    {
        this("id");
    }
    protected AbstractBuildNumberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ¥�� 's ������Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object: ¥�� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:¥��'s �Ƿ����¥property 
     */
    public boolean isModelBuild()
    {
        return getBoolean("modelBuild");
    }
    public void setModelBuild(boolean item)
    {
        setBoolean("modelBuild", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("38776AD0");
    }
}