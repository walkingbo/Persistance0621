package com.ssb.persistance0621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //어노니머스나 람다에서 사용할 수 있도록 final로 변수를 생성
        final EditText edit =(EditText)findViewById(R.id.edit);

        //파일을 생성해서 데이터를 저장하기 위한 버튼의 클릭 이벤트 처리
        Button savebtn = (Button)findViewById(R.id.savebtn);
        savebtn.setOnClickListener((view)->{
            //try~resources 구문
            //( )안에서 만든 객체는 close( )를 하지 않아도 됩니다.
            try(FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE)){
                fos.write("안드로이드 파일 출력".getBytes());
                fos.flush();
                edit.setText("저장성공");
            }catch (Exception e){
                Log.e("파일저장상실패",e.getMessage());
            }
        });

        Button readbtn = (Button)findViewById(R.id.readbtn);
        readbtn.setOnClickListener((view)->{
            try(FileInputStream fis = openFileInput("test.txt")){
                //파일의 내용을 읽을 저장 공간 만들기
                byte [] buf = new byte[fis.available()];
                //파일의 내용 읽기
                fis.read(buf);
                edit.setText(new String(buf));
            }catch (Exception e){
                Log.e("파일읽기 실패",e.getMessage());
            }
        });

        Button deletebtn =(Button)findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener((view) -> {
          boolean result = deleteFile("test.txt");
          if(result){
              edit.setText("삭제성공");
          }else {
              edit.setText("삭제실패");
          }
        });

        Button resourcebtn =(Button)findViewById(R.id.resourcebtn);
        resourcebtn.setOnClickListener((view)->{

            try(InputStream fis = getResources().openRawResource(R.raw.log)) {
                byte [] buf = new byte[fis.available()];
                fis.read(buf);
                edit.setText(new String(buf));
            }catch (Exception e){
                Log.e("리소스 파일 읽기 오류 ",e.getMessage());
            }

        });

    }
}
