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
public interface XMLData extends DataEntity, Iterable<XMLData> {
    public String getName();

    public XMLDataType getType();

    public List<XMLData> getChildren();

    public XMLData getChildByPath(String path);

    public Object getData();
}
