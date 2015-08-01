package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanTemplateCollection extends AbstractObjectCollection 
{
    public PayPlanTemplateCollection()
    {
        super(PayPlanTemplateInfo.class);
    }
    public boolean add(PayPlanTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanTemplateInfo item)
    {
        return removeObject(item);
    }
    public PayPlanTemplateInfo get(int index)
    {
        return(PayPlanTemplateInfo)getObject(index);
    }
    public PayPlanTemplateInfo get(Object key)
    {
        return(PayPlanTemplateInfo)getObject(key);
    }
    public void set(int index, PayPlanTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanTemplateInfo item)
    {
        return super.indexOf(item);
    }
}