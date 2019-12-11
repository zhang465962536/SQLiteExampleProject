package com.example.gamermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gamermanagement.db.DatabaseAdapter;
import com.example.gamermanagement.entity.GamePlayer;
import com.example.gamermanagement.fragments.AddFragment;
import com.example.gamermanagement.fragments.GamePlayerFragment;
import com.example.gamermanagement.fragments.UpDataFragment;

import java.util.ArrayList;

//【8】在程序入口 开始调用
public class MainActivity extends AppCompatActivity implements AddFragment.AddFragmentListener, GamePlayerFragment.GamePlayerFragmentListener, UpDataFragment.UpdateFragmentListener {

    private DatabaseAdapter dbAdapter;
    private GamePlayerFragment gamePlayerFragment;
    private UpDataFragment upDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAdapter = new DatabaseAdapter(this);
        showGamePlayerFragment();
    }

    //在顶部栏 添加按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //点击添加按钮 进行操作
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                AddFragment createGamePlayerFragment = AddFragment.newInstance();
                createGamePlayerFragment.show(getSupportFragmentManager(),null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //按后退键操作逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getSupportFragmentManager().getBackStackEntryCount() == 1){
                finish();
                return true;
            }else {
                getSupportFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //添加玩家操作
    @Override
    public void add(GamePlayer gamePlayer) {
        dbAdapter.add(gamePlayer);
        gamePlayerFragment.changedData();
    }

    //显示玩家列表Fragment操作
    @Override
    public void showGamePlayerFragment() {
        gamePlayerFragment = GamePlayerFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_layout,gamePlayerFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    //显示更新玩家Fragment操作
    @Override
    public void showUpdateFragment(int id) {
        upDataFragment = UpDataFragment.newInstance(id);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_layout,upDataFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    //在玩家列表Fragment删除玩家操作
    @Override
    public void delete(int id) {
        dbAdapter.delete(id);
        gamePlayerFragment.changedData();
    }

    //查询所有在玩家列表Fragment显示玩家操作
    @Override
    public ArrayList<GamePlayer> findAll() {
        return dbAdapter.findAll();
    }

    //在更新Fragment更改玩家属性操作
    @Override
    public void update(GamePlayer gamePlayer) {
        dbAdapter.update(gamePlayer);
        gamePlayerFragment.changedData(); //重新加载适配器 更新界面
    }

    //更新玩家时 单个查询玩家信息操作
    @Override
    public GamePlayer findById(int id) {
        return dbAdapter.findById(id);
    }
}
