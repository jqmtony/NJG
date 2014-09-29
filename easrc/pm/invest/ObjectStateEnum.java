/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ObjectStateEnum extends StringEnum
{
    public static final String SAVE_VALUE = "1";//alias=�걨��
    public static final String DECLARED_VALUE = "2";//alias=���걨
    public static final String AUDIT_VALUE = "3";//alias=������
    public static final String THROUGHAUDIT_VALUE = "4";//alias=����ͨ��
    public static final String ASSESSMENT_VALUE = "5";//alias=������
    public static final String ACCREDIT_VALUE = "6";//alias=����ͨ��
    public static final String COMPLEMENT_VALUE = "7";//alias=��������
    public static final String APPROVAL_VALUE = "8";//alias=������
    public static final String VETO_VALUE = "9";//alias=���
    public static final String ADJUSTED_VALUE = "10";//alias=�ѱ�����
    public static final String STOP_VALUE = "11";//alias=�ݻ�
    public static final String ACCEPTANCE_VALUE = "12";//alias=������
    public static final String CANCEL_VALUE = "13";//alias=����ֹ

    public static final ObjectStateEnum save = new ObjectStateEnum("save", SAVE_VALUE);
    public static final ObjectStateEnum declared = new ObjectStateEnum("declared", DECLARED_VALUE);
    public static final ObjectStateEnum audit = new ObjectStateEnum("audit", AUDIT_VALUE);
    public static final ObjectStateEnum throughAudit = new ObjectStateEnum("throughAudit", THROUGHAUDIT_VALUE);
    public static final ObjectStateEnum assessment = new ObjectStateEnum("assessment", ASSESSMENT_VALUE);
    public static final ObjectStateEnum accredit = new ObjectStateEnum("accredit", ACCREDIT_VALUE);
    public static final ObjectStateEnum complement = new ObjectStateEnum("complement", COMPLEMENT_VALUE);
    public static final ObjectStateEnum approval = new ObjectStateEnum("approval", APPROVAL_VALUE);
    public static final ObjectStateEnum veto = new ObjectStateEnum("veto", VETO_VALUE);
    public static final ObjectStateEnum adjusted = new ObjectStateEnum("adjusted", ADJUSTED_VALUE);
    public static final ObjectStateEnum stop = new ObjectStateEnum("stop", STOP_VALUE);
    public static final ObjectStateEnum acceptance = new ObjectStateEnum("acceptance", ACCEPTANCE_VALUE);
    public static final ObjectStateEnum cancel = new ObjectStateEnum("cancel", CANCEL_VALUE);

    /**
     * construct function
     * @param String objectStateEnum
     */
    private ObjectStateEnum(String name, String objectStateEnum)
    {
        super(name, objectStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ObjectStateEnum getEnum(String objectStateEnum)
    {
        return (ObjectStateEnum)getEnum(ObjectStateEnum.class, objectStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ObjectStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ObjectStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ObjectStateEnum.class);
    }
}