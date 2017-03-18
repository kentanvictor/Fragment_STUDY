# Fragment_Study
## 注意：
新建的LeftFragment類需要繼承Fragment，值得注意的是，這裡會有提示導入兩個不同的Fragment包，其中一個是系統內置的android.app.fragment
一個是support-v4庫中的android.support.v4.app.Fragment。
### 這裡強烈建議使用support-v4庫中的Fragment，因為這樣才能使得碎片功能在所有的Android系統中保持一致性。
比如說，Fragment中嵌套使用Fragment,這個功能是在Android 4.2系統中才開始支持的，如果使用系統內置的Fragment
那麼在Android4.2系統之前的設備運行程序就會崩潰
詳情連接移步至

[LeftFragment.java](https://github.com/kentanvictor/Fragment_Study/blob/master/app/src/main/java/com/example/dell/fragment/LeftFragment.java)

### 動態添加碎片
詳情見
[AnotherRightFragment.java](https://github.com/kentanvictor/Fragment_Study/blob/master/app/src/main/java/com/example/dell/fragment/AnotherRightFragment.java)

#### 這個AnotherRightFragment的佈局文件的代碼與right_frament的佈局文件的代碼基本相同，只是將右側的碎片替換成了一個FrameLayout
#### 在MainActivity中的代碼，給左側碎片中的按鈕註冊了一個點擊事件，然後調用replaceFragment()方法動態添加了RightFragment這個碎片。當點擊左側碎片中的按鈕時，又會調用replaceFragment()方法將右側碎片替換成AnotherRightFragment。結合replaceFragment()方法中的代碼可以看出，動態添加碎片主要分為5步
* 1)创建待添加的碎片實例

* 2)獲取FragmentManager，在活動中可以直接通過調用getSupportFragmentManager()方法得到

* 3)開啟一個事務，通過調用commit()方法完成

* 4)向容器內添加或者替換碎片，一般使用replace()方法實現，需要傳入容器的id和待添加的碎片實例

* 5)提交事務，調用commit()方法來完成

### 在碎片中模擬返回棧
* 很簡單，在FragmentTransaction中提供了一個addToBackStack()方法，可以用於將一個事務添加到返回棧中，修改MainActivity中的代碼：
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener()
{
  ……
  private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.right_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
```
#### 這裡我們在事務提交之前調用了的FragmentTransaction的addToBackStack()方法，它可以接收一個名字用於描述返回棧的狀態，一般傳入null即可
### 在碎片和活動中進行通信
* 為了方便碎片和活動之間進行通信，FragmentManager提供了一個類似于findViewById()的方法，專門用於從佈局文件中獲取碎片的實例
```java
RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.right_layout);
```
#### 調用FragmentManager的findFragmentById()方法，可以在活動中得到相應碎片的實例，然後就能輕鬆地調用碎片裡面的方法了

### 在碎片中調用活動的方法
* 每個碎片都可以通過調用getActivity()方法來得到和當前碎片相關聯的活動實例
 * 代碼如下：
```java
MainActivity activity = (MainActivity)getActivity();
```
#### 有了活動的實例之後就能在碎片中調用方法了
#### 另外，當碎片中需要使用Context對象時，也可以使用getActivity()方法，因為獲取到的活動本身就是一個Context對象

### 碎片與碎片之間的通信
* 思路
 * 第一：在一個碎片中可以得到與它相關聯的活動
  * 第二：通過這個活動去獲取另外一個碎片的實例
* 如此也就實現了兩個碎片之間的通信的功能
# 碎片的生命週期
![碎片生命週期](http://i1.piimg.com/567571/d64e6a7daa36eb03.png)
## 碎片的狀態和回調
* 每個活動的聲明週期內有4種狀態
* 類似地，在碎片的生命週期內也有這幾種狀態，但是細節方面會有一些小的差別
 * 運行狀態
 當碎片是可見的，並且它所關聯的活動正處於運行狀態的時候，該碎片也處於運行狀態。
  * 暫停狀態
   * 當一個活動進入暫停狀態時(由於另一個未佔滿屏幕的活動被添加到棧頂)，與它相關聯的可見碎片就會進入到暫停狀態。
   * 停止狀態
    * 當一個活動進入停止狀態的時候，與它相關聯的碎片就會進入到停止狀態，或者通過調用FragmentTransaction的remove()，replace()方法將碎片從活動移除。但是，如果在事務提交之前調用addToBackStack()方法，這時的碎片也會進入到停止狀態。總的來說，進入到停止狀態的碎片對用戶來說是完全不可見的，有可能會被系統回收。
    * 銷毀狀態
     * 銷毀狀態總是依附于活動而存在的，因此當活動被銷毀的時候，與它相關的碎片就會進入到銷毀狀態。或者通過調用FragmentTransaction的remove()、replace()方法將碎片從活動中移除，但在事務提交之前并沒有調用addToBackStack()方法，這時的碎片也會進入到銷毀狀態。
* 結合之前的活動狀態，Fragment類中也提供了一系列的回調方法，以覆蓋碎片生命週期的每個環節。其中，活動中有回調方法，碎片中幾乎都有，不過碎片還提供了一些附加的回調方法，那我們的重點就是看一下這幾種回調方式：
 * onAttach()。*當碎片和活動建立關聯得到時候調用*
 * onCreateView()。*為碎片創建視圖(加載佈局)時調用*
 * onActivityCreated()。*當與碎片關聯的視圖被移除的時候調用*
 * onDetach()。*當碎片和活動解除關聯的時候調用*
### 那麼我們就體驗一下碎片的生命週期吧
* 源代碼如下所示：
```java
package com.example.dell.fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
  Created by DELL on 2017/3/13.
 */
public class RightFragment extends Fragment {
    public static final String TAG = "RightFragment";
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.right_layout);
        MainActivity activity = (MainActivity) getActivity();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG,"ONSTART");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG,"onResume");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG,"onPause");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG,"onStop");
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView");
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.d(TAG,"onDetach");
    }
}
```
#### 我們在RightFragment中的每一個回調方法中都加入了打印日誌的代碼，然後運行程序，在logcat中可以看到打印信息：
![重寫回調方法](http://p1.bqimg.com/567571/c0f988cbdf531fa2.png)

>**注：**由於AnotherRightFragment替換了RightFragment，此時的RightFragment進入了停止狀態，因此onPause()、onStop()和onDestroyView()方法會得到執行。當然如果在替換的時候沒有調用addToBackStack()方法，此時的RightFragment就會進入銷毀狀態，onDestroy()和onDetach()方法會得到執行。**

## 動態加載佈局的技巧
* 雖然動態添加碎片的功能很強大，可以解決很多實際開發中的問題，但是它畢竟只是在一個佈局文件中進行一些添加和替換操作。如果程序能夠根據設備的分辨率或屏幕大小在運行時來決定加載哪個佈局，那我們可發揮的空間就更多了。
### 使用限定符
* 現如今很多平板都採用雙頁模式(**即為程序會在作左側的面板上顯示一個包含自相的列表，在右側的面板顯示內容**)，因為平板的屏幕足夠大，所以完全可以顯示兩頁的內容，但手機的屏幕一次只能顯示一頁的內容，因此兩個頁面需要分開顯示。
#### 如何才能判斷程序應該是使用雙頁模式還是單頁模式呢？
* 這就需要藉助限定符了(Qualifiiers)來實現了。
* 代碼如下所示：

```xml
    <fragment
        android:id="@+id/left_fragment"
        android:name="com.example.dell.fragment.LeftFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1" />

    <fragment
        android:id="@+id/right_fragment"
        android:name="com.example.dell.fragment.RightFragment"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="3" />

    <!--<FrameLayout
        android:id="@+id/right_layout"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1">
    </FrameLayout>-->
</LinearLayout>

```
>**從上面的代碼可以看到：**layout/activity_main佈局中只包含了一個碎片，即單頁模式，而layout-large/activity_main佈局包含了兩個碎片，即為雙頁模式。其中**large**就是一個**限定符。**那些屏幕被認為是large的設備就會自動加載layout-large文件夾下的佈局，而小屏幕設備則還是會加載layout文件夾下的佈局---------------------------------------------------------------------------------**
#### 注：運行前需要將MainActivity中replaceFragment()方法裡面的代碼注釋掉，並且在平板模擬器上重新運行程序
