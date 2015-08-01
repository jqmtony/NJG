package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeReasonInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractChangeReasonInfo()
    {
        this("id");
    }
    protected AbstractChangeReasonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:变更原因's 变更原因所属类别property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeReasonTypeEnum getChangeReasonType()
    {
        return com.kingdee.eas.fdc.basedata.ChangeReasonTypeEnum.getEnum(getString("changeReasonType"));
    }
    public void setChangeReasonType(com.kingdee.eas.fdc.basedata.ChangeReasonTypeEnum item)
    {
		if (item != null) {
        setString("changeReasonType", item.getValue());
		}
    }
    /**
     * Object: 变更原因 's 变更原因类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ChangeTypeInfo getChangeType()
    {
        return (com.kingdee.eas.fdc.basedata.ChangeTypeInfo)get("changeType");
    }
    public void setChangeType(com.kingdee.eas.fdc.basedata.ChangeTypeInfo item)
    {
        put("changeType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FEF74A06");
    }
}