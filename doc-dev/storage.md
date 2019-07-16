# Storage Object

JSON & YAML

---

## Constructor

### Arguments

| Name | Type | Description |
|------|------|-------------|
| filePath | string | The file path in the plugin's data folder to open/create |

### Returns

| Type | Case/Output |
|------|-------------|
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

| Type | Case/Output |
|------|-------------|
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

| Type | Case/Output |
|------|-------------|
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

| Type | Case/Output |
|------|-------------|
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

| Type | Case/Output |
|------|-------------|
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

| Type | Case/Output |
|------|-------------|
| nil  | The path couldn't be followed in the file |
| dict | The dictionary was found or the default value was returned |


### ``set(path, createParents)``

#### Description

Sets the value at ``path`` and returns a boolean if the value was set.

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| path | string | The value's path, separated by ``.`` |
| createParents | boolean | Whether or not the key's parents and key should be created to set a value. |

#### Returns

| Type | Case/Output |
|------|-------------|
| boolean  | true if the value was set, false if not |


### ``getAbsolutePath()``

#### Description

Gets the absolute path (e.g. ``/home/me/myserver/plugins/myplugin/file.json``).

#### Arguments

None.

#### Returns

| Type | Case/Output |
|------|-------------|
| string | The absolute path |


### ``getRelativePath()``

#### Description

Gets the path of the file relative to the data folder.

#### Arguments

None.

#### Returns

| Type | Case/Output |
|------|-------------|
| string | The relative path |


### ``getName()``

#### Description

Gets the file's name ***excluding the extension***.

#### Arguments

None.

#### Returns

| Type | Case/Output |
|------|-------------|
| string | The filename |


### ``getExtension()``

#### Description

Gets the file's extension.

#### Arguments

None.

#### Returns

| Type | Case/Output |
|------|-------------|
| string | The extension of the file. *Usually* "json" or "yml" but isn't enforced |


### ``getFileType()``

#### Description

Gets the type of data being stored.

#### Arguments

None.

#### Returns

| Type | Case/Output |
|------|-------------|
| string | Either "json" or "yaml" |


### ``save()``

#### Description

Saves the file to disk.

#### Arguments

None.

#### Returns

Nothing.


### ``isSaved()``

#### Description

Whether or not the object in memory has been saved to disk.

#### Arguments

None.

#### Returns

| Type | Case/Output |
|------|-------------|
| boolean  | true if memory matches file, false if not |
