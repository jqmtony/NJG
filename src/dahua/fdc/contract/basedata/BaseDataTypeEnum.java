/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BaseDataTypeEnum extends StringEnum
{
    public static final String USER_VALUE = "1";
    public static final String ORGUNIT_VALUE = "2";
    public static final String CURRENCY_VALUE = "3";
    public static final String PERSON_VALUE = "4";
    public static final String PROVIDER_VALUE = "5";
    public static final String PROJECT_VALUE = "6";
    public static final String PARTA_VALUE = "7";
    public static final String CONTRACTTYPE_VALUE = "8";
    public static final String CONTRACTDETAIL_VALUE = "9";
    public static final String INVITETYPE_VALUE = "10";
    public static final String CHANGEREASON_VALUE = "11";
    public static final String CHANGETYPE_VALUE = "12";
    public static final String JOBTYPE_VALUE = "13";
    public static final String PAYTYPE_VALUE = "14";
    public static final String DEDUTETYPE_VALUE = "15";
    public static final String REJUSTREASON_VALUE = "16";
    public static final String REJUSTTYPE_VALUE = "17";
    public static final String INDEXTYPE_VALUE = "18";
    public static final String INDEXNAME_VALUE = "19";
    public static final String SPECIALTYTYPE_VALUE = "20";
    public static final String COSTTARGET_VALUE = "21";
    public static final String COUNTERCLAIMTYPE_VALUE = "22";
    public static final String INVALIDCOSTREASON_VALUE = "23";
    public static final String SETTLEMENTTYPE_VALUE = "24";
    public static final String FEETYPE_VALUE = "25";
    public static final String FPITEM_VALUE = "26";
    public static final String BIZTYPE_VALUE = "27";
    public static final String ACCOUNTVIEW_VALUE = "28";

    public static final BaseDataTypeEnum User = new BaseDataTypeEnum("User", USER_VALUE);
    public static final BaseDataTypeEnum OrgUnit = new BaseDataTypeEnum("OrgUnit", ORGUNIT_VALUE);
    public static final BaseDataTypeEnum Currency = new BaseDataTypeEnum("Currency", CURRENCY_VALUE);
    public static final BaseDataTypeEnum Person = new BaseDataTypeEnum("Person", PERSON_VALUE);
    public static final BaseDataTypeEnum Provider = new BaseDataTypeEnum("Provider", PROVIDER_VALUE);
    public static final BaseDataTypeEnum Project = new BaseDataTypeEnum("Project", PROJECT_VALUE);
    public static final BaseDataTypeEnum PartA = new BaseDataTypeEnum("PartA", PARTA_VALUE);
    public static final BaseDataTypeEnum ContractType = new BaseDataTypeEnum("ContractType", CONTRACTTYPE_VALUE);
    public static final BaseDataTypeEnum ContractDetail = new BaseDataTypeEnum("ContractDetail", CONTRACTDETAIL_VALUE);
    public static final BaseDataTypeEnum InviteType = new BaseDataTypeEnum("InviteType", INVITETYPE_VALUE);
    public static final BaseDataTypeEnum ChangeReason = new BaseDataTypeEnum("ChangeReason", CHANGEREASON_VALUE);
    public static final BaseDataTypeEnum ChangeType = new BaseDataTypeEnum("ChangeType", CHANGETYPE_VALUE);
    public static final BaseDataTypeEnum JobType = new BaseDataTypeEnum("JobType", JOBTYPE_VALUE);
    public static final BaseDataTypeEnum PayType = new BaseDataTypeEnum("PayType", PAYTYPE_VALUE);
    public static final BaseDataTypeEnum DeduteType = new BaseDataTypeEnum("DeduteType", DEDUTETYPE_VALUE);
    public static final BaseDataTypeEnum RejustReason = new BaseDataTypeEnum("RejustReason", REJUSTREASON_VALUE);
    public static final BaseDataTypeEnum RejustType = new BaseDataTypeEnum("RejustType", REJUSTTYPE_VALUE);
    public static final BaseDataTypeEnum IndexType = new BaseDataTypeEnum("IndexType", INDEXTYPE_VALUE);
    public static final BaseDataTypeEnum IndexName = new BaseDataTypeEnum("IndexName", INDEXNAME_VALUE);
    public static final BaseDataTypeEnum SpecialtyType = new BaseDataTypeEnum("SpecialtyType", SPECIALTYTYPE_VALUE);
    public static final BaseDataTypeEnum CostTarget = new BaseDataTypeEnum("CostTarget", COSTTARGET_VALUE);
    public static final BaseDataTypeEnum CounterclaimType = new BaseDataTypeEnum("CounterclaimType", COUNTERCLAIMTYPE_VALUE);
    public static final BaseDataTypeEnum InvalidCostReason = new BaseDataTypeEnum("InvalidCostReason", INVALIDCOSTREASON_VALUE);
    public static final BaseDataTypeEnum SettlementType = new BaseDataTypeEnum("SettlementType", SETTLEMENTTYPE_VALUE);
    public static final BaseDataTypeEnum FeeType = new BaseDataTypeEnum("FeeType", FEETYPE_VALUE);
    public static final BaseDataTypeEnum FpItem = new BaseDataTypeEnum("FpItem", FPITEM_VALUE);
    public static final BaseDataTypeEnum BizType = new BaseDataTypeEnum("BizType", BIZTYPE_VALUE);
    public static final BaseDataTypeEnum AccountView = new BaseDataTypeEnum("AccountView", ACCOUNTVIEW_VALUE);

    /**
     * construct function
     * @param String baseDataTypeEnum
     */
    private BaseDataTypeEnum(String name, String baseDataTypeEnum)
    {
        super(name, baseDataTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BaseDataTypeEnum getEnum(String baseDataTypeEnum)
    {
        return (BaseDataTypeEnum)getEnum(BaseDataTypeEnum.class, baseDataTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BaseDataTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BaseDataTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BaseDataTypeEnum.class);
    }
}