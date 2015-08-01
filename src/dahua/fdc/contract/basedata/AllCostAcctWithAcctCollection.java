package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AllCostAcctWithAcctCollection extends AbstractObjectCollection 
{
    public AllCostAcctWithAcctCollection()
    {
        super(AllCostAcctWithAcctInfo.class);
    }
    public boolean add(AllCostAcctWithAcctInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AllCostAcctWithAcctCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AllCostAcctWithAcctInfo item)
    {
        return removeObject(item);
    }
    public AllCostAcctWithAcctInfo get(int index)
    {
        return(AllCostAcctWithAcctInfo)getObject(index);
    }
    public AllCostAcctWithAcctInfo get(Object key)
    {
        return(AllCostAcctWithAcctInfo)getObject(key);
    }
    public void set(int index, AllCostAcctWithAcctInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AllCostAcctWithAcctInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AllCostAcctWithAcctInfo item)
    {
        return super.indexOf(item);
    }
}