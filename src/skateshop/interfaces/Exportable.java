package skateshop.interfaces;
/**
 * Exportable interface declares a contract making classes implementing it require to be able to be serialized
 * as an object into a file or represented as a String.
 */
public interface Exportable {
    String toFileString();
    String toDisplayString();
}