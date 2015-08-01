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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IGuerdonBill extends IFDCBill
{
    public GuerdonBillCollection getGuerdonBillCollection() throws BOSException;
    public GuerdonBillCollection getGuerdonBillCollection(EntityViewInfo view) throws BOSException;
    public GuerdonBillCollection getGuerdonBillCollection(String oql) throws BOSException;
    public GuerdonBillInfo getGuerdonBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public GuerdonBillInfo getGuerdonBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public GuerdonBillInfo getGuerdonBillInfo(String oql) throws BOSException, EASBizException;
}