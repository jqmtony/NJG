package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeCellRenderer;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.BodyUI;
import com.kingdee.eas.base.uiframe.client.NewWinMainUI;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcSqlUtil;
import com.kingdee.eas.fdc.contract.programming.IPTECost;
import com.kingdee.eas.fdc.contract.programming.IPTEEnonomy;
import com.kingdee.eas.fdc.contract.programming.IProgrammingTemplate;
import com.kingdee.eas.fdc.contract.programming.IProgrammingTemplateEntire;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostFactory;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyFactory;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ProgrammingTemplateUI extends AbstractProgrammingTemplateUI {
//	private static final String IS_BTN_EDIT = "isBtnEdit";
	
	private static final Logger logger = CoreUIObject.getLogger(ProgrammingTemplateEditUI.class);

	private CreateTableRow create = new CreateTableRow(this.dataBinder);
	private static final String TEMPLATEBASE = "合约框架模板列表";
	private final String LONGNUMBER = "longNumber";
	private final String HEADNUMBER = "headNumber";
	private final String ATTACHMENT = "attachment";
	private final String REMARK = "remark";
	private boolean isCopy = false;

	// 是否重新加载成本科目页签
	private boolean isRewLoadCostAccountTab = true;

	private TreePath editTreePath;

	private TreePath editTreePath2;
	
	/**编辑按钮有2个，点击菜单栏中的BtnEdit时为true*/
	private boolean isBtnEditClicked=false;
	protected KDWorkButton btnAddnewLine;
	protected KDWorkButton btnInsertLine;
	protected KDWorkButton btnRemoveLines;
	protected KDWorkButton btnDetails;

	private TreeSelectionListener[] treeSelectionListeners;
	private ChangeListener[] paneChangeListeners;

	/**
	 * output class constructor
	 */
	public ProgrammingTemplateUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
		
		isRewLoadCostAccountTab = true;

		List rows = this.kdtEntires.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntires.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		this.kdtEntires.setRefresh(true);

		kdtEntires.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		kdtEntires.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		if (!OprtState.ADDNEW.equals(oprtState)) {
			createTree();
			setNameDisplay();
			handleOldData();
//		}
		try {
			loadCostAccountOnLoad();
		} catch (BOSException e) {
			logger.error("loadCostAccount", e);
			handUIExceptionAndAbort(e);
		} catch (EASBizException e) {
			logger.error("loadCostAccount", e);
			handUIExceptionAndAbort(e);
		}
		if (!OprtState.VIEW.equals(getOprtState())) {
			setCellEditorForTable();
		}
		cellAttachment();

		pnlContract.repaint();
	}

	public void onLoad() throws Exception {
		kdtEntires.checkParsed();
		kdtCosetEntries.checkParsed();

		showOnlyOneTab();
		super.onLoad();
		initTable();
		// Map uiContext = this.getUIContext();
		// if(Boolean.TRUE.equals((Boolean)uiContext.get("Copy"))){
		// actionCopy_actionPerformed(null);
		// createTree();
		// isCopy = true;
		// loadCostAccountOnLoad();
		// }
		setSmallButton();
		costAccountF7();
		setAttachmentRenderer();
		setMouseClick();
		buildProgrammingTemplateTree();

		resetTreeMainCellEditor();
		setOprtState(OprtState.VIEW);
		setSmallBtnEnable();
		switchButton();
		setButtonEnabledByCurrentOrgUnit();

		//加载后
		afterOnLoad();
	}

	/**
	 * 描述：加载后
	 * 
	 * @throws Exception
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-20
	 */
	protected void afterOnLoad() throws Exception {
		// 初始化监听器
		treeSelectionListeners = treeMain.getTreeSelectionListeners();
		paneChangeListeners = paneBIZLayerControl9.getChangeListeners();
	}

	/**
	 * 描述：根据当前组织设置按钮是否可以用
	 * @Author：jian_cao
	 * @CreateTime：2012-9-24
	 */
	private void setButtonEnabledByCurrentOrgUnit() {
		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			
			actionEditTemplate.setEnabled(false);
			actionAddTemplate.setEnabled(false);
			actionDelTemplate.setEnabled(false);
			
			actionCopy.setEnabled(false);
			actionEdit.setEnabled(false);
			
			btnAddTemplate.setEnabled(false);
			btnEditTemplate.setEnabled(false);
			btnDelTemplate.setEnabled(false);
			btnSaveTemplate.setEnabled(false);
			
			menuItemEdit.setEnabled(false);
		}
	}

	/**
	 * 描述：设置附件名称
	 * 
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-9
	 */
	private void cellAttachment() {
		if (FDCTableHelper.isEmpty(kdtEntires)) {
			return;
		}

		List idList = FDCTableHelper.getCellStringValueList(kdtEntires, "id");
		Map map = getAllAttachmentName(idList);

		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			Object idObj = kdtEntires.getCell(i, "id").getValue();
			if (idObj != null) {
				String id = idObj.toString();
				StringBuffer allAttachmentName = (StringBuffer) map.get(id);
				if (!FDCHelper.isEmpty(allAttachmentName)) {
					kdtEntires.getCell(i, "attachment").setValue(allAttachmentName);
				} else {
					kdtEntires.getCell(i, "attachment").setValue(null);
				}
			}
		}
	}

	protected void doAfterSubmit(IObjectPK pk) throws Exception {

	}

	/**
	 * 获取合约框架所有附件名称字符串，名称与乐称以";"相隔
	 * 
	 * @param boIDs
	 * @return
	 */
	private Map getAllAttachmentName(List boIDs) {
		Map idAttachmentNameMap = new LinkedHashMap();

		if (FdcCollectionUtil.isEmpty(boIDs)) {
			return idAttachmentNameMap;
		}

		SqlParams sp = new SqlParams();
		String columnName = "boAt.FBoID";
		Object[] columnValues = boIDs.toArray(new Object[0]);
		String blockMergeLogic = "AND";
		String sqlFrag = FdcSqlUtil.generateInFrag(sp, columnName, columnValues, blockMergeLogic);

		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" SELECT DISTINCT boAt.FBoID, at.FID, at.FSimpleName, at.FName_l2, at.FCreateTime FROM T_BAS_Attachment at ");
		fdcBuilder.appendSql(" INNER JOIN T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" WHERE 1 = 1 ");
		fdcBuilder.appendSql(sqlFrag);
		fdcBuilder.appendSql(" ORDER BY boAt.FBoID, at.FCreateTime ");

		fdcBuilder.addParams(sp.getParams());

		logger.info("sql:" + fdcBuilder.getSql().toString());
		logger.info("paramaters:" + fdcBuilder.getParamaters());

		StringBuffer sb = null;
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				String boID = rs.getString("FBoID");
				sb = (StringBuffer) idAttachmentNameMap.get(boID);
				if (null == sb) {
					sb = new StringBuffer();
					idAttachmentNameMap.put(boID, sb);
				}

				if (FDCHelper.isEmpty(rs.getString("FSimpleName"))) {
					sb.append(rs.getString("FName_l2") + ",");
				} else {
					if (rs.isLast()) {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
					} else {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handUIExceptionAndAbort(e);
		}

		return idAttachmentNameMap;
	}

	private void setMouseClick() {
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntires)) {
					setKDTableLostFocus();
				}
			}
		});

		Component[] com = this.getComponents();
		for (int i = 0; i < com.length; i++) {
			if (!com[i].equals(kdtEntires)) {
				com[i].addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent arg0) {
					}

					public void mouseEntered(MouseEvent arg0) {
					}

					public void mouseExited(MouseEvent arg0) {
					}

					public void mousePressed(MouseEvent arg0) {
					}

					public void mouseReleased(MouseEvent arg0) {
						if (!arg0.getComponent().equals(kdtEntires)) {
							setKDTableLostFocus();
						}
					}
				});
			}
		}

		this.txtName.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntires)) {
					setKDTableLostFocus();
				}
			}
		});

		this.txtRemark.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				if (!arg0.getComponent().equals(kdtEntires)) {
					setKDTableLostFocus();
				}
			}
		});
	}

	private void setKDTableLostFocus() {
		kdtEntires.getEditManager().stopEditing();
		kdtEntires.getSelectManager().remove();
		kdtEntires.getSelectManager().setActiveRowIndex(-1);
		btnInsertLine.setEnabled(false);
		btnRemoveLines.setEnabled(false);
		btnDetails.setEnabled(false);
	}

	/**
	 * 初始化分录
	 */
	private void initTable() {
		// 合框框架模版备注限制为255字符
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(255);
		kdtEntires.getColumn(REMARK).setEditor(cellEditor);

		// kdtEntires.getColumn("sortNumber").getStyleAttributes().setHided(false);
		// kdtEntires.getColumn(HEADNUMBER).getStyleAttributes().setHided(false);
		// kdtEntires.getColumn("longName").getStyleAttributes().setHided(false);
	}

	/**
	 * 在面签中添加新增、插入、删除、详细信息按钮
	 */
	private void setSmallButton() {
		btnRefresh.setEnabled(true);
		menuItemRefresh.setEnabled(true);
		btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));

		btnAddnewLine = new KDWorkButton();
		btnInsertLine = new KDWorkButton();
		btnRemoveLines = new KDWorkButton();
		btnDetails = new KDWorkButton();

		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDetails_actionPerformed(e);
				} catch (Exception e1) {
					logger.error("detials", e1);
					handUIExceptionAndAbort(e1);
				}
			}
		});

		btnAddnewLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});

		btnInsertLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLine_actionPerformed(arg0);
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}

			}

		});

		btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});

		setButtonStyle(btnAddnewLine, "新增行", "imgTbtn_addline");
		setButtonStyle(btnRemoveLines, "删除行", "imgTbtn_deleteline");
		setButtonStyle(btnInsertLine, "插入行", "imgTbtn_insert");
		setButtonStyle(btnDetails, "详细信息", "imgTbtn_particular");
	}

	private void setButtonStyle(KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		pnlContract.addButton(button);
	}

	private void setButtionEnable(boolean isEnable) {
		btnAddnewLine.setEnabled(isEnable);
		btnInsertLine.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
	}

	private void setSmallBtnEnable() {
		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
			if (kdtEntires.getSelectManager().getActiveRowIndex() < 0 || kdtEntires.getRowCount() <= 0) {
				btnDetails.setEnabled(false);
			} else {
				btnDetails.setEnabled(true);
			}
		} else {
			
			btnAddnewLine.setEnabled(true);
			if (kdtEntires.getSelectManager().getActiveRowIndex() < 0 || kdtEntires.getRowCount() <= 0) {
				btnInsertLine.setEnabled(false);
				btnRemoveLines.setEnabled(false);
				btnDetails.setEnabled(false);
			} else {
				btnInsertLine.setEnabled(true);
				btnRemoveLines.setEnabled(true);
				btnDetails.setEnabled(true);
			}
		}

		String cuID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if (!cuID.equals(OrgConstants.DEF_CU_ID)) {
			actionEditTemplate.setEnabled(false);
			actionAddTemplate.setEnabled(false);
			actionDelTemplate.setEnabled(false);
			actionSaveTemplate.setEnabled(false);
			menuItemEdit.setEnabled(false);
			btnAddTemplate.setEnabled(false);
			btnEditTemplate.setEnabled(false);
			btnDelTemplate.setEnabled(false);
			btnSaveTemplate.setEnabled(false);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		kdtEntires.addKDTMouseListener(new KDTSortManager(kdtEntires));
		kdtEntires.getSortMange().setSortAuto(false);
	}

	private void setCellEditorForTable() {
		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			KDTextField txtLongNumber = new KDTextField();
			String txt = "";
			Object longNumber = kdtEntires.getCell(i, LONGNUMBER).getValue();
			Object subNumber = kdtEntires.getCell(i, HEADNUMBER).getValue();
			int level = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
			if (longNumber == null || longNumber.toString().trim().length() == 0) {
				if (subNumber != null && subNumber.toString().trim().length() > 0) {
					txt = subNumber.toString().trim() + ".";
				}
			} else {
				txt = longNumber.toString().trim();
			}
			if (level > 1) {
				LimitedTextDocument document = new LimitedTextDocument(subNumber.toString().trim() + ".", true);
				txtLongNumber.setMaxLength(80);
				txtLongNumber.setDocument(document);
				txtLongNumber.setText(txt);
				document.setIsOnload(false);
			} else {
				LimitedTextDocument document = new LimitedTextDocument("");
				txtLongNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtLongNumber.setText(txt);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}

			KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
			kdtEntires.getCell(i, LONGNUMBER).setEditor(cellEditorNumber);
		}
	}

	/**
	 * 加载时生成树形
	 */
	private void createTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtEntires.getRowCount()];

		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			IRow row = kdtEntires.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}

		for (int i = 0; i < kdtEntires.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtEntires.getTreeColumn().setDepth(maxLevel);

		kdtEntires.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	/**
	 * 点击新增行按钮
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.kdtEntires.getSelectManager().getActiveRowIndex();
		int rowCount = kdtEntires.getRowCount();
		int row = -1;
		if (rowIndex < 0) {
			create.addLine(kdtEntires, 1);
			if (rowCount == 0)
				row = 0;
			else
				row = rowCount;
			String number = gainNumberByDate(null);
			kdtEntires.getCell(row, LONGNUMBER).setValue(number);
			kdtEntires.getCell(row, "number").setValue(number);
		} else {
			Object o = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			Object head = kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
			Object headlevel = kdtEntires.getCell(rowIndex, "level").getValue();
			Object name = getCellValue(kdtEntires, rowIndex, "name");
			Object longName = kdtEntires.getCell(rowIndex, "longName").getValue();
			ProgrammingTemplateEntireInfo headObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(rowIndex).getUserObject();
			if (o == null || o.toString().trim().length() == 0) {
				MsgBox.showInfo("分录第 " + (rowIndex + 1) + " 行，框架合约编码不能为空！");
				return;
			} else if ((o.toString().trim() + ".").length() >= 80) {
				MsgBox.showInfo("分录第 " + (rowIndex + 1) + " 行，框架合约编码超长\n请修改后再新增子级框架合约！");
				return;
			} else if (name == null || StringUtils.isEmpty(name.toString())) {
				MsgBox.showInfo("分录第 " + (rowIndex + 1) + " 行，框架合约名称不能为空！");
				return;
			} else if (head == null) {
				int level = new Integer(headlevel.toString()).intValue();
				if (level == 1) {
					row = getInsertRowIndex(o, rowIndex, rowCount);
					create.insertLine(kdtEntires, row, level + 1, headObject);
					kdtEntires.getCell(row, HEADNUMBER).setValue(o);
					if (name != null)
						kdtEntires.getCell(row, "longName").setValue(name.toString().trim() + ".");
					String number = gainNumberByDate((String) o);
					kdtEntires.getCell(row, LONGNUMBER).setValue(o + "." + number);
					kdtEntires.getCell(row, "number").setValue(number);
				}
			} else {
				String ln = o.toString();
				if (ln.length() == (head.toString().length() + 1)) {
					MsgBox.showInfo("分录第 " + (rowIndex + 1) + " 行，框架合约编码不能为空！");
					return;
				}
				row = getInsertRowIndex(o, rowIndex, rowCount);
				create.insertLine(kdtEntires, row, new Integer(headlevel.toString()).intValue() + 1, headObject);
				kdtEntires.getCell(row, HEADNUMBER).setValue(o);
				if (longName != null)
					kdtEntires.getCell(row, "longName").setValue(longName.toString().trim() + ".");
				String number = gainNumberByDate(head.toString());
				kdtEntires.getCell(row, LONGNUMBER).setValue(o + "." + number);
				kdtEntires.getCell(row, "number").setValue(number);
			}
		}
		// KDTextField txtLongNumber = new KDTextField();
		// LimitedTextDocument document = new LimitedTextDocument("");
		// txtLongNumber.setMaxLength(80);
		// txtLongNumber.setDocument(document);
		// KDTDefaultCellEditor cellEditorNumber = new
		// KDTDefaultCellEditor(txtLongNumber);
		// kdtEntires.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
		createTree();
		setSmallBtnEnable();
	}

	/**
	 * 点击插入行按钮
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		FDCTableHelper.checkSelectedAndAbort(this, kdtEntires);
		int rowIndex = this.kdtEntires.getSelectManager().getActiveRowIndex();
		Object o = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		Object name = getCellValue(kdtEntires, rowIndex, "name");
		if (o == null || o.toString().trim().length() == 0) {
			MsgBox.showInfo("分录第 " + (rowIndex + 1) + " 行，框架合约编码不能为空！");
			return;
		} else if (name == null || StringUtils.isEmpty(name.toString())) {
			MsgBox.showInfo("分录第 " + (rowIndex + 1) + " 行，框架合约名称不能为空！");
			return;
		}

		String headNumber = (String) kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
		Object level = kdtEntires.getCell(rowIndex, "level").getValue();
		ProgrammingTemplateEntireInfo headObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(rowIndex).getUserObject();
		create.insertLine(kdtEntires, rowIndex, new Integer(level.toString()).intValue(), headObject.getHead());
		kdtEntires.getCell(rowIndex, HEADNUMBER).setValue(headNumber);
		if (headObject.getHead() != null && headObject.getHead().getDisplayName() != null) {
			kdtEntires.getCell(rowIndex, "longName").setValue(headObject.getHead().getDisplayName().toString().trim() + ".");
		}
		// KDTextField txtLongNumber = new KDTextField();
		// LimitedTextDocument document = new LimitedTextDocument("");
		// txtLongNumber.setDocument(document);
		// KDTDefaultCellEditor cellEditorNumber = new
		// KDTDefaultCellEditor(txtLongNumber);
		String number = gainNumberByDate(headNumber);
		kdtEntires.getCell(rowIndex, "number").setValue(number);
		if (headNumber == null || headNumber.toString().length() == 0) {
			kdtEntires.getCell(rowIndex, LONGNUMBER).setValue(number);
		} else {
			kdtEntires.getCell(rowIndex, LONGNUMBER).setValue(headNumber + "." + number);
		}

		createTree();
	}

	/**
	 * 点击删除行按钮
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.kdtEntires.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		ArrayList list = new ArrayList();
		if (longNumber != null) {
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString(), rowIndex, list);
		}
		if (list.size() > 1) {
			Object name = kdtEntires.getCell(rowIndex, "name").getValue();
			if(name==null) name="";
			if (MsgBox.OK == MsgBox.showConfirm2New(null, "您当前删除的父节点”" + name.toString() + "”下还有其他的框架合约，确定要一起删除吗？")) {
				create.removeLine(kdtEntires, list);
			}
		} else {
			if (MsgBox.OK == MsgBox.showConfirm2New(null, "是否确认删除数据？")) {
				create.removeLine(kdtEntires, rowIndex);
			}
			createTree();

			setSmallBtnEnable();
		}
	}

	private void getSublevel(String longNumber, int rowIndex, ArrayList list) {
		int rowCount = kdtEntires.getRowCount();
		for (int i = rowIndex + 1; i < rowCount; i++) {
			Object headNumber = kdtEntires.getCell(i, HEADNUMBER).getValue();
			if (headNumber != null && headNumber.toString() != null) {
				if (headNumber.toString().startsWith(longNumber)) {
					list.add(new Integer(i));
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 点击详细信息按钮
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionDetails_actionPerformed(ActionEvent e) throws Exception {
		FDCTableHelper.checkSelectedAndAbort(this, kdtEntires);
		int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
		Object oldValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingTemplateEntireCollection pteCollection = getPTECollection();
		uiContext.put("isParent", new Boolean(isParent(rowIndex)));
		uiContext.put("pteCollection", pteCollection);
		ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		uiContext.put("rowObject", rowObject);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PTEEditUI.class.getName(), uiContext, null, oprtState);
		uiWindow.show();
		if (oprtState.equals(OprtState.VIEW))
			return;
		// 绑定数据到分录上
		dataBinder.loadLineFields(kdtEntires, kdtEntires.getRow(rowIndex), rowObject);
		kdtEntires.getRow(rowIndex).setUserObject(rowObject);
		if (rowObject.getPteCost().size() > 0)
			kdtEntires.getCell(rowIndex, "costAccount").setValue(rowObject);
		setEntriesNameCol(rowIndex, level);
		Object newValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldValue != null && newValue != null) {
			if (oldValue.equals(newValue)) {
				return;
			}
		}
		setEntriesNumberCol(rowIndex, level);
	}

	/**
	 * 描述：判定是不是子节点
	 * @param rowIndex
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：jian_cao
	 * @CreateTime：2012-9-10
	 */
	private boolean isParent(int rowIndex) throws BOSException, EASBizException {
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("head", kdtEntires.getCell(rowIndex, "id").getValue());
		return ProgrammingTemplateEntireFactory.getRemoteInstance().exists(filterInfo);
	}

	protected int getInsertRowIndex(Object o, int rowIndex, int rowCount) {
		int row = 0;
		String longNumber = o.toString();
		if (rowIndex + 1 == rowCount) {
			return rowIndex + 1;
		}

		for (int i = rowIndex + 1; i < rowCount; i++) {
			int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 == level || level_2 < level) {
				return i;
			}
			Object n = kdtEntires.getCell(i, LONGNUMBER).getValue();
			if (n != null && n.toString() != null) {
				if (!n.toString().startsWith(longNumber)) {
					row = i;
					break;
				}
				// else if(!longNumber.equals(headNumber.toString())){
				// row = i;
				// }
			} else {
				return i + 1;
			}

			if (rowIndex + 2 == rowCount) {
				return rowCount;
			}
		}
		return row == 0 ? rowCount : row;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void kdtEntires_editStarting(KDTEditEvent e) throws Exception {
		super.kdtEntires_editStarting(e);
		int rowIndex = e.getRowIndex();
		if (e.getColIndex() == kdtEntires.getColumnIndex(LONGNUMBER)) {
			Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			if (longNumber != null && longNumber.toString().trim().length() > 80) {
				MsgBox.showInfo("分录第 " + (e.getRowIndex() + 1) + " 行，框架合约编码超长\n请修改上级编码后再进行编辑！");
				kdtEntires.getEditManager().cancelEditing();
				e.setCancel(true);
			}
		}
	}

	protected void kdtEntires_editStarted(KDTEditEvent e) throws Exception { 
		int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
		if (e.getColIndex() == kdtEntires.getColumnIndex(LONGNUMBER)) {
			longNumberEditStarted(rowIndex);
		}
	}

	private void longNumberEditStarted(int rowIndex) {
		ICellEditor editor = kdtEntires.getCell(rowIndex, LONGNUMBER).getEditor();
		if (editor != null) {
			if (editor instanceof KDTDefaultCellEditor) {
				int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
				KDTDefaultCellEditor de = (KDTDefaultCellEditor) editor;
				KDTextField txtLongNumber = (KDTextField) de.getComponent();
				LimitedTextDocument doc = (LimitedTextDocument) txtLongNumber.getDocument();
				txtLongNumber.setMaxLength(80);
				String txt = "";
				Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
				Object subNumber = kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
				if (longNumber == null || longNumber.toString().trim().length() == 0) {
					if (subNumber != null && subNumber.toString().trim().length() > 0) {
						txt = subNumber.toString().trim() + ".";
						kdtEntires.getCell(rowIndex, LONGNUMBER).setValue(txt);
					}
				} else {
					txt = longNumber.toString().trim();
				}
				if (level > 1) {
					doc.setLimitedText(subNumber.toString().trim() + ".");
					doc.setIsOnload(true);
					doc.setIsAutoUpdate(true);
					txtLongNumber.setText(txt);
					doc.setIsOnload(false);
					doc.setIsAutoUpdate(false);
				} else {
					doc.setIsAutoUpdate(true);
					doc.setIsOnload(true);
					txtLongNumber.setText(txt);
					doc.setIsAutoUpdate(false);
					doc.setIsOnload(false);
				}
			}
		}
	}

	protected void kdtEntires_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		setSmallBtnEnable();
	}

	protected void setFieldsNull(AbstractObjectValue newData) {
		ProgrammingTemplateInfo temp = (ProgrammingTemplateInfo) newData;
		temp.setId(BOSUuid.create(temp.getBOSType()));
		this.txtName.setText("复件 " + temp.getName());
		editData.setName("复件 " + temp.getName());
		txtNumber.setText(null);
		editData.setNumber(null);
		ProgrammingTemplateEntireCollection col = temp.getEntires();
		for (int i = 0; i < col.size(); i++) {
			ProgrammingTemplateEntireInfo info = col.get(i);
			info.setAttachment(null);
			PTECostCollection costCol = info.getPteCost();
			for (int j = 0; j < costCol.size(); j++) {
				PTECostInfo pteInfo = costCol.get(j);
				if (pteInfo.getCostAccount() != null) {
					CostAccountInfo costAccountInfo = pteInfo.getCostAccount();
					if (!costAccountInfo.isIsEnabled()) {
						pteInfo.setCostAccount(null);
						costCol.remove(pteInfo);
					}
				}
			}
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		buildProgrammingTemplateTree();
		treeMain.setSelectionRow(0);
	}

	public void verifyDataBySave() throws Exception {
		try {
			String name = txtName.getText();
			if (name == null || name.trim().equals("")) {
				throw new ProgrammingException(ProgrammingException.NAMENOTNULL);
			}

			FilterInfo filter = new FilterInfo();
			FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name, CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (editData.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}

			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			SorterItemCollection sorter = new SorterItemCollection();
			sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
			if (ProgrammingTemplateFactory.getRemoteInstance().exists(filter)) {
				throw new ProgrammingException(ProgrammingException.CHECKDUPLICATED, new Object[] { name });
			}

			int rowCount = kdtEntires.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				Object number = kdtEntires.getCell(i, "longNumber").getValue();
				String head = (String) kdtEntires.getCell(i, HEADNUMBER).getValue();
				Object longName = kdtEntires.getCell(i, "longName").getValue();
				if (number == null || number.toString().trim() == null) {
					throw new ProgrammingException(ProgrammingException.NUMBER_NULL, new Object[] { new Integer(i + 1) });
				}
				/*
				 * 以下代码有点神经病，当子级   longNumber长度＝上级longnumber长度＋1时，提示“分录第{0}行框架合约编码不能为空！”，进而保存失败
				 * 注释掉 by Owen_wen 2013-8-23
				 * Object level = kdtEntires.getCell(i, "level").getValue();
				int level_int = new Integer(level.toString()).intValue();
				if (level_int != 1) {
					String ln = number.toString();
					if (ln.length() == ((head == null ? 0 : head.toString().length()) + 1)) {
						throw new ProgrammingException(ProgrammingException.NUMBER_NULL, new Object[] { new Integer(i + 1) });
					}
				}*/

				String longNumber = number.toString().trim();
				if (longNumber.length() > 80) {
					throw new EASBizException(new NumericExceptionSubItem("1", "分录第" + (i + 1) + "行，编码超长，请重新输入！"));
				}

				Object proName = getCellValue(kdtEntires, i, "name");
				if (proName == null || proName.toString().trim() == null) {
					throw new ProgrammingException(ProgrammingException.NAME_NULL, new Object[] { new Integer(i + 1) });
				}

				if (proName != null && proName.toString().trim().length() > 80) {
					throw new EASBizException(new NumericExceptionSubItem("1", "分录第" + (i + 1) + "行，框架合约名称超长！"));
				}

				if (longName != null && !StringUtils.isEmpty(longName.toString())) {
					if (longName.toString().length() > 255) {
						throw new EASBizException(new NumericExceptionSubItem("1", "分录第" + (i + 1) + "行，框架合约长名称超长\n请修改框架合约名称数据！"));
					}
				}

				String lnumber = number.toString();
				String name_pro = proName.toString().trim();

				for (int j = 0; j < rowCount; j++) {
					if (j == i)
						continue;

					Object number_2 = kdtEntires.getCell(j, "longNumber").getValue();
					Object proName_2 = getCellValue(kdtEntires, j, "name");

					if (number_2 != null && number_2.toString().trim().length() > 0) {
						if (lnumber.equals(number_2.toString().trim())) {
							throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT, new Object[] { new Integer(i + 1), new Integer(j + 1),
									"编码" });
						}
					}

					if (proName_2 != null && proName_2.toString().trim().length() > 0) {
						if (name_pro.equals(proName_2.toString().trim())) {
							throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT, new Object[] { new Integer(i + 1), new Integer(j + 1),
									"名称" });
						}
					}
				}
			}
		} catch (Exception e) {
			//reSelectedEditTreeNode();
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		try {
			handleProgrammingTemplateName();
			kdtEntires.getEditManager().editingStopped();
			verifyDataBySave();
			int rowCount = this.kdtEntires.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				IRow row = kdtEntires.getRow(i);
				Object name = getCellValue(kdtEntires, i, "name");
				if (name != null && name.toString().trim().length() > 0) {
					row.getCell("name").setValue(name.toString().trim());
				}
				row.getCell("sortNumber").setValue(new Integer(i));
				ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(i).getUserObject();
				setCostAccountToEditData(i, rowObject);
				if (rowObject.getId() != null) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("parent", rowObject.getId().toString(), CompareType.EQUALS));
					PTECostFactory.getRemoteInstance().delete(filter);
					for (int j = 0; j < rowObject.getPteCost().size(); j++) {
						PTECostInfo info = rowObject.getPteCost().get(j);
						info.setParent(rowObject);
						PTECostFactory.getRemoteInstance().save(info);
					}
					rowObject.getPteCost().clear();
					PTEEnonomyFactory.getRemoteInstance().delete(filter);
					for (int j = 0; j < rowObject.getPteEnonomy().size(); j++) {
						PTEEnonomyInfo info = rowObject.getPteEnonomy().get(j);
						info.setParent(rowObject);
						PTEEnonomyFactory.getRemoteInstance().save(info);
					}
					rowObject.getPteEnonomy().clear();
				}
			}

			if (txtNumber.getText() == null || "".equals(txtNumber.getText()))
				txtNumber.setText(getDateString());
			
			super.actionSubmit_actionPerformed(e);
			
			initAfterActionSubmit();
			
		} catch (Exception e1) {
			//reSelectedEditTreeNode();
			handUIExceptionAndAbort(e1);
		}
	}

	/**
	 * 描述：清除父级合约规划的成功构成和经济条款
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author：jian_cao
	 * @CreateTime：2012-9-19
	 */
	private void clearParentPteCostAndPteEnonomy() throws EASBizException, BOSException {

		FilterInfo filterInfo = new FilterInfo();
		ProgrammingTemplateEntireInfo info = null;
		IPTECost iPTECost = PTECostFactory.getRemoteInstance();
		IPTEEnonomy iPTEEnonomy = PTEEnonomyFactory.getRemoteInstance();
		IProgrammingTemplateEntire iTemplateEntire = ProgrammingTemplateEntireFactory.getRemoteInstance();
		
		for (int i = 0, size = kdtEntires.getRowCount(); i < size; i++) {
			filterInfo.getFilterItems().clear();
			filterInfo.appendFilterItem("head", kdtEntires.getCell(i, "id").getValue());
			//如果存在子合约规划
			if (iTemplateEntire.exists(filterInfo)) {
				
				//获取当前合约规划
				info = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(i).getUserObject();
				if (null != info) {
					filterInfo.getFilterItems().clear();
					filterInfo.appendFilterItem("parent", info.getId().toString());

					//清除成功构成
					info.getPteCost().clear();
					iPTECost.delete(filterInfo);

					//清除经济条款
					info.getPteEnonomy().clear();
					iPTEEnonomy.delete(filterInfo);
				}
			}
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(!isSelectedLeafNode()){
			MsgBox.showWarning("请选择要编辑的模板");
			return;
		}
		super.actionEdit_actionPerformed(e);
		setSmallBtnEnable();
		// handleOldData();
		resetTreeMainCellEditor();
		setOprtState(OprtState.EDIT);
		editTreePath=treeMain.getSelectionPath();
		isBtnEditClicked=true;
		switchButton();
		setNameDisplay();
	}

	protected void kdtEntires_editStopped(KDTEditEvent e) throws Exception {
		// 设置编码
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if (oldValue == null && newValue == null) {
			return;
		}

		final int rowIndex = kdtEntires.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) { //没有选中行
			return;
		}
		
		ICell cell = kdtEntires.getCell(rowIndex, "level");
		int level = cell.getValue() == null ? 1 : new Integer(cell.getValue().toString()).intValue();
		if (e.getColIndex() == kdtEntires.getColumnIndex(LONGNUMBER)) {
			if (oldValue != null && newValue != null) {
				if (oldValue.equals(newValue)) {
					return;
				}
			}
			setEntriesNumberCol(rowIndex, level);
		}
		if (e.getColIndex() == kdtEntires.getColumnIndex("name")) {
			setEntriesNameCol(rowIndex, level);
		}

		if (e.getColIndex() == kdtEntires.getColumnIndex("costAccount")) {
			Object cost = kdtEntires.getCell(rowIndex, "costAccount").getValue();
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(rowIndex).getUserObject();
			if (cost == null) {
				rowObject.getPteCost().clear();
			}
			setCostAccountToEditData(rowIndex, rowObject);
		}
	}

	private void setEntriesNumberCol(int rowIndex, int level) {
		Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		if (longNumber != null && longNumber.toString().trim().length() > 0) {
			String lnumber = longNumber.toString();
			if (level == 1) {
				kdtEntires.getCell(rowIndex, "number").setValue(lnumber);
			} else {
				String number = lnumber.substring(lnumber.lastIndexOf(".") + 1, lnumber.length());
				kdtEntires.getCell(rowIndex, "number").setValue(number);
			}
			for (int i = rowIndex + 1; i < kdtEntires.getRowCount(); i++) {
				Object headNumber = kdtEntires.getCell(i, HEADNUMBER).getValue();
				Object longNumber_2 = kdtEntires.getCell(i, LONGNUMBER).getValue();
				int level_2 = new Integer(kdtEntires.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				String[] editString = lnumber.split("\\.");
				if (longNumber_2 != null && longNumber_2.toString().trim().length() > 0) {
					String hNumber_2 = headNumber.toString();
					String lNumber_2 = longNumber_2.toString();
					String[] newL = lNumber_2.split("\\.");
					String[] newH = hNumber_2.split("\\.");
					for (int j = 0; j < editString.length; j++) {
						if (newL[j] != null && newL[j].length() > 0)
							newL[j] = editString[j];
						if (newH[j] != null && newH[j].length() > 0)
							newH[j] = editString[j];
					}
					StringBuffer str = new StringBuffer();
					for (int j = 0; j < newL.length; j++) {
						str.append(newL[j]).append(".");
					}
					if (newL.length < level_2)
						str.append(".");
					StringBuffer str2 = new StringBuffer();
					for (int j = 0; j < newH.length; j++) {
						str2.append(newH[j]).append(".");
					}
					setkdtEntriesNumber(i, str.substring(0, str.length() - 1), str2.substring(0, str2.length() - 1));
				}
			}
		}
	}

	private void setEntriesNameCol(int rowIndex, int level) {
		Object name = getCellValue(kdtEntires, rowIndex, "name");
		if (name != null && name.toString().trim().length() > 0) {
			String nameStr = name.toString().trim();
			String blank = setNameIndent(level);
			kdtEntires.getCell(rowIndex, "name").setValue(blank + nameStr);
			// boolean isLeaf = isLeaf(getCellValue(kdtEntires, rowIndex,
			// LONGNUMBER), kdtEntires, HEADNUMBER);
			// kdtEntires.getCell(rowIndex
			// ,"name").setValue(getNameTreeNode(nameStr, level, isLeaf));
			if (level == 1) {
				kdtEntires.getCell(rowIndex, "longName").setValue(nameStr);
			} else {
				Object lo = kdtEntires.getCell(rowIndex, "longName").getValue();
				String displayName = lo == null ? "" : lo.toString();
				String ln =null;
				if(displayName.lastIndexOf(".")>0){
					ln = displayName.substring(0, displayName.lastIndexOf(".")) + ".";
				}else{
					ln = displayName+ ".";
				}
				kdtEntires.getCell(rowIndex, "longName").setValue(ln + nameStr);
			}

			Object lo = kdtEntires.getCell(rowIndex, "longName").getValue();
			String displayName = lo == null ? "" : lo.toString();
			if (level == 1) {
				displayName = displayName + ".";
			}
			String[] l = displayName.split("\\.");
			for (int i = rowIndex + 1; i < kdtEntires.getRowCount(); i++) {
				ICell cell = kdtEntires.getCell(i, "level");
				int level_2 = cell.getValue() == null ? 1 : new Integer(cell.getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				Object l2 = kdtEntires.getCell(i, "longName").getValue();
				if (l2 == null)
					return;
				String l3[] = l2.toString().split("\\.");
				for (int j = 0; j < l.length; j++) {
					if (l3[j] != null && l3[j].length() > 0) {
						l3[j] = l[j];
					}
				}
				StringBuffer str = new StringBuffer();
				for (int j = 0; j < l3.length; j++) {
					str.append(l3[j]).append(".");
				}
				Object n2 = getCellValue(kdtEntires, rowIndex, "name");
				if (n2 == null) {
					str.append(".");
				}
				kdtEntires.getCell(i, "longName").setValue(str.substring(0, str.length() - 1));
				displayName = null;
			}
		}
	}

	private void setkdtEntriesNumber(int i, String lnumber, String hNumber) {
		kdtEntires.getCell(i, HEADNUMBER).setValue(hNumber);
		kdtEntires.getCell(i, LONGNUMBER).setValue(lnumber);

		ICellEditor editor = kdtEntires.getCell(i, LONGNUMBER).getEditor();
		if (editor != null) {
			if (editor instanceof KDTDefaultCellEditor) {
				KDTDefaultCellEditor de = (KDTDefaultCellEditor) editor;
				KDTextField txtLongNumber = (KDTextField) de.getComponent();
				LimitedTextDocument doc = (LimitedTextDocument) txtLongNumber.getDocument();
				doc.setIsAutoUpdate(true);
				doc.setIsOnload(true);
				txtLongNumber.setText(lnumber);
				doc.setIsAutoUpdate(false);
				doc.setIsOnload(false);
			}
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setIsEnabled(true);
		return objectValue;
	}

	private String getDateString() {
		Calendar cal = Calendar.getInstance();
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		Date bizDate = new Date(ts.getTime());
		return bizDate.toString();
	}

	/**
	 * 加载成本科目F7
	 */
	private void costAccountF7() {
		CostAccountPromptBox selector = new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				StringBuffer str = new StringBuffer();
				if (o != null && o instanceof CostAccountInfo) {
					str.append(((CostAccountInfo) o).getLongNumber().replace('!', '.'));
				}
				if (o != null && o instanceof CostAccountInfo[]) {
					CostAccountInfo[] costInfo = (CostAccountInfo[]) o;
					for (int i = 0; i < costInfo.length; i++) {
						str.append(costInfo[i].getLongNumber().replace('!', '.')).append(",");
					}
				}
				if (o instanceof ProgrammingTemplateEntireInfo) {
					return getProF7DisplayPercent(o);
				}
				if (str != null && str.toString().length() > 1)
					return str.toString().substring(0, str.toString().length() - 1);
				return null;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(true);
		prmtCostAccount.setDisplayFormat("$problem$");
		prmtCostAccount.setEditFormat("$problem$");
		prmtCostAccount.setCommitFormat("$problem$");
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID, CompareType.EQUALS));
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		kdtEntires.getColumn("costAccount").setEditor(caEditor);
		setCostAccountRenderer();
	}

	/**
	 * 设置成本科目F7显示格式
	 */
	private void setCostAccountRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof ProgrammingTemplateEntireInfo) {
					return getProF7DisplayPercent(o);
				}

				if (o != null) {
					return o.toString();
				}
				return null;
			}
		});
		this.kdtEntires.getColumn("costAccount").setRenderer(objectValueRender);
	}

	private String getProF7DisplayPercent(Object o) {
		ProgrammingTemplateEntireInfo info = (ProgrammingTemplateEntireInfo) o;
		PTECostCollection pteCol = info.getPteCost();
		if (pteCol.size() > 0) {
			StringBuffer br = new StringBuffer();
			for (int j = 0; j < pteCol.size(); j++) {
				if (pteCol.get(j).getCostAccount() != null) {
					br.append(pteCol.get(j).getCostAccount().getName());
				}
				if (pteCol.get(j).getContractScale() != null) {
					//modified by ken..分配为100%时，显示比例。。
					if (pteCol.get(j).getContractScale().compareTo(new BigDecimal(0)) > 0
							&& pteCol.get(j).getContractScale().compareTo(new BigDecimal(100)) <= 0) {
						br.append(" ").append(pteCol.get(j).getContractScale() + "%");
					}
				}
				if (pteCol.get(j).getCostAccount() != null) {
					br.append(",");
				}
			}
			if (br != null && br.toString().length() > 1)
				return br.toString().substring(0, br.toString().length() - 1);
			else
				return null;
		}
		return null;
	}

	private void setAttachmentRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					return "查看";
				}
				return null;
			}
		});
		this.kdtEntires.getColumn("attachment").setRenderer(objectValueRender);
	}

	/**
	 * 分录双击操作
	 */

	private String attachMentTempID = null;

	protected void kdtEntires_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		
		// 1. 点击“合约规划名称”列中的+或-号，做出相应的响应
		if (colIndex == kdtEntires.getColumnIndex("name") || e.getClickCount() == 1) {
			ICell cell = kdtEntires.getCell(rowIndex, colIndex);
			if (cell != null) {
				Object value = cell.getValue();
				if (value instanceof CellTreeNode) {
					CellTreeNode node = (CellTreeNode) value;
					node.doTreeClick(kdtEntires, kdtEntires.getCell(rowIndex, colIndex));
				}
			}
		}
		
		// 2. 如果点击的不是表体，需要清除表体的当前选择行，以便新增根级合约规划
		if (e.getType() != KDTStyleConstants.BODY_ROW) {
			kdtEntires.getSelectManager().remove();
			kdtEntires.getSelectManager().setActiveRowIndex(-1);
			return;
		}
		
		Object oldValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingTemplateEntireCollection pteCollection = getPTECollection();
		uiContext.put("pteCollection", pteCollection);
		ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		uiContext.put("rowObject", rowObject);

		/* 双击查看或编辑附件 */
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntires.getColumnIndex(ATTACHMENT)) {
			boolean isEdit = false;// 默认为查看状态
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			AttachmentUIContextInfo info = getAttacheInfo();
			if (info == null) {
				info = new AttachmentUIContextInfo();
			}
			if (FDCHelper.isEmpty(info.getBoID())) {
				String boID = rowObject.getId().toString();
				if (boID == null) {
					if (!isEdit) {
						if (attachMentTempID == null) {
							boID = acm.getAttID().toString();
							attachMentTempID = boID;
						} else {
							boID = attachMentTempID;
						}
					} else {
						return;
					}
				}
				info.setBoID(boID);
				acm.showAttachmentListUIByBoID(boID, this, isEdit);
			}
			SysUtil.abort();
		}
		/* 双击查看框架合约详细信息 */
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			uiContext.put("isParent", new Boolean(isParent(rowIndex)));
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PTEEditUI.class.getName(), uiContext, null, oprtState);
			uiWindow.show();
			if (oprtState.equals(OprtState.VIEW))
				return;
			// 绑定数据到分录上
			dataBinder.loadLineFields(kdtEntires, kdtEntires.getRow(rowIndex), rowObject);
			kdtEntires.getRow(rowIndex).setUserObject(rowObject);
			if (rowObject.getPteCost().size() > 0) {
				kdtEntires.getCell(rowIndex, "costAccount").setValue(rowObject);
			}
			setEntriesNameCol(rowIndex, level);
			Object newValue = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldValue != null && newValue != null) {
				if (oldValue.equals(newValue)) {
					return;
				}
			}
			setEntriesNumberCol(rowIndex, level);
		}
	}

	/**
	 * 获取框架合约模版所有分录对象集合
	 * 
	 * @param uiContext
	 * @return
	 */
	private ProgrammingTemplateEntireCollection getPTECollection() {
		ProgrammingTemplateEntireCollection pteCollection = new ProgrammingTemplateEntireCollection();
		ProgrammingTemplateEntireInfo pteInfo = null;
		int columnCount = kdtEntires.getRowCount();
		for (int i = 0; i < columnCount; i++) {
			pteInfo = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(i).getUserObject();
			setContractToEditData(i, pteInfo);
			// setCostAccountToEditData(i, pteInfo);
			pteCollection.add(pteInfo);
		}
		return pteCollection;
	}

	/**
	 * 手动添加成本科目到合约框架模板分录
	 * 
	 * @param rowIndex
	 * @param rowObject
	 */
	protected void setCostAccountToEditData(int rowIndex, ProgrammingTemplateEntireInfo rowObject) {
		Object cost = kdtEntires.getCell(rowIndex, "costAccount").getValue();
		PTECostCollection pteCost = rowObject.getPteCost();
		if (cost instanceof Object[]) {
			Object[] list = (Object[]) cost;

			if (pteCost.size() > 0) {
				setCoatAccountToPTECol(pteCost, list);
			} else {
				for (int i = 0; i < list.length; i++) {
					CostAccountInfo costInfo = (CostAccountInfo) list[i];
					PTECostInfo info = new PTECostInfo();
					info.setCostAccount(costInfo);
					pteCost.add(info);
				}
			}
		}
		if (pteCost.size() > 0)
			kdtEntires.getCell(rowIndex, "costAccount").setValue(rowObject);
	}

	private void setCoatAccountToPTECol(PTECostCollection pteCost, Object[] list) {
		ArrayList sameList = new ArrayList();
		for (int i = 0; i < pteCost.size(); i++) {
			PTECostInfo pteCostInfo = pteCost.get(i);
			boolean isSame = false;
			for (int j = 0; j < list.length; j++) {
				CostAccountInfo costInfo = (CostAccountInfo) list[j];
				if (pteCostInfo.getCostAccount().equals(costInfo)) {
					isSame = true;
				}
			}

			if (!isSame) {
				sameList.add(pteCostInfo);
			}
		}

		for (int i = 0; i < sameList.size(); i++) {
			pteCost.remove(((PTECostInfo) sameList.get(i)));
		}

		for (int i = 0; i < list.length; i++) {
			CostAccountInfo costInfo = (CostAccountInfo) list[i];
			if (pteCost.size() > 0) {
				boolean isSame = false;
				for (int j = 0; j < pteCost.size(); j++) {
					PTECostInfo pteCostInfo = pteCost.get(j);
					if (pteCostInfo.getCostAccount().equals(costInfo)) {
						isSame = true;
					}
				}
				if (!isSame) {
					PTECostInfo info = new PTECostInfo();
					info.setCostAccount(costInfo);
					pteCost.add(info);
				}
			} else {
				PTECostInfo info = new PTECostInfo();
				info.setCostAccount(costInfo);
				pteCost.add(info);
			}
		}
	}

	private void setContractToEditData(int rowIndex, ProgrammingTemplateEntireInfo rowObject) {
		if (rowObject == null) {
			return;
		}
		int level = new Integer(kdtEntires.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (level > 1) {
			Object longNumber = kdtEntires.getCell(rowIndex, LONGNUMBER).getValue();
			if (FDCHelper.isEmpty(longNumber)) {
				String subNumber = (String) kdtEntires.getCell(rowIndex, HEADNUMBER).getValue();
				if (!FDCHelper.isEmpty(subNumber)) {
					rowObject.setLongNumber(subNumber.toString().trim() + ".");// 长编码
				}
			} else {
				rowObject.setLongNumber((String) kdtEntires.getCell(rowIndex, "longNumber").getValue());// 长编码
			}
		} else {
			rowObject.setLongNumber((String) kdtEntires.getCell(rowIndex, "longNumber").getValue());// 长编码
		}
		rowObject.setDisplayName((String) kdtEntires.getCell(rowIndex, "longName").getValue());// 长名称
		rowObject.setLevel(level);
		rowObject.setNumber((String) kdtEntires.getCell(rowIndex, "number").getValue());// 编码
		rowObject.setName(getCellValue(kdtEntires, rowIndex, "name"));// 名称
		rowObject.setDescription((String) kdtEntires.getCell(rowIndex, "remark").getValue());// 备注
	}

	private void setNameDisplay() {
		int rowCount = this.kdtEntires.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntires.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			Object name = getCellValue(kdtEntires, i, "name");
			if (name != null && name.toString().trim().length() > 0) {
				if (getOprtState().equals(OprtState.VIEW)) {
					boolean isLeaf = isLeaf((String) row.getCell(LONGNUMBER).getValue(), this.kdtEntires, HEADNUMBER);
					row.getCell("name").setValue(getNameTreeNode(name.toString().trim(), level, isLeaf));
				} else {
					String blank = setNameIndent(level);
					row.getCell("name").setValue(blank + name.toString().trim());
				}
			}
		}
	}

	/**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * 界面加载时手动绑定成本科目对象到分录字段上。
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void loadCostAccountOnLoad() throws BOSException, EASBizException {
		int rowCount = this.kdtEntires.getRowCount();

		List rowIdList = FDCTableHelper.getRowIdList(this.kdtEntires);
		Set rowIdSet = new LinkedHashSet(rowIdList);

		EntityViewInfo pteView = new EntityViewInfo();
		FilterInfo pteFilter = new FilterInfo();
		pteFilter.getFilterItems().add(new FilterItemInfo("parent.id", rowIdSet, CompareType.INCLUDE));
		pteView.setFilter(pteFilter);
		pteView.getSelector().add("*");
		pteView.getSelector().add("costAccount.*");
		PTECostCollection allPteCol = PTECostFactory.getRemoteInstance().getPTECostCollection(pteView);

		EntityViewInfo pteEcView = new EntityViewInfo();
		FilterInfo pteEcFilter = new FilterInfo();
		pteEcFilter.getFilterItems().add(new FilterItemInfo("parent.id", rowIdSet, CompareType.INCLUDE));
		pteEcView.setFilter(pteEcFilter);
		pteEcView.getSelector().add("*");
		pteEcView.getSelector().add("paymentType.*");
		PTEEnonomyCollection allPteEcCol = PTEEnonomyFactory.getRemoteInstance().getPTEEnonomyCollection(pteView);

		Map allPteMap = FdcObjectCollectionUtil.parsePropertyMap(allPteCol, "parent.id");
		Map allPteEcMap = FdcObjectCollectionUtil.parsePropertyMap(allPteEcCol, "parent.id");

		IRow row = null;
		BOSUuid rowId = null;
		for (int i = 0; i < rowCount; i++) {
			row = kdtEntires.getRow(i);
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) row.getUserObject();
			if (!isCopy) {
				// rowObject.getPteCost().clear();
			} else {
				if (rowObject.getPteCost().size() > 0) {
					row.getCell("costAccount").setValue(rowObject);
				}
			}
			rowId = rowObject.getId();
			if (rowId != null) {
				List col = (List) allPteMap.get(rowId);
				List pteEC = (List) allPteEcMap.get(rowId);

				if (FdcCollectionUtil.isNotEmpty(col)) {
					int size = col.size();
					Object[] costValue = new Object[size];
					for (int j = 0; j < size; j++) {
						PTECostInfo pteInfo = (PTECostInfo) col.get(j);
						if (pteInfo != null) {
							rowObject.getPteCost().add(pteInfo);
						}
					}
					if (costValue.length > 0) {
						row.getCell("costAccount").setValue(rowObject);
					}
				}

				if (FdcCollectionUtil.isNotEmpty(pteEC)) {
					int pteeSize = pteEC.size();
					for (int j = 0; j < pteeSize; j++) {
						PTEEnonomyInfo pteeInfo = (PTEEnonomyInfo) pteEC.get(j);
						if (pteeInfo != null) {
							rowObject.getPteEnonomy().add(pteeInfo);
						}
					}
				}
			}
		}
	}

	protected void pnlBizLayer_stateChanged(ChangeEvent e) throws Exception {
		int selectedIndex = paneBIZLayerControl9.getSelectedIndex();

		if (selectedIndex == 1) {
			// 查看时无需每次重新刷新数据
			boolean flag = OprtState.EDIT.equals(this.getOprtState()) || isRewLoadCostAccountTab;

			if (flag) {
				loadCostAccountTabData();
				setCostNumberFormat();
				createCostTree();

				List rows = this.kdtCosetEntries.getBody().getRows();
				Collections.sort(rows, new TableCellComparator(kdtCosetEntries.getColumnIndex("costNumber"), KDTSortManager.SORT_ASCEND));
				kdtCosetEntries.setRefresh(false);

				isRewLoadCostAccountTab = false;
			}
		}
	}

	/**
	 * 描述：检查是否正在编辑中
	 * 
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-20
	 */
	protected void checkIsEditing() {
		if (OprtState.EDIT.equals(this.getOprtState())) {
			int oldSelectedIndex = 0 == paneBIZLayerControl9.getSelectedIndex() ? 1 : 0;
			MsgBox.showWarning(this, "请先保存");

			//卸载监听事件
			detachListeners();
			paneBIZLayerControl9.setSelectedIndex(oldSelectedIndex);
			//装载监听事件
			attachListeners();

			SysUtil.abort();
		}
	}

	/**
	 * 加载成本科目页签数据显示
	 */
	private void loadCostAccountTabData() {
		long startTime = System.currentTimeMillis();

		//////////////////////////////////////////////////////////////////

		kdtCosetEntries.removeRows();
		kdtCosetEntries.getStyleAttributes().setLocked(true);
		int rowCount = kdtEntires.getRowCount();

		// 取得上级成本科目Map
		Map parentCostAccountMap = getParentCostAccountMap();
		for (int i = 0; i < rowCount; i++) {
			Object cost = kdtEntires.getCell(i, "costAccount").getValue();
			Object name = getCellValue(kdtEntires, i, "name");// 框架合约模板名称
			String proName = "";
			String oldName = "";
			if (name != null && name.toString().trim().length() > 0) {
				proName = name.toString().trim();
				oldName = name.toString().trim();
			}
			createCostEntriesRow(i, cost, proName, oldName, parentCostAccountMap);
		}

		setCostAccountDisplay();

		//////////////////////////////////////////////////////////////////

		long endTime = System.currentTimeMillis();
		double exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("ProgrammingTemplateUI.loadCostAccountTabData(),exeTime:" + exeTime + "秒");
	}

	private void createCostEntriesRow(int i, Object cost, String proName, String oldName, Map parentCostAccountMap) {
		if (cost instanceof ProgrammingTemplateEntireInfo) {
			ProgrammingTemplateEntireInfo rowObject = (ProgrammingTemplateEntireInfo) kdtEntires.getRow(i).getUserObject();
			if (rowObject != null) {
				PTECostCollection pteCol = rowObject.getPteCost();
				if (pteCol.size() > 0) {
					addRowForCost(proName, oldName, pteCol, parentCostAccountMap);
				}
			}
		}
	}

	private void addRowForCost(String proName, String oldName, PTECostCollection pteCol, Map parentCostAccountMap) {
		for (int i = 0; i < pteCol.size(); i++) {
			boolean isHas = false;
			PTECostInfo info = pteCol.get(i);
			if (info.getContractScale() != null) {
				//modified by ken..当有多个成本科目时,后几个分配为100时,在'成本科目标签'里框架合约名称显示问题.
				if (info.getContractScale().compareTo(new BigDecimal(0)) > 0 && info.getContractScale().compareTo(new BigDecimal(100)) <= 0) {
					proName = oldName + " " + info.getContractScale() + "%";
				}
			}
			CostAccountInfo costInfo = info.getCostAccount();
			String name = null;
			for (int j = 0; j < kdtCosetEntries.getRowCount(); j++) {
				name = null;
				IRow row_k = kdtCosetEntries.getRow(j);
				String number = row_k.getCell("costNumber").getValue().toString();
				Object name_1 = row_k.getCell("name").getValue();
				if (name_1 != null)
					name = name_1.toString();
				if (number.equals(costInfo.getLongNumber())) {
					isHas = true;
					if (name == null) {
						row_k.getCell("name").setValue(proName);
					} else {
						row_k.getCell("name").setValue(name + "," + proName);
					}
				}
			}
			if (!isHas) {
				List list = new ArrayList();
				getParentCostAccountInfo(costInfo, list, parentCostAccountMap);
				getCostAccountParent(list);
				IRow row = kdtCosetEntries.addRow();
				row.getCell("costNumber").setValue(costInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costInfo.getLevel()) + costInfo.getName());
				row.getCell("level").setValue(costInfo.getLevel() + "");
				row.getCell("name").setValue(proName);
				row.getCell("headNumber").setValue(costInfo.getParent() == null ? "" : costInfo.getParent().getLongNumber());
			}
		}
	}
	
	/**
	 * 描述： 取得上级成本科目Map
	 * 
	 * @param pteCol 成本构成 集合
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-10
	 */
	private Map getParentCostAccountMap() {
		long startTime = System.currentTimeMillis();

		//////////////////////////////////////////////////////////////////

		Map parentMap = new LinkedHashMap();

		if (FDCTableHelper.isEmpty(kdtEntires)) {
			return parentMap;
		}

		IObjectCollection parentCols = null;
		try {
			// 级联查找所有父类TreeBase
			parentCols = ProgrammingTemplateHelper.findTreeBaseInfoWithSelf(this);
			//			parentCols = FdcTreeBaseUtil.findParentTreeBaseInfo(CostAccountFactory.getRemoteInstance(), "8423FF6E", OrgConstants.DEF_CU_ID,
			//					costAccountCols, true);
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}

		if (FdcObjectCollectionUtil.isNotEmpty(parentCols)) {
			parentMap = FdcObjectCollectionUtil.parseUniqueIdMap(parentCols);
		}

		//////////////////////////////////////////////////////////////////

		long endTime = System.currentTimeMillis();
		double exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("ProgrammingTemplateUI.getParentCostAccountMap(),exeTime:" + exeTime + "秒");

		return parentMap;
	}

	private void getCostAccountParent(List list) {
		for (int i = 0; i < list.size(); i++) {
			CostAccountInfo costAccountInfo = (CostAccountInfo) list.get(i);
			boolean isHas = false;
			for (int j = 0; j < kdtCosetEntries.getRowCount(); j++) {
				IRow row = kdtCosetEntries.getRow(j);
				String number = row.getCell("costNumber").getValue().toString();
				if (number.equals(costAccountInfo.getLongNumber())) {
					isHas = true;
				}
			}
			if (!isHas) {
				IRow row = kdtCosetEntries.addRow();
				row.getCell("costNumber").setValue(costAccountInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costAccountInfo.getLevel()) + costAccountInfo.getName());
				row.getCell("level").setValue(costAccountInfo.getLevel() + "");
				row.getCell("headNumber").setValue(costAccountInfo.getParent() == null ? "" : costAccountInfo.getParent().getLongNumber());
			}
		}
	}

	/**
	 * 描述： 从缓存中取得上级成本科目
	 * 
	 * @param info
	 * @param list
	 * @param parentCostAccountMap
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-10
	 */
	private CostAccountInfo getParentCostAccountInfo(CostAccountInfo info, List list, Map parentCostAccountMap) {
		CostAccountInfo parentCostInfo = info.getParent();

		if (parentCostInfo != null) {
			CostAccountInfo parentFullCostInfo = null;
			try {
				parentFullCostInfo = (CostAccountInfo) parentCostAccountMap.get(parentCostInfo.getId());
				info.setParent(parentFullCostInfo);
			} catch (Exception e) {
				logger.error(e);
				handUIExceptionAndAbort(e);
			}
			list.add(parentFullCostInfo);

			return getParentCostAccountInfo(parentFullCostInfo, list, parentCostAccountMap);
		}

		return null;
	}



	/**
	 * 设置成本页签的成本科目长编码显示格式
	 */
	private void setCostNumberFormat() {
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return null;
			}
		});
		kdtCosetEntries.getColumn("costNumber").setRenderer(render);
	}

	/**
	 * 设置成本页签的树形
	 */
	private void createCostTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtCosetEntries.getRowCount()];
		for (int i = 0; i < kdtCosetEntries.getRowCount(); i++) {
			IRow row = kdtCosetEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		for (int i = 0; i < kdtCosetEntries.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtCosetEntries.getTreeColumn().setDepth(maxLevel);
		kdtCosetEntries.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("Entires.*"));
		sic.add(new SelectorItemInfo("Entires.pteCost.*"));
		sic.add(new SelectorItemInfo("Entires.pteEnonomy.*"));
		sic.add(new SelectorItemInfo("Entires.number"));
		sic.add(new SelectorItemInfo("Entires.name"));
		sic.add(new SelectorItemInfo("Entires.description"));
		sic.add(new SelectorItemInfo("Entires.id"));
		sic.add(new SelectorItemInfo("Entires.level"));
		sic.add(new SelectorItemInfo("Entires.longNumber"));
		sic.add(new SelectorItemInfo("Entires.head.*"));
		sic.add(new SelectorItemInfo("Entires.head.longNumber"));
		sic.add(new SelectorItemInfo("Entires.attachment"));
		sic.add(new SelectorItemInfo("Entires.displayName"));
		sic.add(new SelectorItemInfo("Entires.sortNumber"));
		sic.add(new SelectorItemInfo("number"));
		return sic;
	}

	// public boolean checkBeforeWindowClosing() {
	// return super.checkBeforeWindowClosing();
	// }

	protected void handleOldData() {
		if (!(getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}

	private void buildProgrammingTemplateTree() throws Exception {
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode();
		root.setText(TEMPLATEBASE);
		root.setUserObject(createNewData());
		KingdeeTreeModel model = new KingdeeTreeModel(root);
		EntityViewInfo view = new EntityViewInfo();
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("name"));
		view.setSorter(sic);
		CoreBaseCollection coll = ProgrammingTemplateFactory.getRemoteInstance().getCollection(view);
		ProgrammingTemplateInfo info = null;
		for (int i = 0; i < coll.size(); i++) {
			info = (ProgrammingTemplateInfo) coll.get(i);
			// reloadProgrammingTemplateEntryFromDB(info);
			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
			node.setUserObject(info);
			node.setText(info.getName());
			model.insertNodeInto(node, root, root.getChildCount());
			info = null;
		}
		treeMain.setEditable(true);
		treeMain.setModel(model);
		if (editTreePath2 != null) {
			treeMain.setSelectionNode(getTreeNode(editTreePath2));
			editTreePath2 = null;
		} else {
			treeMain.setSelectionNode(root);
		}
	}

	public void actionDelTemplate_actionPerformed(ActionEvent e) throws Exception {
		if (isSelectedRootNode()) {
			MsgBox.showWarning("请选中要删除的模板");
			SysUtil.abort();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() != null) {
			if (MsgBox.OK == MsgBox.showConfirm2("确定删除选中的模版吗？")) {
				ProgrammingTemplateInfo info = (ProgrammingTemplateInfo) node.getUserObject();
				if (info.getId() != null) {
					ProgrammingTemplateFactory.getRemoteInstance().delete(new ObjectUuidPK(info.getId()));
				}
				editTreePath = null; 
				setOprtState(OprtState.VIEW);
				treeMain.removeNodeFromParent(node);
				treeMain.setSelectionPath(treeMain.getPathForRow(0));
			}
		}
		switchButton();
	}
	
	public void actionAddTemplate_actionPerformed(ActionEvent e) throws Exception {
		if(isSelectedLeafNode()){
			if (!TEMPLATEBASE.equals(getTreeNode(treeMain.getSelectionPath()).toString())) {
				MsgBox.showWarning("请选择\"合约框架列表\",点击新增");
				return;
			}
		}
		IObjectValue info = createNewData();
		DefaultKingdeeTreeNode newNode = new DefaultKingdeeTreeNode();
		newNode.setUserObject(info);
		newNode.setText("");
		this.addTreeNodeToTreeMain(newNode);   //将新建的树节点添加到树中
		this.setDataObject(info);  //将新建模板设置到editUI中
		this.loadFields();  
		resetTreeMainCellEditor();  //重新设置TreeCellEditor
		treeMain.setSelectionNode(newNode);    //选择新增节点
		treeMain.startEditingAtPath(treeMain.getSelectionPath());  //开始编辑
		MyTreeCellEditor cellEditor = (MyTreeCellEditor)treeMain.getCellEditor();
		cellEditor.requestFocus(); //让编辑器获得焦点
		this.editTreePath = treeMain.getSelectionPath();
		setOprtState(OprtState.ADDNEW);  
		
		setSmallBtnEnable();    //设置分录按钮状态
		setCellEditorForTable();  //设置分录可编辑
		switchButton();  //前换按钮使能状态
	}
	
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		if (isSelectedRootNode()) {
			MsgBox.showWarning("请选中要复制的模板");	
			SysUtil.abort();
		}
		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
		ProgrammingTemplateInfo info = getProgrammingTemplateInfo(treeNode);
		if (info != null && info.getId() != null) {
			DefaultKingdeeTreeNode newNode = new DefaultKingdeeTreeNode();
			addTreeNodeToTreeMain(newNode);
			resetTreeMainCellEditor();
			this.getUIContext().put("ID", info.getId().toString());
			this.loadData();
			editData.setName("复件" + editData.getName());
			newNode.setUserObject(editData);
			this.loadFields();

			// 把原有数据中相关id设为空
			editData.setId(null);
			editData.setNumber(getDateString());
			editData.setIsEnabled(true);
			txtNumber.setText(editData.getNumber());
			ProgrammingTemplateEntireCollection entries = editData.getEntires();
			ProgrammingTemplateEntireInfo entry = null;
			PTECostCollection pteCost = null;
			PTEEnonomyCollection pteEnonomy = null;
			Map oldNewKeySuit=new HashMap();
			Set parents=new HashSet();
			for (int i = 0; i < entries.size(); i++) {
				entry = entries.get(i);
				BOSUuid oldId = entry.getId();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				oldNewKeySuit.put(oldId,entry.getId());
				parents.add(entry.getHead());
				entry.setAttachment(null);
				pteCost = entry.getPteCost();
				for (int j = 0; j < pteCost.size(); j++) {
					pteCost.get(j).setId(null);
				}
				pteEnonomy = entry.getPteEnonomy();
				for (int k = 0; k < pteEnonomy.size(); k++) {
					pteEnonomy.get(k).setId(null);
				}
			}
			Iterator its = parents.iterator();
			for(;its.hasNext();){
				ProgrammingTemplateEntireInfo parentPteInfo=(ProgrammingTemplateEntireInfo) its.next();
				if(parentPteInfo!=null) {
					Object object = oldNewKeySuit.get(parentPteInfo.getId());
					parentPteInfo.setId((BOSUuid) object);
				}
			}
			for (int i = 0; i < this.kdtEntires.getRowCount(); i++) {
				kdtEntires.getCell(i,"id").setValue(((ProgrammingTemplateEntireInfo)kdtEntires.getRow(i).getUserObject()).getId());
			}

			detachListeners();
			treeMain.setSelectionNode(newNode);
			attachListeners();
			treeMain.startEditingAtPath(treeMain.getSelectionPath());
			editTreePath = treeMain.getSelectionPath();
			MyTreeCellEditor cellEditor = (MyTreeCellEditor) treeMain.getCellEditor();
			cellEditor.requestFocus();
			setOprtState(OprtState.COPYADDNEW);
			setSmallBtnEnable();
			setCellEditorForTable();
			switchButton();
		}
	}

	
	protected void loadData() throws Exception {
		handleHisotryData();
		super.loadData();
	}
	/**
	 * 处理历史数据
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void handleHisotryData() throws BOSException, EASBizException {
		Object object = getUIContext().get("ID");
		if(object!=null){
			IProgrammingTemplate service = ProgrammingTemplateFactory.getRemoteInstance();
			SelectorItemCollection selectors =new SelectorItemCollection();
			selectors.add("*");
			selectors.add("Entires.*");
			ProgrammingTemplateInfo ptInfo = (ProgrammingTemplateInfo) service.getDataBaseInfo(new ObjectUuidPK(object.toString()), selectors );
			ProgrammingTemplateEntireCollection entires = ptInfo.getEntires();
			boolean isChanged=false;
			for(int i=0;i<entires.size();i++){
				ProgrammingTemplateEntireInfo pteInfo = entires.get(i);
				StringBuffer sb=new StringBuffer();
				if(pteInfo.getScope()!=null&&!pteInfo.getScope().trim().equals("")){
					sb.append(pteInfo.getScope());
				}
				if(pteInfo.getProblem()!=null&&!pteInfo.getProblem().trim().equals("")){
					sb.append("\n").append(pteInfo.getProblem());
					isChanged=true;
					pteInfo.setProblem(null);
				}
				if(pteInfo.getDescription()!=null&&!pteInfo.getDescription().trim().equals("")){
					sb.append("\n").append(pteInfo.getDescription());
					isChanged=true;
					pteInfo.setDescription(null);
				}
			}
			if(isChanged){
				service.save(ptInfo);
			}
		}
	}
	
	public void resetTreeMainCellEditor() {
		DefaultKingdeeTreeCellRenderer renderer = (DefaultKingdeeTreeCellRenderer) treeMain.getCellRenderer();
		final MyTreeCellEditor cellEditor = new MyTreeCellEditor(treeMain, renderer);
		treeMain.setCellEditor(cellEditor);
	}
	
	
	
	private void addTreeNodeToTreeMain(DefaultKingdeeTreeNode newNode) {
		KingdeeTreeModel model = (KingdeeTreeModel) treeMain.getModel();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
		model.insertNodeInto(newNode, (MutableTreeNode) model.getRoot(), root.getChildCount());
	}

	public void actionEditTemplate_actionPerformed(ActionEvent e) throws Exception {
		if (isSelectedRootNode()) {
			MsgBox.showWarning("请选中要修改的模板");
			SysUtil.abort();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		TreePath path = null;
		if (node.getUserObject() != null) {
			resetTreeMainCellEditor();
			path = treeMain.getSelectionPath();
			treeMain.startEditingAtPath(path);
			this.editTreePath = path;
			MyTreeCellEditor cellEditor = (MyTreeCellEditor) treeMain.getCellEditor();
			cellEditor.requestFocus();
			setOprtState(OprtState.EDIT);
			setSmallBtnEnable();
			setCellEditorForTable();
			switchButton();
		}
	}
	

	public void actionSaveTemplate_actionPerformed(ActionEvent e) throws Exception {

		if (isSelectedRootNode()) {
			MsgBox.showWarning("请选中要保存的模板");
			return;
		}
		fireActionSubmit();
	}
	
	private KDWorkButton btnAddTemplate = new KDWorkButton(actionAddTemplate);
	private KDWorkButton btnEditTemplate = new KDWorkButton(actionEditTemplate);
	private KDWorkButton btnDelTemplate = new KDWorkButton(actionDelTemplate);
	private KDWorkButton btnSaveTemplate = new KDWorkButton(actionSaveTemplate);
	
	protected void initWorkButton() {
		super.initWorkButton(); 
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);

		actionAddTemplate.putValue(Action.SHORT_DESCRIPTION, "新增模板");
		actionAddTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_new"));
		actionEditTemplate.putValue(Action.SHORT_DESCRIPTION, "修改模板");
		actionEditTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_edit"));
		actionSaveTemplate.putValue(Action.SHORT_DESCRIPTION, "保存模板");
		actionSaveTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_save"));
		actionDelTemplate.putValue(Action.SHORT_DESCRIPTION, "删除模板");
		actionDelTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_delete"));

		
		
		btnAddTemplate.setEnabled(true);
		btnEditTemplate.setEnabled(true);
		btnDelTemplate.setEnabled(true);
		btnSaveTemplate.setEnabled(true);
		Component[] components = treeMainView.getControlPane().getComponents();
		if(components!=null && components.length>0){
			components[components.length-1].setVisible(false);
		}
		treeMainView.getControlPane().add(btnAddTemplate);
		treeMainView.getControlPane().add(btnEditTemplate);
		treeMainView.getControlPane().add(btnSaveTemplate);
		treeMainView.getControlPane().add(btnDelTemplate);
		
		treeMainView.setShowControlPanel(true);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		if(isFirstOnload()) return;
		checkWhenOprtStateNotView(e);
		DefaultKingdeeTreeNode treeNode = getTreeNode(e.getNewLeadSelectionPath());
		ProgrammingTemplateInfo uObject = getProgrammingTemplateInfo(treeNode);
		if (uObject != null && uObject.getId() != null) {
			this.getUIContext().put("ID", uObject.getId().toString());
			handleHisotryData();
			editData=null;
			this.loadData();
			this.loadFields();
		} else {
			this.setDataObject(uObject);
			this.loadFields();
		}
		paneBIZLayerControl9.setSelectedIndex(0);
		setOprtState(OprtState.VIEW);
		editTreePath=null;
		isBtnEditClicked=false;
		switchButton();
		setSmallBtnEnable();
		setButtonEnabledByCurrentOrgUnit();
	}
	/**
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void checkWhenOprtStateNotView(TreeSelectionEvent e) throws Exception {
		if (oprtState.equals(OprtState.VIEW))
			return;
		if (oprtState.equals(OprtState.ADDNEW) || oprtState.equals(OprtState.COPYADDNEW)) {
			String temp = oprtState.equals(OprtState.ADDNEW) ? "新增" : "复制新增";
			int cf = MsgBox.showConfirm3(temp + "模板 未保存，是否保存");
			if (cf == MsgBox.YES) {
				fireActionSubmit();
				SysUtil.abort();
			} else if (cf == MsgBox.NO) {
				treeMain.removeNodeFromParent(getTreeNode(editTreePath));
				setOprtState(OprtState.VIEW);
				editTreePath = null;
			} else {
				handleProgrammingTemplateName();
				reSelectedEditTreeNode();
				SysUtil.abort();
			}
		} else if (oprtState.equals(OprtState.EDIT)) {
			int cf = MsgBox.showConfirm3("数据已修改，是否保存");
			if (cf == MsgBox.YES) {
				fireActionSubmit();
				SysUtil.abort();
			} else if (cf == MsgBox.NO) {
				setOprtState(OprtState.VIEW);
				editTreePath = null;
			} else {
				handleProgrammingTemplateName();
				reSelectedEditTreeNode();
				SysUtil.abort();
			}
		}
	}
	
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}
	
	/**
	 * 从新选择处于编辑状态的节点
	 */
	private void reSelectedEditTreeNode() {
		if (editTreePath == null)
			return;
		detachListeners();
		MyTreeCellEditor cellEditor = (MyTreeCellEditor) treeMain.getCellEditor();
		if(getTreeNode(editTreePath).getText()==null ||getTreeNode(editTreePath).getText().trim().length()==0){
			getTreeNode(editTreePath).setText("                                 ");
		}
		treeMain.setSelectionPath(editTreePath);
		treeMain.startEditingAtPath(editTreePath);
		cellEditor.requestFocus();
		attachListeners();
	}

	/**
	 * 模拟点击提交按钮提交保存
	 * 
	 * @throws Exception
	 */
	protected void fireActionSubmit() throws Exception {
		ActionEvent envent = new ActionEvent(btnSubmit, new Random().nextInt(Integer.MAX_VALUE),
				"com.kingdee.eas.framework.client.AbstractEditUI$ActionSubmit");
		this.actionSubmit_actionPerformed(envent);
	}

	protected void attachListeners() {
		if (treeSelectionListeners != null) {
			for (int i = 0; i < treeSelectionListeners.length; i++) {
				this.treeMain.addTreeSelectionListener(treeSelectionListeners[i]);
			}
		}

		if (FdcArrayUtil.isNotEmpty(paneChangeListeners)) {
			ChangeListener changeListener = null;
			for (int i = 0, length = paneChangeListeners.length; i < length; i++) {
				changeListener = paneChangeListeners[i];

				this.paneBIZLayerControl9.addChangeListener(changeListener);
			}
		}
	}

	protected void detachListeners() {
		if (treeSelectionListeners != null) {
			for (int i = 0; i < treeSelectionListeners.length; i++) {
				this.treeMain.removeTreeSelectionListener(treeSelectionListeners[i]);
			}
		}

		if (FdcArrayUtil.isNotEmpty(paneChangeListeners)) {
			ChangeListener changeListener = null;
			for (int i = 0, length = paneChangeListeners.length; i < length; i++) {
				changeListener = paneChangeListeners[i];

				this.paneBIZLayerControl9.removeChangeListener(changeListener);
			}
		}
	}

	protected void initListener() {
		super.initListener();
	}

	/**
	 * 保存后，重设预制状态
	 * 
	 * @throws Exception
	 */
	private void initAfterActionSubmit() throws Exception {
		editTreePath2 = editTreePath;
		
		editTreePath = null;
		isBtnEditClicked = false;
		this.clearParentPteCostAndPteEnonomy();
		setOprtState(OprtState.VIEW);
		buildProgrammingTemplateTree();
		setNameDisplay();
	}

	/**
	 * 将cellEditor中的值填充到node和txtName中
	 * 
	 * @throws Exception
	 */
	private void handleProgrammingTemplateName() throws Exception {
		if (editTreePath == null)
			return;
		if(isBtnEditClicked)
			return;
		String name = (String) treeMain.getCellEditor().getCellEditorValue();
		txtName.setText(name==null?"":name.trim());
		getTreeNode(editTreePath).setText(name);
	}

	/**
	 * 根据状态设置按钮使能
	 */
	private void switchButton() {
		btnSubmit.setVisible(false);
		btnCopy.setVisible(true);
		if(isSelectedRootNode()){
			switchButtonEnabled(true);
			btnEdit.setEnabled(false);
			return;
		}
		switchButtonEnabled(true);
		if(oprtState.equals(OprtState.VIEW)){
			
		}else if(oprtState.equals(OprtState.ADDNEW)){
			btnSubmit.setVisible(true);
			btnCopy.setVisible(false);
			btnEdit.setEnabled(false);
			
		}else if(oprtState.equals(OprtState.COPYADDNEW)){
			btnSubmit.setVisible(true);
			btnCopy.setVisible(false);
			btnEdit.setEnabled(false);
			
		}else if(oprtState.equals(OprtState.EDIT)){
			btnSubmit.setVisible(true);
			btnCopy.setVisible(false);
			btnEdit.setEnabled(false);
		}
	}
	
	private void switchButtonEnabled(boolean enabled){
		btnEdit.setEnabled(enabled);
		btnSubmit.setEnabled(enabled);
		btnCopy.setEnabled(enabled);
		
		btnAddTemplate.setEnabled(enabled);
		btnEditTemplate.setEnabled(enabled);
		btnDelTemplate.setEnabled(enabled);
		btnSaveTemplate.setEnabled(enabled);
	}
	

	/**
	 * 是否选择了root节点
	 * 
	 * @return
	 */
	private boolean isSelectedRootNode() {
		if (treeMain.getSelectionPath() != null) {
			DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
			return treeNode.isRoot();
		}
		return false;
	}
	
	private boolean isSelectedLeafNode() {
		if(treeMain.getSelectionPath()!=null){
			DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
			return treeNode.isLeaf();
		}
		return false;
	}

	/**
	 * 通过TreePath获取DefaultKingdeeTreeNode
	 * 
	 * @param path
	 * @return
	 */
	private DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}

	/**
	 * 通过DefaultKingdeeTreeNode获取ProgrammingTemplateInfo
	 * 
	 * @param treeNode
	 * @return
	 */
	private ProgrammingTemplateInfo getProgrammingTemplateInfo(DefaultKingdeeTreeNode treeNode) {
		return treeNode == null ? null : (ProgrammingTemplateInfo) treeNode.getUserObject();
	}

	/**
	 * 根据head是否为空，生成不同长度的number
	 * 
	 * @param head
	 * @return
	 */
	private String gainNumberByDate(String head) {
		String result = null;
		if (StringUtils.isEmpty(head)) {
			result = String.valueOf(System.currentTimeMillis());
		} else {
			Random random = new Random();
			do {
				result = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(random.nextInt(1000)), 5, "0");
			} while (!checkNewNumberValid(result));
		}
		return result;
	}

	private boolean checkNewNumberValid(String number) {
		if (StringUtils.isEmpty(number))
			return false;
		int rowCount = kdtEntires.getRowCount();
		String value = null;
		for (int i = 0; i < rowCount; i++) {
			value = (String) kdtEntires.getCell(i, "number").getValue();
			if (value == null)
				continue;
			if (number.equals(value))
				return false;
		}
		return true;
	}

	/**
	 * 获取单元格树节点
	 * 
	 * @param name
	 * @param level
	 * @param isLeaf
	 * @return
	 */
	private CellTreeNode getNameTreeNode(String name, int level, boolean isLeaf) {
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(name);
		treeNode.setTreeLevel(level);
		treeNode.setCollapse(false);
		treeNode.setHasChildren(!isLeaf);
		return treeNode;
	}

	/**
	 * 判断是否为叶子节点
	 * 
	 * @param parentLongNumber
	 * @param table
	 * @param colHeadNumber
	 * @return
	 */
	private boolean isLeaf(String parentLongNumber, KDTable table, String colHeadNumber) {
		if (parentLongNumber == null || parentLongNumber.length() == 0)
			return true;
		int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String number = getCellValue(table, i, colHeadNumber);
			if (number == null)
				continue;
			if (number.startsWith(parentLongNumber)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置成本页签树+-显示
	 */
	private void setCostAccountDisplay() {
		int rowCount = this.kdtCosetEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtCosetEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			String costName = getCellValue(kdtCosetEntries, i, "costName");
			if (costName != null) {
				costName = costName.trim();
			}
			if (costName != null && costName.toString().trim().length() > 0) {
				boolean isLeaf = isLeaf(getCellValue(kdtCosetEntries, i, "costNumber"), kdtCosetEntries, "headNumber");
				row.getCell("costName").setValue(getNameTreeNode(costName, level, isLeaf));
			}
		}
	}

	/**
	 * 获取KDTable指定rowIndex中colName中的值，如果有CellTreeNode，则从CellTreeNode中取得值
	 * 
	 * @param table
	 * @param rowIndex
	 * @param colName
	 * @return
	 */
	private static String getCellValue(KDTable table, int rowIndex, String colName) {
		if (table == null || colName == null)
			return null;
		ICell cell = table.getCell(rowIndex, colName);
		if (cell == null)
			return null;
		Object value = cell.getValue();
		String result = null;
		if (value instanceof CellTreeNode)
			result = (String) ((CellTreeNode) value).getValue();
		else
			result = (String) cell.getValue();
		return result == null ? null : result.trim();
	}

	/**
	 * 设置table指定rowIndex中colName的值为value
	 * 
	 * @param table
	 * @param rowIndex
	 * @param colName
	 * @param value
	 */
	private static void setCellValue(KDTable table, int rowIndex, String colName, Object value) {
		if (table == null || colName == null)
			return;
		ICell cell = table.getCell(rowIndex, colName);
		if (cell == null)
			return;
		if (cell.getValue() instanceof CellTreeNode) {
			((CellTreeNode) cell.getValue()).setValue(value);
		} else {
			cell.setValue(value);
		}
	}

	/**
	 * 成本页签树节点cell点击事件
	 */
	protected void kdtCosetEntries_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (colIndex == kdtCosetEntries.getColumnIndex("costName") || e.getClickCount() == 1) {
			ICell cell = kdtCosetEntries.getCell(rowIndex, colIndex);
			if (cell == null)
				return;
			Object value = cell.getValue();
			if (value == null)
				return;
			if (value instanceof CellTreeNode) {
				CellTreeNode node = (CellTreeNode) value;
				node.doTreeClick(kdtCosetEntries, kdtCosetEntries.getCell(rowIndex, colIndex));
			}
		}
	}
	
	private String verifyName(String name) throws Exception{
		String result="";
		if (name == null || name.trim().equals("")) {
			result="合约框架名称不能为空";
		}

		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if (ProgrammingTemplateFactory.getRemoteInstance().exists(filter)) {
			result="合约框架名称\""+name+"\"已存在";
		}
		return result;
	}
	
	private void showOnlyOneTab() {
		NewWinMainUI mainUI = getMainUI();
		if(mainUI!=null){
			BodyUI bodyUI = mainUI.getBodyUI();
			Component[] c1 = bodyUI.getComponents();
			for(int i=0;i<c1.length;i++){
				if(c1[i] instanceof com.kingdee.bos.ctrl.swing.KDTabbedPane){
					Component[] c2 = ((com.kingdee.bos.ctrl.swing.KDTabbedPane)c1[i]).getComponents();
					for(int j=0;j<c2.length;j++){
						if(c2[j] instanceof ProgrammingTemplateUI ){
							ProgrammingTemplateUI ptUI=(ProgrammingTemplateUI) c2[j];
							if(!ptUI.equals(this)){
								ptUI.getUIWindow().close();
							}
						}
					}
				}
			}
		}
	}
	private com.kingdee.eas.base.uiframe.client.NewWinMainUI getMainUI() {
		com.kingdee.eas.base.uiframe.client.NewMainFrame main=((com.kingdee.eas.base.uiframe.client.NewMainFrame)getUIContext().get("Owner"));
		Component[] c1 = main.getComponents();
		for(int i=0;i<c1.length;i++){
			if(c1[i] instanceof com.kingdee.bos.ctrl.swing.KDSkinRootPane){
				Component[] c2 = ((com.kingdee.bos.ctrl.swing.KDSkinRootPane)c1[i]).getComponents();
				for(int j=0;j<c2.length;j++){
					if(c2[j] instanceof javax.swing.JLayeredPane){
						Component[] c3 = ((javax.swing.JLayeredPane)c2[j]).getComponents();
						for(int k=0;k<c3.length;k++){
							if(c3[k] instanceof javax.swing.JPanel){
								Component[] c4 = ((javax.swing.JPanel)c3[k]).getComponents();
								for(int m=0;m<c4.length;m++){
									if(c4[m] instanceof com.kingdee.eas.base.uiframe.client.NewWinMainUI){
										return (com.kingdee.eas.base.uiframe.client.NewWinMainUI)c4[m];
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}


}