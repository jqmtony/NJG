package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.PropertyUnitCollection;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.QueryUtils;
import com.kingdee.eas.base.commonquery.client.CommonFilterUtil;
import com.kingdee.eas.base.commonquery.client.DataObject;
import com.kingdee.eas.base.commonquery.client.DefaultPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.util.StringUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.util.enums.IntEnum;
import com.kingdee.util.enums.StringEnum;



/**
 * 序事薄快速查找工具
 * @author guangyue_liu
 *
 */
public class UIFastQueryUtil {
	
	private final static String commonQueryFilterRes = "com.kingdee.eas.base.commonquery.client.CommonFilterPanel";

	private final static String commonEntityFilterRes = "com.kingdee.eas.base.commonquery.client.CommonEntityFilter";
	private ListUI listUI = null;
	private KDTable tblMain = null;
	
	private KDPanel pnlFastQuery = new KDPanel();
	private KDLabelContainer contQueryAt = null;
	private KDLabelContainer contQueryValue = null;
	private KDComboBox cboFields = null;
	private KDTextField txtValue = null;
	private KDDatePicker pkDateValue = null;
	private KDWorkButton btnQuery = null;
	private KDLabelContainer contFilterAt = null;
	private KDComboBox cboFilterInfo = null;
	private boolean isNoFilter = false;
	
	public static String CURR_USER = "curr_user";
	public static String ALL = "all";
	public static String RESP_PERSON = "respPerson.id";
	public static String CREATOR = "creator.id";
	
	public boolean hasFilter = true;	//是否有过滤条件
	
	public UIFastQueryUtil(ListUI listUI,KDTable tblMain) {
		this.listUI = listUI;
		this.tblMain = tblMain;
		jbInit();
		initUIContentLayout();
		initAction();

		listUI.getUIContext().put("UIFastQueryUtil", this);
	}
	
	public UIFastQueryUtil(ListUI listUI,KDTable tblMain,boolean hasFilter) {
		this(listUI, tblMain);
		this.hasFilter = hasFilter;
		setFilterVisible();
	}

	private void setFilterVisible() {
		contFilterAt.setVisible(hasFilter);
		if(!hasFilter){

			int x = 10;
			int y = 10;

			contQueryAt.setBounds(new Rectangle(x, y, 400, 19));
			pnlFastQuery.add(contQueryAt, new KDLayout.Constraints(x, y, 290, 19, KDLayout.Constraints.ANCHOR_TOP
					| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
	        
			contQueryValue.setBounds(new Rectangle(x + 300, 10, 250, 19));
			pnlFastQuery.add(contQueryValue, new KDLayout.Constraints(x + 300, 10, 260, 19, KDLayout.Constraints.ANCHOR_TOP
					| KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

			btnQuery.setBounds(new Rectangle(x + 570, 10, 100, 19));
			pnlFastQuery.add(btnQuery, new KDLayout.Constraints(x + 610, 10, 100, 19, KDLayout.Constraints.ANCHOR_TOP
					| KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
	       
		}
	}


	ActionListener cboFilterAction = null;
	private void initAction() {
		
		cboFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cboFields_actionPerformed(e);
				} catch (Exception e1) {
					listUI.handUIException(e1);
				}
			}
		});

		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnQuery_actionPerformed(e);
				} catch (Exception e1) {
					listUI.handUIException(e1);
				}
			}
		});
		
		
		cboFilterAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cboFilterInfo_actionPerformed(e);
				} catch (Exception e1) {
					listUI.handUIException(e1);
				}
			}
		};
		cboFilterInfo.addActionListener(cboFilterAction);

	}


	protected void cboFields_actionPerformed(ActionEvent e) {


		// 筛选过滤
		Object selectObject = cboFields.getSelectedItem();

		if (selectObject instanceof ColumnFields) {
			ColumnFields selectValue = (ColumnFields) selectObject;

			QueryInfo queryInfo = listUI.getQueryInfo(listUI.getMainQueryPK());
			DataObject dataObject = getRefMetaData(queryInfo, selectValue.getKey());
			int colMaxLength = 80;
			// dataObject.getName();
			if (dataObject != null) // 需要设置编辑器
			{
				setDataObjectCompareValue(dataObject, queryInfo, selectValue.getKey());
			} else {
				KDTextField field = new KDTextField();
				contQueryValue.setBoundEditor(field);
			}
		}
	}

	private void setDataObjectCompareValue(DataObject dataObject, QueryInfo queryInfo, String value) {



		FocusAdapter focusAdapter = new FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent e) {
				try {
					txtNumber_focusLost(e);
				} catch (Exception exc) {
					listUI.handUIExceptionAndAbort(exc);
				}
			}
		};

		String type = dataObject.getName();
		if (CommonFilterUtil.isEnumType(type)) {
			String enumClassName = dataObject.getValue();
			Object[] em = EnumUtils.getEnumList(enumClassName).toArray();
			KDComboBox kdComboBox = new KDComboBox(em);
			kdComboBox.setMaximumRowCount(10);
			kdComboBox.addFocusListener(focusAdapter);
			contQueryValue.setBoundEditor(kdComboBox);
		} else if (CommonFilterUtil.isBooleanType(type)) {
			String enumClassName = dataObject.getValue();
			Object[] em = EnumUtils.getEnumList(enumClassName).toArray();
			KDComboBox kdComboBox = new KDComboBox(em);
			kdComboBox.setMaximumRowCount(10);
			kdComboBox.addFocusListener(focusAdapter);
			contQueryValue.setBoundEditor(kdComboBox);
		} else if (CommonFilterUtil.isDateType(type)) {
			KDDatePicker datePicker = new KDDatePicker();
			datePicker.setValue(new Date());
			datePicker.addFocusListener(focusAdapter);
			contQueryValue.setBoundEditor(datePicker);
		} else if (CommonFilterUtil.isIntType(type)) {
			KDFormattedTextField txtValue = new KDFormattedTextField();
			txtValue.setDataType(txtValue.BIGDECIMAL_TYPE);
			txtValue.setPrecision(2);
			txtValue.setSupportedEmpty(true);
			txtValue.setHorizontalAlignment(KDFormattedTextField.RIGHT);
			txtValue.addFocusListener(focusAdapter);
			contQueryValue.setBoundEditor(txtValue);
		} else if (CommonFilterUtil.isF7Type(type)) {

			KDPromptBox f7 = getF7(value, queryInfo, dataObject.getValue(), null);

			((KDBizPromptBox) f7).setEnabledMultiSelection(true);
			ObjectValueRender avr = new ObjectValueRender();
			avr.setFormat(new BizDataFormat(((KDBizPromptBox) f7).getDisplayFormatter().toString()));
			f7.addFocusListener(focusAdapter);
			contQueryValue.setBoundEditor(f7);
		} else {
			KDTextField txtValue = new KDTextField();
			txtValue.addFocusListener(focusAdapter);
			contQueryValue.setBoundEditor(txtValue);
		}
	}

	protected void txtNumber_focusLost(FocusEvent e) throws Exception {

		btnQuery_actionPerformed(null);

	}


	/**
	 * 获得 F7 对象
	 * 
	 * @param rowIndex
	 * @return 返回空代表非F7控件
	 */
	protected static KDPromptBox getF7(String f7ConditonValue, QueryInfo mainQueryInfo, String entityName, IPromptBoxFactory promptBoxFactory) {
		PropertyUnitCollection propertyUnitCollection = mainQueryInfo.getUnits();
		int size = propertyUnitCollection.size();
		for (int i = 0; i < size; i++) {
			if (propertyUnitCollection.get(i) instanceof QueryFieldInfo) {
				QueryFieldInfo queryFieldInfo = (QueryFieldInfo) propertyUnitCollection.get(i);
				if (f7ConditonValue.equalsIgnoreCase(queryFieldInfo.getName())) {
					String defaultF7 = Util.getDefaultF7Query(queryFieldInfo);
					// query属俧的扩展属俧中定义了defaultF7
					if (defaultF7 != null && !defaultF7.trim().equalsIgnoreCase("")) {
						return createF7(defaultF7, mainQueryInfo, queryFieldInfo.getName(), promptBoxFactory);
					} else {
						defaultF7 = Util.getDefaultF7Query(new MetaDataPK(entityName));
						String defaultF7UI = Util.getDefaultF7UI(queryFieldInfo);
						if (defaultF7 == null || defaultF7.trim().equalsIgnoreCase("")) {
							if (defaultF7UI == null || defaultF7UI.trim().equalsIgnoreCase("")) {
								MsgBox.showError(EASResource.getString(commonQueryFilterRes, "getF7Warning") + mainQueryInfo.getFullName()
										+ EASResource.getString(commonQueryFilterRes, "getF7Warning1") + queryFieldInfo.getName()
										+ EASResource.getString(commonQueryFilterRes, "getF7Warning2") + entityName
										+ EASResource.getString(commonQueryFilterRes, "getF7Warning3"));
							} else {
								return createF7(defaultF7, mainQueryInfo, queryFieldInfo.getName(), promptBoxFactory);
							}
						} else {
							return createF7(defaultF7, mainQueryInfo, queryFieldInfo.getName(), promptBoxFactory);
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 基于Query的翚用查询使用 创建F7控件
	 * 
	 * @param queryName
	 * @param promptBoxFactory
	 * @param promptDialogFactory
	 * @return
	 */
	private static KDPromptBox createF7(String f7QueryName, QueryInfo mainQuery, String queryFiledName, IPromptBoxFactory promptBoxFactory) {
		if (promptBoxFactory != null) {
			KDPromptBox promptBox = promptBoxFactory.create(f7QueryName);

			if (promptBox == null) {
				return promptBoxFactory.create(f7QueryName, mainQuery, queryFiledName);
			} else {
				return promptBox;
			}
		} else {
			return new DefaultPromptBoxFactory().create(f7QueryName, mainQuery, queryFiledName);
		}
	}

	protected static DataObject getRefMetaData(QueryInfo queryInfo, String queryFieldName) {
		com.kingdee.eas.base.commonquery.DataObject do1 = QueryUtils.getRefMetaData(null, queryInfo, queryFieldName);
		return toClientDataObject(do1);
	}

	// 重构时增加
	private static DataObject toClientDataObject(com.kingdee.eas.base.commonquery.DataObject do1) {
		if (do1 == null)
			return null;
		DataObject dataObject = new DataObject();
		dataObject.setColLength(do1.getColLength());
		dataObject.setExtendValue(do1.getExtendValue());
		dataObject.setName(do1.getName());
		dataObject.setValue(do1.getValue());
		return dataObject;
	}

	/**
	 * 描述：快速筛选
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void cboFilterInfo_actionPerformed(ActionEvent e) throws Exception {
		tblMain.removeRows();
	}


	public FilterInfo getFilterInfo() {

		// 快速查询过滤
		FilterInfo filter1 = new FilterInfo();
		if (isNoFilter) {
			return filter1;
		}

		Object selectObject = cboFields.getSelectedItem();
		if (selectObject instanceof ColumnFields) {
			// 筛选过滤
			ColumnFields selectValue = (ColumnFields) selectObject;

			JComponent comp = contQueryValue.getBoundEditor();
			if (comp instanceof KDTextField) {
				KDTextField txtField = (KDTextField) comp;
				if (!StringUtils.isEmpty(txtField.getText())) {
					filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), "%" + txtField.getText() + "%", CompareType.LIKE));
				}
			} else if (comp instanceof KDFormattedTextField) {
				KDFormattedTextField txtField = (KDFormattedTextField) comp;
				if (!StringUtils.isEmpty(txtField.getText())) {
					filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), FDCHelper.toBigDecimal(txtField.getText().replaceAll(",", ""))));
				}
			} else if (comp instanceof KDDatePicker) {
				KDDatePicker pkDate = (KDDatePicker) comp;
				if (pkDate.getValue() != null) {
					Date selectDate = (Date) pkDate.getValue();
					filter1.getFilterItems().add(
							new FilterItemInfo(selectValue.getKey(), FDCDateHelper.getDayBegin(selectDate), CompareType.GREATER_EQUALS));
					filter1.getFilterItems().add(
							new FilterItemInfo(selectValue.getKey(), FDCDateHelper.getDayEnd(selectDate), CompareType.LESS_EQUALS));
				}
			} else if (comp instanceof KDComboBox) {
				KDComboBox cboItem = (KDComboBox) comp;
				if (cboItem.getSelectedItem() != null) {
					Object selectDate = cboItem.getSelectedItem();
					if (selectDate instanceof StringEnum) {
						StringEnum enumItem = (StringEnum) selectDate;
						filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), enumItem.getValue()));
					} else if (selectDate instanceof IntEnum) {
						IntEnum enumItem = (IntEnum) selectDate;
						filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), new Integer(enumItem.getValue())));
					}
				}
			} else if (comp instanceof KDComboBox) {
				KDComboBox cboItem = (KDComboBox) comp;
				if (cboItem.getSelectedItem() != null) {
					Object selectDate = cboItem.getSelectedItem();
					if (selectDate instanceof StringEnum) {
						StringEnum enumItem = (StringEnum) selectDate;
						filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), enumItem.getValue()));
					} else if (selectDate instanceof IntEnum) {
						IntEnum enumItem = (IntEnum) selectDate;
						filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), new Integer(enumItem.getValue())));
					}
				}
			} else if (comp instanceof KDBizPromptBox) {
				KDBizPromptBox prmtBox = (KDBizPromptBox) comp;
				if (prmtBox.getText() != null) {
					filter1.getFilterItems().add(new FilterItemInfo(selectValue.getKey(), prmtBox.getText()));
				}
			}
		}
		FilterInfo filter2 = new FilterInfo();
		// 筛选过滤
		selectObject = cboFilterInfo.getSelectedItem();
		if(selectObject instanceof  ColumnFields){
			ColumnFields selectValue = (ColumnFields) selectObject;
			
			String userid = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			String personid = "";
			UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
			if (userInfo.getPerson() != null) {
				personid = SysContext.getSysContext().getCurrentUserInfo().getPerson().getId().toString();
			}
			
			if (CURR_USER.equals(selectValue.getKey())) {
				filter2.getFilterItems().add(new FilterItemInfo(CREATOR, userid));
				if (personid.length() != 0) {
					filter2.getFilterItems().add(new FilterItemInfo(RESP_PERSON, personid));
					filter2.setMaskString("(#0 or #1)");
				}
			} else if (selectValue.getKeys() != null) {
				String[] filters = selectValue.getKeys();
				String maskStr = "";
				int m=0;
				for (int i = 0; i < filters.length; i++) {
					String filterStr = filters[i];
					if (filterStr.startsWith("user:")) {
						String[] users = filterStr.split(":")[1].split(",");
						for (int j = 0; j < users.length; j++) {
							filter2.getFilterItems().add(new FilterItemInfo(users[j], userid));
							if (maskStr.length() == 0) {
								maskStr += "(#" + m;
							} else {
								maskStr += " or #" + m;
							}
							m++;
						}
					} else if (filterStr.startsWith("person:")) {

						if (personid.length() == 0) {
							continue;
						}

						String[] persons = filterStr.split(":")[1].split(",");
						for (int j = 0; j < persons.length; j++) {
							filter2.getFilterItems().add(new FilterItemInfo(persons[j], personid));
							if (maskStr.length() == 0) {
								maskStr += "(#" + m;
							} else {
								maskStr += " or #" + m;
							}
							m++;
						}
					} 
				}

				if (maskStr.length() > 0) {
					filter2.setMaskString(maskStr + ")");
				}
			}
		}
		try {
			filter1.mergeFilter(filter2, "and");
		} catch (BOSException e) {
			listUI.handUIException(e);
		}
		return filter1;
	}


	public void mergerFilter(EntityViewInfo ev) {
		FilterInfo filter = getFilterInfo();
		EntityViewInfo viewInfoClone = ev;
		FilterInfo f = viewInfoClone.getFilter();
		if (f == null) {
			viewInfoClone.setFilter(filter);
		} else {
			try {
				filter.mergeFilter(f, "and");
				viewInfoClone.setFilter(filter);
			} catch (BOSException e) {
				listUI.handUIException(e);
			}
		}
	}

	/**
	 * 描述：快速查找
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void btnQuery_actionPerformed(ActionEvent e) throws Exception {
		
		tblMain.removeRows();
		
	}



	public void initUIContentLayout() {
		
		int x = 10;
		int y = 10;
		pnlFastQuery.setBounds(new Rectangle(0, 30, 733, 40));
		pnlFastQuery.setLayout(new KDLayout());
		pnlFastQuery.putClientProperty("OriginalBounds", new Rectangle(0, 30, 733, 40));
		pnlFastQuery.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
		contQueryAt.setBounds(new Rectangle(x, y, 400, 19));
		pnlFastQuery.add(contQueryAt, new KDLayout.Constraints(x, y, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        
		// contQueryAt.setBounds(new Rectangle(x, y, 400, 19));
		// pnlFastQuery.add(contQueryAt, new KDLayout.Constraints(x, y, 200, 19,
		// KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT
		// | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

		contQueryValue.setBounds(new Rectangle(x + 210, 10, 200, 19));
		pnlFastQuery.add(contQueryValue, new KDLayout.Constraints(x + 210, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP
				| KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

		// pkDateValue.setBounds(new Rectangle(x + 210, 10, 200, 19));
		// pnlFastQuery.add(pkDateValue, new KDLayout.Constraints(x + 210, 10,
		// 200, 19, KDLayout.Constraints.ANCHOR_TOP
		// | KDLayout.Constraints.ANCHOR_LEFT_SCALE |
		// KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		// pkDateValue.setVisible(false);
		//
		// txtValue.setBounds(new Rectangle(x+210, 10, 200, 19));
		// pnlFastQuery.add(txtValue, new KDLayout.Constraints(x+210, 10, 200,
		// 19, KDLayout.Constraints.ANCHOR_TOP |
		// KDLayout.Constraints.ANCHOR_LEFT_SCALE |
		// KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		//
        btnQuery.setBounds(new Rectangle(x+420, 10, 50, 19));
        pnlFastQuery.add(btnQuery, new KDLayout.Constraints(x+420, 10, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        
        contFilterAt.setBounds(new Rectangle(pnlFastQuery.getWidth()-210, 10, 200, 19));
        pnlFastQuery.add(contFilterAt, new KDLayout.Constraints(pnlFastQuery.getWidth()-210, 10, 200, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
		
        contQueryAt.setBoundEditor(cboFields);
		contQueryValue.setBoundEditor(txtValue);
        contFilterAt.setBoundEditor(cboFilterInfo);

		// KDPanel pnlQueryMain = new KDPanel();
		// pnlQueryMain.setBounds(tblMain.getParent().getParent().getBounds());
		// pnlQueryMain.setLayout(new KDLayout());

		if (tblMain.getParent().getParent() == null) {
			Container contPanel = tblMain.getParent();
			// 适用于tblMain外没有面板，直接是UI，如合约规化F7
			JPanel pnlQueryMain = (JPanel) contPanel;
			pnlQueryMain.setBounds(new Rectangle(0, 0, 700, 480));
			pnlQueryMain.setLayout(new KDLayout());
			pnlQueryMain.putClientProperty("OriginalBounds", new Rectangle(0, 0, 700, 480));
			pnlQueryMain.add(pnlFastQuery, new KDLayout.Constraints(0, 0, 700, 40, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT
					| KDLayout.Constraints.ANCHOR_RIGHT));
			pnlQueryMain.add(tblMain, new KDLayout.Constraints(0, 40, 700, 440, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM
					| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

		} else {
			// 适用于tblMain外有嵌套面板，获取面板外的外面再进行设置，如合同序事薄基类
			Container contPanel = tblMain.getParent().getParent().getParent();
			if (contPanel == null) {
				contPanel = tblMain.getParent();
				if (contPanel instanceof KDSplitPane) {
					KDSplitPane spMain = (KDSplitPane) contPanel;
					KDPanel pnlQueryMain = new KDPanel();
					spMain.add(pnlQueryMain, "right");

					pnlQueryMain.setBounds(new Rectangle(0, 0, 733, 578));
					pnlQueryMain.setLayout(new KDLayout());
					pnlQueryMain.putClientProperty("OriginalBounds", new Rectangle(0, 0, 733, 578));
					pnlQueryMain.add(pnlFastQuery, new KDLayout.Constraints(0, 0, 733, 40, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
					pnlQueryMain.add(tblMain, new KDLayout.Constraints(0, 40, 733, 538, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));

				}// 适用于tblMain外有嵌套面板KDPanel，获取面板外的外面再进行设置
				else if (contPanel instanceof KDPanel) {
					KDPanel kdSplitPane = (KDPanel) contPanel;
					KDPanel pnlQueryMain = new KDPanel();
					Rectangle rectangle = tblMain.getParent().getBounds();
					pnlQueryMain.setBounds(rectangle);
					pnlQueryMain.setLayout(new KDLayout());
					pnlQueryMain.putClientProperty("OriginalBounds", new Rectangle(0, rectangle.y, rectangle.width, rectangle.height));
					kdSplitPane.add(pnlQueryMain, new KDLayout.Constraints(0, rectangle.y, rectangle.width, rectangle.height,
							KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT
									| KDLayout.Constraints.ANCHOR_RIGHT));

					pnlQueryMain.add(pnlFastQuery, new KDLayout.Constraints(0, 0, rectangle.width - 1, 40, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
					pnlQueryMain.add(tblMain, new KDLayout.Constraints(0, 40, rectangle.width - 1, rectangle.height - 50,
							KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT
									| KDLayout.Constraints.ANCHOR_RIGHT));
				}
			} else if (contPanel instanceof KDSplitPane) {
				KDSplitPane kdSplitPane = (KDSplitPane) contPanel;
				KDPanel pnlQueryMain = new KDPanel();
				pnlQueryMain.setBounds(tblMain.getParent().getParent().getBounds());
				pnlQueryMain.setLayout(new KDLayout());
				kdSplitPane.add(pnlQueryMain, "top");

				pnlQueryMain.setBounds(new Rectangle(0, 0, 733, 578));
				pnlQueryMain.setLayout(new KDLayout());
				pnlQueryMain.putClientProperty("OriginalBounds", new Rectangle(0, 0, 733, 578));
				pnlQueryMain.add(pnlFastQuery, new KDLayout.Constraints(0, 0, 733, 40, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
				pnlQueryMain.add(tblMain.getParent().getParent(), new KDLayout.Constraints(0, 40, 733, 538, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
			}
			// 适用于tblMain外有嵌套面板KDPanel，获取面板外的外面再进行设置
			else if (contPanel instanceof KDPanel) {
				KDPanel kdSplitPane = (KDPanel) contPanel;
				KDPanel pnlQueryMain = new KDPanel();
				Rectangle rectangle = tblMain.getParent().getParent().getBounds();
				pnlQueryMain.setBounds(rectangle);
				pnlQueryMain.setLayout(new KDLayout());
				pnlQueryMain.putClientProperty("OriginalBounds", new Rectangle(0, rectangle.y, rectangle.width, rectangle.height));
				kdSplitPane.add(pnlQueryMain, new KDLayout.Constraints(0, rectangle.y, rectangle.width, rectangle.height,
						KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT
								| KDLayout.Constraints.ANCHOR_RIGHT));

				pnlQueryMain.add(pnlFastQuery, new KDLayout.Constraints(0, 0, rectangle.width - 1, 40, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
				pnlQueryMain.add(tblMain.getParent().getParent(), new KDLayout.Constraints(0, 40, rectangle.width - 1, rectangle.height - 40,
						KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT
								| KDLayout.Constraints.ANCHOR_RIGHT));
			}
		}
	}




	private void jbInit() {
		
		contQueryAt = new KDLabelContainer();
		contQueryAt.setName("contQueryAt");
		contQueryAt.setBoundLabelText("快速查找按");
		contQueryAt.setBoundLabelLength(65);
		contQueryAt.setBoundLabelAlignment(7);		
		contQueryAt.setBoundLabelUnderline(true);

		contQueryValue = new KDLabelContainer();
		contQueryValue.setName("contQueryValue");
		contQueryValue.setBoundLabelText("");
		contQueryValue.setBoundLabelLength(0);
		contQueryValue.setBoundLabelUnderline(true);
		
		cboFields = new KDComboBox();
		cboFields.setName("cboFields");
		
		pkDateValue = new KDDatePicker();
		pkDateValue.setName("pkDateValue");

		txtValue = new KDTextField();
		txtValue.setName("txtValue");
		
		btnQuery = new KDWorkButton();
		btnQuery.setName("btnQuery");
		btnQuery.setText("查找");
		
		
		contFilterAt = new KDLabelContainer();
		contFilterAt.setName("contFilterAt");
		contFilterAt.setBoundLabelText("筛选");
		contFilterAt.setBoundLabelLength(50);
		contFilterAt.setBoundLabelUnderline(true);
		
		cboFilterInfo = new KDComboBox();
		cboFilterInfo.setName("cboFilterInfo");

	}


	public KDPanel getFastQuery(){
		return pnlFastQuery;
	}

	//快速查询设置
	public void setTableFields(String key,String value){
		ColumnFields fields = new ColumnFields();
		fields.setKey(key);
		fields.setName(value);
		cboFields.addItem(fields);
	}


	public void clearQuery() {
		cboFields.removeAllItems();
		cboFilterInfo.removeAllItems();
	}

	public void clearFilterInfo() {
		cboFilterInfo.removeAllItems();
	}

	//快速过滤设置
	public void setFilterInfo(String key,String value){
		ColumnFields fields = new ColumnFields();
		fields.setKey(key);
		fields.setName(value);
		
		cboFilterInfo.removeActionListener(cboFilterAction);
		cboFilterInfo.addItem(fields);
		cboFilterInfo.addActionListener(cboFilterAction);
	}

	// 快速过滤设置
	public void setFilterInfo(String keys[], String value) {
		ColumnFields fields = new ColumnFields();
		fields.setKeys(keys);
		fields.setName(value);
		cboFilterInfo.addItem(fields);
	}


	public KDTable getTblMain() {
		return tblMain;
	}

	public void setTblMain(KDTable tblMain) {
		this.tblMain = tblMain;
	}

	public KDComboBox getCboFilterInfo() {
		return cboFilterInfo;
	}

	public boolean isNoFilter() {
		return isNoFilter;
	}

	public void setNoFilter(boolean isNoFilter) {
		this.isNoFilter = isNoFilter;
	}
	
}

class ColumnFields{
	
	private String name;
	private String key;
	private String keys[];
	private int dataType;
	
	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String toString() {
		return name;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

}


