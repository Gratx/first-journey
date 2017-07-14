package co.ginx.mt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.ginx.mt.Database.MtDbHelper;
import co.ginx.mt.Model.RekeningDto;

/**
 * Created by user-02 on 14/07/17.
 */

public class InsertRekActivity extends AppCompatActivity{

    MtDbHelper mDbHelper;
    Button mBtnSave;
    EditText txtNamaRek,txtNoRek;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new MtDbHelper(this);
        setContentView(R.layout.insert_rek);
        mBtnSave = (Button)findViewById(R.id.btn_save);
        txtNamaRek = (EditText)findViewById(R.id.nama_rekening);
        txtNoRek = (EditText)findViewById(R.id.no_rekening);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNoRek.getText().toString().length()!=10){
                    Toast.makeText(getApplicationContext(),"No Rekening anda tidak valid",Toast.LENGTH_SHORT);
                }else{
                    RekeningDto dto = new RekeningDto();
                    dto.no_rekening = txtNoRek.getText().toString();
                    dto.nama_rekening = txtNamaRek.getText().toString().toUpperCase();
                    Toast.makeText(InsertRekActivity.this, "save : "+mDbHelper.insertRekening(dto), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
