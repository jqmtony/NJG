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

public interface ISupEvaluateTemplateTree extends ITreeBase
{
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(String oql) throws BOSException, EASBizException;
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection() throws BOSException;
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(EntityViewInfo view) throws BOSException;
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(String oql) throws BOSException;
}