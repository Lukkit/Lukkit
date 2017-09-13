package nz.co.jammehcow.lukkit.environment.exception;

import org.luaj.vm2.LuaError;

/**
 * @author jammehcow
 */

public class StorageObjectException extends LuaError {
    public StorageObjectException(String message) {
        super(message);
    }
}
