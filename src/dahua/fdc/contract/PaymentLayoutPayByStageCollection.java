package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentLayoutPayByStageCollection extends AbstractObjectCollection 
{
    public PaymentLayoutPayByStageCollection()
    {
        super(PaymentLayoutPayByStageInfo.class);
    }
    public boolean add(PaymentLayoutPayByStageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentLayoutPayByStageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentLayoutPayByStageInfo item)
    {
        return removeObject(item);
    }
    public PaymentLayoutPayByStageInfo get(int index)
    {
        return(PaymentLayoutPayByStageInfo)getObject(index);
    }
    public PaymentLayoutPayByStageInfo get(Object key)
    {
        return(PaymentLayoutPayByStageInfo)getObject(key);
    }
    public void set(int index, PaymentLayoutPayByStageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentLayoutPayByStageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentLayoutPayByStageInfo item)
    {
        return super.indexOf(item);
    }
}