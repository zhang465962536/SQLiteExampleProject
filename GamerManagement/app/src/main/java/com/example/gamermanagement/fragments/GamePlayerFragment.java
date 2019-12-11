package com.example.gamermanagement.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamermanagement.R;
import com.example.gamermanagement.entity.GamePlayer;

import java.util.ArrayList;

//【5】显示 玩家列表 和 长按Item 显示对话框实现
public class GamePlayerFragment extends Fragment {

    private GamePlayerFragmentListener gamePlayerFragmentListener;
    private GamePlayerAdapter gamePlayerAdapter;

    //使用创建接口回调
    public static interface GamePlayerFragmentListener{
        public void showGamePlayerFragment();
        public void showUpdateFragment(int id);
        public void delete(int id);
        public ArrayList<GamePlayer> findAll();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            gamePlayerFragmentListener = (GamePlayerFragmentListener) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    //解除添加
    @Override
    public void onDetach() {
        super.onDetach();
        gamePlayerFragmentListener = null;
    }

    public static GamePlayerFragment newInstance(){
        GamePlayerFragment fragment = new GamePlayerFragment();
        return fragment;
    }

    public GamePlayerFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //一调用该fragment 就显示数据库中 已有的数据
        ArrayList<GamePlayer> gamePlayers = gamePlayerFragmentListener.findAll();
        gamePlayerAdapter = new GamePlayerAdapter(getActivity(),gamePlayers);
    }

    //实例化的时候 显示列表所有内容
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_player,container,false);
        ListView listView = view.findViewById(R.id.listView);
        registerForContextMenu(listView); //listview 注册上下文 以后长按Item 有用
        listView.setAdapter(gamePlayerAdapter);
        return view;
    }

    //长按listView Item 出现菜单项
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("修改/删除");
        menu.setHeaderIcon(android.R.drawable.ic_menu_edit);
        getActivity().getMenuInflater().inflate(R.menu.listview_context_menu,menu);
    }

    //菜单项的点击事件
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
          case R.id.delete_menu:
              //菜单信息对象  targetView即选中的item
              AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
              TextView textView_id = info.targetView.findViewById(R.id.tv_id);
              int id = Integer.parseInt(textView_id.getText().toString());
              gamePlayerFragmentListener.delete(id);
              //删除某条Item 后 刷新界面
              changedData();
              break;

          case R.id.update_menu:
              info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
              textView_id = info.targetView.findViewById(R.id.tv_id);
              id = Integer.parseInt(textView_id.getText().toString());
              gamePlayerFragmentListener.showUpdateFragment(id);
              break;
      }
        return super.onContextItemSelected(item);
    }

    //重新查询 适配器重新加载
    public void changedData() {
        gamePlayerAdapter.setGamePlayers(gamePlayerFragmentListener.findAll());
        gamePlayerAdapter.notifyDataSetChanged();
    }

    private static class GamePlayerAdapter extends BaseAdapter{

        private Context context;
        private ArrayList<GamePlayer> gamePlayers;

        //listView 改变得时候 重写设置列表
        public void setGamePlayers(ArrayList<GamePlayer> gamePlayers){
            this.gamePlayers = gamePlayers;
        }

        public GamePlayerAdapter(Context context,ArrayList<GamePlayer> gamePlayers){
            this.context = context;
            this.gamePlayers = gamePlayers;
        }

        @Override
        public int getCount() {
            return gamePlayers.size();
        }

        @Override
        public Object getItem(int position) {
            return gamePlayers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.game_player_list_item_layout,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_id = convertView.findViewById(R.id.tv_id);
                viewHolder.tv_player = convertView.findViewById(R.id.textView_player);
                viewHolder.tv_score = convertView.findViewById(R.id.textView_score);
                viewHolder.tv_level = convertView.findViewById(R.id.textView_level);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            GamePlayer gamePlayer = gamePlayers.get(position);
            viewHolder.tv_id.setText(String.valueOf(gamePlayer.getId()));
            viewHolder.tv_player.setText(gamePlayer.getPlayer());
            viewHolder.tv_score.setText(String.valueOf(gamePlayer.getScore()));
            viewHolder.tv_level.setText(String.valueOf(gamePlayer.getLevel()));
            return convertView;
        }

    }

    private static class ViewHolder{
        TextView tv_id;
        TextView tv_player;
        TextView tv_score;
        TextView tv_level;
    }
}
