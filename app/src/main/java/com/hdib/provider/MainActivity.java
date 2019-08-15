package com.hdib.provider;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hdib.provider.bean.Fruit;
import com.hdib.provider.bean.User;
import com.hdib.provider.core.Common;
import com.hdib.provider.helper.callback.Callback;
import com.hdib.provider.helper.callback.EntityListCallback;
import com.hdib.provider.helper.repo.FruitRepo;
import com.hdib.provider.helper.repo.UserRepo;

import java.util.List;
import java.util.Random;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private UserRepo userRepo;
    private FruitRepo fruitRepo;
    private Callback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userRepo = new UserRepo(this);
        fruitRepo = new FruitRepo(this);
        callback = new Callback() {
            @Override
            public void callback(int result) {
                Toast.makeText(getApplicationContext(), result + "", Toast.LENGTH_SHORT).show();
            }
        };

        getContentResolver().registerContentObserver(Common.URI.TABLE_USER_URI, true, new ContentObserver(new Handler()) {
            public void onChange(boolean selfChange) {
                LogUtil.d("变了。。。");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        userRepo.quit();
        fruitRepo.quit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_result:
                userRepo.queryAll(new EntityListCallback<User>() {
                    @Override
                    public void callback(List<User> list) {
                        Intent intent = new Intent();
                        intent.putExtra("RESULT", list.toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                break;
            case R.id.btn_query_user:
                userRepo.queryAll(new EntityListCallback<User>() {
                    @Override
                    public void callback(List<User> list) {
                        Toast.makeText(getApplicationContext(), list + "", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_add_user:
                String name = UUID.randomUUID().toString();
                int age = new Random(System.currentTimeMillis()).nextInt(10) + 15;
                userRepo.insert(new User(name, age), callback);
                break;
            case R.id.btn_update_user:
                userRepo.updateColumnByRow(1,
                        Common.Columns.UserColumns.AGE,
                        String.valueOf(new Random(System.currentTimeMillis()).nextInt(10) + 15),
                        callback);
                break;
            case R.id.btn_delete_user:
                userRepo.deleteByAge(23, callback);
                break;
            case R.id.btn_query_fruit:
                fruitRepo.queryAll(new EntityListCallback<Fruit>() {
                    @Override
                    public void callback(List<Fruit> list) {
                        Toast.makeText(getApplicationContext(), list + "", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_add_fruit:
                fruitRepo.insert(new Fruit("苹果", "red"), callback);
                break;
            case R.id.btn_update_fruit:
                fruitRepo.updateColumnByRow(1, Common.Columns.FruitColumns.COLOR, "yellow", callback);
                break;
            case R.id.btn_delete_fruit:
                fruitRepo.deleteByColor("red", callback);
                break;
        }
    }
}
