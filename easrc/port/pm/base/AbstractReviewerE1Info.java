package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReviewerE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReviewerE1Info()
    {
        this("id");
    }
    protected AbstractReviewerE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审人员 's null property 
     */
    public com.kingdee.eas.port.pm.base.ReviewerInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.ReviewerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.ReviewerInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 评审人员 's 评审人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getJudges()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("judges");
    }
    public void setJudges(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("judges", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0DA05C52");
    }
}