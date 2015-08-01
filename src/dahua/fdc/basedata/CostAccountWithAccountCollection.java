package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostAccountWithAccountCollection extends AbstractObjectCollection 
{
    public CostAccountWithAccountCollection()
    {
        super(CostAccountWithAccountInfo.class);
    }
    public boolean add(CostAccountWithAccountInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostAccountWithAccountCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostAccountWithAccountInfo item)
    {
        return removeObject(item);
    }
    public CostAccountWithAccountInfo get(int index)
    {
        return(CostAccountWithAccountInfo)getObject(index);
    }
    public CostAccountWithAccountInfo get(Object key)
    {
        return(CostAccountWithAccountInfo)getObject(key);
    }
    public void set(int index, CostAccountWithAccountInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostAccountWithAccountInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostAccountWithAccountInfo item)
    {
        return super.indexOf(item);
    }
}