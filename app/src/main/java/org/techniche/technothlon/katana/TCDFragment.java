package org.techniche.technothlon.katana;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import org.techniche.technothlon.katana.tcd.TCDContent;
import org.techniche.technothlon.katana.tcd.TCDListAdapter;


public class TCDFragment extends Fragment implements AbsListView.OnItemClickListener, AbsListView.OnScrollListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private OnTCDInteractionListener mListener;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    public TCDListAdapter mAdapter;
    private int mLastFirstVisibleItem = -1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TCDFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TCDListAdapter(getActivity(), R.layout.item_list_single);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        Log.d("TCD", TCDContent.ITEMS.size() + " objects in it");

        // Set the adapter
        assert view != null;
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        assert mListView != null;
        mListView.setAdapter((mAdapter));

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTCDInteractionListener) activity;
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void update(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onTCDInteraction(position);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            assert emptyView != null;
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        if (firstVisibleItem > mLastFirstVisibleItem) TCDListAdapter.direction = TCDListAdapter.DOWN;
        else if (firstVisibleItem < mLastFirstVisibleItem) TCDListAdapter.direction = TCDListAdapter.UP;
        else TCDListAdapter.direction = TCDListAdapter.NONE;
        TCDListAdapter.firstVisible = mLastFirstVisibleItem = firstVisibleItem;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTCDInteractionListener {
        public void onTCDInteraction(int id);
    }

}

//TODO - Support photos & videos in Techno Coup D'oœil
