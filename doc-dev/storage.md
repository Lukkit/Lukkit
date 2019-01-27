# Storage Object

JSON & YAML

---

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

---

## Instance Methods

### ``get(path, ?defaultValue)``

#### Description

Gets the value at ``path`` or returns a default value (if supplied).
Does not type check.

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to the desired value separated by ``.`` |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| ?    | The value was found and was returned. Can be anything |

