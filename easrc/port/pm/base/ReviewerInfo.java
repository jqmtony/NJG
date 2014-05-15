package com.kingdee.eas.port.pm.base;

import java.io.Serializable;

public class ReviewerInfo extends AbstractReviewerInfo implements Serializable 
{
    public ReviewerInfo()
    {
        super();
    }
    protected ReviewerInfo(String pkField)
    {
        super(pkField);
    }
}