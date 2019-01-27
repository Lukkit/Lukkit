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
| path | string | The path in the file to the desired value, separated by ``.`` |
| ?defaultValue | ? | An optional value to return if the path couldn't be followed. |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| ?    | The value was found and was returned. Can be anything |


### ``getNumber(path, ?defaultValue)``

#### Description

Gets the number at ``path`` or returns a default value (if supplied).

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to get the number from, separated by ``.`` |
| ?defaultValue | number | An optional number to return if the path couldn't be followed. |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| number | The number was found or the default value was returned |


### ``getString(path, ?defaultValue)``

#### Description

Gets the string at ``path`` or returns a default value (if supplied).

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to get the string from, separated by ``.`` |
| ?defaultValue | string | An optional string to return if the path couldn't be followed. |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| string | The string was found or the default value was returned |


### ``getArray(path, ?defaultValue)``

#### Description

Gets the array (indexed dict) at ``path`` or returns a default value (if supplied).

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to get the array from, separated by ``.`` |
| ?defaultValue | array | An optional array to return if the path couldn't be followed. Can't think of a reason to default an array |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| array | The array was found or the default value was returned |


### ``getDict(path, ?defaultValue)``

#### Description

Gets the dictionary at ``path`` or returns a default value (if supplied).

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The path in the file to get the dictionary from, separated by ``.`` |
| ?defaultValue | dict | An optional dictionary to return if the path couldn't be followed. Can't think of a reason to default a dict |

#### Returns

| Type | Case |
|------|------|
| nil  | The path couldn't be followed in the file |
| dict | The dictionary was found or the default value was returned |


### ``set(path, doCreate)``

#### Description

Sets the value at ``path`` and returns a boolean if the value was set.

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The value's path, separated by ``.`` |
| doCreate | boolean | Whether or not the key's parents and key should be created to set a value. |

#### Returns

| Type | Case |
|------|------|
| boolean  | True if the value was set, false if not |


### ``save()``

#### Description

Saves the file to disk.

#### Arguments

None.

#### Returns

Nothing.
