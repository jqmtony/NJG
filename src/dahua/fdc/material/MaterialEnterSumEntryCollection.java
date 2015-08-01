package com.kingdee.eas.fdc.material;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialEnterSumEntryCollection extends AbstractObjectCollection 
{
    public MaterialEnterSumEntryCollection()
    {
        super(MaterialEnterSumEntryInfo.class);
    }
    public boolean add(MaterialEnterSumEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialEnterSumEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialEnterSumEntryInfo item)
    {
        return removeObject(item);
    }
    public MaterialEnterSumEntryInfo get(int index)
    {
        return(MaterialEnterSumEntryInfo)getObject(index);
    }
    public MaterialEnterSumEntryInfo get(Object key)
    {
        return(MaterialEnterSumEntryInfo)getObject(key);
    }
    public void set(int index, MaterialEnterSumEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialEnterSumEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialEnterSumEntryInfo item)
    {
        return super.indexOf(item);
    }
}