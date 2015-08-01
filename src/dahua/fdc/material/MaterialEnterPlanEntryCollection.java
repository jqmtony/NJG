package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialEnterPlanEntryCollection extends AbstractObjectCollection 
{
    public MaterialEnterPlanEntryCollection()
    {
        super(MaterialEnterPlanEntryInfo.class);
    }
    public boolean add(MaterialEnterPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialEnterPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialEnterPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public MaterialEnterPlanEntryInfo get(int index)
    {
        return(MaterialEnterPlanEntryInfo)getObject(index);
    }
    public MaterialEnterPlanEntryInfo get(Object key)
    {
        return(MaterialEnterPlanEntryInfo)getObject(key);
    }
    public void set(int index, MaterialEnterPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialEnterPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialEnterPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}