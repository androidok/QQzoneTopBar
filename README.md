># <font color = "#ff3456">**Android自定义ListView实现QQ空间顶部效果**</font> #
转载请注明出处：[http://blog.csdn.net/qq_23179075/article/details/53558062](http://blog.csdn.net/qq_23179075/article/details/53558062)
  
  

>## (一) 效果图: ##
>### QQ空间原图: ###
>![](http://i.imgur.com/mTPrByT.gif)


>## (二)布局分析 ##
>![](http://i.imgur.com/r79LUBT.png)
>#### <font color = "#ff3456">1、首先先分析一下上图的View组成部分,可以很明显的看出View是由: Listview+headView和TopBar两部分组成</font> ####


>#### <font color = "#ff3456">2、然后是上面的TopBar的渐变效果,我们的重点就是在这个渐变效果上。可以一眼就看出TopBar的渐变是根据ListView的滑动来改变TopBar的透明度,其实要实现这种效果只要监听一下ListView的滑动事件就能搞定</font>。 ####


>## (三) 创建项目正式撸代码: ##
>#### 创建QqZoneTopBarListView类继承Listview ####


```java
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
```

>#### 注释写的很清楚就不做过多解释了,上面需要用到一个headView的布局文件,虽然简单还是贴上来吧: ####
>#### head_layout.xml ####


```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="250dp"
        android:src="@mipmap/head"
        android:scaleType="centerCrop"/>
</LinearLayout>
```

>#### 主布局Xml文件 ####

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="zhengliang.com.qqzonetopbar.MainActivity">

    <zhengliang.com.qqzonetopbar.QqZoneTopBarListView
        android:id="@+id/qq_list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true">

    </zhengliang.com.qqzonetopbar.QqZoneTopBarListView>

    <!--topBar-->
    <include layout="@layout/top_bar" />
</RelativeLayout>

```

>#### 主布局中就是一个我们自定义的ListView和一个TobBar,TopBar可以自己自定义也可以直接使用Toolbar或者ActionBar都行。自行决定TopBar布局,为了简化这里的代码,我就不用上代码。 ####

>#### 布局java代码: ####
>#### MainActivity.java ####

```java
public class MainActivity extends AppCompatActivity {

    private LinearLayout TopBar;
    private QqZoneTopBarListView qqZoneTopBarListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 让状态了成半透明状态
         */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        /**
         * 为了达到效果将状态栏设置沉浸式状态栏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        TopBar = (LinearLayout) findViewById(R.id.TopBar);
        qqZoneTopBarListView = (QqZoneTopBarListView) findViewById(R.id.qq_list_view);
        qqZoneTopBarListView.setTopBar(TopBar);
    }
}

```

>#### 使用跟ListView一样,该怎么使用就怎么使用,不比多讲... ####


>### 看看最后的效果的图 ###
>![](http://i.imgur.com/9VXC7Bj.gif)

>博客主页:[http://blog.csdn.net/qq_23179075](http://blog.csdn.net/qq_23179075)

>### 印象丶亮仔 ###
