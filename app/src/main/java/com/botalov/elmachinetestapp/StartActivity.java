package com.botalov.elmachinetestapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.botalov.elmachinetestapp.adapters.RecyclePhotoAdapter;
import com.botalov.elmachinetestapp.helpers.InternetHelper;
import com.botalov.elmachinetestapp.helpers.PermissionsHelper;
import com.botalov.elmachinetestapp.models.AnswerFromServerModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {

    private OkHttpClient httpClient;
    private RecyclerView recyclerView;
    private Context thisContext;
    private RecyclePhotoAdapter recyclePhotoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Проверка наличия необходимых разрешений для приложения
        PermissionsHelper.verifyStoragePermissions(this);

        thisContext = this;


        recyclerView = (RecyclerView)findViewById(R.id.photosContainer);
        if(recyclerView!=null){
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }

    }


    public void startClick(View view){
        try{
            if(InternetHelper.IsNetworkAvailable(thisContext)) {
                view.setVisibility(View.GONE);
                getPhotosFromServer();
            }
            else{
                Toast.makeText(thisContext, getString(R.string.internet_error), Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Получение изображений от сервера
     */
    private void getPhotosFromServer(){
        try {
            httpClient = new OkHttpClient.Builder().build();

            Request request = new Request.Builder()
                    .url(getString(R.string.downloadUrl))
                    .header("Authorization", getString(R.string.client_id))
                    .header("User-Agent", getString(R.string.user_agent))
                    .build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final AnswerFromServerModel answerFromServerModel = parseAnswer(response.body().string());
                    if(answerFromServerModel!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclePhotoAdapter = new RecyclePhotoAdapter(answerFromServerModel.getPhotos(), thisContext);
                                recyclerView.setAdapter(recyclePhotoAdapter);
                                recyclerView.invalidate();

                                SearchView searchView = (SearchView)findViewById(R.id.searchView);
                                if(searchView!=null){
                                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                        @Override
                                        public boolean onQueryTextSubmit(String query) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onQueryTextChange(String newText) {
                                            if(recyclerView!=null && recyclePhotoAdapter!=null){
                                                recyclePhotoAdapter.getFilter().filter(newText);
                                            }

                                            return false;
                                        }
                                    });
                                }
                            }
                        });
                    }

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private AnswerFromServerModel parseAnswer(String responseString){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseString, AnswerFromServerModel.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
