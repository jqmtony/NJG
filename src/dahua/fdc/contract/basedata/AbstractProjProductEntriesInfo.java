package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjProductEntriesInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProjProductEntriesInfo()
    {
        this("id");
    }
    protected AbstractProjProductEntriesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Ŀ��Ʒ���÷�¼'s �Ƿ�������property 
     */
    public boolean isIsAccObj()
    {
        return getBoolean("isAccObj");
    }
    public void setIsAccObj(boolean item)
    {
        setBoolean("isAccObj", item);
    }
    /**
     * Object: ������Ŀ��Ʒ���÷�¼ 's ��Ʒ���� property 
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
     * Object:������Ŀ��Ʒ���÷�¼'s �������property 
     */
    public java.math.BigDecimal getCompArea()
    {
        return getBigDecimal("compArea");
    }
    public void setCompArea(java.math.BigDecimal item)
    {
        setBigDecimal("compArea", item);
    }
    /**
     * Object:������Ŀ��Ʒ���÷�¼'s ��������property 
     */
    public java.util.Date getCompDate()
    {
        return getDate("compDate");
    }
    public void setCompDate(java.util.Date item)
    {
        setDate("compDate", item);
    }
    /**
     * Object:������Ŀ��Ʒ���÷�¼'s �ܳɱ�property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:������Ŀ��Ʒ���÷�¼'s �Ƿ񿢹�����property 
     */
    public boolean isIsCompSettle()
    {
        return getBoolean("isCompSettle");
    }
    public void setIsCompSettle(boolean item)
    {
        setBoolean("isCompSettle", item);
    }
    /**
     * Object:������Ŀ��Ʒ���÷�¼'s �Ƿ�������ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object:������Ŀ��Ʒ���÷�¼'s ������property 
     */
    public boolean isIsSplit()
    {
        return getBoolean("isSplit");
    }
    public void setIsSplit(boolean item)
    {
        setBoolean("isSplit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C3137BD0");
    }
}