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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface IFDCNoCostSplitBill extends IFDCBill
{
    public FDCNoCostSplitBillInfo getFDCNoCostSplitBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCNoCostSplitBillInfo getFDCNoCostSplitBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCNoCostSplitBillInfo getFDCNoCostSplitBillInfo(String oql) throws BOSException, EASBizException;
    public FDCNoCostSplitBillCollection getFDCNoCostSplitBillCollection() throws BOSException;
    public FDCNoCostSplitBillCollection getFDCNoCostSplitBillCollection(EntityViewInfo view) throws BOSException;
    public FDCNoCostSplitBillCollection getFDCNoCostSplitBillCollection(String oql) throws BOSException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void audit(List idList) throws BOSException, EASBizException;
    public void unAudit(List idList) throws BOSException, EASBizException;
    public Map fetchInitParam() throws BOSException, EASBizException;
}