package com.hyeung.sendapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        final String url = "연동할 주소";

        final EditText editText = (EditText)findViewById(R.id.inputNb);
        Button button1 = (Button) findViewById(R.id.sendBtn) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().length()!=0){
                    final Integer num = Integer.parseInt(editText.getText().toString());

                    addNum(url, num);
                }else{
                    Toast.makeText(getBaseContext(), "숫자를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getNumList(url);
    }


    public void getNumList(final String url){
        List<Integer> numList = new ArrayList<Integer>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.getNumList();
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();

                if(repo.getReCd() !=null){
                    Log.d("★★★result★★★",repo.getReCd());
                    List<Integer> numList = repo.getNumList();
                    if(numList.size()>0){
                        setTbNum(url, numList);
                    }else{
                        Toast.makeText(getBaseContext(), "숫자 리스트가 비었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "서버와 연결이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {

            }
        });
    }

    public void numDelete(final String url, final int num){
        final EditText editText = (EditText)findViewById(R.id.inputNb);

        String jsonString = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.delNum(num);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                Log.d("★★★result★★★",repo.getReCd());
                if("01".equals(repo.getReCd())) {
                    Toast.makeText(getBaseContext(), num + " 삭제 성공", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }else if("02".equals(repo.getReCd())){
                    Toast.makeText(getBaseContext(), " 삭제할 대상이 비어있음", Toast.LENGTH_SHORT).show();
                }else{ // 03
                    Toast.makeText(getBaseContext(), " 리스트가 비어있음", Toast.LENGTH_SHORT).show();
                }
                final TableLayout tableLayout = (TableLayout) findViewById(R.id.numTb); // 테이블 id 명
                tableLayout.removeAllViews();
                final List<Integer> numList = repo.getNumList();
                if(numList.size()>0){
                    setTbNum(url, numList);
               }
            }

            @Override
            public void onFailure(Call<RetrofitRepo> call, Throwable t) {

            }
        });
    }
    public void addNum(final String url, final int num) {
        String jsonString = null;
        final EditText editText = (EditText) findViewById(R.id.inputNb);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<RetrofitRepo> call = retrofitService.addNum(num);
        call.enqueue(new Callback<RetrofitRepo>() {
            @Override
            public void onResponse(Call<RetrofitRepo> call, Response<RetrofitRepo> response) {
                RetrofitRepo repo = response.body();
                Log.d("★★★result★★★", repo.getReCd());
                List<Integer> numList = null;

                if ("01".equals(repo.getReCd())) {
                    Toast.makeText(getBaseContext(), num + " 전송 성공", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    numList = repo.getNumList();
                    Log.d("numList >>",numList.toString());
                    if (numList.size() > 0) {
                        setTbNum(url, numList);
                    }
                } else if ("03".equals(repo.getReCd())) {
                    Toast.makeText(getBaseContext(), num + " 번호는 이미 있는번호입니다.", Toast.LENGTH_SHORT).show();
                    numList = repo.getNumList();

                    if (numList.size() > 0) {
                        setTbNum(url, numList);
                    }
                }
            }
            @Override
            public void onFailure (Call < RetrofitRepo > call, Throwable t){

            }

        });
    }
    public void setTbNum (final String url, final List<Integer> numList){
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.numTb); // 테이블 id 명
        tableLayout.removeAllViews();
        final AlertDialog.Builder oDialog = new AlertDialog.Builder(MainActivity.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        // Creation row
        TableRow tableRow = new TableRow(getBaseContext());


        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
        tableRowParams.setMargins(0,30,0,30);
        tableRow.setLayoutParams(tableRowParams);


        for (int i = 0; i < numList.size(); i++) {
            final Integer temp = i;
            final TextView text = new TextView(getBaseContext());
            text.setTextSize(40);

            text.setText(numList.get(i) + " ");
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text.setLongClickable(true);
            text.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            oDialog.setMessage(numList.get(temp) + " 번호를 삭제하시겠습니까?")
                                    .setTitle("삭제")
                                    .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.i("Dialog", "취소");
                                            Toast.makeText(getApplicationContext(), "삭제 취소", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .setNeutralButton("예", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            numDelete(url,numList.get(temp));
                                        }
                                    })
                                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                                    .show();
                            return true;
                        }
                    });
            tableRow.addView(text);

            if (numList.size() ==1 || (i + 1) % 4 == 0) {
                tableLayout.addView(tableRow);
                tableRow = new TableRow(getBaseContext());
                tableRowParams=
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);

                tableRowParams.setMargins(0,30,0,30);
                tableRow.setLayoutParams(tableRowParams);
            }
            if (i == numList.size() - 1) {
                tableLayout.addView(tableRow);
            }
        }

    }
}

