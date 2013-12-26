package org.techniche.technothlon.katana.tcd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.techniche.technothlon.katana.R;

import static org.techniche.technothlon.katana.tcd.TCDContent.ITEMS;

/**
 * Created by Rahul Kadyan on 25/12/13.
 * Part of org.techniche.technothlon.katana.tcd
 */
public class TCDListAdapter extends ArrayAdapter<TCDContent.TCDQuestionMini> {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int NONE = 2;
    public static int direction = UP;
    public static int firstVisible = -1;
    private Context context;

    public TCDListAdapter(Context context, int resource) {
        super(context, resource, ITEMS);
        this.context = context;
    }

    @Override
    public int getCount() {
        return ITEMS.size();
    }

    @Override
    public TCDContent.TCDQuestionMini getItem(int position) {
        return ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TCDContent.TCDHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_single, parent, false);
            holder = new TCDContent.TCDHolder();
            assert convertView != null;
            holder.id = (TextView) convertView.findViewById(R.id.tcd_id);
            holder.title = ((TextView) convertView.findViewById(R.id.tcd_title));
            holder.question = ((TextView) convertView.findViewById(R.id.tcd_question));
            holder.status = ((TextView) convertView.findViewById(R.id.tcd_status));
            convertView.setTag(holder);
        }

        TCDContent.TCDQuestion question = TCDContent.ITEM_MAP.get(ITEMS.get(position).id);
        holder = (TCDContent.TCDHolder) convertView.getTag();
        if (question != null) {
            holder.id.setText("#" + question.id);
            holder.title.setText(question.title);
            holder.question.setText(question.question);
            holder.status.setText(question.getStatus());
            holder.id.setBackgroundResource(question.color);
        }


        int animId = R.anim.up_from_bottom;
        if (position > firstVisible) animId = R.anim.up_from_bottom;
        else if (position < firstVisible) animId = R.anim.down_from_top;

        Animation animation = AnimationUtils.loadAnimation(getContext(), animId);
        assert animation != null;
        convertView.startAnimation(animation);

        return convertView;
    }
}
