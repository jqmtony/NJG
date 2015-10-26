package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildPriceIndexEindexDataEbaseUnitCollection extends AbstractObjectCollection 
{
    public BuildPriceIndexEindexDataEbaseUnitCollection()
    {
        super(BuildPriceIndexEindexDataEbaseUnitInfo.class);
    }
    public boolean add(BuildPriceIndexEindexDataEbaseUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildPriceIndexEindexDataEbaseUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildPriceIndexEindexDataEbaseUnitInfo item)
    {
        return removeObject(item);
    }
    public BuildPriceIndexEindexDataEbaseUnitInfo get(int index)
    {
        return(BuildPriceIndexEindexDataEbaseUnitInfo)getObject(index);
    }
    public BuildPriceIndexEindexDataEbaseUnitInfo get(Object key)
    {
        return(BuildPriceIndexEindexDataEbaseUnitInfo)getObject(key);
    }
    public void set(int index, BuildPriceIndexEindexDataEbaseUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildPriceIndexEindexDataEbaseUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildPriceIndexEindexDataEbaseUnitInfo item)
    {
        return super.indexOf(item);
    }
}