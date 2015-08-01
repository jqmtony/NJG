package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractVoucherForProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractVoucherForProjectInfo()
    {
        this("id");
    }
    protected AbstractVoucherForProjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工程项目凭证's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: 工程项目凭证 's 财务成本一体化科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beAccount");
    }
    public void setBeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beAccount", item);
    }
    /**
     * Object: 工程项目凭证 's 工程项目 property 
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
     * Object: 工程项目凭证 's 产品 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: 工程项目凭证 's 工程项目产品分录 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo getProjectEntry()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo)get("projectEntry");
    }
    public void setProjectEntry(com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo item)
    {
        put("projectEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DBCAE6AC");
    }
}