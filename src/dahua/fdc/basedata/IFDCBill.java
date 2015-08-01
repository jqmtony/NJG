package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IFDCBill extends ICoreBillBase
{
    public FDCBillInfo getFDCBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCBillInfo getFDCBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCBillInfo getFDCBillInfo(String oql) throws BOSException, EASBizException;
    public FDCBillCollection getFDCBillCollection() throws BOSException;
    public FDCBillCollection getFDCBillCollection(EntityViewInfo view) throws BOSException;
    public FDCBillCollection getFDCBillCollection(String oql) throws BOSException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unAudit(List idList) throws BOSException, EASBizException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void cancel(IObjectPK pk) throws BOSException, EASBizException;
    public void cancelCancel(IObjectPK pk) throws BOSException, EASBizException;
    public void cancel(IObjectPK[] pkArray) throws BOSException, EASBizException;
    public void cancelCancel(IObjectPK[] pkArray) throws BOSException, EASBizException;
    public boolean checkCanSubmit(String id) throws BOSException, EASBizException;
    public Map fetchInitData(Map paramMap) throws BOSException, EASBizException;
    public Map fetchFilterInitData(Map paramMap) throws BOSException, EASBizException;
    public void setRespite(BOSUuid billId, boolean value) throws BOSException, EASBizException;
    public void setRespite(List ids, boolean value) throws BOSException, EASBizException;
}