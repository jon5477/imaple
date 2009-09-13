/*
 * Haiiiiiiii faggot
 */
package imaple.xml;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 *
 * @author David
 */
public interface DataDirectoryEntry {
    public List<DataDirectoryEntry> getSubdirectories();

    public DataEntry getEntry(String name);
}
