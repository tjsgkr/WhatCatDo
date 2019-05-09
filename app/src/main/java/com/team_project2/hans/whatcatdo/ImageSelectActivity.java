package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ImageSelectActivity extends AppCompatActivity {
    private static final String TAG = "IMAGE SELECT ACTIVITY";

    private static final int REQUEST_CODE = 1;

    /*layout component*/
    private ImageView img_select;
    private Button btn_select;
    private Button btn_analyze_picture;
    private TextView text_score;



    /*image buffer*/
    private boolean isSelect = false;
    private Bitmap img;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        img_select = findViewById(R.id.img_select);
        btn_select = findViewById(R.id.btn_select);
        btn_analyze_picture = findViewById(R.id.btn_analyze_picture);
        text_score = findViewById(R.id.text_score);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallary();
            }
        });

        btn_analyze_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelect)
                    startActivity(IntentBitmap(img_select));
            }
        });
    }

    /**
     * 비트맵을 인자로 가지는 Intent를 생성하는 메소드.
     * */
    Intent IntentBitmap(ImageView imageView){
        Intent intent = new Intent(ImageSelectActivity.this,ImageResultActivity.class);
        Bitmap bitmap = ImageViewToBitmap(imageView,Common.INPUT_SIZE);
        intent.putExtra("bitmap",bitmap);
        intent.putExtra("path",path);
        return intent;
    }

    /**
     * 이미지뷰의 이미지를 비트맵으로 변환하는 메소드 입니다
     * */
    Bitmap ImageViewToBitmap(ImageView imageView,int INPUT_SIZE){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
    }


    /**
     *갤러리를 여는 메소드 입니다
     * */
    void openGallary(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * 갤러리에서 불러온 이미지를 screen에 저장, 출력하는 메소드.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());

                    path = data.getData().getPath();
                    Uri aa2 = data.getData();
                    String path = getRealPathFromURI(aa2);
                    Log.d(TAG,path);
                    img = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    img_select.setImageBitmap(img);
                    isSelect = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    public String getRealPathFromURI(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(uri, proj, null, null, null);
        int index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        c.moveToFirst();
        String path = c.getString(index);

        return path;

    }

}
