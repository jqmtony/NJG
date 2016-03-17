package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HousingCollection extends AbstractObjectCollection 
{
    public HousingCollection()
    {
        super(HousingInfo.class);
    }
    public boolean add(HousingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HousingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HousingInfo item)
    {
        return removeObject(item);
    }
    public HousingInfo get(int index)
    {
        return(HousingInfo)getObject(index);
    }
    public HousingInfo get(Object key)
    {
        return(HousingInfo)getObject(key);
    }
    public void set(int index, HousingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HousingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HousingInfo item)
    {
        return super.indexOf(item);
    }
}