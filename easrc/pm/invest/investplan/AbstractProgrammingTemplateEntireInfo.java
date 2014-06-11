package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTemplateEntireInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingTemplateEntireInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTemplateEntireInfo(String pkField)
    {
        super(pkField);
        put("pteCost", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteCostCollection());
        put("pteEnonomy", new com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteEnonomyCollection());
    }
    /**
     * Object: ��Լ��� 's ��Լ���ģ�� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Լ���'s ������Χproperty 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:��Լ���'s �׹�����ָ����property 
     */
    public String getProblem()
    {
        return getString("problem");
    }
    public void setProblem(String item)
    {
        setString("problem", item);
    }
    /**
     * Object:��Լ���'s ����property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: ��Լ��� 's �ϼ���ܺ�Լ property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo getHead()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo)get("head");
    }
    public void setHead(com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntireInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��Լ��� 's �ɱ����� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteCostCollection getPteCost()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteCostCollection)get("pteCost");
    }
    /**
     * Object: ��Լ��� 's �������� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteEnonomyCollection getPteEnonomy()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingTemplateEntirePteEnonomyCollection)get("pteEnonomy");
    }
    /**
     * Object:��Լ���'s ������property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    /**
     * Object: ��Լ��� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("96023477");
    }
}