package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.BuildPriceIndexInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��¼ 's ��Ŀ���� property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getAccountNumber()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("accountNumber");
    }
    public void setAccountNumber(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("accountNumber", item);
    }
    /**
     * Object:��¼'s ��Ŀ����property 
     */
    public String getAccountName()
    {
        return getString("accountName");
    }
    public void setAccountName(String item)
    {
        setString("accountName", item);
    }
    /**
     * Object:��¼'s �Ƿ�¼��ָ������property 
     */
    public boolean isIsInput()
    {
        return getBoolean("isInput");
    }
    public void setIsInput(boolean item)
    {
        setBoolean("isInput", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("96D234C1");
    }
}