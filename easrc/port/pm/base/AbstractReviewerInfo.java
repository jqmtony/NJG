package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReviewerInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractReviewerInfo()
    {
        this("id");
    }
    protected AbstractReviewerInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.base.ReviewerE1Collection());
    }
    /**
     * Object: 评审人员 's 评审人员 property 
     */
    public com.kingdee.eas.port.pm.base.ReviewerE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.base.ReviewerE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("970E8D46");
    }
}