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

### ``get(path)``

#### Description

Gets the value at ``path``. Does not do type checking as the value returned could be anything.

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to the desired value separated by ``.`` |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| ?    | The value was found and was returned. Can be anything |


### ``getDefault(path, defaultValue)``

#### Description

Gets the value at ``path`` or returns the default argument.
Does not do type checking as the value returned could be anything.

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to the desired value separated by ``.`` |
| defaultValue | ? | Any value which you want to return if the path couldn't be followed |

#### Returns

| Type | Case |
|------|------|
| defaultValue's type | The path couldn't be followed in the file so the default value was returned |
| ?    | The value was found and was returned. Could be anything |

