package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectIndexDataInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectIndexDataInfo()
    {
        this("id");
    }
    protected AbstractProjectIndexDataInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection());
    }
    /**
     * Object:��Ŀָ������'s ������Ŀ������֯IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjOrOrgID()
    {
        return getBOSUuid("projOrOrgID");
    }
    public void setProjOrOrgID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projOrOrgID", item);
    }
    /**
     * Object:��Ŀָ������'s ��Ŀ�׶�property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectStageEnum getProjectStage()
    {
        return com.kingdee.eas.fdc.basedata.ProjectStageEnum.getEnum(getString("projectStage"));
    }
    public void setProjectStage(com.kingdee.eas.fdc.basedata.ProjectStageEnum item)
    {
		if (item != null) {
        setString("projectStage", item.getValue());
		}
    }
    /**
     * Object: ��Ŀָ������ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:��Ŀָ������'s �޶�ʱ��property 
     */
    public java.sql.Timestamp getVerTime()
    {
        return getTimestamp("verTime");
    }
    public void setVerTime(java.sql.Timestamp item)
    {
        setTimestamp("verTime", item);
    }
    /**
     * Object:��Ŀָ������'s �汾��property 
     */
    public String getVerNo()
    {
        return getString("verNo");
    }
    public void setVerNo(String item)
    {
        setString("verNo", item);
    }
    /**
     * Object:��Ŀָ������'s �汾����property 
     */
    public String getVerName()
    {
        return getString("verName");
    }
    public void setVerName(String item)
    {
        setString("verName", item);
    }
    /**
     * Object:��Ŀָ������'s �汾˵��property 
     */
    public String getVerRemark()
    {
        return getString("verRemark");
    }
    public void setVerRemark(String item)
    {
        setString("verRemark", item);
    }
    /**
     * Object:��Ŀָ������'s �Ƿ����°汾property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: ��Ŀָ������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection)get("entries");
    }
    /**
     * Object:��Ŀָ������'s �Ӱ汾��������property 
     */
    public boolean isIsLatestSubVer()
    {
        return getBoolean("isLatestSubVer");
    }
    public void setIsLatestSubVer(boolean item)
    {
        setBoolean("isLatestSubVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("734D0775");
    }
}