package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgesComfirmEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractJudgesComfirmEntryInfo()
    {
        this("id");
    }
    protected AbstractJudgesComfirmEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 专家信息 's null property 
     */
    public com.kingdee.eas.port.pm.invite.JudgesComfirmInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.JudgesComfirmInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.JudgesComfirmInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 专家信息 's 专家编码 property 
     */
    public com.kingdee.eas.port.pm.base.JudgesInfo getJudgeNumber()
    {
        return (com.kingdee.eas.port.pm.base.JudgesInfo)get("judgeNumber");
    }
    public void setJudgeNumber(com.kingdee.eas.port.pm.base.JudgesInfo item)
    {
        put("judgeNumber", item);
    }
    /**
     * Object:专家信息's 专家姓名property 
     */
    public String getJuName()
    {
        return getString("juName");
    }
    public void setJuName(String item)
    {
        setString("juName", item);
    }
    /**
     * Object:专家信息's 专家类型property 
     */
    public String getJuType()
    {
        return getString("juType");
    }
    public void setJuType(String item)
    {
        setString("juType", item);
    }
    /**
     * Object:专家信息's 所属组织property 
     */
    public String getOrgUnit()
    {
        return getString("orgUnit");
    }
    public void setOrgUnit(String item)
    {
        setString("orgUnit", item);
    }
    /**
     * Object:专家信息's 联系电话property 
     */
    public String getTelephone()
    {
        return getString("telephone");
    }
    public void setTelephone(String item)
    {
        setString("telephone", item);
    }
    /**
     * Object:专家信息's 备注property 
     */
    public String getComment()
    {
        return getString("comment");
    }
    public void setComment(String item)
    {
        setString("comment", item);
    }
    /**
     * Object:专家信息's 专家姓名property 
     */
    public String getJudgesName()
    {
        return getString("judgesName");
    }
    public void setJudgesName(String item)
    {
        setString("judgesName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2A348FD8");
    }
}