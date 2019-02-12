# HTTP

Communicate with the internet!

---

## Implemented

- [ ] ``constructor``

### Instance Members

- [ ] ``headers``
- [ ] ``body``

### Static Methods

- [ ] ``get(url)``

### Instance Methods

- [ ] ``method(method)``
- [ ] ``header(name, ?value)``
- [ ] ``body(value)``
- [ ] ``json(value)``
- [ ] ``request()``

---

## Constructor

### Arguments

| Name | Type | Description |
|------|------|-------------|
| url | string | The url to make the request to, must unclude protocol e.g. https://example.com |
| ?method | string | Request method e.g. GET (default), POST, DELETE |

### Returns

| Type | Case/Output |
|------|-------------|
| nil  | If HTTP has been disabled in the config. |
| self | HTTP request object. |

---

## Instance Members

### ``headers : dict``

Headers for the request
### ``body : string : nil``

Body of the request

---

## Static Methods

### ``get(url)``

#### Description

Runs a GET request to a url

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| url | string | The url to make the request to, must unclude protocol e.g. https://example.com |

#### Returns

| Type     | Case/Output |
|----------|-------------|
| nil      | There was a network error |
| Response | Response object |

---

## Instance Methods

### ``method(method)``

#### Description

Set's the request method

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| method | string | Request method e.g. GET, POST, DELETE |

#### Returns

| Type | Case/Output |
|------|-------------|
| self | For chaining methods |

### ``header(name, ?value)``

#### Description

Sets, modifies, or deletes a header. If value is nil or missing, the header will be removed.

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| name | string | Name of the header e.g. Content-Type, Authentication |
| ?value | string | Header value |

#### Returns

| Type | Case/Output |
|------|-------------|
| self | For chaining methods |

### ``body(value)``

#### Description

Sets the body of the request

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| value | string | Body value |

#### Returns

| Type | Case/Output |
|------|-------------|
| self | For chaining methods |

### ``json(value)``

#### Description

Converts the dict to a JSON string and sets it as the body. Also sets the headers `Content-Type` to `application/json`

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| value | dict | Body value |

#### Returns

| Type | Case/Output |
|------|-------------|
| self | For chaining methods |

### ``request()``

#### Description

Executes the HTTP request

#### Arguments

| Name | Type | Description |
|------|------|-------------|
| value | dict | Body value |

#### Returns

| Type | Case/Output |
|------|-------------|
| response | Response object |
