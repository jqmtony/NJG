package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgUnitCollection extends AbstractObjectCollection 
{
    public OrgUnitCollection()
    {
        super(OrgUnitInfo.class);
    }
    public boolean add(OrgUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgUnitInfo item)
    {
        return removeObject(item);
    }
    public OrgUnitInfo get(int index)
    {
        return(OrgUnitInfo)getObject(index);
    }
    public OrgUnitInfo get(Object key)
    {
        return(OrgUnitInfo)getObject(key);
    }
    public void set(int index, OrgUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgUnitInfo item)
    {
        return super.indexOf(item);
    }
}