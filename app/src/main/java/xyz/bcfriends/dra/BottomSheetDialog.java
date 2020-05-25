package xyz.bcfriends.dra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{

    private ImageButton feelingBad;
    private ImageButton feelingSad;
    private ImageButton feelingNormal;
    private ImageButton feelingGood;
    private ImageButton feelingNice;

    public static BottomSheetDialog getInstance() { return new BottomSheetDialog(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container,false);

        // TODO : ADD Listener

//        feelingBad.setOnClickListener(this);
//        feelingSad.setOnClickListener(this);
//        feelingNormal.setOnClickListener(this);
//        feelingGood.setOnClickListener(this);
//        feelingNice.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        int feeling = 0;

        switch (v.getId()){
            case R.id.feeling_bad:
                feeling = 1;
                break;
            case R.id.feeling_sad:
                feeling = 2;
                break;
            case R.id.feeling_normal:
                feeling = 3;
                break;
            case R.id.feeling_good:
                feeling = 4;
                break;
            case R.id.feeling_nice:
                feeling = 5;
                break;
            default:
                break;
        }

        dataBaseHelper.insertDepressStatus(feeling);

        dismiss();
    }
}
