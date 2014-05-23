package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOverhaulNoticeInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractOverhaulNoticeInfo()
    {
        this("id");
    }
    protected AbstractOverhaulNoticeInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.equipment.special.OverhaulNoticeEntryCollection());
    }
    /**
     * Object: 不合格整改通知单 's 整改明细 property 
     */
    public com.kingdee.eas.port.equipment.special.OverhaulNoticeEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.equipment.special.OverhaulNoticeEntryCollection)get("Entry");
    }
    /**
     * Object:不合格整改通知单's 通知日期property 
     */
    public java.util.Date getNoticeDate()
    {
        return getDate("noticeDate");
    }
    public void setNoticeDate(java.util.Date item)
    {
        setDate("noticeDate", item);
    }
    /**
     * Object:不合格整改通知单's 要求完成日期property 
     */
    public java.util.Date getPlanFinishDate()
    {
        return getDate("planFinishDate");
    }
    public void setPlanFinishDate(java.util.Date item)
    {
        setDate("planFinishDate", item);
    }
    /**
     * Object:不合格整改通知单's 整改要求property 
     */
    public String getRequestContent()
    {
        return getString("requestContent");
    }
    public void setRequestContent(String item)
    {
        setString("requestContent", item);
    }
    /**
     * Object:不合格整改通知单's 整改反馈property 
     */
    public String getFeedback()
    {
        return getString("feedback");
    }
    public void setFeedback(String item)
    {
        setString("feedback", item);
    }
    /**
     * Object:不合格整改通知单's 反馈日期property 
     */
    public java.util.Date getBackDate()
    {
        return getDate("backDate");
    }
    public void setBackDate(java.util.Date item)
    {
        setDate("backDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D2BED306");
    }
}