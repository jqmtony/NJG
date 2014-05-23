package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYIPlanAccredE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYIPlanAccredE1Info()
    {
        this("id");
    }
    protected AbstractYIPlanAccredE1Info(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Collection());
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.port.pm.invest.YIPlanAccredInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.YIPlanAccredInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.YIPlanAccredInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��1������ 's ��2������ property 
     */
    public com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Collection getE2()
    {
        return (com.kingdee.eas.port.pm.invest.YIPlanAccredE1E2Collection)get("E2");
    }
    /**
     * Object: ��1������ 's ��Ŀ���� property 
     */
    public com.kingdee.eas.port.pm.base.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.port.pm.base.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.port.pm.base.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    /**
     * Object:��1������'s Ͷ�ʽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��1������'s ��Ŀ����property 
     */
    public String getProjectConclude()
    {
        return getString("projectConclude");
    }
    public void setProjectConclude(String item)
    {
        setString("projectConclude", item);
    }
    /**
     * Object:��1������'s ���ݼ�����Ҫ��property 
     */
    public String getContentSReq()
    {
        return getString("contentSReq");
    }
    public void setContentSReq(String item)
    {
        setString("contentSReq", item);
    }
    /**
     * Object: ��1������ 's �걨��λ property 
     */
    public com.kingdee.eas.basedata.org.CtrlUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CtrlUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CtrlUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:��1������'s ������property 
     */
    public com.kingdee.eas.port.pm.invest.ObjectStateEnum getAccredResu()
    {
        return com.kingdee.eas.port.pm.invest.ObjectStateEnum.getEnum(getString("accredResu"));
    }
    public void setAccredResu(com.kingdee.eas.port.pm.invest.ObjectStateEnum item)
    {
		if (item != null) {
        setString("accredResu", item.getValue());
		}
    }
    /**
     * Object: ��1������ 's ��Ŀ���� property 
     */
    public com.kingdee.eas.port.pm.invest.YearInvestPlanInfo getProjectName()
    {
        return (com.kingdee.eas.port.pm.invest.YearInvestPlanInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.port.pm.invest.YearInvestPlanInfo item)
    {
        put("projectName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("19E2A772");
    }
}