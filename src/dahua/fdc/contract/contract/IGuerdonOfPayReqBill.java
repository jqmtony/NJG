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

public interface IGuerdonOfPayReqBill extends ICoreBase
{
    public GuerdonOfPayReqBillInfo getGuerdonOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public GuerdonOfPayReqBillInfo getGuerdonOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public GuerdonOfPayReqBillInfo getGuerdonOfPayReqBillInfo(String oql) throws BOSException, EASBizException;
    public GuerdonOfPayReqBillCollection getGuerdonOfPayReqBillCollection() throws BOSException;
    public GuerdonOfPayReqBillCollection getGuerdonOfPayReqBillCollection(EntityViewInfo view) throws BOSException;
    public GuerdonOfPayReqBillCollection getGuerdonOfPayReqBillCollection(String oql) throws BOSException;
    public void reCalcAmount(String payReqID) throws BOSException, EASBizException;
}