package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import java.util.Set;

public interface IFDCBillWFFacade extends IBizCtrl
{
    public void setWFAuditNotPrint(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException;
    public void setWFAuditOrgInfo(BOSUuid billId, BOSUuid auditorId, String org) throws BOSException, EASBizException;
    public List getWFAuditResultForPrint(String billId) throws BOSException, EASBizException;
    public List getWFAuditResultForPrint2(String billId, boolean showHistory) throws BOSException, EASBizException;
    public Map getWFBillLastAuditorAndTime(Set billIds) throws BOSException, EASBizException;
}