/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ChangeBillStateEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1Saved";//alias=保存
    public static final String REGISTER_VALUE = "2Register";//alias=已登记
    public static final String SUBMIT_VALUE = "3Submit";//alias=已提交
    public static final String AUDITTING_VALUE = "4Auditting";//alias=审批中
    public static final String AUDIT_VALUE = "5Audit";//alias=已审批
    public static final String ANNOUNCE_VALUE = "6Announce";//alias=已下发
    public static final String VISA_VALUE = "7Visa";//alias=已签证
    public static final String AHEADDISPATCH_VALUE = "8AheadDisPatch";//alias=已提前下发
    public static final String INVALID_VALUE = "9INVALID";//alias=作废

    public static final ChangeBillStateEnum Saved = new ChangeBillStateEnum("Saved", SAVED_VALUE);
    public static final ChangeBillStateEnum Register = new ChangeBillStateEnum("Register", REGISTER_VALUE);
    public static final ChangeBillStateEnum Submit = new ChangeBillStateEnum("Submit", SUBMIT_VALUE);
    public static final ChangeBillStateEnum Auditting = new ChangeBillStateEnum("Auditting", AUDITTING_VALUE);
    public static final ChangeBillStateEnum Audit = new ChangeBillStateEnum("Audit", AUDIT_VALUE);
    public static final ChangeBillStateEnum Announce = new ChangeBillStateEnum("Announce", ANNOUNCE_VALUE);
    public static final ChangeBillStateEnum Visa = new ChangeBillStateEnum("Visa", VISA_VALUE);
    public static final ChangeBillStateEnum AheadDisPatch = new ChangeBillStateEnum("AheadDisPatch", AHEADDISPATCH_VALUE);
    public static final ChangeBillStateEnum INVALID = new ChangeBillStateEnum("INVALID", INVALID_VALUE);

    /**
     * construct function
     * @param String changeBillStateEnum
     */
    private ChangeBillStateEnum(String name, String changeBillStateEnum)
    {
        super(name, changeBillStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeBillStateEnum getEnum(String changeBillStateEnum)
    {
        return (ChangeBillStateEnum)getEnum(ChangeBillStateEnum.class, changeBillStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeBillStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeBillStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeBillStateEnum.class);
    }
}