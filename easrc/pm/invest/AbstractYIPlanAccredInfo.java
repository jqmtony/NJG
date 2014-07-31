package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYIPlanAccredInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractYIPlanAccredInfo()
    {
        this("id");
    }
    protected AbstractYIPlanAccredInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection());
    }
    /**
     * Object: 年度投资计划评审 's 投资信息 property 
     */
    public com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.YIPlanAccredE1Collection)get("E1");
    }
    /**
     * Object:年度投资计划评审's 评审日期property 
     */
    public java.util.Date getAccredDate()
    {
        return getDate("accredDate");
    }
    public void setAccredDate(java.util.Date item)
    {
        setDate("accredDate", item);
    }
    /**
     * Object: 年度投资计划评审 's 评审人 property 
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
     * Object:年度投资计划评审's 评审类型property 
     */
    public com.kingdee.eas.port.pm.invest.AccredTypeEnum getAccredType()
    {
        return com.kingdee.eas.port.pm.invest.AccredTypeEnum.getEnum(getString("accredType"));
    }
    public void setAccredType(com.kingdee.eas.port.pm.invest.AccredTypeEnum item)
    {
		if (item != null) {
        setString("accredType", item.getValue());
		}
    }
    /**
     * Object:年度投资计划评审's 备注property 
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
     * Object:年度投资计划评审's 评审信息property 
     */
    public String getAccredInformation()
    {
        return getString("accredInformation");
    }
    public void setAccredInformation(String item)
    {
        setString("accredInformation", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5FA92066");
    }
}