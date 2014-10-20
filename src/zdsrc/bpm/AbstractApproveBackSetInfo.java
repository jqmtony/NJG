package com.kingdee.eas.bpm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractApproveBackSetInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractApproveBackSetInfo()
    {
        this("id");
    }
    protected AbstractApproveBackSetInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.bpm.ApproveBackSetEntryCollection());
    }
    /**
     * Object: ������д�ֶ����� 's ��� property 
     */
    public com.kingdee.eas.bpm.ApproveBackSetTreeInfo getTreeid()
    {
        return (com.kingdee.eas.bpm.ApproveBackSetTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.bpm.ApproveBackSetTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object: ������д�ֶ����� 's ��1������ property 
     */
    public com.kingdee.eas.bpm.ApproveBackSetEntryCollection getEntry()
    {
        return (com.kingdee.eas.bpm.ApproveBackSetEntryCollection)get("Entry");
    }
    /**
     * Object:������д�ֶ�����'s ��ͼsqlproperty 
     */
    public String getView()
    {
        return getString("view");
    }
    public void setView(String item)
    {
        setString("view", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("91D08B6F");
    }
}