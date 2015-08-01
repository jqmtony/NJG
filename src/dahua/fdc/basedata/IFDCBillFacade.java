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
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IFDCBillFacade extends IBizCtrl
{
    public void autoChangeSettle(String settleId, boolean isAll) throws BOSException, EASBizException;
    public Object execAction(Map param) throws BOSException;
    public FDCSplitBillInfo autoPropSplit(String type, Map dataMap) throws BOSException, EASBizException;
    public void setBillAudited4wf(BOSUuid billId) throws BOSException, EASBizException;
    public void setBillAudited4wf2(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException;
    public void setBillAuditing4wf2(BOSUuid billId, BOSUuid auditorId) throws BOSException, EASBizException;
    public void dealSaveAboutConChange(String settleId) throws BOSException, EASBizException;
}