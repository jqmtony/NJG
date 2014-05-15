package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreprojectPreProjectEntryCollection extends AbstractObjectCollection 
{
    public PreprojectPreProjectEntryCollection()
    {
        super(PreprojectPreProjectEntryInfo.class);
    }
    public boolean add(PreprojectPreProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreprojectPreProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreprojectPreProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public PreprojectPreProjectEntryInfo get(int index)
    {
        return(PreprojectPreProjectEntryInfo)getObject(index);
    }
    public PreprojectPreProjectEntryInfo get(Object key)
    {
        return(PreprojectPreProjectEntryInfo)getObject(key);
    }
    public void set(int index, PreprojectPreProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreprojectPreProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreprojectPreProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}