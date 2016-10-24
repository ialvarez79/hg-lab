package uy.com.hg.labs.helloworld.config;

public class FileConfigDefinition {
	public enum Type {
		PROPERTIES, XML, SYSTEM;
	}

	public enum RuntimeEnvironment {
		DESARROLLO, TEST, PREPROD, PRODUCCION, ANY;
	}

	private Type type;
	private String fileName;
	private RuntimeEnvironment targetEnvironment;

	public FileConfigDefinition(Type type, String fileName, RuntimeEnvironment targetEnvironment) {
		super();
		this.type = type;
		this.fileName = fileName;
		this.targetEnvironment = targetEnvironment;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public RuntimeEnvironment getTargetEnvironment() {
		return targetEnvironment;
	}

	public void setTargetEnvironment(RuntimeEnvironment targetEnvironment) {
		this.targetEnvironment = targetEnvironment;
	}

	@Override
	public String toString() {
		return String.format("FileConfigDefinition [type=%s, fileName=%s, targetEnvironment=%s]",type, fileName, targetEnvironment);
	}
}
