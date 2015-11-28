package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildSplitBillEntryDetailInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildSplitBillEntryDetailInfo()
    {
        this("id");
    }
    protected AbstractBuildSplitBillEntryDetailInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ϸ���� 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo getParent1()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object: ��ϸ���� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo getBuildNumber()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo)get("buildNumber");
    }
    public void setBuildNumber(com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo item)
    {
        put("buildNumber", item);
    }
    /**
     * Object: ��ϸ���� 's ��Ʒ���� property 
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
     * Object:��ϸ����'s �Ƿ����¥property 
     */
    public boolean isModelBuild()
    {
        return getBoolean("modelBuild");
    }
    public void setModelBuild(boolean item)
    {
        setBoolean("modelBuild", item);
    }
    /**
     * Object:��ϸ����'s ��ֵproperty 
     */
    public java.math.BigDecimal getDataValue()
    {
        return getBigDecimal("dataValue");
    }
    public void setDataValue(java.math.BigDecimal item)
    {
        setBigDecimal("dataValue", item);
    }
    /**
     * Object:��ϸ����'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("501915A9");
    }
}