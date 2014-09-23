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
     * Object: ���׺�ͬ 's ��¼ property 
     */
    public com.kingdee.eas.bpmdemo.ContractDemoEntryCollection getEntrys()
    {
        return (com.kingdee.eas.bpmdemo.ContractDemoEntryCollection)get("entrys");
    }
    /**
     * Object:���׺�ͬ's �Ƿ�����ƾ֤property 
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
     * Object:���׺�ͬ's ��֯property 
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
     * Object:���׺�ͬ's ��ͬ����property 
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
     * Object:���׺�ͬ's �׷�property 
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
     * Object:���׺�ͬ's �ҷ�property 
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
     * Object:���׺�ͬ's ������Ŀproperty 
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
     * Object:���׺�ͬ's ��ͬ����property 
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
     * Object:���׺�ͬ's ��ͬ���property 
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
     * Object:���׺�ͬ's ���β���property 
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
     * Object:���׺�ͬ's ������property 
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