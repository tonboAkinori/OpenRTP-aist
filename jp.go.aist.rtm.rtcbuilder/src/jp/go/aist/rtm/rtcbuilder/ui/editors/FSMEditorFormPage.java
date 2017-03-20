package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.fsm.ScXMLHandler;
import jp.go.aist.rtm.rtcbuilder.fsm.StateParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.PropertyParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.toolscommon.fsm.editor.SCXMLGraphEditor;
import jp.go.aist.rtm.toolscommon.fsm.editor.SCXMLNotifier;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * FSMページ
 */
public class FSMEditorFormPage extends AbstractEditorFormPage {

	private Button fsmBtn;
	private Button staticBtn;
	private Button dynamicBtn;
	private Button importBtn;
	private Button newBtn;
	private Button editBtn;
	
	private SCXMLReceiver observer;
	//
	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public FSMEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", "FSM");
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = super.createBase(managedForm, "Finite State Machine(FSM)");
		FormToolkit toolkit = managedForm.getToolkit();
		//
		createFSMSection(toolkit, form);
		createHintSection(toolkit, form);

		load();
	}

	private void createHintSection(FormToolkit toolkit, ScrolledForm form) {
		Composite composite = createHintSectionBase(toolkit, form, 3);
		createHintLabel(IMessageConstants.DOCUMENT_HINT_COMPONENT_TITLE, IMessageConstants.DOCUMENT_HINT_COMPONENT_DESC, toolkit, composite);
		createHintLabel(IMessageConstants.DOCUMENT_HINT_ETC_TITLE, IMessageConstants.DOCUMENT_HINT_ETC_DESC, toolkit, composite);
	}
	
	private void createFSMSection(FormToolkit toolkit, ScrolledForm form) {
		Section sctOverView = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		sctOverView.setText(IMessageConstants.DOCUMENT_OVERVIEW_TITLE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		sctOverView.setLayoutData(gridData);
		//
		Composite composite = toolkit.createComposite(sctOverView, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		GridLayout gl = new GridLayout(4, false);
		composite.setLayout(gl);
		GridData gd = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gd);
		sctOverView.setClient(composite);
		//
		fsmBtn = createRadioCheckButton(toolkit, composite, "FSM", SWT.CHECK);
		gd = new GridData();
		gd.verticalAlignment = SWT.CENTER;
		fsmBtn.setLayoutData(gd);
		fsmBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				staticBtn.setEnabled(fsmBtn.getSelection());
				dynamicBtn.setEnabled(fsmBtn.getSelection());
				newBtn.setEnabled(fsmBtn.getSelection());
				editBtn.setEnabled(fsmBtn.getSelection());
				importBtn.setEnabled(fsmBtn.getSelection());
				if(fsmBtn.getSelection()) {
					editor.getRtcParam().addFSMPort();
				} else {
					editor.getRtcParam().deleteFSMPort();
				}
				editor.updateEMFDataPorts(
						editor.getRtcParam().getInports(), editor.getRtcParam().getOutports(),
						editor.getRtcParam().getServicePorts());
				update();
			}
		});
		
		Group compGroup = new Group(composite, SWT.NONE);
		compGroup.setLayout(new GridLayout(3, false));
		gd = new GridData();
		gd.horizontalSpan = 3;
		compGroup.setLayoutData(gd);
		
		staticBtn = createRadioCheckButton(toolkit, compGroup, IRtcBuilderConstants.FSMTYTPE_STATIC, SWT.RADIO);
		dynamicBtn = createRadioCheckButton(toolkit, compGroup, IRtcBuilderConstants.FSMTYTPE_DYNAMIC, SWT.RADIO);
		staticBtn.setSelection(true);
		dynamicBtn.setSelection(false);
		
		toolkit.createLabel(composite, "SCXML");
		
		newBtn = toolkit.createButton(composite, "New", SWT.PUSH);
		gd = new GridData();
		gd.widthHint = 100;
		gd.horizontalAlignment = GridData.BEGINNING;
		newBtn.setLayoutData(gd);
		newBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String cmpName = editor.getRtcParam().getName() + "FSM.scxml";
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				IProject project = root.getProject(editor.getRtcParam().getOutputProject());
				IFile fsmFile  = project.getFile(cmpName);
				if(fsmFile.exists()) {
					boolean confirm = MessageDialog.openConfirm(getSite().getShell(), "FSM Editor",
									  "FSM定義が存在します．上書きしてもよろしいですか？");
					if (!confirm) return;
				}
				/////
				try {
					if(observer==null) {
						observer = new SCXMLReceiver();
					}
					observer.setFsmName(cmpName);
					SCXMLGraphEditor scxmlEditor = SCXMLGraphEditor.openEditor(null, observer, false);
	//				String contents = FileUtil.readFile(targetFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		editBtn = toolkit.createButton(composite, "Edit", SWT.PUSH);
		gd = new GridData();
		gd.widthHint = 100;
		gd.horizontalAlignment = GridData.END;
		editBtn.setLayoutData(gd);
		editBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String fsmName = editor.getRtcParam().getName() + "FSM.scxml";
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				IProject project = root.getProject(editor.getRtcParam().getOutputProject());
				IFile fsmFile  = project.getFile(fsmName);
				String targetFile = "";
				if(fsmFile.exists()) {
					targetFile = fsmFile.getLocation().toOSString();
				} else {
					MessageDialog.openWarning(getSite().getShell(), "FSM Editor",
								"FSMの定義が存在しません");
					return;
				}
				
				try {
					if(observer==null) {
						observer = new SCXMLReceiver();
					}
					observer.setFsmName(fsmName);
					SCXMLGraphEditor scxmlEditor = SCXMLGraphEditor.openEditor(targetFile, observer, false);
//					String contents = FileUtil.readFile(targetFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		/////
		importBtn = toolkit.createButton(composite, "Import", SWT.PUSH);
		gd = new GridData();
		gd.widthHint = 100;
		gd.horizontalAlignment = GridData.BEGINNING;
		importBtn.setLayoutData(gd);
		importBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String cmpName = editor.getRtcParam().getName() + "FSM.scxml";
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				IProject project = root.getProject(editor.getRtcParam().getOutputProject());
				IFile fsmFile  = project.getFile(cmpName);
				if(fsmFile.exists()) {
					boolean confirm = MessageDialog.openConfirm(getSite().getShell(), "FSM Editor",
									  "FSM定義が存在します．上書きしてもよろしいですか？");
					if (!confirm) return;
				}
				/////
				FileDialog dialog = new FileDialog(getEditorSite().getShell(), SWT.OPEN);
				dialog.setFilterNames(new String[]{"SCXMLファイル", "XMLファイル"});
				dialog.setFilterExtensions(new String[]{"*.scxml", "*.xml"});
				String newFile = dialog.open();
				if (newFile == null) return;
				/////
				String strPath = fsmFile.getLocation().toOSString();
				try {
    				Path inputPath = FileSystems.getDefault().getPath(newFile);
    				Path outputPath = FileSystems.getDefault().getPath(strPath);			        				
					Files.copy(inputPath, outputPath);
					MessageDialog.openInformation(getSite().getShell(), "Save", "対象データをインポートしました");
				} catch (IOException e1) {
					MessageDialog.openWarning(getSite().getShell(), "Save", "対象データのインポートに失敗しました");
					e1.printStackTrace();
				}
			}
		});
		staticBtn.setEnabled(false);
		dynamicBtn.setEnabled(false);
		newBtn.setEnabled(false);
		editBtn.setEnabled(false);
		importBtn.setEnabled(false);
		/////
//		Button updateButton = toolkit.createButton(composite, "Update", SWT.PUSH);
//		updateButton.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				scxmlEditor.updateEditor("Off");
//				scxmlEditor.updateEditor("Idle");
//				scxmlEditor.updateEditor("Configuring");
//			}
//		});
//		Button parseButton = toolkit.createButton(composite, "Parse", SWT.PUSH);
//		parseButton.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				String cmpName = editor.getRtcParam().getName() + "FSM.scxml";
//				IWorkspace workspace = ResourcesPlugin.getWorkspace();
//				IWorkspaceRoot root = workspace.getRoot();
//				IProject project = root.getProject(editor.getRtcParam().getOutputProject());
//				IFile fsmFile  = project.getFile(cmpName);
//				
//				ScXMLHandler handler = new ScXMLHandler();
//				handler.parseSCXML(fsmFile.getLocation().toOSString());
//			}
//		});
	}

	public void update() {
		RtcParam rtcParam = editor.getRtcParam();

		if( fsmBtn != null ) {
			String targetFile = editor.getRtcParam().getName() + "FSM.scxml";
			if(targetFile!=null && targetFile.length()!=0) {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				IProject project = root.getProject(editor.getRtcParam().getOutputProject());
				IFile fsmFile  = project.getFile(targetFile);
				if(fsmFile.exists()) {
					targetFile = fsmFile.getLocation().toOSString();
				}
				
				ScXMLHandler handler = new ScXMLHandler();
				StateParam rootState = handler.parseSCXML(targetFile);
				if(rootState!=null) {
					rtcParam.setFsmParam(rootState);
				}
			}
			
			rtcParam.setProperty(IRtcBuilderConstants.PROP_TYPE_FSM, Boolean.valueOf(fsmBtn.getSelection()).toString());
			//
			String fsmCompType = "";
			if(dynamicBtn.getSelection()) {
				fsmCompType = IRtcBuilderConstants.FSMTYTPE_DYNAMIC;
			} else if(staticBtn.getSelection()) {
				fsmCompType = IRtcBuilderConstants.FSMTYTPE_STATIC;
			}
			rtcParam.setProperty(IRtcBuilderConstants.PROP_TYPE_FSMTYTPE, fsmCompType);
		}

		editor.updateDirty();
	}

	/**
	 * データをロードする
	 */
	public void load() {
		if(fsmBtn==null) return;
		RtcParam rtcParam = editor.getRtcParam();

		PropertyParam target = rtcParam.getProperty(IRtcBuilderConstants.PROP_TYPE_FSM);
		if(target!=null) {
			fsmBtn.setSelection(Boolean.valueOf(target.getValue()));
			staticBtn.setEnabled(fsmBtn.getSelection());
			dynamicBtn.setEnabled(fsmBtn.getSelection());
			newBtn.setEnabled(fsmBtn.getSelection());
			editBtn.setEnabled(fsmBtn.getSelection());
			importBtn.setEnabled(fsmBtn.getSelection());
		}
		//
		PropertyParam fsmType = rtcParam.getProperty(IRtcBuilderConstants.PROP_TYPE_FSMTYTPE);
		if(fsmType!=null) {
			String fsmCompType = fsmType.getValue();
			if(fsmCompType.equals(IRtcBuilderConstants.FSMTYTPE_DYNAMIC) ) {
				dynamicBtn.setSelection(true);
				staticBtn.setSelection(false);
			} else if(fsmCompType.equals(IRtcBuilderConstants.FSMTYTPE_STATIC)) {
				dynamicBtn.setSelection(false);
				staticBtn.setSelection(true);
			}
		}
		//
		String targetFile = editor.getRtcParam().getName() + "FSM.scxml";
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project = root.getProject(editor.getRtcParam().getOutputProject());
		IFile fsmFile  = project.getFile(targetFile);
		if(fsmFile.exists()) {
			targetFile = fsmFile.getLocation().toOSString();
			ScXMLHandler handler = new ScXMLHandler();
			StateParam rootState = handler.parseSCXML(targetFile);
			if(rootState!=null) {
				rtcParam.setFsmParam(rootState);
			}
		}
	}

	public String validateParam() {
		String result = null;
		RtcParam rtcParam = editor.getRtcParam();
		if(rtcParam==null) return result;
		PropertyParam fsm = rtcParam.getProperty(IRtcBuilderConstants.PROP_TYPE_FSM);
		if(fsm==null) return result;
		
		if(Boolean.valueOf(fsm.getValue())) {
			PropertyParam fsmType = rtcParam.getProperty(IRtcBuilderConstants.PROP_TYPE_FSMTYTPE);
			if(fsmType==null) {
				result = "FSM型が指定されていません";
			} else {
				String strType = fsmType.getValue();
				if(!(strType.equals(IRtcBuilderConstants.FSMTYTPE_STATIC) || strType.equals(IRtcBuilderConstants.FSMTYTPE_DYNAMIC))) {
					result = "FSM型が不正です";
				}
			}
			
			StateParam fsmParam = rtcParam.getFsmParam();
			if(fsmParam==null) {
				result = "FSMコンポーネントの状態遷移図が設定されていません";
			} else {
				List<String> stateList = new ArrayList<String>();
				stateList.add(fsmParam.getName());
				for(StateParam param : fsmParam.getAllStateList() ) {
					if(stateList.contains(param.getName())) {
						result = "状態[" + param.getName() + "]が重複しています";
						break;
					} else {
						stateList.add(param.getName());
					}
				}
			}
		}
		return result;
	}
	
	class SCXMLReceiver implements SCXMLNotifier {
		private String fsmName; 
		private String scXmlContents; 
		
		public void setFsmName(String fsmName) {
			this.fsmName = fsmName;
		}

		@Override
		public void notifyContents(String contents) {
			scXmlContents = contents;
			//
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			IProject project = root.getProject(editor.getRtcParam().getOutputProject());
			IFile fsmFile  = project.getFile(fsmName);
			if(contents.trim().length()==0) {
				try {
					fsmFile.delete(true, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			} else {
				if(fsmFile.exists()==false) {
					try {
						fsmFile.create(null, true, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				String strPath = fsmFile.getLocation().toOSString();
				String xmlSplit[] = scXmlContents.split("\n");
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(strPath), "UTF-8"));
					for (String s : xmlSplit) {
						writer.write(s);
						writer.newLine();
					}
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}