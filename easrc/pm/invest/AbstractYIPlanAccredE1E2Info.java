package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYIPlanAccredE1E2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYIPlanAccredE1E2Info()
    {
        this("id");
    }
    protected AbstractYIPlanAccredE1E2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审信息 's null property 
     */
    public com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info getParent1()
    {
        return (com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info)get("parent1");
    }
    public void setParent1(com.kingdee.eas.port.pm.invest.YIPlanAccredE1Info item)
    {
        put("parent1", item);
    }
    /**
     * Object: 评审信息 's 评审部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAccredDpart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("accredDpart");
    }
    public void setAccredDpart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("accredDpart", item);
    }
    /**
     * Object: 评审信息 's 评审人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAccredPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("accredPerson");
    }
    public void setAccredPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("accredPerson", item);
    }
    /**
     * Object:评审信息's 意见property 
     */
    public String getOpinion()
    {
        return getString("opinion");
    }
    public void setOpinion(String item)
    {
        setString("opinion", item);
    }
    /**
     * Object:评审信息's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:评审信息's 评审结论property 
     */
    public com.kingdee.eas.port.pm.invest.ObjectStateEnum getAccreConclu()
    {
        return com.kingdee.eas.port.pm.invest.ObjectStateEnum.getEnum(getString("accreConclu"));
    }
    public void setAccreConclu(com.kingdee.eas.port.pm.invest.ObjectStateEnum item)
    {
		if (item != null) {
        setString("accreConclu", item.getValue());
		}
    }
    /**
     * Object:评审信息's 评审时间property 
     */
    public java.util.Date getAduitTime()
    {
        return getDate("aduitTime");
    }
    public void setAduitTime(java.util.Date item)
    {
        setDate("aduitTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2BD69B7F");
    }
}