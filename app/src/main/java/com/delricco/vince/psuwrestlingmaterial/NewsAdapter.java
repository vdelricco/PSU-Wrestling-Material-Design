package com.delricco.vince.psuwrestlingmaterial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private ArrayList<News> newsList = new ArrayList<>();
    private final LayoutInflater inflater;
    private Context context;
    private static final String PSUWC_NEWS_URL =
            "http://www.pennstatewrestlingclub.org/content/read_news.php?id=";

    NewsAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new FinestWebView.Builder(context).show(PSUWC_NEWS_URL + newsList.get(position).getId());
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.news_item, null);
            holder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            holder.newsDate = (TextView) convertView.findViewById(R.id.news_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.newsTitle.setText(newsList.get(position).getTitle());
        holder.newsDate.setText(newsList.get(position).getDate());
        return convertView;
    }

    private static class ViewHolder {
        private TextView newsTitle;
        private TextView newsDate;
    }

    public void addItem(final News item) {
        newsList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }
}
