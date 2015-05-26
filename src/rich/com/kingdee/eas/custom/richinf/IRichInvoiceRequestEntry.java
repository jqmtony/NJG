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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IRichInvoiceRequestEntry extends ICoreBillEntryBase
{
    public RichInvoiceRequestEntryInfo getRichInvoiceRequestEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RichInvoiceRequestEntryInfo getRichInvoiceRequestEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RichInvoiceRequestEntryInfo getRichInvoiceRequestEntryInfo(String oql) throws BOSException, EASBizException;
    public RichInvoiceRequestEntryCollection getRichInvoiceRequestEntryCollection() throws BOSException;
    public RichInvoiceRequestEntryCollection getRichInvoiceRequestEntryCollection(EntityViewInfo view) throws BOSException;
    public RichInvoiceRequestEntryCollection getRichInvoiceRequestEntryCollection(String oql) throws BOSException;
}