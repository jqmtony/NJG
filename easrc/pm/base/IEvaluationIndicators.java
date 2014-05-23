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

public interface IEvaluationIndicators extends IDataBase
{
    public EvaluationIndicatorsInfo getEvaluationIndicatorsInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EvaluationIndicatorsInfo getEvaluationIndicatorsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EvaluationIndicatorsInfo getEvaluationIndicatorsInfo(String oql) throws BOSException, EASBizException;
    public EvaluationIndicatorsCollection getEvaluationIndicatorsCollection() throws BOSException;
    public EvaluationIndicatorsCollection getEvaluationIndicatorsCollection(EntityViewInfo view) throws BOSException;
    public EvaluationIndicatorsCollection getEvaluationIndicatorsCollection(String oql) throws BOSException;
}