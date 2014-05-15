package com.kingdee.eas.xr;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractXRBillBaseInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractXRBillBaseInfo()
    {
        this("");
    }
    protected AbstractXRBillBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:XRҵ�񵥾�ģ��'s ����״̬property 
     */
    public com.kingdee.eas.xr.app.XRBillStatusEnum getStatus()
    {
        return com.kingdee.eas.xr.app.XRBillStatusEnum.getEnum(getInt("status"));
    }
    public void setStatus(com.kingdee.eas.xr.app.XRBillStatusEnum item)
    {
		if (item != null) {
        setInt("status", item.getValue());
		}
    }
    /**
     * Object:XRҵ�񵥾�ģ��'s ҵ��״̬property 
     */
    public com.kingdee.eas.xr.app.XRBizActionEnum getBizStatus()
    {
        return com.kingdee.eas.xr.app.XRBizActionEnum.getEnum(getInt("bizStatus"));
    }
    public void setBizStatus(com.kingdee.eas.xr.app.XRBizActionEnum item)
    {
		if (item != null) {
        setInt("bizStatus", item.getValue());
		}
    }
    /**
     * Object:XRҵ�񵥾�ģ��'s ���ʱ��property 
     */
    public java.sql.Timestamp getAuditTime()
    {
        return getTimestamp("auditTime");
    }
    public void setAuditTime(java.sql.Timestamp item)
    {
        setTimestamp("auditTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F95834E");
    }
}