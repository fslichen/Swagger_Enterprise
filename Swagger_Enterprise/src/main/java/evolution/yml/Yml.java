package evolution.yml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import evolution.Str;

public class Yml {	
	public static void write(Object object, String filePath, boolean overwrite, Representer representer, boolean printLog) {
		// File Configurations
		File file = new File(filePath);
		if (file.exists()) {
			if (overwrite == false) {
				Str.println("The file " + filePath + " already exists. In order to overwrite, set the overwrite property as true.");
				return;
			}
		}
		// Set Options
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);// Display data in tree structure; Use indent rather than curly braces; Disable the YAML tags 
		Yaml yaml = new Yaml (new Constructor(), representer, options);
		// Dump
		try {
			yaml.dump(object, new FileWriter(filePath));
		} catch (IOException e) {
			Str.println("An exception occurred while writing to " + filePath + ".");
		}
		// Print Log
		if (printLog) {
			Str.println(yaml.dump(object));
		}
	}
}
