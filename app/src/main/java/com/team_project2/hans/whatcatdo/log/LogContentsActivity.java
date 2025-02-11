package com.team_project2.hans.whatcatdo.log;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.team_project2.hans.whatcatdo.R;
import com.team_project2.hans.whatcatdo.database.Emotion;
import com.team_project2.hans.whatcatdo.database.LogDBManager;
import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.text.SimpleDateFormat;

public class LogContentsActivity extends AppCompatActivity {

    LogEmotion logEmotion;
    TextView text_cont_date;
    TextView text_cont_time;
    TextView text_cont_comment;
    TextView text_cont_emotion;
    Button btn_delete;

    ImageView img_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_contents);
        getSupportActionBar().hide();

        logEmotion = (LogEmotion)getIntent().getSerializableExtra("logEmotion");

        text_cont_comment = findViewById(R.id.text_cont_comment);
        text_cont_date = findViewById(R.id.text_cont_date);
        text_cont_time = findViewById(R.id.text_cont_time);

        text_cont_emotion = findViewById(R.id.text_cont_emotion);

        img_cont = findViewById(R.id.img_cont);
        btn_delete = findViewById(R.id.btn_delete);

        initData();

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"정말 지우시겠습니까?",Snackbar.LENGTH_LONG).setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogDBManager db = new LogDBManager(LogContentsActivity.this);
                        db.delete(logEmotion.getTimestamp());
                        finish();
                    }
                }).show();

            }
        });
    }

    void initData(){
        if(logEmotion.getComment()!=null)
            text_cont_comment.setText(logEmotion.getComment());
        setTimeText();
        setBitmapImage();
        setEmotionText();
    }


    void setTimeText(){
        String date =  new SimpleDateFormat("yyyy년 MM월 dd일").format(logEmotion.getTimestamp());
        text_cont_date.setText(date);

        String time =  new SimpleDateFormat("HH시 mm분 SS초").format(logEmotion.getTimestamp());
        text_cont_time.setText(time);
    }

    void setBitmapImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(logEmotion.getPath());
        img_cont.setImageBitmap(bitmap);

    }

    void setEmotionText(){
        String str = "";
        for(Emotion e : logEmotion.getEmotions()){
            str += e.getTitle() +" -  "+ e.getPercent()*100.2f + "%\n";//정확도 출력할때 소숫점 2번째 자리까지만 출력.
        }
        text_cont_emotion.setText(str);
    }
}
