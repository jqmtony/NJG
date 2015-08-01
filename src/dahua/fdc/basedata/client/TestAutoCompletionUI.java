/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.util.REAutoCompletionDataFilter;
import com.kingdee.eas.fdc.basedata.util.REAutoCompletionDataSetter;
import com.kingdee.eas.fdc.basedata.util.REAutoCompletionField;
import com.kingdee.eas.fdc.basedata.util.REAutoCompletionProperty;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class TestAutoCompletionUI extends AbstractTestAutoCompletionUI implements REAutoCompletionDataSetter, REAutoCompletionDataFilter
{
    private static final Logger logger = CoreUIObject.getLogger(TestAutoCompletionUI.class);
    
    /**
     * output class constructor
     */
    public TestAutoCompletionUI() throws Exception
    {
        super();
    }
  
	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		REAutoCompletionProperty properties = new REAutoCompletionProperty();
		properties.setSetter(this);
		properties.setMaxShowLine(8);
		properties.setRendererFieldName("longNumber");
		REAutoCompletionField.initAutoCompletion(this.kDTextField1, this, properties);
	}

	public void setFieldData(Object data) {
		if (data instanceof CostAccountInfo) {
			this.kDTextField1.setText(((CostAccountInfo) data).getLongNumber());
			this.kDTextField1.setUserObject(data);
		}
	}

	public ArrayList filter(String text) {
		if (text == null || text.trim().length() == 0) {
			return null;
		}
		try {
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("longNumber", text + "%", CompareType.LIKE));
			view.getFilter().getFilterItems().add(new FilterItemInfo("fullOrgUnit", SysContext.getSysContext().getCurrentCostUnit().getId()));
			view.setSelector(new SelectorItemCollection());
			view.getSelector().add("longNumber");
			view.getSelector().add("name");
			view.getSelector().add("id");
			view.setSorter(new SorterItemCollection());
			view.getSorter().add(new SorterItemInfo("longNumber"));			
			CostAccountCollection coll = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			if (coll != null) {
				ArrayList ret = new ArrayList();
				for (int i = 0; i < coll.size(); ++i) {
					ret.add(coll.get(i));
				}
				return ret;
			}
		} catch (BOSException e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
		return null;
	}
}