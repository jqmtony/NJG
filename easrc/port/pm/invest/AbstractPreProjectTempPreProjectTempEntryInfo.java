package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreProjectTempPreProjectTempEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPreProjectTempPreProjectTempEntryInfo()
    {
        this("id");
    }
    protected AbstractPreProjectTempPreProjectTempEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目前期模板 's null property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectTempInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.PreProjectTempInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目前期模板's 前期工作内容property 
     */
    public String getPreWorkContent()
    {
        return getString("preWorkContent");
    }
    public void setPreWorkContent(String item)
    {
        setString("preWorkContent", item);
    }
    /**
     * Object:项目前期模板's 工作要求property 
     */
    public String getWorkRequest()
    {
        return getString("workRequest");
    }
    public void setWorkRequest(String item)
    {
        setString("workRequest", item);
    }
    /**
     * Object: 项目前期模板 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getResponDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("responDepart");
    }
    public void setResponDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("responDepart", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C0167A21");
    }
}