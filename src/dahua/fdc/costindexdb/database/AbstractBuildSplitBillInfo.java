package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildSplitBillInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBuildSplitBillInfo()
    {
        this("id");
    }
    protected AbstractBuildSplitBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryCollection());
    }
    /**
     * Object: ¥�Ų�ֵ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryCollection)get("entrys");
    }
    /**
     * Object:¥�Ų�ֵ�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: ¥�Ų�ֵ� 's �ɱ���Ŀ property 
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
     * Object: ¥�Ų�ֵ� 's ��Ŀ���� property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProjectName()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("projectName", item);
    }
    /**
     * Object:¥�Ų�ֵ�'s ԭ���ݱ��property 
     */
    public String getSourceNumber()
    {
        return getString("sourceNumber");
    }
    public void setSourceNumber(String item)
    {
        setString("sourceNumber", item);
    }
    /**
     * Object:¥�Ų�ֵ�'s ��������property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType getDataType()
    {
        return com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType.getEnum(getString("dataType"));
    }
    public void setDataType(com.kingdee.eas.fdc.costindexdb.database.BuildSplitDataType item)
    {
		if (item != null) {
        setString("dataType", item.getValue());
		}
    }
    /**
     * Object:¥�Ų�ֵ�'s ��ͬ�׶�property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitContract getContractLevel()
    {
        return com.kingdee.eas.fdc.costindexdb.database.BuildSplitContract.getEnum(getString("contractLevel"));
    }
    public void setContractLevel(com.kingdee.eas.fdc.costindexdb.database.BuildSplitContract item)
    {
		if (item != null) {
        setString("contractLevel", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C672055A");
    }
}