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
import com.kingdee.eas.framework.ICoreBase;

public interface ICompensationOfPayReqBill extends ICoreBase
{
    public CompensationOfPayReqBillInfo getCompensationOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompensationOfPayReqBillInfo getCompensationOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompensationOfPayReqBillInfo getCompensationOfPayReqBillInfo(String oql) throws BOSException, EASBizException;
    public CompensationOfPayReqBillCollection getCompensationOfPayReqBillCollection() throws BOSException;
    public CompensationOfPayReqBillCollection getCompensationOfPayReqBillCollection(EntityViewInfo view) throws BOSException;
    public CompensationOfPayReqBillCollection getCompensationOfPayReqBillCollection(String oql) throws BOSException;
    public void reCalcAmount(String payReqID) throws BOSException, EASBizException;
}