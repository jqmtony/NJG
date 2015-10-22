package com.kingdee.eas.port.pm.qa;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ILinkBill extends IDataBase
{
    public LinkBillInfo getLinkBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public LinkBillInfo getLinkBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public LinkBillInfo getLinkBillInfo(String oql) throws BOSException, EASBizException;
    public LinkBillCollection getLinkBillCollection() throws BOSException;
    public LinkBillCollection getLinkBillCollection(EntityViewInfo view) throws BOSException;
    public LinkBillCollection getLinkBillCollection(String oql) throws BOSException;
}