package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEntryCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEntryCollection()
    {
        super(BuildPriceIndexEntryInfo.class);
    }
    public boolean add(BuildPriceIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEntryInfo get(int index)
    {
        return(BuildPriceIndexEntryInfo)getObject(index);
    }
    public BuildPriceIndexEntryInfo get(Object key)
    {
        return(BuildPriceIndexEntryInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}