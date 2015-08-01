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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IInvalidCostReason extends ITreeBase
{
    public InvalidCostReasonInfo getInvalidCostReasonInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InvalidCostReasonInfo getInvalidCostReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InvalidCostReasonInfo getInvalidCostReasonInfo(String oql) throws BOSException, EASBizException;
    public InvalidCostReasonCollection getInvalidCostReasonCollection() throws BOSException;
    public InvalidCostReasonCollection getInvalidCostReasonCollection(EntityViewInfo view) throws BOSException;
    public InvalidCostReasonCollection getInvalidCostReasonCollection(String oql) throws BOSException;
    public boolean enabled(IObjectPK ctPK) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ctPK) throws BOSException, EASBizException;
}