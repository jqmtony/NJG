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
public class XRBizActionEnum extends IntEnum
{
    public static final int SAVE_VALUE = 101;//alias=����
    public static final int SUBMIT_VALUE = 102;//alias=�ύ
    public static final int AUDIT_VALUE = 103;//alias=���
    public static final int DELETE_VALUE = 104;//alias=ɾ��
    public static final int VOUCHER_VALUE = 105;//alias=����ƾ֤
    public static final int UNVOUCHER_VALUE = 106;//alias=ɾ��ƾ֤
    public static final int REVERSE_VALUE = 107;//alias=����
    public static final int CANCEL_VALUE = 108;//alias=ȡ��
    public static final int UNAUDIT_VALUE = 109;//alias=�����
    public static final int CLOSE_VALUE = 201;//alias=�ر�
    public static final int FREEZE_VALUE = 202;//alias=����
    public static final int UNFREEZE_VALUE = 203;//alias=�ⶳ
    public static final int PRINT_VALUE = 301;//alias=��ӡ
    public static final int PRINTPREVIEW_VALUE = 302;//alias=��ӡԤ��
    public static final int UNCLOSE_VALUE = 303;//alias=���ر�
    public static final int FIRST_VALUE = 401;//alias=��һ
    public static final int PREVIOUS_VALUE = 402;//alias=ǰһ
    public static final int NEXT_VALUE = 403;//alias=��һ
    public static final int LAST_VALUE = 404;//alias=���
    public static final int TRANSFORM_VALUE = 501;//alias=����ת��
    public static final int RECEIVE_VALUE = 601;//alias=�տ�
    public static final int PAY_VALUE = 602;//alias=����
    public static final int ALTER_VALUE = 701;//alias=���
    public static final int LOCK_VALUE = 702;//alias=����
    public static final int UNLOCK_VALUE = 703;//alias=����
    public static final int IMPORT_VALUE = 704;//alias=����
    public static final int ENTRYRELEASE_VALUE = 801;//alias=�´�
    public static final int ENTRYUNRELEASE_VALUE = 802;//alias=���´�
    public static final int CLOSEAR_VALUE = 901;//alias=�ر�Ӧ��
    public static final int UNCLOSEAR_VALUE = 902;//alias=���ر�Ӧ��
    public static final int CREATEAS_VALUE = 903;//alias=���ɹ�����Ϣ

    public static final XRBizActionEnum SAVE = new XRBizActionEnum("SAVE", SAVE_VALUE);
    public static final XRBizActionEnum SUBMIT = new XRBizActionEnum("SUBMIT", SUBMIT_VALUE);
    public static final XRBizActionEnum AUDIT = new XRBizActionEnum("AUDIT", AUDIT_VALUE);
    public static final XRBizActionEnum DELETE = new XRBizActionEnum("DELETE", DELETE_VALUE);
    public static final XRBizActionEnum VOUCHER = new XRBizActionEnum("VOUCHER", VOUCHER_VALUE);
    public static final XRBizActionEnum UNVOUCHER = new XRBizActionEnum("UNVOUCHER", UNVOUCHER_VALUE);
    public static final XRBizActionEnum REVERSE = new XRBizActionEnum("REVERSE", REVERSE_VALUE);
    public static final XRBizActionEnum CANCEL = new XRBizActionEnum("CANCEL", CANCEL_VALUE);
    public static final XRBizActionEnum UNAUDIT = new XRBizActionEnum("UNAUDIT", UNAUDIT_VALUE);
    public static final XRBizActionEnum CLOSE = new XRBizActionEnum("CLOSE", CLOSE_VALUE);
    public static final XRBizActionEnum FREEZE = new XRBizActionEnum("FREEZE", FREEZE_VALUE);
    public static final XRBizActionEnum UNFREEZE = new XRBizActionEnum("UNFREEZE", UNFREEZE_VALUE);
    public static final XRBizActionEnum PRINT = new XRBizActionEnum("PRINT", PRINT_VALUE);
    public static final XRBizActionEnum PRINTPREVIEW = new XRBizActionEnum("PRINTPREVIEW", PRINTPREVIEW_VALUE);
    public static final XRBizActionEnum UNCLOSE = new XRBizActionEnum("UNCLOSE", UNCLOSE_VALUE);
    public static final XRBizActionEnum FIRST = new XRBizActionEnum("FIRST", FIRST_VALUE);
    public static final XRBizActionEnum PREVIOUS = new XRBizActionEnum("PREVIOUS", PREVIOUS_VALUE);
    public static final XRBizActionEnum NEXT = new XRBizActionEnum("NEXT", NEXT_VALUE);
    public static final XRBizActionEnum LAST = new XRBizActionEnum("LAST", LAST_VALUE);
    public static final XRBizActionEnum TRANSFORM = new XRBizActionEnum("TRANSFORM", TRANSFORM_VALUE);
    public static final XRBizActionEnum RECEIVE = new XRBizActionEnum("RECEIVE", RECEIVE_VALUE);
    public static final XRBizActionEnum PAY = new XRBizActionEnum("PAY", PAY_VALUE);
    public static final XRBizActionEnum ALTER = new XRBizActionEnum("ALTER", ALTER_VALUE);
    public static final XRBizActionEnum LOCK = new XRBizActionEnum("LOCK", LOCK_VALUE);
    public static final XRBizActionEnum UNLOCK = new XRBizActionEnum("UNLOCK", UNLOCK_VALUE);
    public static final XRBizActionEnum IMPORT = new XRBizActionEnum("IMPORT", IMPORT_VALUE);
    public static final XRBizActionEnum ENTRYRELEASE = new XRBizActionEnum("ENTRYRELEASE", ENTRYRELEASE_VALUE);
    public static final XRBizActionEnum ENTRYUNRELEASE = new XRBizActionEnum("ENTRYUNRELEASE", ENTRYUNRELEASE_VALUE);
    public static final XRBizActionEnum CLOSEAR = new XRBizActionEnum("CLOSEAR", CLOSEAR_VALUE);
    public static final XRBizActionEnum UNCLOSEAR = new XRBizActionEnum("UNCLOSEAR", UNCLOSEAR_VALUE);
    public static final XRBizActionEnum CREATEAS = new XRBizActionEnum("CREATEAS", CREATEAS_VALUE);

    /**
     * construct function
     * @param integer xRBizActionEnum
     */
    private XRBizActionEnum(String name, int xRBizActionEnum)
    {
        super(name, xRBizActionEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static XRBizActionEnum getEnum(String xRBizActionEnum)
    {
        return (XRBizActionEnum)getEnum(XRBizActionEnum.class, xRBizActionEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static XRBizActionEnum getEnum(int xRBizActionEnum)
    {
        return (XRBizActionEnum)getEnum(XRBizActionEnum.class, xRBizActionEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(XRBizActionEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(XRBizActionEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(XRBizActionEnum.class);
    }
}