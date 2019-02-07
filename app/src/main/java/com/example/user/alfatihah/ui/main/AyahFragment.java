package com.example.user.alfatihah.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.alfatihah.R;
import com.example.user.alfatihah.model.DataModel;

public class AyahFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ayah_fragment, container, false);

        TextView number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda;

        number = view.findViewById(R.id.number);
        text = view.findViewById(R.id.text);
        numberInSurah = view.findViewById(R.id.numberInSurah);
        juz = view.findViewById(R.id.juz);
        manzil = view.findViewById(R.id.manzil);
        page = view.findViewById(R.id.page);
        ruku = view.findViewById(R.id.ruku);
        hizbQuarter = view.findViewById(R.id.hizbQuarter);
        sajda = view.findViewById(R.id.sajda);

        Bundle bundle = getArguments();
        DataModel ayah = (DataModel) bundle.getSerializable("ayah");

        number.setText(ayah.getNumber());
        text.setText(ayah.getText());
        numberInSurah.setText(ayah.getNumberInSurah());
        juz.setText(ayah.getJuz());
        manzil.setText(ayah.getManzil());
        page.setText(ayah.getPage());
        ruku.setText(ayah.getRuku());
        hizbQuarter.setText(ayah.getHizbQuarter());
        sajda.setText(ayah.getSajda());

        return view;
    }
}
