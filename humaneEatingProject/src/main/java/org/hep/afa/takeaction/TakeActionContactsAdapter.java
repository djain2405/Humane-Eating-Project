package org.hep.afa.takeaction;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.hep.afa.R;

import java.util.ArrayList;
import java.util.List;

import static org.hep.afa.takeaction.InviteFragment.DISPLAY_NAME_INDEX;
import static org.hep.afa.takeaction.InviteFragment.EMAIL_INDEX;

/**
 * Created by heather on 10/31/16.
 */

public class TakeActionContactsAdapter extends CursorAdapter implements View.OnClickListener {

    private List<Integer> selectedContacts = new ArrayList<>();

    private static class ViewHolder {
        TextView contactName;
        CheckBox inviteCheckbox;
        CheckedTextView checkedTextView;
    }

    public TakeActionContactsAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    public List<String> getSelectedContacts() {
        List<String> emails = new ArrayList<>();
        Cursor cursor = getCursor();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (selectedContacts.contains(cursor.getPosition())) {
                emails.add(cursor.getString(EMAIL_INDEX));
            }
        }
        return emails;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.list_item_takeaction_invite, null);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.contactName = (TextView) convertView.findViewById(R.id.invite_name);
        viewHolder.inviteCheckbox = (CheckBox) convertView.findViewById(R.id.invite_checkbox);
        viewHolder.checkedTextView = (CheckedTextView) convertView.findViewById(R.id.invite_checked_text);

        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.checkedTextView.setText(cursor.getString(DISPLAY_NAME_INDEX));
        viewHolder.checkedTextView.setTag(cursor.getPosition());
        viewHolder.checkedTextView.setOnClickListener(this);
        if (selectedContacts.contains(cursor.getPosition())) {
            viewHolder.checkedTextView.setChecked(true);
        }
        else {
            viewHolder.checkedTextView.setChecked(false);
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CheckedTextView) {
            CheckedTextView checkedTextView = (CheckedTextView) view;
            Integer position = (Integer) checkedTextView.getTag();
            checkedTextView.toggle();
            if (checkedTextView.isChecked()) {
                selectedContacts.add(position);
            }
            else {
                selectedContacts.remove(position);
            }
        }
    }
}
