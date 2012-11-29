package searchEngine;

import javax.swing.ImageIcon;

public class SearchEngine{
    public final String name;
    public final String url;
    public final ImageIcon favicon;
    public SearchEngine(String name, String url, ImageIcon icon) {
        this.name    = name;
        this.url     = url;
        this.favicon = icon;
    }
    
    @Override 
    public String toString() {
        return name;
    }
}