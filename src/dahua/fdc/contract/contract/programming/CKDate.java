/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CKDate extends StringEnum
{
    public static final String SGT_VALUE = "SGT";//alias=ʩ��ͼ��ɽ���ʱ��
    public static final String CSD_VALUE = "CSD";//alias=��ͬǩ��ʱ��
    public static final String SWD_VALUE = "SWD";//alias=����ʱ��
    public static final String EWD_VALUE = "EWD";//alias=����ʱ��
    public static final String CSED_VALUE = "CSED";//alias=��ͬǩ�����ʱ��

    public static final CKDate SGT = new CKDate("SGT", SGT_VALUE);
    public static final CKDate CSD = new CKDate("CSD", CSD_VALUE);
    public static final CKDate SWD = new CKDate("SWD", SWD_VALUE);
    public static final CKDate EWD = new CKDate("EWD", EWD_VALUE);
    public static final CKDate CSED = new CKDate("CSED", CSED_VALUE);

    /**
     * construct function
     * @param String cKDate
     */
    private CKDate(String name, String cKDate)
    {
        super(name, cKDate);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CKDate getEnum(String cKDate)
    {
        return (CKDate)getEnum(CKDate.class, cKDate);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CKDate.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CKDate.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CKDate.class);
    }
}