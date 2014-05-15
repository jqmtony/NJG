package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreProjectTempPreProjectTempEntryCollection extends AbstractObjectCollection 
{
    public PreProjectTempPreProjectTempEntryCollection()
    {
        super(PreProjectTempPreProjectTempEntryInfo.class);
    }
    public boolean add(PreProjectTempPreProjectTempEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreProjectTempPreProjectTempEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreProjectTempPreProjectTempEntryInfo item)
    {
        return removeObject(item);
    }
    public PreProjectTempPreProjectTempEntryInfo get(int index)
    {
        return(PreProjectTempPreProjectTempEntryInfo)getObject(index);
    }
    public PreProjectTempPreProjectTempEntryInfo get(Object key)
    {
        return(PreProjectTempPreProjectTempEntryInfo)getObject(key);
    }
    public void set(int index, PreProjectTempPreProjectTempEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreProjectTempPreProjectTempEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreProjectTempPreProjectTempEntryInfo item)
    {
        return super.indexOf(item);
    }
}