package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractDemoInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractContractDemoInfo()
    {
        this("id");
    }
    protected AbstractContractDemoInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.bpmdemo.ContractDemoEntryCollection());
    }
    /**
     * Object: 简易合同 's 分录 property 
     */
    public com.kingdee.eas.bpmdemo.ContractDemoEntryCollection getEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractDemoEntryCollection)get("entrys");
    }
    /**
     * Object:简易合同's 是否生成凭证property 
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
     * Object:简易合同's 组织property 
     */
    public String getOrg()
    {
        return getString("org");
    }
    public void setOrg(String item)
    {
        setString("org", item);
    }
    /**
     * Object:简易合同's 合同名称property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:简易合同's 甲方property 
     */
    public String getA()
    {
        return getString("A");
    }
    public void setA(String item)
    {
        setString("A", item);
    }
    /**
     * Object:简易合同's 乙方property 
     */
    public String getB()
    {
        return getString("B");
    }
    public void setB(String item)
    {
        setString("B", item);
    }
    /**
     * Object:简易合同's 工程项目property 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:简易合同's 合同类型property 
     */
    public String getContractType()
    {
        return getString("contractType");
    }
    public void setContractType(String item)
    {
        setString("contractType", item);
    }
    /**
     * Object:简易合同's 合同金额property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:简易合同's 责任部门property 
     */
    public String getDep()
    {
        return getString("dep");
    }
    public void setDep(String item)
    {
        setString("dep", item);
    }
    /**
     * Object:简易合同's 责任人property 
     */
    public String getPerosn()
    {
        return getString("perosn");
    }
    public void setPerosn(String item)
    {
        setString("perosn", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7EF7D53");
    }
}