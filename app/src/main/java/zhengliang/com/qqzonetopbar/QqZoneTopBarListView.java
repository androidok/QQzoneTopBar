package zhengliang.com.qqzonetopbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 时 间: 2016/12/10 0010
 * 作 者: 郑亮
 * Q  Q : 1023007219
 */
public class QqZoneTopBarListView extends ListView {
    private View headView;
    private View TopBar;

    public QqZoneTopBarListView(Context context) {
        super(context);
        init(context);

    }

    public QqZoneTopBarListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public QqZoneTopBarListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        //获取headView的布局文件
        headView = LayoutInflater.from(context).inflate(R.layout.head_layout,null);
        //添加headView
        this.addHeaderView(headView);

        //设置监听滑动监听
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //获得headView的高度
                int headHeight = headView.getHeight();
                //获得headView的到顶部的距离
                int headTopSize = headView.getTop();
                //向上滑动的时候可能为负数,所以得到它的绝对值
                headTopSize = Math.abs(headTopSize);

                //当TopBar不为空时才进行下面的操作
                if (TopBar!=null){
                    //得到TopBar中的add图标
                    ImageView ivAdd = (ImageView) TopBar.findViewById(R.id.iv_add);

                    //根据headView的高度和到顶部的距离来计算当前TopBar的alpha的值
                    int alpha = headTopSize*255/headHeight;

                    if (alpha>244){
                        ivAdd.setImageResource(R.mipmap.ic_add_normal);
                    }else {
                        ivAdd.setImageResource(R.mipmap.ic_add);
                    }

                    TopBar.getBackground().setAlpha(alpha);
                }

            }
        });

        //设置数据适配器,也可以在Activity中设置, 这里为了方便就在这里写了
        setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                TextView textView = new TextView(context);
                textView.setText("动态"+i);

                return textView ;
            }
        });

    }

    //设置顶部状态栏
    public void setTopBar(View topBar) {
        TopBar = topBar;
    }


}
