package co.ginx.mt.Model;

/**
 * Created by user-02 on 05/07/17.
 */

public class RekeningDto {
    public int id;
    public String no_rekening;
    public String nama_rekening;

    @Override
    public String toString() {
        return no_rekening+" - "+nama_rekening;
    }
}
