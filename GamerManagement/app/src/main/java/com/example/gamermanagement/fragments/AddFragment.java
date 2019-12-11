package com.example.gamermanagement.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gamermanagement.R;
import com.example.gamermanagement.entity.GamePlayer;
//【6】创建添加对话框 进行添加操作
public class AddFragment extends DialogFragment {

    private AddFragmentListener addFragmentListener;

    //回调接口  与Activity 进行交互
    public static interface AddFragmentListener{
        public void add(GamePlayer gamePlayer);
    }

    public AddFragment(){}

    public static AddFragment newInstance(){
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addFragmentListener = (AddFragmentListener) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       final View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_gameplayer_dialog,null);

        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_input_add)
                .setView(view)
                .setTitle("新增游戏玩家")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_player = view.findViewById(R.id.ediText_player);
                        EditText et_score = view.findViewById(R.id.ediText_score);
                        EditText et_level = view.findViewById(R.id.ediText_level);
                        GamePlayer gamePlayer = new GamePlayer();
                        gamePlayer.setPlayer(et_player.getText().toString());
                        gamePlayer.setScore(Integer.parseInt(et_score.getText().toString()));
                        gamePlayer.setLevel(Integer.parseInt(et_level.getText().toString()));
                        addFragmentListener.add(gamePlayer);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
