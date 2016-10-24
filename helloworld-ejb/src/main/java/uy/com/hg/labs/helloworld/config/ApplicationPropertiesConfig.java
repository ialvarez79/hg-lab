package uy.com.hg.labs.helloworld.config;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.configuration.ConfigurationException;

import uy.com.hg.labs.helloworld.config.FileConfigDefinition.RuntimeEnvironment;
import uy.com.hg.labs.helloworld.config.FileConfigDefinition.Type;

@ApplicationScoped
public class ApplicationPropertiesConfig  extends AbstractCommonConfigHandler {
	public static final String KEY_VERSION_APLICACION = "version.aplicacion";

	public ApplicationPropertiesConfig() throws ConfigurationException {
		super();
	}
	
	@Override
	public FileConfigDefinition[] getConfigDefinitions() {
		FileConfigDefinition systemConfig = new FileConfigDefinition(Type.SYSTEM,null,RuntimeEnvironment.ANY);
		
				
		FileConfigDefinition labhgConfig = new FileConfigDefinition(Type.PROPERTIES,"labhg-config.properties",RuntimeEnvironment.ANY);
		return new FileConfigDefinition[]{systemConfig, labhgConfig};
	}
	
}
