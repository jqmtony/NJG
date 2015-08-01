package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IFDCBillBase extends IBillBase
{
    public FDCBillBaseInfo getFDCBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCBillBaseInfo getFDCBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCBillBaseInfo getFDCBillBaseInfo(String oql) throws BOSException, EASBizException;
    public FDCBillBaseCollection getFDCBillBaseCollection() throws BOSException;
    public FDCBillBaseCollection getFDCBillBaseCollection(EntityViewInfo view) throws BOSException;
    public FDCBillBaseCollection getFDCBillBaseCollection(String oql) throws BOSException;
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