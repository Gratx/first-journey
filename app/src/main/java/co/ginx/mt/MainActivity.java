package co.ginx.mt;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import co.ginx.mt.Database.MtDbHelper;
import co.ginx.mt.Model.RekeningDto;

public class MainActivity extends AppCompatActivity {

    MtDbHelper mDbHelper;
    private TextView mRek;
    private Button mBtnGetRek,mBtnInsertRek;

    private Spinner mSpinner;
    private ArrayAdapter<RekeningDto> mAdapterRek;
    private List<RekeningDto> mListStore = new ArrayList<RekeningDto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new MtDbHelper(this);
        mBtnGetRek = (Button)(findViewById(R.id.btn_getRekening));
        mBtnInsertRek = (Button)(findViewById(R.id.btn_insert));
        mRek = (TextView)findViewById(R.id.txt_rek);
        mSpinner = (Spinner)findViewById(R.id.spin_rek);

        mAdapterRek = new ArrayAdapter<RekeningDto>(this,android.R.layout.simple_list_item_1,mListStore);
        mAdapterRek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapterRek);
        mBtnInsertRek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mBtnGetRek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur = mDbHelper.getDataRekening();
                if(cur.getCount()>0){
                    mListStore.clear();
                    while (cur.moveToNext()){
                        RekeningDto dto = new RekeningDto();
                        dto.id = cur.getInt(0);
                        dto.no_rekening = cur.getString(1);
                        dto.nama_rekening = cur.getString(2);
                        mListStore.add(dto);
                    }
                    mAdapterRek.notifyDataSetChanged();
                }
                cur.close();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RekeningDto al = (RekeningDto) mSpinner.getSelectedItem();
                Log.e("will","spinner selected : "+al.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                RekeningDto al = (RekeningDto) mSpinner.getSelectedItem();
                Log.e("will","spinner zonk selected : "+al.toString());
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setupTransparentSystemBarsForLmp(getWindow());
    }

    public static boolean isLmpOrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Sets up transparent navigation and status bars in LMP. This method is a
     * no-op for other platform versions.
     */
    @TargetApi(19)
    public static void setupTransparentSystemBarsForLmp(Window window) {
        // TODO(sansid): use the APIs directly when compiling against L sdk.
        // Currently we use reflection to access the flags and the API to set
        // the transparency
        // on the System bars.
        if (isLmpOrAbove()) {
            try {
                //getWindow().clearFlags(
                window.clearFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //getWindow().getDecorView().setSystemUiVisibility(
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                Field drawsSysBackgroundsField = WindowManager.LayoutParams.class
                        .getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
                //getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
                window.addFlags(drawsSysBackgroundsField.getInt(null));

                Method setStatusBarColorMethod = Window.class
                        .getDeclaredMethod("setStatusBarColor", int.class);
                Method setNavigationBarColorMethod = Window.class
                        .getDeclaredMethod("setNavigationBarColor", int.class);
                //setStatusBarColorMethod.invoke(getWindow(), Color.TRANSPARENT);
                setStatusBarColorMethod.invoke(window, Color.TRANSPARENT);
                //setNavigationBarColorMethod.invoke(getWindow(),
                setNavigationBarColorMethod.invoke(window,
                        Color.TRANSPARENT);
            } catch (NoSuchFieldException e) {

            } catch (NoSuchMethodException ex) {

            } catch (IllegalAccessException e) {

            } catch (IllegalArgumentException e) {

            } catch (InvocationTargetException e) {

            } finally {
            }
        }

    }
}
