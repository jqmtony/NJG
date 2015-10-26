package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataEtextDataCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataEtextDataCollection()
    {
        super(BuildPriceIndexEindexDataEtextDataInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataEtextDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataEtextDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataEtextDataInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataEtextDataInfo get(int index)
    {
        return(BuildPriceIndexEindexDataEtextDataInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataEtextDataInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataEtextDataInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataEtextDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataEtextDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataEtextDataInfo item)
    {
        return super.indexOf(item);
    }
}