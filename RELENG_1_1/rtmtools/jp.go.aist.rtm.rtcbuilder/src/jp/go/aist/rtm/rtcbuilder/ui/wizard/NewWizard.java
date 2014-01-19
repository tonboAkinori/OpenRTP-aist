package jp.go.aist.rtm.rtcbuilder.ui.wizard;

import java.io.ByteArrayInputStream;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.generator.ProfileHandler;
import jp.go.aist.rtm.rtcbuilder.ui.editors.RtcBuilderEditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class NewWizard extends Wizard implements INewWizard, IExecutableExtension {

	private WizardNewProjectCreationPage newProjectPage;
	private IConfigurationElement configElement;

	public NewWizard() {
		super();
		setWindowTitle("RT-Component Builder Project");
	}

	public boolean performFinish() {
		IProject projectHandle = newProjectPage.getProjectHandle();
		IFile rtcxml = null;
		try {
			if( !newProjectPage.useDefaults() ) {
				IPath newPath = newProjectPage.getLocationPath();
				IWorkspace workspace = projectHandle.getWorkspace();
		
		        final IProjectDescription description = 
		        			workspace.newProjectDescription(projectHandle.getName());
		        description.setLocation(newPath);
				projectHandle.create(description, null);
			} else {
				projectHandle.create(null);
			}
			projectHandle.open(null);
			//
			XMLGregorianCalendar calendar = new XMLGregorianCalendarImpl(new GregorianCalendar());
			calendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
			String dateTime = calendar.toString();
			ProfileHandler handler = new ProfileHandler();
			String xmlFile = handler.createInitialRtcXml(dateTime);
			//
			rtcxml = projectHandle.getFile(IRtcBuilderConstants.DEFAULT_RTC_XML);
			rtcxml.create(new ByteArrayInputStream(xmlFile.getBytes()), false, null);
		} catch (CoreException ex) {
			System.out.println(ex);
			return false;
		}
		// パースペクティブを切り替え
		BasicNewProjectResourceWizard.updatePerspective(configElement);
		
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		try {
			window.getActivePage().openEditor(new FileEditorInput(rtcxml),
					RtcBuilderEditor.RTC_BUILDER_EDITOR_ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
    public void setInitializationData(
		IConfigurationElement cfig, String propertyName, Object data) {
        configElement = cfig;
    }

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public void addPages() {
		newProjectPage = new WizardNewProjectCreationPage("ProjectCreation");
		addPage(newProjectPage);
	}
}