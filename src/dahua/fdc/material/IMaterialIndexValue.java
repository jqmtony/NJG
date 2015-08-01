package com.kingdee.eas.fdc.material;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.framework.ICoreBase;

public interface IMaterialIndexValue extends ICoreBase
{
    public MaterialIndexValueInfo getMaterialIndexValueInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MaterialIndexValueInfo getMaterialIndexValueInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MaterialIndexValueInfo getMaterialIndexValueInfo(String oql) throws BOSException, EASBizException;
    public MaterialIndexValueCollection getMaterialIndexValueCollection() throws BOSException;
    public MaterialIndexValueCollection getMaterialIndexValueCollection(EntityViewInfo view) throws BOSException;
    public MaterialIndexValueCollection getMaterialIndexValueCollection(String oql) throws BOSException;
    public void insertIndexValue(ArrayList params, IObjectPK materialInfoId) throws BOSException;
}