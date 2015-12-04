package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IPcontractTrackBill extends ICoreBillBase
{
    public PcontractTrackBillCollection getPcontractTrackBillCollection() throws BOSException;
    public PcontractTrackBillCollection getPcontractTrackBillCollection(EntityViewInfo view) throws BOSException;
    public PcontractTrackBillCollection getPcontractTrackBillCollection(String oql) throws BOSException;
    public PcontractTrackBillInfo getPcontractTrackBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PcontractTrackBillInfo getPcontractTrackBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PcontractTrackBillInfo getPcontractTrackBillInfo(String oql) throws BOSException, EASBizException;
    public void audit(PcontractTrackBillInfo model) throws BOSException;
    public void unaudit(PcontractTrackBillInfo model) throws BOSException;
    public void fix(PcontractTrackBillInfo model) throws BOSException;
}