package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTargetDescEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTargetDescEntryInfo()
    {
        this("id");
    }
    protected AbstractTargetDescEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ƶ���Ӫ-ָ��������Ϣ��¼ 's ָ��¼��ͷ property 
     */
    public com.kingdee.eas.fdc.basedata.TargetDescInfo getHead()
    {
        return (com.kingdee.eas.fdc.basedata.TargetDescInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basedata.TargetDescInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ��¼'s ָ��Idproperty 
     */
    public String getTargetId()
    {
        return getString("targetId");
    }
    public void setTargetId(String item)
    {
        setString("targetId", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ��¼'s ԭ����ϸ˵��property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ��¼'s �Ƿ�����property 
     */
    public boolean isIsHighLight()
    {
        return getBoolean("isHighLight");
    }
    public void setIsHighLight(boolean item)
    {
        setBoolean("isHighLight", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ��¼'s ָ������property 
     */
    public String getTargetName()
    {
        return getString("targetName");
    }
    public void setTargetName(String item)
    {
        setString("targetName", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ��������Ϣ��¼'s ����property 
     */
    public com.kingdee.eas.fdc.basedata.TargetGroupEnum getTargetGroup()
    {
        return com.kingdee.eas.fdc.basedata.TargetGroupEnum.getEnum(getString("targetGroup"));
    }
    public void setTargetGroup(com.kingdee.eas.fdc.basedata.TargetGroupEnum item)
    {
		if (item != null) {
        setString("targetGroup", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC03549E");
    }
}