package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProfessionPointEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProfessionPointEntryInfo()
    {
        this("id");
    }
    protected AbstractProfessionPointEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.ProfessionPointInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ProfessionPointInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.database.ProfessionPointInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��¼ 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:��¼'s �Ƿ��¥��property 
     */
    public boolean isSplitBuild()
    {
        return getBoolean("splitBuild");
    }
    public void setSplitBuild(boolean item)
    {
        setBoolean("splitBuild", item);
    }
    /**
     * Object:��¼'s Ҫ��property 
     */
    public String getPointName()
    {
        return getString("pointName");
    }
    public void setPointName(String item)
    {
        setString("pointName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33ECDF85");
    }
}