package nz.co.jammehcow.lukkit.pluginwizard;

public class PluginTemplate {
    private boolean isFinalized;
    public String name;
    public String version;
    public String description;
    public String author;

    public void setFinalized() {
        this.isFinalized = true;
    }

    public boolean isFinalized() {
        return this.isFinalized;
    }
}
