package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataEdateDataCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataEdateDataCollection()
    {
        super(BuildPriceIndexEindexDataEdateDataInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataEdateDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataEdateDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataEdateDataInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataEdateDataInfo get(int index)
    {
        return(BuildPriceIndexEindexDataEdateDataInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataEdateDataInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataEdateDataInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataEdateDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataEdateDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataEdateDataInfo item)
    {
        return super.indexOf(item);
    }
}