package jp.go.aist.rtm.rtcbuilder.template;

import java.io.File;

import static jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants.*;
import static jp.go.aist.rtm.rtcbuilder.util.StringUtil.*;

/**
 * テンプレートを出力する際に使用されるヘルパー
 */
public class TemplateHelper {

	/**
	 * ベース名を取得する
	 * 
	 * @param fullName
	 * @return
	 */
	public static String getBasename(String fullName) {
		String[] split = fullName.split("::");
		return split[split.length - 1];
	}

	/**
	 * ファイル名を取得する
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFileName(String fullPath) {
		if (fullPath == null)
			return "";
		File file = new File(fullPath);
		return file.getName();
	}

	/**
	 * 拡張子無しファイル名を取得する
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFilenameNoExt(String fullPath) {
		String fileName = getFileName(fullPath);
		if (fileName == null)
			return "";
		int index = fileName.lastIndexOf('.');
		if (index > 0 && index < fileName.length() - 1) {
			return fileName.substring(0, index);
		}
		return "";
	}

	public static String toSvcImpl(String fullPath) {
		String name = getFilenameNoExt(fullPath);
		if (name.isEmpty()) {
			return name;
		}
		return name + getServiceImplSuffix();
	}

	public static String toSvcSkel(String fullPath) {
		String name = getFilenameNoExt(fullPath);
		if (name.isEmpty()) {
			return name;
		}
		return name + getServiceSkelSuffix();
	}

	public static String toSvcStub(String fullPath) {
		String name = getFilenameNoExt(fullPath);
		if (name.isEmpty()) {
			return name;
		}
		return name + getServiceStubSuffix();
	}

	public String convFormat(String source) {
		return source.replace("::", ".");
	}

	public boolean isModule(String source) {
		return source.contains("::");
	}

	public static String getServiceImplSuffix() {
		return DEFAULT_SVC_IMPL_SUFFIX;
	}

	public static String getServiceSkelSuffix() {
		return DEFAULT_SVC_SKEL_SUFFIX;
	}

	public static String getServiceStubSuffix() {
		return DEFAULT_SVC_STUB_SUFFIX;
	}

	public String convertDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_DEFAULT_PREFIX,
				DOC_DEFAULT_OFFSET);
	}

	public String convertDescDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_DESC_PREFIX,
				DOC_DESC_OFFSET);
	}

	public String convertPreDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_DESC_PREFIX,
				DOC_PRE_OFFSET);
	}

	public String convertPostDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_DESC_PREFIX,
				DOC_POST_OFFSET);
	}

	public String convertUnitDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_UNIT_PREFIX,
				DOC_UNIT_OFFSET);
	}

	public String convertRangeDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_RANGE_PREFIX,
				DOC_RANGE_OFFSET);
	}

	public String convertConstraintDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_CONSTRAINT_PREFIX,
				DOC_CONSTRAINT_OFFSET);
	}

	public String convertTypeDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_UNIT_PREFIX,
				DOC_UNIT_OFFSET);
	}

	public String convertNumberDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_NUMBER_PREFIX,
				DOC_NUMBER_OFFSET);
	}

	public String convertSemanticsDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_SEMANTICS_PREFIX,
				DOC_SEMANTICS_OFFSET);
	}

	public String convertFrequencyDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_SEMANTICS_PREFIX,
				DOC_SEMANTICS_OFFSET);
	}

	public String convertCycleDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_CYCLE_PREFIX,
				DOC_CYCLE_OFFSET);
	}

	public String convertInterfaceDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_INTERFACE_PREFIX,
				DOC_INTERFACE_OFFSET);
	}

	public String convertInterfaceDetailDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH,
				DOC_INTERFACE_DETAIL_PREFIX, DOC_INTERFACE_DETAIL_OFFSET);
	}

	public String convertReadMePortDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_README_PORT_PREFIX,
				DOC_README_PORT_OFFSET);
	}

	public String convertReadMePortDetailDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH,
				DOC_README_PORT_DETAIL_PREFIX, DOC_README_PORT_DETAIL_OFFSET);
	}

	public String convertReadMeInterfaceDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH,
				DOC_README_INTERFACE_PREFIX, DOC_README_INTERFACE_OFFSET);
	}

	public String convertReadMeConfigDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH,
				DOC_README_PORT_DETAIL_PREFIX, DOC_README_PORT_DETAIL_OFFSET);
	}

	public String convertReadMeModuleDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_README_MODULE_PREFIX,
				DOC_README_MODULE_OFFSET);
	}

	public String convertReadMeActivityDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH,
				DOC_README_ACTIVITY_PREFIX, DOC_README_ACTIVITY_OFFSET);
	}

	public String convertReadMeAuthorDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_README_PREFIX,
				DOC_AUTHOR_OFFSET);
	}

	public String convertReadMeCopyRightDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH,
				DOC_README_COPYRIGHT_PREFIX, DOC_DEFAULT_OFFSET);
	}

	public String convertAuthorDoc(String source) {
		return splitString(source, DOC_DEFAULT_WIDTH, DOC_DEFAULT_PREFIX,
				DOC_AUTHOR_OFFSET);
	}

	public static String toLower(String s) {
		return (s == null) ? "" : s.toLowerCase();
	}

	public static String getVerMajor(String ver) {
		if (ver == null || ver.isEmpty()) {
			return "";
		}
		String[] vers = ver.split("\\.");
		return (vers.length > 0) ? vers[0] : "0";
	}

	public static String getVerMinor(String ver) {
		if (ver == null || ver.isEmpty()) {
			return "";
		}
		String[] vers = ver.split("\\.");
		return (vers.length > 1) ? vers[1] : "0";
	}

	public static String getVerPatch(String ver) {
		if (ver == null || ver.isEmpty()) {
			return "";
		}
		String[] vers = ver.split("\\.");
		return (vers.length > 2) ? vers[2] : "0";
	}

}