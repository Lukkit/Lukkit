package nz.co.jammehcow.lukkit.pluginwizard;

public class PluginTemplate {
    public String name;
    public String version;
    public String description;
    public String author;
    private boolean isFinalized;

    public void setFinalized() {
        this.isFinalized = true;
    }

    public boolean isFinalized() {
        return this.isFinalized;
    }
}
