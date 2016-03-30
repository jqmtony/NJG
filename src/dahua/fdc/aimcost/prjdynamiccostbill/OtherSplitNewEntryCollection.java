package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherSplitNewEntryCollection extends AbstractObjectCollection 
{
    public OtherSplitNewEntryCollection()
    {
        super(OtherSplitNewEntryInfo.class);
    }
    public boolean add(OtherSplitNewEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherSplitNewEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherSplitNewEntryInfo item)
    {
        return removeObject(item);
    }
    public OtherSplitNewEntryInfo get(int index)
    {
        return(OtherSplitNewEntryInfo)getObject(index);
    }
    public OtherSplitNewEntryInfo get(Object key)
    {
        return(OtherSplitNewEntryInfo)getObject(key);
    }
    public void set(int index, OtherSplitNewEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherSplitNewEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherSplitNewEntryInfo item)
    {
        return super.indexOf(item);
    }
}