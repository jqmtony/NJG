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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IRichExamTempTab extends IDataBase
{
    public RichExamTempTabInfo getRichExamTempTabInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RichExamTempTabInfo getRichExamTempTabInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RichExamTempTabInfo getRichExamTempTabInfo(String oql) throws BOSException, EASBizException;
    public RichExamTempTabCollection getRichExamTempTabCollection() throws BOSException;
    public RichExamTempTabCollection getRichExamTempTabCollection(EntityViewInfo view) throws BOSException;
    public RichExamTempTabCollection getRichExamTempTabCollection(String oql) throws BOSException;
    public void syncRichExamed(RichExamTempTabInfo model) throws BOSException;
}