
package uja.meta.utils;

/**
 *
 * @author Carlos
 */
public class Pair {
    private int l=-1;
    private int r=-1;
    public Pair(int l, int r){
        this.l = l;
        this.r = r;
    }
    public int getL(){ return l; }
    public int getR(){ return r; }
    public void setL(int l){ this.l = l; }
    public void setR(int r){ this.r = r; }
}
