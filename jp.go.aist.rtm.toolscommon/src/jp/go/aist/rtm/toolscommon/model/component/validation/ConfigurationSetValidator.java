package jp.go.aist.rtm.toolscommon.model.component.validation;


import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ConfigurationSetValidator {
	boolean validate();

	boolean validateId(String value);
	boolean validateConfigurationData(EList<NameValue> value);
}