/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class examRecord extends StringEnum
{
    public static final String HAVE_VALUE = "1";//alias=有
    public static final String NONE_VALUE = "2";//alias=无
    public static final String NOTFOUND_VALUE = "3";//alias=尚未发现
    public static final String CONFIRM_VALUE = "4";//alias=查实

    public static final examRecord have = new examRecord("have", HAVE_VALUE);
    public static final examRecord none = new examRecord("none", NONE_VALUE);
    public static final examRecord notFound = new examRecord("notFound", NOTFOUND_VALUE);
    public static final examRecord confirm = new examRecord("confirm", CONFIRM_VALUE);

    /**
     * construct function
     * @param String examRecord
     */
    private examRecord(String name, String examRecord)
    {
        super(name, examRecord);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static examRecord getEnum(String examRecord)
    {
        return (examRecord)getEnum(examRecord.class, examRecord);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(examRecord.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(examRecord.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(examRecord.class);
    }
}