/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.toolscommon.model.component.validation;

import _SDOPackage.ServiceProfile;

import jp.go.aist.rtm.toolscommon.model.component.CorbaComponent;
import org.omg.PortableServer.Servant;

/**
 * A sample validator interface for {@link jp.go.aist.rtm.toolscommon.model.component.CorbaObserver}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CorbaObserverValidator {
	boolean validate();

	boolean validateServiceProfile(ServiceProfile value);
	boolean validateServant(Servant value);
	boolean validateServantReference(org.omg.CORBA.Object value);

	boolean validateComponent(CorbaComponent value);
}
