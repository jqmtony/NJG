package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AccountStageCollection extends AbstractObjectCollection 
{
    public AccountStageCollection()
    {
        super(AccountStageInfo.class);
    }
    public boolean add(AccountStageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AccountStageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AccountStageInfo item)
    {
        return removeObject(item);
    }
    public AccountStageInfo get(int index)
    {
        return(AccountStageInfo)getObject(index);
    }
    public AccountStageInfo get(Object key)
    {
        return(AccountStageInfo)getObject(key);
    }
    public void set(int index, AccountStageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AccountStageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AccountStageInfo item)
    {
        return super.indexOf(item);
    }
}