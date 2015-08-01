package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanDetailCollection extends AbstractObjectCollection 
{
    public ConPayPlanDetailCollection()
    {
        super(ConPayPlanDetailInfo.class);
    }
    public boolean add(ConPayPlanDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanDetailInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanDetailInfo get(int index)
    {
        return(ConPayPlanDetailInfo)getObject(index);
    }
    public ConPayPlanDetailInfo get(Object key)
    {
        return(ConPayPlanDetailInfo)getObject(key);
    }
    public void set(int index, ConPayPlanDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanDetailInfo item)
    {
        return super.indexOf(item);
    }
}