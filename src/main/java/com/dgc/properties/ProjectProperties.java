package com.dgc.properties;

import java.util.ResourceBundle;

public class ProjectProperties {

	public ProjectProperties() {
		super();
	}

	private static ResourceBundle resourceBundle;
	private static ResourceBundle resourceBundleConf;
	private static ResourceBundle resourceBundleVersao;

	public static ResourceBundle getResourceBundle() {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle("messages");
		}
		return resourceBundle;
	}
	
	public static ResourceBundle getResourceBundleVersao() {
		if (resourceBundleVersao == null) {
			resourceBundleVersao = ResourceBundle.getBundle("versao");
		}
		return resourceBundleVersao;
	}
}
