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

public interface IDeductOfPayReqBill extends ICoreBase
{
    public DeductOfPayReqBillInfo getDeductOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DeductOfPayReqBillInfo getDeductOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DeductOfPayReqBillInfo getDeductOfPayReqBillInfo(String oql) throws BOSException, EASBizException;
    public DeductOfPayReqBillCollection getDeductOfPayReqBillCollection() throws BOSException;
    public DeductOfPayReqBillCollection getDeductOfPayReqBillCollection(EntityViewInfo view) throws BOSException;
    public DeductOfPayReqBillCollection getDeductOfPayReqBillCollection(String oql) throws BOSException;
    public void reCalcAmount(String payReqID) throws BOSException, EASBizException;
}