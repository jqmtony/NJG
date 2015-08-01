package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetInfoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTargetInfoInfo()
    {
        this("id");
    }
    protected AbstractTargetInfoInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.basedata.TargetInfoEntryCollection());
    }
    /**
     * Object:�ƶ���Ӫ-ָ��¼��'s ��property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��¼��'s �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object: �ƶ���Ӫ-ָ��¼�� 's ������Ŀ property 
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
     * Object: �ƶ���Ӫ-ָ��¼�� 's �ɱ����� property 
     */
    public com.kingdee.eas.basedata.ncm.CostCenterOrgUnitGroupInfo getCostCenter()
    {
        return (com.kingdee.eas.basedata.ncm.CostCenterOrgUnitGroupInfo)get("costCenter");
    }
    public void setCostCenter(com.kingdee.eas.basedata.ncm.CostCenterOrgUnitGroupInfo item)
    {
        put("costCenter", item);
    }
    /**
     * Object: �ƶ���Ӫ-ָ��¼�� 's ָ��¼���¼ property 
     */
    public com.kingdee.eas.fdc.basedata.TargetInfoEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.basedata.TargetInfoEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4825AA51");
    }
}