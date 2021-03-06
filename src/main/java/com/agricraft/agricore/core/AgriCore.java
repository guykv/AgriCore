/*
 */
package com.agricraft.agricore.core;

import com.agricraft.agricore.config.AgriConfig;
import com.agricraft.agricore.config.AgriConfigAdapter;
import com.agricraft.agricore.defaults.AgriDefaultConfig;
import com.agricraft.agricore.defaults.AgriDefaultConverter;
import com.agricraft.agricore.defaults.AgriDefaultLog;
import com.agricraft.agricore.defaults.AgriDefaultTranslator;
import com.agricraft.agricore.defaults.AgriDefaultValidator;
import com.agricraft.agricore.lang.AgriTranslationAdapter;
import com.agricraft.agricore.lang.AgriTranslator;
import com.agricraft.agricore.log.AgriLogAdapter;
import com.agricraft.agricore.log.AgriLogManager;
import com.agricraft.agricore.log.AgriLogger;
import com.agricraft.agricore.registry.AgriMutations;
import com.agricraft.agricore.registry.AgriPlants;
import com.agricraft.agricore.util.AgriConverter;
import com.agricraft.agricore.util.AgriValidator;
import java.nio.file.Paths;

/**
 *
 * @author RlonRyan
 */
public final class AgriCore {
	
	private static AgriLogManager logManager;
	
	private static AgriTranslator translator;
	
	private static AgriValidator validator;
	
	private static AgriConverter converter;
	
	private static AgriConfig config;
	
	private static AgriMutations mutations;
	
	private static AgriPlants plants;
	
	private AgriCore() {
	}
	
	static {
		AgriCore.init(
				new AgriDefaultLog(),
				new AgriDefaultTranslator(),
				new AgriDefaultValidator(),
				new AgriDefaultConverter(),
				new AgriDefaultConfig(Paths.get("config", "AgriCore", "agricore.config"))
		);
	}
	
	public static void init(
			AgriLogAdapter log,
			AgriTranslationAdapter trans,
			AgriValidator validator,
			AgriConverter converter,
			AgriConfigAdapter provider
	) {
		AgriCore.logManager = new AgriLogManager(log);
		AgriCore.translator = new AgriTranslator(trans);
		AgriCore.config = new AgriConfig(provider);
		AgriLogger logger = AgriCore.getCoreLogger();
		logger.info("Initializing core!");
		AgriCore.validator = validator;
		AgriCore.converter = converter;
		AgriCore.plants = new AgriPlants();
		AgriCore.mutations = new AgriMutations();
		logger.info("Loading config!");
		AgriCore.config.load();
		logger.info("Loaded config!");
		logger.info("Configuring modules!");
		AgriCore.config.addConfigurable(logger);
		AgriCore.config.addConfigurable(validator);
		AgriCore.config.addConfigurable(plants);
		AgriCore.config.addConfigurable(mutations);
		logger.info("Configured modules!");
		logger.info("Saving config!");
		AgriCore.config.save();
		logger.info("Saved config!");
		logger.info("Initialized core!");
	}
	
	public static AgriLogger getCoreLogger() {
		return getLogger("AgriCore");
	}
	
	public static AgriLogger getLogger(Object source) {
		return logManager.getLogger(source);
	}
	
	public static AgriTranslator getTranslator() {
		return translator;
	}
	
	public static AgriLogManager getLogManager() {
		return logManager;
	}
	
	public static AgriValidator getValidator() {
		return validator;
	}
	
	public static AgriConverter getConverter() {
		return converter;
	}
	
	public static AgriConfig getConfig() {
		return config;
	}
	
	public static AgriMutations getMutations() {
		return mutations;
	}
	
	public static AgriPlants getPlants() {
		return plants;
	}
	
}
