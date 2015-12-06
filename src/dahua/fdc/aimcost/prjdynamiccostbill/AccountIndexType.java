/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AccountIndexType extends StringEnum
{
    public static final String LAND_VALUE = "LAND";//alias=土地科目
    public static final String EARLYSTAGE_VALUE = "EARLYSTAGE";//alias=前期科目
    public static final String CONSTRUCTION_VALUE = "CONSTRUCTION";//alias=建安科目
    public static final String BASESTATION_VALUE = "BASESTATION";//alias=基础设施科目
    public static final String PUBCONSTRUCTION_VALUE = "PUBCONSTRUCTION";//alias=公建配套
    public static final String DEVELOPINDIRECT_VALUE = "DEVELOPINDIRECT";//alias=开发间接科目

    public static final AccountIndexType Land = new AccountIndexType("Land", LAND_VALUE);
    public static final AccountIndexType EarlyStage = new AccountIndexType("EarlyStage", EARLYSTAGE_VALUE);
    public static final AccountIndexType Construction = new AccountIndexType("Construction", CONSTRUCTION_VALUE);
    public static final AccountIndexType BaseStation = new AccountIndexType("BaseStation", BASESTATION_VALUE);
    public static final AccountIndexType PubConstruction = new AccountIndexType("PubConstruction", PUBCONSTRUCTION_VALUE);
    public static final AccountIndexType DevelopIndirect = new AccountIndexType("DevelopIndirect", DEVELOPINDIRECT_VALUE);

    /**
     * construct function
     * @param String accountIndexType
     */
    private AccountIndexType(String name, String accountIndexType)
    {
        super(name, accountIndexType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AccountIndexType getEnum(String accountIndexType)
    {
        return (AccountIndexType)getEnum(AccountIndexType.class, accountIndexType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AccountIndexType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AccountIndexType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AccountIndexType.class);
    }
}