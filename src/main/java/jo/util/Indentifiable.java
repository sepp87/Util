package jo.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;

/**
 *
 * @author JoostMeulenkamp
 */
public interface Indentifiable {

    StringProperty id = new SimpleStringProperty(null);
    StringProperty name = new SimpleStringProperty(null);

    /**
     * @return the id property of this Object.
     */
    default StringProperty idProperty() {
        return id;
    }

    /**
     * @return the name property of this Object.
     */
    default StringProperty nameProperty() {
        return name;
    }

    /**
     * The id of this Object.
     *
     * @return the id assigned to this Object or null, if no id has been
     * assigned.
     */
    default String getId() {
        Group g = new Group();
        return id.get();
    }

    /**
     * @param value to set to the property id.
     */
    default void setId(String value) {
        id.set(value);
    }

    /**
     * The name of this Object.
     *
     * @return the name assigned to this Object or null, if no id has been
     * assigned.
     */
    default String getName() {
        return name.get();
    }

    /**
     * @param value to set to the property name.
     */
    default void setName(String value) {
        name.set(value);
    }

}
