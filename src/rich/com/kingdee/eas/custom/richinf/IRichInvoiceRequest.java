package com.kingdee.eas.custom.richinf;

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

public interface IRichInvoiceRequest extends ICoreBillBase
{
    public RichInvoiceRequestCollection getRichInvoiceRequestCollection() throws BOSException;
    public RichInvoiceRequestCollection getRichInvoiceRequestCollection(EntityViewInfo view) throws BOSException;
    public RichInvoiceRequestCollection getRichInvoiceRequestCollection(String oql) throws BOSException;
    public RichInvoiceRequestInfo getRichInvoiceRequestInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RichInvoiceRequestInfo getRichInvoiceRequestInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RichInvoiceRequestInfo getRichInvoiceRequestInfo(String oql) throws BOSException, EASBizException;
    public void audit(RichInvoiceRequestInfo model) throws BOSException;
    public void unAudit(RichInvoiceRequestInfo model) throws BOSException;
}