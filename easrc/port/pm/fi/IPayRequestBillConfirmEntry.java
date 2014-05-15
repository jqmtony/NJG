package com.kingdee.eas.port.pm.fi;

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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IPayRequestBillConfirmEntry extends IBillEntryBase
{
    public PayRequestBillConfirmEntryInfo getPayRequestBillConfirmEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PayRequestBillConfirmEntryInfo getPayRequestBillConfirmEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PayRequestBillConfirmEntryInfo getPayRequestBillConfirmEntryInfo(String oql) throws BOSException, EASBizException;
    public PayRequestBillConfirmEntryCollection getPayRequestBillConfirmEntryCollection() throws BOSException;
    public PayRequestBillConfirmEntryCollection getPayRequestBillConfirmEntryCollection(EntityViewInfo view) throws BOSException;
    public PayRequestBillConfirmEntryCollection getPayRequestBillConfirmEntryCollection(String oql) throws BOSException;
}