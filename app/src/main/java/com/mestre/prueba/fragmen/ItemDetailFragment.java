package com.mestre.prueba.fragmen;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.mestre.prueba.fragmen.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        final Context context = getActivity();

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.nombre)).setText(mItem.nombre);
            String pros="";
            for(int i=0;i<mItem.productos.length;i++){
                if(i!=0)
                    pros+=" - ";
                pros+= mItem.productos[i];
            }
            ((TextView) rootView.findViewById(R.id.productos)).setText(pros);
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(android.R.drawable.progress_indeterminate_horizontal);

            ImageRequest imageRequest = new ImageRequest(mItem.image,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            ((ImageView) rootView.findViewById(R.id.image)).setImageBitmap(response);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener(){
                        public void onErrorResponse(VolleyError error){
                            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(android.R.drawable.stat_notify_error);
                            Toast.makeText(context, "Not image" , Toast.LENGTH_SHORT).show();
                        }
                    });

            MySingleton.getInstance(getActivity()).addRequestQueue(imageRequest);
        }

        return rootView;
    }
}
