package com.kingdee.eas.fdc.basedata;

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

public interface IVoucherForProject extends IFDCBill
{
    public VoucherForProjectInfo getVoucherForProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public VoucherForProjectInfo getVoucherForProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public VoucherForProjectInfo getVoucherForProjectInfo(String oql) throws BOSException, EASBizException;
    public VoucherForProjectCollection getVoucherForProjectCollection() throws BOSException;
    public VoucherForProjectCollection getVoucherForProjectCollection(EntityViewInfo view) throws BOSException;
    public VoucherForProjectCollection getVoucherForProjectCollection(String oql) throws BOSException;
}