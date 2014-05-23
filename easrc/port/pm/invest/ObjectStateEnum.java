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
    public static final String SAVE_VALUE = "1";//alias=申报中
    public static final String DECLARED_VALUE = "2";//alias=已申报
    public static final String AUDIT_VALUE = "3";//alias=初审中
    public static final String THROUGHAUDIT_VALUE = "4";//alias=初审通过
    public static final String ASSESSMENT_VALUE = "5";//alias=评审中
    public static final String ACCREDIT_VALUE = "6";//alias=评审通过
    public static final String COMPLEMENT_VALUE = "7";//alias=补充完善
    public static final String APPROVAL_VALUE = "8";//alias=已立项
    public static final String VETO_VALUE = "9";//alias=否决

    public static final ObjectStateEnum save = new ObjectStateEnum("save", SAVE_VALUE);
    public static final ObjectStateEnum declared = new ObjectStateEnum("declared", DECLARED_VALUE);
    public static final ObjectStateEnum audit = new ObjectStateEnum("audit", AUDIT_VALUE);
    public static final ObjectStateEnum throughAudit = new ObjectStateEnum("throughAudit", THROUGHAUDIT_VALUE);
    public static final ObjectStateEnum assessment = new ObjectStateEnum("assessment", ASSESSMENT_VALUE);
    public static final ObjectStateEnum accredit = new ObjectStateEnum("accredit", ACCREDIT_VALUE);
    public static final ObjectStateEnum complement = new ObjectStateEnum("complement", COMPLEMENT_VALUE);
    public static final ObjectStateEnum approval = new ObjectStateEnum("approval", APPROVAL_VALUE);
    public static final ObjectStateEnum veto = new ObjectStateEnum("veto", VETO_VALUE);

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