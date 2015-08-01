package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAcctAccreditUserInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAcctAccreditUserInfo()
    {
        this("id");
    }
    protected AbstractAcctAccreditUserInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɱ���Ŀ��Ȩ�û� 's �û� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    /**
     * Object: �ɱ���Ŀ��Ȩ�û� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getFullOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("fullOrgUnit");
    }
    public void setFullOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("fullOrgUnit", item);
    }
    /**
     * Object: �ɱ���Ŀ��Ȩ�û� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �ɱ���Ŀ��Ȩ�û� 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo getScheme()
    {
        return (com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo)get("scheme");
    }
    public void setScheme(com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo item)
    {
        put("scheme", item);
    }
    /**
     * Object: �ɱ���Ŀ��Ȩ�û� 's ��ɫ property 
     */
    public com.kingdee.eas.base.permission.RoleInfo getRole()
    {
        return (com.kingdee.eas.base.permission.RoleInfo)get("role");
    }
    public void setRole(com.kingdee.eas.base.permission.RoleInfo item)
    {
        put("role", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("83710AAB");
    }
}