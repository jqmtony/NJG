/**
 * output package name
 */
package com.kingdee.eas.xr.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class XRBillStatusEnum extends IntEnum
{
    public static final int VERSION_VALUE = -3;//alias=��ʷ�汾
    public static final int ALTERING_VALUE = -2;//alias=�����
    public static final int NULL_VALUE = -1;//alias=
    public static final int ADD_VALUE = 0;//alias=����
    public static final int TEMPORARILYSAVED_VALUE = 1;//alias=����
    public static final int SUBMITED_VALUE = 2;//alias=�����
    public static final int DELETED_VALUE = 3;//alias=����
    public static final int AUDITED_VALUE = 4;//alias=�����
    public static final int RELEASED_VALUE = 5;//alias=�´�
    public static final int BLOCKED_VALUE = 6;//alias=����
    public static final int CLOSED_VALUE = 7;//alias=�ر�
    public static final int COMPLETE_VALUE = 8;//alias=�깤
    public static final int FINISH_VALUE = 90;//alias=���
    public static final int EXECUTION_VALUE = 10;//alias=��ִ��
    public static final int RECTIFICATION_VALUE = 11;//alias=������

    public static final XRBillStatusEnum VERSION = new XRBillStatusEnum("VERSION", VERSION_VALUE);
    public static final XRBillStatusEnum ALTERING = new XRBillStatusEnum("ALTERING", ALTERING_VALUE);
    public static final XRBillStatusEnum NULL = new XRBillStatusEnum("NULL", NULL_VALUE);
    public static final XRBillStatusEnum ADD = new XRBillStatusEnum("ADD", ADD_VALUE);
    public static final XRBillStatusEnum TEMPORARILYSAVED = new XRBillStatusEnum("TEMPORARILYSAVED", TEMPORARILYSAVED_VALUE);
    public static final XRBillStatusEnum SUBMITED = new XRBillStatusEnum("SUBMITED", SUBMITED_VALUE);
    public static final XRBillStatusEnum DELETED = new XRBillStatusEnum("DELETED", DELETED_VALUE);
    public static final XRBillStatusEnum AUDITED = new XRBillStatusEnum("AUDITED", AUDITED_VALUE);
    public static final XRBillStatusEnum RELEASED = new XRBillStatusEnum("RELEASED", RELEASED_VALUE);
    public static final XRBillStatusEnum BLOCKED = new XRBillStatusEnum("BLOCKED", BLOCKED_VALUE);
    public static final XRBillStatusEnum CLOSED = new XRBillStatusEnum("CLOSED", CLOSED_VALUE);
    public static final XRBillStatusEnum COMPLETE = new XRBillStatusEnum("COMPLETE", COMPLETE_VALUE);
    public static final XRBillStatusEnum FINISH = new XRBillStatusEnum("FINISH", FINISH_VALUE);
    public static final XRBillStatusEnum EXECUTION = new XRBillStatusEnum("EXECUTION", EXECUTION_VALUE);
    public static final XRBillStatusEnum RECTIFICATION = new XRBillStatusEnum("RECTIFICATION", RECTIFICATION_VALUE);

    /**
     * construct function
     * @param integer xRBillStatusEnum
     */
    private XRBillStatusEnum(String name, int xRBillStatusEnum)
    {
        super(name, xRBillStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static XRBillStatusEnum getEnum(String xRBillStatusEnum)
    {
        return (XRBillStatusEnum)getEnum(XRBillStatusEnum.class, xRBillStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static XRBillStatusEnum getEnum(int xRBillStatusEnum)
    {
        return (XRBillStatusEnum)getEnum(XRBillStatusEnum.class, xRBillStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(XRBillStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(XRBillStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(XRBillStatusEnum.class);
    }
}