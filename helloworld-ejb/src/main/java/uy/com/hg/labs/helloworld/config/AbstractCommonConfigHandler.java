package uy.com.hg.labs.helloworld.config;

import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.com.hg.labs.helloworld.config.FileConfigDefinition.RuntimeEnvironment;

public abstract class AbstractCommonConfigHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommonConfigHandler.class);

	public static final String RUNTIME_ENVIRONMENT_TYPE_KEY = "RUNTIME_ENVIRONMENT_TYPE";
	
	private CompositeConfiguration configuration;
	
	public AbstractCommonConfigHandler() throws ConfigurationException{
		configuration = initConfiguration();
	}

	private CompositeConfiguration initConfiguration() throws ConfigurationException {
		FileConfigDefinition[] configArr = getConfigDefinitions();
		SystemConfiguration systemConfig = new SystemConfiguration();
		FileConfigDefinition.RuntimeEnvironment currentEnvironment = getRuntimeEnvironment(systemConfig);
		CompositeConfiguration config = new CompositeConfiguration();
		for (FileConfigDefinition fileConfigDefinition : configArr) {
			if (environmentMatches(currentEnvironment,fileConfigDefinition.getTargetEnvironment())){
				if (FileConfigDefinition.Type.PROPERTIES.equals(fileConfigDefinition.getType())){
					config.addConfiguration(new PropertiesConfiguration(fileConfigDefinition.getFileName()));
				} else {
					if (FileConfigDefinition.Type.XML.equals(fileConfigDefinition.getType())){
						config.addConfiguration(new XMLPropertiesConfiguration(fileConfigDefinition.getFileName()));					
					} else {
						if (FileConfigDefinition.Type.SYSTEM.equals(fileConfigDefinition.getType())){
							config.addConfiguration(systemConfig);
						} else {
							throw new UnsupportedOperationException("Tipo de configuracion no soportado"+fileConfigDefinition.getType());
						}
					}
				}
			}
		}
		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("Configured keys:");
			for (Iterator iterator = config.getKeys(); iterator.hasNext();) {
				Object key = iterator.next();
				LOGGER.debug(key.toString());
			}
		}
		return config;
	}


	private boolean environmentMatches(RuntimeEnvironment currentEnvironment, RuntimeEnvironment targetEnvironment) {
		if (RuntimeEnvironment.ANY.equals(targetEnvironment)){
			return true;
		} else {			
			return currentEnvironment.equals(targetEnvironment);
		}
	}

	private RuntimeEnvironment getRuntimeEnvironment(SystemConfiguration systemConfig) {
		String environmentConfig = systemConfig.getString(RUNTIME_ENVIRONMENT_TYPE_KEY);
		if (environmentConfig != null){
			return RuntimeEnvironment.valueOf(environmentConfig);
		}
		LOGGER.error("*****************************************************************************");
		LOGGER.error("Variable de ambiente RUNTIME_ENVIRONMENT_TYPE no tiene un valor correcto. Se configura el ambiente como producción");
		LOGGER.error("Los valores posibles son:"+RuntimeEnvironment.values());
		LOGGER.error("*****************************************************************************");
		return RuntimeEnvironment.PRODUCCION;
	}

	public abstract FileConfigDefinition[] getConfigDefinitions();
	
	public String getString(String key){
		return getString(key, false);
	}
	
	public String getString(String key, boolean required) {
		String value = configuration.getString(key);
		return validateRequiredValue(key, required, value);
	}

	private <T extends Object> T validateRequiredValue(String key, boolean required, T value) {
		if (value != null){
			return value;
		} else { 
			if (required){
				LOGGER.error("Missing required configuration. KEY:"+key);
				LOGGER.error("Configuration being used: "+configuration);
				throw new CommonConfigurationException("Missing required configuration. KEY:"+key);
			} else {
				return value;
			}
		}
	}
	
	public Integer getInteger(String key, boolean required){
		Integer value = configuration.getInteger(key, null);
		return validateRequiredValue(key, required, value);
	}

	public Boolean getBoolean(String key, boolean required){
		Boolean value = configuration.getBoolean(key, null);
		return validateRequiredValue(key, required, value);
	}
	
	public Long getLong(String key, boolean required){
		Long value = configuration.getLong(key, null);
		return validateRequiredValue(key, required, value);
	}
	
	public void agregarAProperties(Properties properties, String[] keys, boolean required){
		for (String currentKey : keys) {
			properties.put(currentKey, getString(currentKey,required));
		}
	}
	
}
