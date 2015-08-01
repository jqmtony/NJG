package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewUnsignCollection extends AbstractObjectCollection 
{
    public PayPlanNewUnsignCollection()
    {
        super(PayPlanNewUnsignInfo.class);
    }
    public boolean add(PayPlanNewUnsignInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewUnsignCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewUnsignInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewUnsignInfo get(int index)
    {
        return(PayPlanNewUnsignInfo)getObject(index);
    }
    public PayPlanNewUnsignInfo get(Object key)
    {
        return(PayPlanNewUnsignInfo)getObject(key);
    }
    public void set(int index, PayPlanNewUnsignInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewUnsignInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewUnsignInfo item)
    {
        return super.indexOf(item);
    }
}