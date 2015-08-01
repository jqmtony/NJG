package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBITargetInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractBITargetInfo()
    {
        this("id");
    }
    protected AbstractBITargetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ����property 
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
     * Object:�ƶ���Ӫ-ָ���'s ָ��Idproperty 
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
     * Object:�ƶ���Ӫ-ָ���'s ����property 
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
    /**
     * Object:�ƶ���Ӫ-ָ���'s չ����ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.TargetExpandTypeEnum getExpandType()
    {
        return com.kingdee.eas.fdc.basedata.TargetExpandTypeEnum.getEnum(getString("expandType"));
    }
    public void setExpandType(com.kingdee.eas.fdc.basedata.TargetExpandTypeEnum item)
    {
		if (item != null) {
        setString("expandType", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ����property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ��עproperty 
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
     * Object:�ƶ���Ӫ-ָ���'s �Ƿ�ֱ��չ��property 
     */
    public com.kingdee.eas.fdc.basedata.TargetStatusEnum getIsDirect()
    {
        return com.kingdee.eas.fdc.basedata.TargetStatusEnum.getEnum(getString("isDirect"));
    }
    public void setIsDirect(com.kingdee.eas.fdc.basedata.TargetStatusEnum item)
    {
		if (item != null) {
        setString("isDirect", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s �Ƿ��뷧ֵ�Ƚ�property 
     */
    public com.kingdee.eas.fdc.basedata.TargetStatusEnum getIsThreshold()
    {
        return com.kingdee.eas.fdc.basedata.TargetStatusEnum.getEnum(getString("isThreshold"));
    }
    public void setIsThreshold(com.kingdee.eas.fdc.basedata.TargetStatusEnum item)
    {
		if (item != null) {
        setString("isThreshold", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s �ȽϷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.TargetCompareTypeEnum getCompareType()
    {
        return com.kingdee.eas.fdc.basedata.TargetCompareTypeEnum.getEnum(getString("compareType"));
    }
    public void setCompareType(com.kingdee.eas.fdc.basedata.TargetCompareTypeEnum item)
    {
		if (item != null) {
        setString("compareType", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ָ��������ʾ���property 
     */
    public int getWidth()
    {
        return getInt("width");
    }
    public void setWidth(int item)
    {
        setInt("width", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s �Ƿ�չ��property 
     */
    public com.kingdee.eas.fdc.basedata.TargetStatusEnum getIsExpand()
    {
        return com.kingdee.eas.fdc.basedata.TargetStatusEnum.getEnum(getString("isExpand"));
    }
    public void setIsExpand(com.kingdee.eas.fdc.basedata.TargetStatusEnum item)
    {
		if (item != null) {
        setString("isExpand", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s �Ƿ�ɼ�property 
     */
    public com.kingdee.eas.fdc.basedata.TargetStatusEnum getIsShow()
    {
        return com.kingdee.eas.fdc.basedata.TargetStatusEnum.getEnum(getString("isShow"));
    }
    public void setIsShow(com.kingdee.eas.fdc.basedata.TargetStatusEnum item)
    {
		if (item != null) {
        setString("isShow", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ��������property 
     */
    public com.kingdee.eas.fdc.basedata.TargetDataTypeEnum getDataType()
    {
        return com.kingdee.eas.fdc.basedata.TargetDataTypeEnum.getEnum(getString("dataType"));
    }
    public void setDataType(com.kingdee.eas.fdc.basedata.TargetDataTypeEnum item)
    {
		if (item != null) {
        setString("dataType", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ¼���ָ�������ĸ�TargetGroupproperty 
     */
    public com.kingdee.eas.fdc.basedata.TargetGroupEnum getFromWhichGroup()
    {
        return com.kingdee.eas.fdc.basedata.TargetGroupEnum.getEnum(getString("fromWhichGroup"));
    }
    public void setFromWhichGroup(com.kingdee.eas.fdc.basedata.TargetGroupEnum item)
    {
		if (item != null) {
        setString("fromWhichGroup", item.getValue());
		}
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s չʾ��ָ�����Ƿ���Eas��¼���property 
     */
    public boolean isIsInputedFromEAS()
    {
        return getBoolean("isInputedFromEAS");
    }
    public void setIsInputedFromEAS(boolean item)
    {
        setBoolean("isInputedFromEAS", item);
    }
    /**
     * Object:�ƶ���Ӫ-ָ���'s ͳ������property 
     */
    public com.kingdee.eas.fdc.basedata.TargetDataStatisticsTypeEnum getStatisticsType()
    {
        return com.kingdee.eas.fdc.basedata.TargetDataStatisticsTypeEnum.getEnum(getString("statisticsType"));
    }
    public void setStatisticsType(com.kingdee.eas.fdc.basedata.TargetDataStatisticsTypeEnum item)
    {
		if (item != null) {
        setString("statisticsType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7394758A");
    }
}