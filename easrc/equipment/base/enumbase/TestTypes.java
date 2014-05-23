/**
 * output package name
 */
package com.kingdee.eas.port.equipment.base.enumbase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TestTypes extends StringEnum
{
    public static final String CITYTEST_VALUE = "1";//alias=ÊÐ¼ì
    public static final String ANNUALTEST_VALUE = "2";//alias=Äê¼ì

    public static final TestTypes cityTest = new TestTypes("cityTest", CITYTEST_VALUE);
    public static final TestTypes annualTest = new TestTypes("annualTest", ANNUALTEST_VALUE);

    /**
     * construct function
     * @param String testTypes
     */
    private TestTypes(String name, String testTypes)
    {
        super(name, testTypes);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TestTypes getEnum(String testTypes)
    {
        return (TestTypes)getEnum(TestTypes.class, testTypes);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TestTypes.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TestTypes.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TestTypes.class);
    }
}