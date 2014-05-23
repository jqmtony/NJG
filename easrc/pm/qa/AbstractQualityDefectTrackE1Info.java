package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQualityDefectTrackE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractQualityDefectTrackE1Info()
    {
        this("id");
    }
    protected AbstractQualityDefectTrackE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ȱ�ݸ��� 's null property 
     */
    public com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo getParent()
    {
        return (com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.qa.QualityDefectTrackInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ȱ�ݸ���'s ��Ҫ����property 
     */
    public String getMainRespond()
    {
        return getString("mainRespond");
    }
    public void setMainRespond(String item)
    {
        setString("mainRespond", item);
    }
    /**
     * Object: ȱ�ݸ��� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:ȱ�ݸ���'s ˵��property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0AE27AC9");
    }
}