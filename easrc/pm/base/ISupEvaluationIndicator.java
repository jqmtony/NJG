package com.kingdee.eas.port.pm.base;

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

public interface ISupEvaluationIndicator extends IDataBase
{
    public SupEvaluationIndicatorInfo getSupEvaluationIndicatorInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupEvaluationIndicatorInfo getSupEvaluationIndicatorInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupEvaluationIndicatorInfo getSupEvaluationIndicatorInfo(String oql) throws BOSException, EASBizException;
    public SupEvaluationIndicatorCollection getSupEvaluationIndicatorCollection() throws BOSException;
    public SupEvaluationIndicatorCollection getSupEvaluationIndicatorCollection(EntityViewInfo view) throws BOSException;
    public SupEvaluationIndicatorCollection getSupEvaluationIndicatorCollection(String oql) throws BOSException;
}