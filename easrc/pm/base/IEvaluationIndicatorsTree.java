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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IEvaluationIndicatorsTree extends ITreeBase
{
    public EvaluationIndicatorsTreeInfo getEvaluationIndicatorsTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EvaluationIndicatorsTreeInfo getEvaluationIndicatorsTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EvaluationIndicatorsTreeInfo getEvaluationIndicatorsTreeInfo(String oql) throws BOSException, EASBizException;
    public EvaluationIndicatorsTreeCollection getEvaluationIndicatorsTreeCollection() throws BOSException;
    public EvaluationIndicatorsTreeCollection getEvaluationIndicatorsTreeCollection(EntityViewInfo view) throws BOSException;
    public EvaluationIndicatorsTreeCollection getEvaluationIndicatorsTreeCollection(String oql) throws BOSException;
}