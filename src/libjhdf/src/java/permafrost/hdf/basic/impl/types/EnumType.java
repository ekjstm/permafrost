/**
 *
 */
package permafrost.hdf.basic.impl.types;

import java.util.ArrayList;
import java.util.List;

import permafrost.hdf.libhdf.DatatypeLib;

/**
 * TODO add type documentation
 *
 */
public class EnumType extends AtomicType {

    public static class EnumEntry {
        private final String name;
        private final long value;
        
        public EnumEntry(String name, long value) {
            super();
            this.name = name;
            this.value = value;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the value
         */
        public long getValue() {
            return value;
        }
        
        
    }
    
    /**
     * Creates a new EnumType object.
     *
     * @param hid
     */
    public EnumType(int hid) {
        super(hid);        
    }

    /**
     * Creates a new EnumType object.
     *
     * @param hid
     * @param name
     */
    public EnumType(int hid, String name) {
        super(hid, name);       
    }

    
    public int getCardinality() {
        assert this.isInitialized() : "Native reference not initialized";
        int size = DatatypeLib.H5Tget_nmembers(this.hid);
        assert size >= 0 : "Untrapped error in native code.";
        return (size);
    }
    
    public List<EnumEntry> getEntries() {
        assert this.isInitialized() : "Native reference not initialized";
        
        int size = DatatypeLib.H5Tget_nmembers(this.hid);        
        assert size >= 0 : "Untrapped error in native code.";
        ArrayList<EnumEntry> entries = new ArrayList<EnumEntry>();
        
        int[] value = new int[1];
        for (int n=0; n<size; n++) {
            String name = DatatypeLib.H5Tget_member_name(this.hid, n);
            assert name != null : "Untrapped error in native code.";            
            int status = DatatypeLib.H5Tget_member_value(this.hid, n, value); // FIXME get entries will break if someone defines a large enumerated value. 
            assert status >=0 : "Untrapped error in native code.";
            entries.add(new EnumEntry(name, value[0]));
        }
        
        return (entries);        
    }
    
    public List<String> getNames() {
        assert this.isInitialized() : "Native reference not initialized";
        
        int size = DatatypeLib.H5Tget_nmembers(this.hid);        
        assert size >= 0 : "Untrapped error in native code.";
        ArrayList<String> entries = new ArrayList<String>();
        
        for (int n=0; n<size; n++) {
            String name = DatatypeLib.H5Tget_member_name(this.hid, n);
            assert name != null : "Untrapped error in native code.";                        
            entries.add(name);
        }
        
        return (entries);        
    }
    
}
