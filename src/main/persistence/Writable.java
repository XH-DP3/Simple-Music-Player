package persistence;

import org.json.JSONObject;

// The interface is cited from
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
public interface Writable {
    // EFFECTS: return this as JSON object.
    JSONObject toJson();
}
