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
     * Object: 造价指标库 's 分录 property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEntryCollection)get("entrys");
    }
    /**
     * Object:造价指标库's 所属组织property 
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
     * Object:造价指标库's 合同信息property 
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
     * Object:造价指标库's 备注property 
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
     * Object:造价指标库's 单据状态property 
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
     * Object:造价指标库's 项目阶段property 
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
     * Object:造价指标库's 合同阶段property 
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
     * Object: 造价指标库 's 指标数据 property 
     */
    public com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataCollection getEindexData()
    {
        return (com.kingdee.eas.fdc.costindexdb.BuildPriceIndexEindexDataCollection)get("EindexData");
    }
    /**
     * Object:造价指标库's 项目IDproperty 
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
     * Object:造价指标库's 合同IDproperty 
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