package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataCollection()
    {
        super(BuildPriceIndexEindexDataInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataInfo get(int index)
    {
        return(BuildPriceIndexEindexDataInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataInfo item)
    {
        return super.indexOf(item);
    }
}