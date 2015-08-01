package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AcctAccreditSchemeCollection extends AbstractObjectCollection 
{
    public AcctAccreditSchemeCollection()
    {
        super(AcctAccreditSchemeInfo.class);
    }
    public boolean add(AcctAccreditSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AcctAccreditSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AcctAccreditSchemeInfo item)
    {
        return removeObject(item);
    }
    public AcctAccreditSchemeInfo get(int index)
    {
        return(AcctAccreditSchemeInfo)getObject(index);
    }
    public AcctAccreditSchemeInfo get(Object key)
    {
        return(AcctAccreditSchemeInfo)getObject(key);
    }
    public void set(int index, AcctAccreditSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AcctAccreditSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AcctAccreditSchemeInfo item)
    {
        return super.indexOf(item);
    }
}