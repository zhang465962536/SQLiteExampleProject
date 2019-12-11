package com.example.gamermanagement.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamermanagement.R;
import com.example.gamermanagement.entity.GamePlayer;
//【7】更新界面 进行更新操作
public class UpDataFragment extends Fragment implements View.OnClickListener {

    private UpdateFragmentListener updateFragmentListener;
    private GamePlayer gamePlayer;
    private EditText et_player;
    private EditText et_score;
    private EditText et_level;
    private TextView tv_id;


    public static interface UpdateFragmentListener {
        public void update(GamePlayer gamePlayer);

        public GamePlayer findById(int id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            updateFragmentListener = (UpdateFragmentListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        updateFragmentListener = null;
    }

    public UpDataFragment() {
    }

    //看看是更新哪条记录
    public static UpDataFragment newInstance(int id) {
        UpDataFragment upDataFragment = new UpDataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        upDataFragment.setArguments(bundle);
        return upDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getArguments().getInt("id");
        gamePlayer = updateFragmentListener.findById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updata, container, false);
        tv_id = view.findViewById(R.id.textView_id);
        et_player = view.findViewById(R.id.ediText_player);
        et_score = view.findViewById(R.id.ediText_score);
        et_level = view.findViewById(R.id.ediText_level);
        Button btn_updata = view.findViewById(R.id.btn_updata);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_updata.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        et_player.setText(gamePlayer.getPlayer());
        et_score.setText(String.valueOf(gamePlayer.getScore()));
        et_level.setText(String.valueOf(gamePlayer.getLevel()));
        tv_id.setText(String.valueOf(gamePlayer.getId()));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_updata:
                save();
                break;
            case R.id.btn_cancel:
                //返回上一个fragment  回退栈 创建fragment的时候 把它们加入栈中
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    //更新后执行保存操作
    private void save() {
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setId(Integer.valueOf(tv_id.getText().toString()));
        gamePlayer.setPlayer(et_player.getText().toString());
        gamePlayer.setScore(Integer.parseInt(et_score.getText().toString()));
        gamePlayer.setLevel(Integer.parseInt(et_level.getText().toString()));
        updateFragmentListener.update(gamePlayer);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
