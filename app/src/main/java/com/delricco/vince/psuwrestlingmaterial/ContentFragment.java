package com.delricco.vince.psuwrestlingmaterial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContentFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public ContentFragment() {
    }

    public static ContentFragment newInstance(int sectionNumber) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        try {
            ((OnSectionSelectedListener) getActivity()).onSectionSelected(sectionNumber, rootView);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }

        return rootView;
    }

    public interface OnSectionSelectedListener {
        void onSectionSelected(int section, View view);
    }
}
