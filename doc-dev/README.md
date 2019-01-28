# Documentation for Lukkit globals

``doc-dev`` is a "live document" for the v3 RC.

## Creating a New File

Just copy ``template.md`` and adjust to suit. Take note of formatting please.

## File Sections

### Constructor

This is the constructor which is really just a Lua function that returns an object.
Called like ``obj.new(args)``

### Static Variables

Static variables, accessed as ``obj.variable``

### Instance Members

Instance members, accessed as ``obj:variable`` and must be on an instantiated object.

### Static Methods

These methods are static and are accessed with ``obj.method(args)``.
Can not be called on an instantiated object (???).

### Instance Methods

These are instance methods and are accessed with ``obj:method(args)``.
Object must be instantiated first.
