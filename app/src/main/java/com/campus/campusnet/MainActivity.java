package com.campus.campusnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registe(View view) {
        IRequest.obtain()
                .action(1)
                .add("name", "william")
                .add("sex", "male")
                .next(MODE.CAMPUTATION, new ICallback() {
                    @Override
                    public IRequest next(IResponse response) {
                        if(response.getException() != null){

                            return null;
                        }

                        IRequest request = IRequest.obtain()
                                .action(2)
                                .initParams(response.getData())
                                .add("account", response.getString("account"))
                                .add("password", response.getString("password"));
                        return request;
                    }
                }).next(MODE.IO, MODE.UI, new ICallback() {
                    @Override
                    public IRequest next(IResponse response) {
                        if(response.getException() == null){
                            Toast.makeText(getApplicationContext()
                                    ,"name:" + response.getString("name") + " account:" + response.getString("account")
                                    , Toast.LENGTH_SHORT)
                                    .show();
                        }
                        return null;
                    }
                }).submit();
    }

}
