package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildPriceIndexInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBuildPriceIndexInfo()
    {
        this("id");
    }
    protected AbstractBuildPriceIndexInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection());
        put("EindexData", new com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataCollection());
    }
    /**
     * Object: ���ָ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection)get("entrys");
    }
    /**
     * Object:���ָ���'s ������֯property 
     */
    public String getOrgName()
    {
        return getString("orgName");
    }
    public void setOrgName(String item)
    {
        setString("orgName", item);
    }
    /**
     * Object:���ָ���'s ��ͬ��Ϣproperty 
     */
    public String getContractInfo()
    {
        return getString("contractInfo");
    }
    public void setContractInfo(String item)
    {
        setString("contractInfo", item);
    }
    /**
     * Object:���ָ���'s ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:���ָ���'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getBuildPriceBillStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("buildPriceBillStatus"));
    }
    public void setBuildPriceBillStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("buildPriceBillStatus", item.getValue());
		}
    }
    /**
     * Object:���ָ���'s ��Ŀ�׶�property 
     */
    public com.kingdee.eas.fdc.costindexdb.ProjectStationEnum getProjectStation()
    {
        return com.kingdee.eas.fdc.costindexdb.ProjectStationEnum.getEnum(getString("projectStation"));
    }
    public void setProjectStation(com.kingdee.eas.fdc.costindexdb.ProjectStationEnum item)
    {
		if (item != null) {
        setString("projectStation", item.getValue());
		}
    }
    /**
     * Object:���ָ���'s ��ͬ�׶�property 
     */
    public com.kingdee.eas.fdc.costindexdb.ContractStationEnum getContractStation()
    {
        return com.kingdee.eas.fdc.costindexdb.ContractStationEnum.getEnum(getString("contractStation"));
    }
    public void setContractStation(com.kingdee.eas.fdc.costindexdb.ContractStationEnum item)
    {
		if (item != null) {
        setString("contractStation", item.getValue());
		}
    }
    /**
     * Object: ���ָ��� 's ָ������ property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataCollection getEindexData()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataCollection)get("EindexData");
    }
    /**
     * Object:���ָ���'s ��ĿIDproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:���ָ���'s ��ͬIDproperty 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FFB9A31");
    }
}