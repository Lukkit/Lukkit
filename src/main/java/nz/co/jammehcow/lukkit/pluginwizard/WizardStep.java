package nz.co.jammehcow.lukkit.pluginwizard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WizardStep {
    PluginWizard.Step value();

    String firstRunOutput();  // Not really required, but useful for a less static experience
}
