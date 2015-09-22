package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexCollection()
    {
        super(BuildPriceIndexInfo.class);
    }
    public boolean add(BuildPriceIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexInfo get(int index)
    {
        return(BuildPriceIndexInfo)getObject(index);
    }
    public BuildPriceIndexInfo get(Object key)
    {
        return(BuildPriceIndexInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexInfo item)
    {
        return super.indexOf(item);
    }
}