package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgesExamineEntryIndicatorInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractJudgesExamineEntryIndicatorInfo()
    {
        this("id");
    }
    protected AbstractJudgesExamineEntryIndicatorInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 考核指标分录 's null property 
     */
    public com.kingdee.eas.port.pm.invite.JudgesExamineInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.JudgesExamineInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.JudgesExamineInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:考核指标分录's 考核类别property 
     */
    public String getExamCategory()
    {
        return getString("examCategory");
    }
    public void setExamCategory(String item)
    {
        setString("examCategory", item);
    }
    /**
     * Object:考核指标分录's 考核细目property 
     */
    public String getExamIndicators()
    {
        return getString("examIndicators");
    }
    public void setExamIndicators(String item)
    {
        setString("examIndicators", item);
    }
    /**
     * Object:考核指标分录's 考核记录property 
     */
    public com.kingdee.eas.port.pm.invite.examRecord getRecord()
    {
        return com.kingdee.eas.port.pm.invite.examRecord.getEnum(getString("record"));
    }
    public void setRecord(com.kingdee.eas.port.pm.invite.examRecord item)
    {
		if (item != null) {
        setString("record", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2635E5B9");
    }
}