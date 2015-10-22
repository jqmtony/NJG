/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractProgrammingTemplateUI extends com.kingdee.eas.basedata.framework.client.DataBaseSIEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProgrammingTemplateUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlHide;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane paneBIZLayerControl9;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeMainView;
    protected com.kingdee.bos.ctrl.swing.KDContainer pnlContract;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntires;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCosetEntries;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefresh;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefresh;
    protected com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo editData = null;
    protected ActionRefresh actionRefresh = null;
    protected ActionAddTemplate actionAddTemplate = null;
    protected ActionEditTemplate actionEditTemplate = null;
    protected ActionDelTemplate actionDelTemplate = null;
    protected ActionSaveTemplate actionSaveTemplate = null;
    /**
     * output class constructor
     */
    public AbstractProgrammingTemplateUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractProgrammingTemplateUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRefresh
        this.actionRefresh = new ActionRefresh(this);
        getActionManager().registerAction("actionRefresh", actionRefresh);
         this.actionRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTemplate
        this.actionAddTemplate = new ActionAddTemplate(this);
        getActionManager().registerAction("actionAddTemplate", actionAddTemplate);
         this.actionAddTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEditTemplate
        this.actionEditTemplate = new ActionEditTemplate(this);
        getActionManager().registerAction("actionEditTemplate", actionEditTemplate);
         this.actionEditTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelTemplate
        this.actionDelTemplate = new ActionDelTemplate(this);
        getActionManager().registerAction("actionDelTemplate", actionDelTemplate);
         this.actionDelTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveTemplate
        this.actionSaveTemplate = new ActionSaveTemplate(this);
        getActionManager().registerAction("actionSaveTemplate", actionSaveTemplate);
         this.actionSaveTemplate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.pnlHide = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.paneBIZLayerControl9 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.treeMainView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.pnlContract = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntires = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtCosetEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnRefresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRefresh = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.pnlHide.setName("pnlHide");
        this.paneBIZLayerControl9.setName("paneBIZLayerControl9");
        this.treeMainView.setName("treeMainView");
        this.pnlContract.setName("pnlContract");
        this.kDContainer1.setName("kDContainer1");
        this.kdtEntires.setName("kdtEntires");
        this.kdtCosetEntries.setName("kdtCosetEntries");
        this.treeMain.setName("treeMain");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtRemark.setName("txtRemark");
        this.btnRefresh.setName("btnRefresh");
        this.menuItemRefresh.setName("menuItemRefresh");
        // CoreUI		
        this.kDSeparator1.setVisible(false);		
        this.btnAddNew.setVisible(false);		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setToolTipText(resHelper.getString("btnEdit.toolTipText"));		
        this.btnSave.setVisible(false);		
        this.btnRemove.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.btnCancel.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemAddNew.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuItemCopy.setEnabled(false);		
        this.menuItemFirst.setVisible(false);		
        this.menuItemPre.setVisible(false);		
        this.menuItemNext.setVisible(false);		
        this.menuItemLast.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuItemRemove.setVisible(false);		
        this.menuItemRemove.setEnabled(false);		
        this.MenuItemAttachment.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancel.setVisible(false);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // pnlHide
        // paneBIZLayerControl9		
        this.paneBIZLayerControl9.setVisible(true);
        this.paneBIZLayerControl9.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    pnlBizLayer_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // treeMainView		
        this.treeMainView.setShowButton(false);		
        this.treeMainView.setTitle(resHelper.getString("treeMainView.title"));
        // pnlContract		
        this.pnlContract.setVisible(true);		
        this.pnlContract.setTitle(resHelper.getString("pnlContract.title"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdtEntires
		String kdtEntiresStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" hidden=\"true\" /><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" /><t:Column t:key=\"contractRange\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"hyType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"costAccount\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"remark\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"attachment\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"headNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"longName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"sortNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"contractContUI\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"attachWork\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"attContract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{contractRange}</t:Cell><t:Cell>$Resource{hyType}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{attachment}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{headNumber}</t:Cell><t:Cell>$Resource{longName}</t:Cell><t:Cell>$Resource{sortNumber}</t:Cell><t:Cell>$Resource{contractContUI}</t:Cell><t:Cell>$Resource{attachWork}</t:Cell><t:Cell>$Resource{attContract}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntires.setFormatXml(resHelper.translateString("kdtEntires",kdtEntiresStrXML));
        this.kdtEntires.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEntires_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntires.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtEntires_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEntires.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntires_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntires_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntires_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntires.putBindContents("editData",new String[] {"longNumber","name","contractRange","hyType","","scope","id","attachment","number","level","head.longNumber","displayName","sortNumber","contractContUI","attachWork","attContract"});


        this.kdtEntires.checkParsed();
        KDTextField kdtEntires_contractRange_TextField = new KDTextField();
        kdtEntires_contractRange_TextField.setName("kdtEntires_contractRange_TextField");
        kdtEntires_contractRange_TextField.setMaxLength(500);
        KDTDefaultCellEditor kdtEntires_contractRange_CellEditor = new KDTDefaultCellEditor(kdtEntires_contractRange_TextField);
        this.kdtEntires.getColumn("contractRange").setEditor(kdtEntires_contractRange_CellEditor);
        final KDBizPromptBox kdtEntires_hyType_PromptBox = new KDBizPromptBox();
        kdtEntires_hyType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.PcTypeQuery");
        kdtEntires_hyType_PromptBox.setVisible(true);
        kdtEntires_hyType_PromptBox.setEditable(true);
        kdtEntires_hyType_PromptBox.setDisplayFormat("$number$");
        kdtEntires_hyType_PromptBox.setEditFormat("$number$");
        kdtEntires_hyType_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntires_hyType_CellEditor = new KDTDefaultCellEditor(kdtEntires_hyType_PromptBox);
        this.kdtEntires.getColumn("hyType").setEditor(kdtEntires_hyType_CellEditor);
        ObjectValueRender kdtEntires_hyType_OVR = new ObjectValueRender();
        kdtEntires_hyType_OVR.setFormat(new BizDataFormat("$hyType$"));
        this.kdtEntires.getColumn("hyType").setRenderer(kdtEntires_hyType_OVR);
        KDTextField kdtEntires_remark_TextField = new KDTextField();
        kdtEntires_remark_TextField.setName("kdtEntires_remark_TextField");
        kdtEntires_remark_TextField.setMaxLength(1024);
        KDTDefaultCellEditor kdtEntires_remark_CellEditor = new KDTDefaultCellEditor(kdtEntires_remark_TextField);
        this.kdtEntires.getColumn("remark").setEditor(kdtEntires_remark_CellEditor);
        KDTextField kdtEntires_attachment_TextField = new KDTextField();
        kdtEntires_attachment_TextField.setName("kdtEntires_attachment_TextField");
        kdtEntires_attachment_TextField.setMaxLength(255);
        KDTDefaultCellEditor kdtEntires_attachment_CellEditor = new KDTDefaultCellEditor(kdtEntires_attachment_TextField);
        this.kdtEntires.getColumn("attachment").setEditor(kdtEntires_attachment_CellEditor);
        KDFormattedTextField kdtEntires_sortNumber_TextField = new KDFormattedTextField();
        kdtEntires_sortNumber_TextField.setName("kdtEntires_sortNumber_TextField");
        kdtEntires_sortNumber_TextField.setVisible(true);
        kdtEntires_sortNumber_TextField.setEditable(true);
        kdtEntires_sortNumber_TextField.setHorizontalAlignment(2);
        kdtEntires_sortNumber_TextField.setDataType(0);
        KDTDefaultCellEditor kdtEntires_sortNumber_CellEditor = new KDTDefaultCellEditor(kdtEntires_sortNumber_TextField);
        this.kdtEntires.getColumn("sortNumber").setEditor(kdtEntires_sortNumber_CellEditor);
        KDTextField kdtEntires_contractContUI_TextField = new KDTextField();
        kdtEntires_contractContUI_TextField.setName("kdtEntires_contractContUI_TextField");
        kdtEntires_contractContUI_TextField.setMaxLength(500);
        KDTDefaultCellEditor kdtEntires_contractContUI_CellEditor = new KDTDefaultCellEditor(kdtEntires_contractContUI_TextField);
        this.kdtEntires.getColumn("contractContUI").setEditor(kdtEntires_contractContUI_CellEditor);
        KDTextField kdtEntires_attachWork_TextField = new KDTextField();
        kdtEntires_attachWork_TextField.setName("kdtEntires_attachWork_TextField");
        kdtEntires_attachWork_TextField.setMaxLength(500);
        KDTDefaultCellEditor kdtEntires_attachWork_CellEditor = new KDTDefaultCellEditor(kdtEntires_attachWork_TextField);
        this.kdtEntires.getColumn("attachWork").setEditor(kdtEntires_attachWork_CellEditor);
        KDTextField kdtEntires_attContract_TextField = new KDTextField();
        kdtEntires_attContract_TextField.setName("kdtEntires_attContract_TextField");
        kdtEntires_attContract_TextField.setMaxLength(500);
        KDTDefaultCellEditor kdtEntires_attContract_CellEditor = new KDTDefaultCellEditor(kdtEntires_attContract_TextField);
        this.kdtEntires.getColumn("attContract").setEditor(kdtEntires_attContract_CellEditor);
        // kdtCosetEntries
		String kdtCosetEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"costNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"costName\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"name\" t:width=\"300\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"headNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{costNumber}</t:Cell><t:Cell>$Resource{costName}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{level}</t:Cell><t:Cell>$Resource{headNumber}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtCosetEntries.setFormatXml(resHelper.translateString("kdtCosetEntries",kdtCosetEntriesStrXML));
        this.kdtCosetEntries.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtCosetEntries_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        this.kdtCosetEntries.checkParsed();
        // treeMain		
        this.treeMain.setEditable(true);
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.treeMain.addInputMethodListener(new com.kingdee.bos.ctrl.swing.event.InputMethodAdapter() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent e) {
                try {
                    treeMain_inputMethodTextChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtNumber		
        this.txtNumber.setVisible(false);		
        this.txtNumber.setEnabled(false);
        // txtName		
        this.txtName.setMaxLength(80);		
        this.txtName.setEnabled(false);		
        this.txtName.setVisible(false);
        // txtRemark		
        this.txtRemark.setMaxLength(255);		
        this.txtRemark.setEnabled(false);		
        this.txtRemark.setVisible(false);
        // btnRefresh
        this.btnRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefresh.setText(resHelper.getString("btnRefresh.text"));		
        this.btnRefresh.setVisible(false);
        // menuItemRefresh
        this.menuItemRefresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefresh.setText(resHelper.getString("menuItemRefresh.text"));		
        this.menuItemRefresh.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {kdtEntires}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(0, 0, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));
        kDSplitPane1.setBounds(new Rectangle(3, 2, 1007, 625));
        this.add(kDSplitPane1, new KDLayout.Constraints(3, 2, 1007, 625, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlHide.setBounds(new Rectangle(1104, 63, 338, 120));
        this.add(pnlHide, new KDLayout.Constraints(1104, 63, 338, 120, 0));
        //kDSplitPane1
        kDSplitPane1.add(paneBIZLayerControl9, "right");
        kDSplitPane1.add(treeMainView, "left");
        //paneBIZLayerControl9
        paneBIZLayerControl9.add(pnlContract, resHelper.getString("pnlContract.constraints"));
        paneBIZLayerControl9.add(kDContainer1, resHelper.getString("kDContainer1.constraints"));
        //pnlContract
pnlContract.getContentPane().setLayout(new BorderLayout(0, 0));        pnlContract.getContentPane().add(kdtEntires, BorderLayout.CENTER);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtCosetEntries, BorderLayout.CENTER);
        //treeMainView
        treeMainView.setTree(treeMain);
        //pnlHide
        pnlHide.setLayout(null);        txtNumber.setBounds(new Rectangle(67, 10, 170, 19));
        pnlHide.add(txtNumber, null);
        txtName.setBounds(new Rectangle(66, 38, 170, 19));
        pnlHide.add(txtName, null);
        txtRemark.setBounds(new Rectangle(66, 69, 170, 19));
        pnlHide.add(txtRemark, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("Entires.number", String.class, this.kdtEntires, "number.text");
		dataBinder.registerBinding("Entires", com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo.class, this.kdtEntires, "userObject");
		dataBinder.registerBinding("Entires.name", String.class, this.kdtEntires, "name.text");
		dataBinder.registerBinding("Entires.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEntires, "id.text");
		dataBinder.registerBinding("Entires.level", int.class, this.kdtEntires, "level.text");
		dataBinder.registerBinding("Entires.longNumber", String.class, this.kdtEntires, "longNumber.text");
		dataBinder.registerBinding("Entires.head.longNumber", String.class, this.kdtEntires, "headNumber.text");
		dataBinder.registerBinding("Entires.attachment", String.class, this.kdtEntires, "attachment.text");
		dataBinder.registerBinding("Entires.displayName", String.class, this.kdtEntires, "longName.text");
		dataBinder.registerBinding("Entires.sortNumber", int.class, this.kdtEntires, "sortNumber.text");
		dataBinder.registerBinding("Entires.scope", String.class, this.kdtEntires, "remark.text");
		dataBinder.registerBinding("Entires.contractRange", String.class, this.kdtEntires, "contractRange.text");
		dataBinder.registerBinding("Entires.hyType", java.lang.Object.class, this.kdtEntires, "hyType.text");
		dataBinder.registerBinding("Entires.contractContUI", String.class, this.kdtEntires, "contractContUI.text");
		dataBinder.registerBinding("Entires.attachWork", String.class, this.kdtEntires, "attachWork.text");
		dataBinder.registerBinding("Entires.attContract", String.class, this.kdtEntires, "attContract.text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("description", String.class, this.txtRemark, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.ProgrammingTemplateUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.kdtEntires.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("Entires.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.head.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.attachment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.sortNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.scope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.contractRange", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.hyType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.contractContUI", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.attachWork", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("Entires.attContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output pnlBizLayer_stateChanged method
     */
    protected void pnlBizLayer_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtEntires_tableClicked method
     */
    protected void kdtEntires_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntires_editStopped method
     */
    protected void kdtEntires_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntires_editStarting method
     */
    protected void kdtEntires_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntires_editStarted method
     */
    protected void kdtEntires_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtEntires_activeCellChanged method
     */
    protected void kdtEntires_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtCosetEntries_tableClicked method
     */
    protected void kdtCosetEntries_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output treeMain_inputMethodTextChanged method
     */
    protected void treeMain_inputMethodTextChanged(java.awt.event.InputMethodEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
    	sic.add(new SelectorItemInfo("Entires.number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entires.*"));
		}
		else{
			sic.add(new SelectorItemInfo("Entires.name"));
		}
    	sic.add(new SelectorItemInfo("Entires.id"));
    	sic.add(new SelectorItemInfo("Entires.level"));
    	sic.add(new SelectorItemInfo("Entires.longNumber"));
    	sic.add(new SelectorItemInfo("Entires.head.longNumber"));
    	sic.add(new SelectorItemInfo("Entires.attachment"));
    	sic.add(new SelectorItemInfo("Entires.displayName"));
    	sic.add(new SelectorItemInfo("Entires.sortNumber"));
    	sic.add(new SelectorItemInfo("Entires.scope"));
    	sic.add(new SelectorItemInfo("Entires.contractRange"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("Entires.hyType.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("Entires.hyType.id"));
			sic.add(new SelectorItemInfo("Entires.hyType.hyType"));
        	sic.add(new SelectorItemInfo("Entires.hyType.number"));
		}
    	sic.add(new SelectorItemInfo("Entires.contractContUI"));
    	sic.add(new SelectorItemInfo("Entires.attachWork"));
    	sic.add(new SelectorItemInfo("Entires.attContract"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        
    	

    /**
     * output actionRefresh_actionPerformed method
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTemplate_actionPerformed method
     */
    public void actionAddTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEditTemplate_actionPerformed method
     */
    public void actionEditTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelTemplate_actionPerformed method
     */
    public void actionDelTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveTemplate_actionPerformed method
     */
    public void actionSaveTemplate_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefresh() {
    	return false;
    }
	public RequestContext prepareActionAddTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddTemplate() {
    	return false;
    }
	public RequestContext prepareActionEditTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEditTemplate() {
    	return false;
    }
	public RequestContext prepareActionDelTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionDelTemplate() {
    	return false;
    }
	public RequestContext prepareActionSaveTemplate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveTemplate() {
    	return false;
    }

    /**
     * output ActionRefresh class
     */     
    protected class ActionRefresh extends ItemAction {     
    
        public ActionRefresh()
        {
            this(null);
        }

        public ActionRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingTemplateUI.this, "ActionRefresh", "actionRefresh_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTemplate class
     */     
    protected class ActionAddTemplate extends ItemAction {     
    
        public ActionAddTemplate()
        {
            this(null);
        }

        public ActionAddTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingTemplateUI.this, "ActionAddTemplate", "actionAddTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionEditTemplate class
     */     
    protected class ActionEditTemplate extends ItemAction {     
    
        public ActionEditTemplate()
        {
            this(null);
        }

        public ActionEditTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEditTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEditTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingTemplateUI.this, "ActionEditTemplate", "actionEditTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionDelTemplate class
     */     
    protected class ActionDelTemplate extends ItemAction {     
    
        public ActionDelTemplate()
        {
            this(null);
        }

        public ActionDelTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionDelTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingTemplateUI.this, "ActionDelTemplate", "actionDelTemplate_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveTemplate class
     */     
    protected class ActionSaveTemplate extends ItemAction {     
    
        public ActionSaveTemplate()
        {
            this(null);
        }

        public ActionSaveTemplate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSaveTemplate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveTemplate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveTemplate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractProgrammingTemplateUI.this, "ActionSaveTemplate", "actionSaveTemplate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "ProgrammingTemplateUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.contract.programming.client.ProgrammingTemplateUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtEntires;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}