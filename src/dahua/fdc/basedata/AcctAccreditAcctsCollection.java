package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AcctAccreditAcctsCollection extends AbstractObjectCollection 
{
    public AcctAccreditAcctsCollection()
    {
        super(AcctAccreditAcctsInfo.class);
    }
    public boolean add(AcctAccreditAcctsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AcctAccreditAcctsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AcctAccreditAcctsInfo item)
    {
        return removeObject(item);
    }
    public AcctAccreditAcctsInfo get(int index)
    {
        return(AcctAccreditAcctsInfo)getObject(index);
    }
    public AcctAccreditAcctsInfo get(Object key)
    {
        return(AcctAccreditAcctsInfo)getObject(key);
    }
    public void set(int index, AcctAccreditAcctsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AcctAccreditAcctsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AcctAccreditAcctsInfo item)
    {
        return super.indexOf(item);
    }
}