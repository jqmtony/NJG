package com.kingdee.eas.fdc.gcftbiaoa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTreeNodeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTreeNodeInfo()
    {
        this("id");
    }
    protected AbstractTreeNodeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 自定义公司所属树节点 's 所属公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("47DFAA47");
    }
}