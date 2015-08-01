package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AcctAccreditUserCollection extends AbstractObjectCollection 
{
    public AcctAccreditUserCollection()
    {
        super(AcctAccreditUserInfo.class);
    }
    public boolean add(AcctAccreditUserInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AcctAccreditUserCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AcctAccreditUserInfo item)
    {
        return removeObject(item);
    }
    public AcctAccreditUserInfo get(int index)
    {
        return(AcctAccreditUserInfo)getObject(index);
    }
    public AcctAccreditUserInfo get(Object key)
    {
        return(AcctAccreditUserInfo)getObject(key);
    }
    public void set(int index, AcctAccreditUserInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AcctAccreditUserInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AcctAccreditUserInfo item)
    {
        return super.indexOf(item);
    }
}