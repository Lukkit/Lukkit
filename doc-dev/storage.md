# Storage Object

JSON & YAML

## Constructor

### Arguments

| Name | Type | Description |
|------|------|-------------|
| filePath | string | The file path in the plugin's data folder to open/create |

### Returns

| Type | Case |
|------|------|
| nil  | There was an issue getting the file. Errors are logged |
| Storage | A file was found or a new one *can* be created |
