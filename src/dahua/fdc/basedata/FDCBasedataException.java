/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class FDCBasedataException extends EASBizException
{
    private static final String MAINCODE = "16";

    public static final NumericExceptionSubItem NUMBER_IS_EMPTY = new NumericExceptionSubItem("100", "NUMBER_IS_EMPTY");
    public static final NumericExceptionSubItem NUMBER_ALREADY_EXIST = new NumericExceptionSubItem("101", "NUMBER_ALREADY_EXIST");
    public static final NumericExceptionSubItem NAME_IS_EMPTY = new NumericExceptionSubItem("102", "NAME_IS_EMPTY");
    public static final NumericExceptionSubItem NAME_ALREADY_EXIST = new NumericExceptionSubItem("103", "NAME_ALREADY_EXIST");
    public static final NumericExceptionSubItem NUMBER_CHECK = new NumericExceptionSubItem("104", "NUMBER_CHECK");
    public static final NumericExceptionSubItem LANDDEVELOPER_IS_EMPTY = new NumericExceptionSubItem("105", "LANDDEVELOPER_IS_EMPTY");
    public static final NumericExceptionSubItem STARTDATE_IS_NULL = new NumericExceptionSubItem("108", "STARTDATE_IS_NULL");
    public static final NumericExceptionSubItem DELETE_ISPARENT_FAIL = new NumericExceptionSubItem("201", "DELETE_ISPARENT_FAIL");
    public static final NumericExceptionSubItem DELETE_ISREFERENCE_FAIL = new NumericExceptionSubItem("202", "DELETE_ISREFERENCE_FAIL");
    public static final NumericExceptionSubItem DELETE_ISDISENABLED_FAIL = new NumericExceptionSubItem("203", "DELETE_ISDISENABLED_FAIL");
    public static final NumericExceptionSubItem NUMBER_CHECK_2 = new NumericExceptionSubItem("106", "NUMBER_CHECK_2");
    public static final NumericExceptionSubItem NUMBER_CHECK_3 = new NumericExceptionSubItem("107", "NUMBER_CHECK_3");
    public static final NumericExceptionSubItem CANNOT_VERSIONCHANGE = new NumericExceptionSubItem("300", "CANNOT_VERSIONCHANGE");
    public static final NumericExceptionSubItem CONTRACTTYPE_IS_EMPTY = new NumericExceptionSubItem("310", "CONTRACTTYPE_IS_EMPTY");
    public static final NumericExceptionSubItem DATATYPE_IS_EMPTY = new NumericExceptionSubItem("311", "DATATYPE_IS_EMPTY");
    public static final NumericExceptionSubItem CONTRACTTYPE_PARENT_DISENABLE = new NumericExceptionSubItem("321", "CONTRACTTYPE_PARENT_DISENABLE");
    public static final NumericExceptionSubItem PROJECT_PARENT_DISENABLE = new NumericExceptionSubItem("322", "PROJECT_PARENT_DISENABLE");
    public static final NumericExceptionSubItem VERSIONSAVE_CANNOT_SAVE = new NumericExceptionSubItem("323", "VERSIONSAVE_CANNOT_SAVE");
    public static final NumericExceptionSubItem VERSIONSAVE_NAME_ISNULL = new NumericExceptionSubItem("324", "VERSIONSAVE_NAME_ISNULL");
    public static final NumericExceptionSubItem CONTRACTTYPE_DEL_REFERENCE = new NumericExceptionSubItem("325", "CONTRACTTYPE_DEL_REFERENCE");
    public static final NumericExceptionSubItem IMPORT_ONLYTOPROJECT = new NumericExceptionSubItem("326", "IMPORT_ONLYTOPROJECT");
    public static final NumericExceptionSubItem IMPORT_TREESELECT = new NumericExceptionSubItem("327", "IMPORT_TREESELECT");
    public static final NumericExceptionSubItem DISASSIGN_ISREFERENCE = new NumericExceptionSubItem("328", "DISASSIGN_ISREFERENCE");
    public static final NumericExceptionSubItem TARGETTYPE_PARENT_DISENABLE = new NumericExceptionSubItem("329", "TARGETTYPE_PARENT_DISENABLE");
    public static final NumericExceptionSubItem MEASUREUNIT_ISNULL = new NumericExceptionSubItem("330", "MEASUREUNIT_ISNULL");
    public static final NumericExceptionSubItem TARGETTYPE_ISNULL = new NumericExceptionSubItem("331", "TARGETTYPE_ISNULL");
    public static final NumericExceptionSubItem TARGETTYPE_DEL_REFERENCE = new NumericExceptionSubItem("332", "TARGETTYPE_DEL_REFERENCE");
    public static final NumericExceptionSubItem COSTACCOUNT_ADD_SUBHAVE = new NumericExceptionSubItem("333", "COSTACCOUNT_ADD_SUBHAVE");
    public static final NumericExceptionSubItem COSTACCOUNT_TEMP_IMPORT = new NumericExceptionSubItem("334", "COSTACCOUNT_TEMP_IMPORT");
    public static final NumericExceptionSubItem DISENABLE_CANNOT_USED = new NumericExceptionSubItem("335", "DISENABLE_CANNOT_USED");
    public static final NumericExceptionSubItem DISENABLE_CANNOT_ONLY = new NumericExceptionSubItem("336", "DISENABLE_CANNOT_ONLY");
    public static final NumericExceptionSubItem DISENABLE_SUBNODE_REFERENCE = new NumericExceptionSubItem("337", "DISENABLE_SUBNODE_REFERENCE");
    public static final NumericExceptionSubItem COSTACCOUNT_PARENT_DISENABLE = new NumericExceptionSubItem("338", "COSTACCOUNT_PARENT_DISENABLE");
    public static final NumericExceptionSubItem COSTACCOUNT_DISASSIGN = new NumericExceptionSubItem("339", "COSTACCOUNT_DISASSIGN");
    public static final NumericExceptionSubItem PROJECTSTATUS_IS_EMPTY = new NumericExceptionSubItem("340", "PROJECTSTATUS_IS_EMPTY");
    public static final NumericExceptionSubItem CHANGETYPE_ISNULL = new NumericExceptionSubItem("341", "CHANGETYPE_ISNULL");
    public static final NumericExceptionSubItem CONTRACTCODINTTYPE = new NumericExceptionSubItem("342", "CONTRACTCODINTTYPE");
    public static final NumericExceptionSubItem NUMBER_IS_EXIST = new NumericExceptionSubItem("000", "NUMBER_IS_EXIST");
    public static final NumericExceptionSubItem CONTRACTCODINTTYPE_IS_EXIST = new NumericExceptionSubItem("343", "CONTRACTCODINTTYPE_IS_EXIST");
    public static final NumericExceptionSubItem CONTRACT_TYPE_ISNULL = new NumericExceptionSubItem("344", "CONTRACT_TYPE_ISNULL");
    public static final NumericExceptionSubItem UNITDATAMANGER_COMPANY_ISNULL = new NumericExceptionSubItem("350", "UNITDATAMANGER_COMPANY_ISNULL");
    public static final NumericExceptionSubItem UNITDATAMANGER_ADDRESS_ISNULL = new NumericExceptionSubItem("351", "UNITDATAMANGER_ADDRESS_ISNULL");
    public static final NumericExceptionSubItem COMPANY_SAME = new NumericExceptionSubItem("352", "COMPANY_SAME");
    public static final NumericExceptionSubItem COSTACCOUNTTYPE_ISNULL = new NumericExceptionSubItem("353", "COSTACCOUNTTYPE_ISNULL");
    public static final NumericExceptionSubItem PROJECTTYPE_REFERENCE = new NumericExceptionSubItem("354", "PROJECTTYPE_REFERENCE");
    public static final NumericExceptionSubItem DELETE_SYSDATA = new NumericExceptionSubItem("001", "DELETE_SYSDATA");
    public static final NumericExceptionSubItem INVALIDCOSTREASON_DEL_REFERENCE = new NumericExceptionSubItem("002", "INVALIDCOSTREASON_DEL_REFERENCE");
    public static final NumericExceptionSubItem INVALIDCOSTREASON_PARENT_DISENABLE = new NumericExceptionSubItem("003", "INVALIDCOSTREASON_PARENT_DISENABLE");
    public static final NumericExceptionSubItem AUDITORMUSTBETHESAMEUSER = new NumericExceptionSubItem("355", "AUDITORMUSTBETHESAMEUSER");
    public static final NumericExceptionSubItem PROJECT_PARENT_ISNOTDEVPRJ = new NumericExceptionSubItem("356", "PROJECT_PARENT_ISNOTDEVPRJ");
    public static final NumericExceptionSubItem ISNOTDEVPRJ_CANNOT_ONLY = new NumericExceptionSubItem("357", "ISNOTDEVPRJ_CANNOT_ONLY");
    public static final NumericExceptionSubItem CONTRACTTYPE_DISABLED = new NumericExceptionSubItem("358", "CONTRACTTYPE_DISABLED");
    public static final NumericExceptionSubItem NUMBER_MUST_DIGIT = new NumericExceptionSubItem("110", "NUMBER_MUST_DIGIT");
    public static final NumericExceptionSubItem INCOMEACCOUNT_ADD_SUBHAVE = new NumericExceptionSubItem("401", "INCOMEACCOUNT_ADD_SUBHAVE");
    public static final NumericExceptionSubItem INCOMEACCOUNT_PARENT_DISENABLE = new NumericExceptionSubItem("402", "INCOMEACCOUNT_PARENT_DISENABLE");
    public static final NumericExceptionSubItem DISENABLE_CANNOT_USED2 = new NumericExceptionSubItem("403", "DISENABLE_CANNOT_USED2");
    public static final NumericExceptionSubItem INCOMEACCOUNT_DISASSIGN = new NumericExceptionSubItem("404", "INCOMEACCOUNT_DISASSIGN");
    public static final NumericExceptionSubItem DISASSIGN_ISREFERENCE2 = new NumericExceptionSubItem("405", "DISASSIGN_ISREFERENCE2");
    public static final NumericExceptionSubItem INVITETYPE_DEL_REFERENCE = new NumericExceptionSubItem("406", "INVITETYPE_DEL_REFERENCE");
    public static final NumericExceptionSubItem DISENABLE_SUBNODE_REFERENCE1 = new NumericExceptionSubItem("004", "DISENABLE_SUBNODE_REFERENCE1");
    public static final NumericExceptionSubItem SELECTLEAFPROJECT = new NumericExceptionSubItem("407", "SElECTLEAFPROJECT");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public FDCBasedataException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public FDCBasedataException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public FDCBasedataException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public FDCBasedataException(NumericExceptionSubItem info)
    {
        this(info, null, null);
    }

    /**
     * getMainCode function
     */
    public String getMainCode()
    {
        return MAINCODE;
    }
}