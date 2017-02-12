package com.delricco.vince.psuwrestlingmaterial;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity implements
        ContentFragment.OnSectionSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private NewsAdapter mNewsAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mNewsAdapter = new NewsAdapter(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSectionSelected(int section, View view) {
        final ContentFragment contentFragment = (ContentFragment) mSectionsPagerAdapter.getItem(
                section);

        switch (section) {
            case 1:
                ListView listView = (ListView) view.findViewById(R.id.content_list);
                listView.setAdapter(mNewsAdapter);
                listView.setOnItemClickListener(mNewsAdapter);
                showNews(view);
                break;
            default:
                break;
        }
    }

    private void showNews(final View view) {
        String url = "http://www.pennstatewrestlingclub.org/content/news.php";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document html = Jsoup.parse(response);

                        Elements tables = html.select("table");

                        Element newsTable = null;

                        for (Element table : tables) {
                            if (table.select("th") != null) {
                                newsTable = table;
                            }
                        }

                        if (newsTable != null) {
                            Elements newsTableRows = newsTable.select("tr");
                            for (Element row : newsTableRows) {
                                String title;
                                String date;
                                int id;

                                if (row.select("a").size() > 0) {
                                    Element a = row.select("a").get(0);

                                    title = a.ownText();

                                    String link = a.attr("href");
                                    id = Integer.parseInt(
                                            link.substring(link.indexOf("=") + 1, link.length()));

                                    date = row.select("td").get(1).ownText();

                                    mNewsAdapter.addItem(new News(title, date, id));
                                }
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mNewsAdapter.addItem(new News("Error!", error.toString(), 0));
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ContentFragment
            return ContentFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "News";
                case 1:
                    return "Schedule / Results";
                case 2:
                    return "Rankings";
            }
            return null;
        }
    }
}
