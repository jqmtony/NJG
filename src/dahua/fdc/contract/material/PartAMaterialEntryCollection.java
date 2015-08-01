package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartAMaterialEntryCollection extends AbstractObjectCollection 
{
    public PartAMaterialEntryCollection()
    {
        super(PartAMaterialEntryInfo.class);
    }
    public boolean add(PartAMaterialEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartAMaterialEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartAMaterialEntryInfo item)
    {
        return removeObject(item);
    }
    public PartAMaterialEntryInfo get(int index)
    {
        return(PartAMaterialEntryInfo)getObject(index);
    }
    public PartAMaterialEntryInfo get(Object key)
    {
        return(PartAMaterialEntryInfo)getObject(key);
    }
    public void set(int index, PartAMaterialEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartAMaterialEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartAMaterialEntryInfo item)
    {
        return super.indexOf(item);
    }
}