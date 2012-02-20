/**
 *
 */
package permafrost.hdf.basic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a path in a HDF5 structure.
 *
 */
public class Path {
    
    private List<String> absTokens = Collections.<String>emptyList();
    
    public Path() {
        super();        
    }
    
    /**
     * Creates a new Path object.
     *
     */
    public Path(Path rel, String path) {
        super();         
        this.tokenize(rel, path);
    }
    
    
    public void setPath(Path rel, String path) {
        this.tokenize(rel, path);
    }
    
    /* FIXME this doesn't work */
    private void tokenize(Path rel, String path) {        
        List<String> lRoot = rel.getAbsTokens();
        String[] tokens = path.split("/");
        ArrayList<String> lTokens = new ArrayList<String>(lRoot.size() + tokens.length);
        lTokens.addAll(lRoot);
        for (String token : tokens) {           
            lTokens.add(token);
        }
        this.absTokens = lTokens;
    }
    
    private List<String> getAbsTokens() {
        return (this.absTokens);
    }

}
