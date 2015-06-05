package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fi.ar.OtherBillCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public interface IRichCompayWriteOff extends ICoreBillBase
{
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection() throws BOSException;
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(EntityViewInfo view) throws BOSException;
    public RichCompayWriteOffCollection getRichCompayWriteOffCollection(String oql) throws BOSException;
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RichCompayWriteOffInfo getRichCompayWriteOffInfo(String oql) throws BOSException, EASBizException;
    public boolean aboutHxAndFanHx(OtherBillCollection fpColl, RichExamedCollection richExamColl, int hxType, RichCompayWriteOffInfo ov) throws BOSException;
}